/*
 * Copyright (c) 2019-2022 EclipseSource and others.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0, or the MIT License which is
 * available at https://opensource.org/licenses/MIT.
 *
 * SPDX-License-Identifier: EPL-2.0 OR MIT
 */
import {
    AddCommand,
    CommandExecutionResult,
    CommandExecutionType,
    ModelServerClient,
    ModelServerCommand,
    ModelServerMessage,
    ModelServerReferenceDescription,
    ModelServerSubscriptionService,
    RemoveCommand,
    SetCommand
} from '@eclipse-emfcloud/modelserver-theia/lib/common';
import {
    AddCommandProperty,
    BaseTreeEditorWidget,
    DetailFormWidget,
    MasterTreeWidget,
    NavigatableTreeEditorOptions,
    NavigatableTreeEditorWidget,
    TreeEditor
} from '@eclipse-emfcloud/theia-tree-editor';
import { Title, TreeNode, Widget } from '@theia/core/lib/browser';
import { ILogger } from '@theia/core/lib/common';
import URI from '@theia/core/lib/common/uri';
import { WorkspaceService } from '@theia/workspace/lib/browser/workspace-service';
import { inject, injectable } from 'inversify';
import { get, isEqual, isObject, reduce } from 'lodash';

import { CoffeeModel } from './coffee-model';
import { AddAutomatedTaskCommand, AddDecisionNodeCommand, AddManualTaskCommand, AddMergeNodeCommand, ID_PROP } from './model-server';

interface PathSegment {
    property: string;
    index?: string;
}

@injectable()
export class CoffeeTreeEditorWidget extends NavigatableTreeEditorWidget {
    private delayedRefresh = false;
    private idToPath: Map<string, PathSegment[]> = new Map<string, PathSegment[]>();

    constructor(
        @inject(MasterTreeWidget) readonly treeWidget: MasterTreeWidget,
        @inject(DetailFormWidget) readonly formWidget: DetailFormWidget,
        @inject(WorkspaceService) readonly workspaceService: WorkspaceService,
        @inject(ILogger) readonly logger: ILogger,
        @inject(NavigatableTreeEditorOptions)
        protected readonly options: NavigatableTreeEditorOptions,
        @inject(ModelServerClient)
        private readonly modelServerApi: ModelServerClient,
        @inject(ModelServerSubscriptionService)
        private readonly subscriptionService: ModelServerSubscriptionService
    ) {
        super(treeWidget, formWidget, workspaceService, logger, CoffeeTreeEditorConstants.WIDGET_ID, options);

        this.subscriptionService.onDirtyStateListener(modelServerMessage => {
            this.dirty = modelServerMessage.data as boolean;
            this.onDirtyChangedEmitter.fire();
        });

        this.subscriptionService.onFullUpdateListener(modelServerMessage => {
            this.instanceData = undefined;
            this.instanceData = modelServerMessage.data;

            this.treeWidget
                .setData({ error: false, data: this.instanceData })
                .then(() => this.treeWidget.select(this.getOldSelectedPath()));

            if (!this.isVisible) {
                this.delayedRefresh = true;
            }
        });

        this.subscriptionService.onIncrementalUpdateListener((modelServerMessage: ModelServerMessage) => {
            if (CommandExecutionResult.is(modelServerMessage.data)) {
                this.updateViaCommand(modelServerMessage.data);
            }
        });

        this.loadModel();

        this.modelServerApi.subscribe(this.getModelIDToRequest());

        // see https://developer.mozilla.org/en-US/docs/Web/API/WindowEventHandlers/onbeforeunload
        window.onbeforeunload = () => this.dispose();
    }

    private loadModel(initialLoad = true): void {
        this.modelServerApi.get(this.getModelIDToRequest(), 'json').then(response => {
            if (response.statusCode === 200) {
                if (isEqual(this.instanceData, response.body)) {
                    return;
                }
                this.instanceData = undefined;
                this.instanceData = response.body;
                this.treeWidget
                    .setData({ error: false, data: this.instanceData })
                    .then(() => this.initIdMap())
                    .then(() => (initialLoad ? this.treeWidget.selectFirst() : this.treeWidget.select(this.getOldSelectedPath())));
                this.update();
                return;
            }
            this.treeWidget.setData({ error: !!response.statusMessage });
            this.renderError(
                `An error occurred when requesting '
                        ${this.getModelIDToRequest()}' - Status ${response.statusCode} ${response.statusMessage}`
            );
            this.instanceData = undefined;
            return;
        });
    }

    private getOldSelectedPath(): string[] {
        const paths: string[] = [];
        if (!this.selectedNode) {
            return paths;
        }
        paths.push(this.selectedNode.name);
        let parent = this.selectedNode.parent;
        while (parent) {
            parent.name && paths.push(parent.name);
            parent = parent.parent;
        }
        paths.splice(paths.length - 1, 1);
        return paths;
    }

    /** Initializes the map from element ids to their path segments */
    private initIdMap(): void {
        this.idToPath.clear();
        const machineNode = (this.treeWidget.model.root as TreeEditor.RootNode).children[0] as TreeEditor.Node;
        this.idToPath.set(machineNode.jsonforms.data[ID_PROP], []);
        const recursion = (node: TreeEditor.Node, path: PathSegment[]): void => {
            const nodePath = [
                ...path,
                {
                    property: node.jsonforms.property,
                    index: node.jsonforms.index
                }
            ];
            this.idToPath.set(node.jsonforms.data[ID_PROP], nodePath);
            node.children.forEach(child => recursion(child as TreeEditor.Node, nodePath));
        };
        machineNode.children.forEach(node => recursion(node as TreeEditor.Node, []));
    }

    protected async updateViaCommand(commandResult: CommandExecutionResult): Promise<void> {
        switch (commandResult.type) {
            case CommandExecutionType.EXECCUTE:
            case CommandExecutionType.UNDO:
            case CommandExecutionType.REDO: {
                if (commandResult.changeDescription.objectChanges && commandResult.changeDescription.objectChanges.length > 0) {
                    const changedObject = commandResult.changeDescription.objectChanges[0];
                    const relativeRefURI = new URI(this.getRelativeModelUri(changedObject.key.$ref.replace('file:', '')));
                    if (this.isCurrentModelUri(relativeRefURI)) {
                        await new Promise(f => setTimeout(f, 250));
                        this.loadModel(false);
                    }
                }
                this.update();
                break;
            }
        }
    }

    protected getRelativeModelUri(sourceUri: string): string {
        const workspaceUri = this.workspaceService.getWorkspaceRootUri(new URI(sourceUri));
        if (workspaceUri) {
            const workspaceString = workspaceUri.toString().replace('file://', '');
            const rootUriLength = workspaceString.length;
            return sourceUri.substring(rootUriLength + 1);
        }
        return '';
    }

    protected isCurrentModelUri(uri: URI): boolean {
        return uri.path.toString() === '/' + this.getModelIDToRequest();
    }

    public save(): void {
        this.logger.info('Save data to server');
        this.modelServerApi.save(this.getModelIDToRequest());
    }

    protected async deleteNode(node: Readonly<TreeEditor.Node>): Promise<void> {
        const removeCommand = new RemoveCommand(
            this.getNodeDescription(node.parent as TreeEditor.Node),
            node.jsonforms.property,
            node.jsonforms.index ? [Number(node.jsonforms.index)] : []
        );
        this.modelServerApi.edit(this.getModelIDToRequest(), removeCommand);
    }

    protected async addNode({ node, type, property }: AddCommandProperty): Promise<void> {
        let addCommand;
        if (type === CoffeeModel.Type.AutomaticTask) {
            addCommand = new AddAutomatedTaskCommand();
        } else if (type === CoffeeModel.Type.ManualTask) {
            addCommand = new AddManualTaskCommand();
        } else if (type === CoffeeModel.Type.Decision) {
            addCommand = new AddDecisionNodeCommand();
        } else if (type === CoffeeModel.Type.Merge) {
            addCommand = new AddMergeNodeCommand();
        } else {
            addCommand = new AddCommand(this.getNodeDescription(node), property, [{ eClass: type }]);
        }
        this.modelServerApi.edit(this.getModelIDToRequest(), addCommand);
    }

    dispose(): void {
        this.modelServerApi.unsubscribe(this.getModelIDToRequest());
        super.dispose();
    }

    protected async handleFormUpdate(jsonFormsData: any, node: TreeEditor.Node): Promise<void> {
        if (
            jsonFormsData[ID_PROP] === this.selectedNode.jsonforms.data[ID_PROP] &&
            !isEqual(jsonFormsData, this.selectedNode.jsonforms.data)
        ) {
            const changedFeatures = this.getObjectDiff(jsonFormsData, this.selectedNode.jsonforms.data);
            if (changedFeatures.length > 0) {
                const editCommand = this.createSetCommand(changedFeatures[0], jsonFormsData);
                this.modelServerApi.edit(this.getModelIDToRequest(), editCommand);
            } else {
                // TODO temporary workaround to add and remove ram objects from the ram array
                // it is not really stable yet, please re-evaluate if deepDiff does return the actual added/removed objects
                // removing objects is currently disabled via CSS, but the remove command is already working if the correct index can be determined
                const addedRamElement = this.deepDiff(this.selectedNode.jsonforms.data, jsonFormsData);
                if (addedRamElement['ram'] && addedRamElement['ram'].length > 0) {
                    const addRamCommand = this.createAddRamCommand(jsonFormsData);
                    this.modelServerApi.edit(this.getModelIDToRequest(), addRamCommand);
                } else {
                    const removedRamElement = this.deepDiff(jsonFormsData, this.selectedNode.jsonforms.data);
                    if (removedRamElement) {
                        // TODO ensure that ram object and not array is handed over to createRemoveRamCommand
                        const removeRamCommand = this.createRemoveRamCommand(jsonFormsData);
                        this.modelServerApi.edit(this.getModelIDToRequest(), removeRamCommand);
                    }
                }
            }
        }
    }

    protected deepDiff(o1: any, o2: any): any {
        return Object.keys(o2).reduce((diff, key) => {
            if (o1[key] === o2[key]) {
                return diff;
            }
            return {
                ...diff,
                [key]: o2[key]
            };
        }, {});
    }

    private getObjectDiff(object: Record<string, any>, base: Record<string, any>): string[] {
        function difference(obj1: any, obj2: any, path: string): any {
            obj1 = obj1 || {};
            obj2 = obj2 || {};

            return reduce<any, string[]>(
                obj1,
                (result, value, key) => {
                    const p = path ? path + '.' + key : key;
                    if (isObject(value)) {
                        const d = difference(value, obj2[key], p);
                        return d.length ? result.concat(d) : result;
                    }
                    return isEqual(value, obj2[key]) ? result : result.concat(p);
                },
                []
            );
        }
        return difference(object, base, '');
    }

    protected createSetCommand(changedFeature: string, jsonFormsData: any): ModelServerCommand {
        const nestedFeatures = changedFeature.split('.');
        if (nestedFeatures.length > 1) {
            // TODO check why change of dimension (width/height/length) values is not working properly
            const ownerFeatureName = nestedFeatures[0];
            const featureName = nestedFeatures[nestedFeatures.length - 1];
            const changedValue = get(jsonFormsData, nestedFeatures);
            const setCommand = new SetCommand(this.getOwner(jsonFormsData[ownerFeatureName], ownerFeatureName), featureName, [
                changedValue
            ]);
            return setCommand;
        } else {
            return new SetCommand(this.getOwner(jsonFormsData), changedFeature, [jsonFormsData[changedFeature]]);
        }
    }

    protected createAddRamCommand(jsonFormsData: any): ModelServerCommand {
        const addCommand = new AddCommand(this.getOwner(jsonFormsData), 'ram', []);
        const toAdd = { eClass: CoffeeModel.Type.RAM };
        addCommand.objectsToAdd = [toAdd];
        const ref = { $ref: '//@objectsToAdd.0', eClass: CoffeeModel.Type.RAM };
        addCommand.objectValues = [ref];
        addCommand.indices = [-1];
        return addCommand;
    }

    protected createRemoveRamCommand(jsonFormsData: any): ModelServerCommand {
        // TODO fix index
        return new RemoveCommand(this.getOwner(jsonFormsData), 'ram', [0]);
    }

    protected getOwner(jsonFormsData: any, ownerFeatureName?: string): ModelServerReferenceDescription {
        let eClass = jsonFormsData.eClass;
        if (!eClass && ownerFeatureName) {
            switch (ownerFeatureName) {
                case 'processor':
                    eClass = CoffeeModel.Type.Processor;
                    break;
                case 'dimension':
                    eClass = CoffeeModel.Type.Dimension;
                    break;
                case 'ram':
                    eClass = CoffeeModel.Type.RAM;
                    break;
                case 'display':
                    eClass = CoffeeModel.Type.Display;
                    break;
            }
        }
        return {
            $ref: this.getOwnerRef(jsonFormsData[ID_PROP]),
            eClass: eClass
        };
    }

    protected getOwnerRef(elementId: string): string {
        return `${this.workspaceService.workspace!.resource}/${this.getModelIDToRequest()}#${elementId}`.replace('file:///', 'file:/');
    }

    /**
     * Create the corresponding ModelServerReferenceDescription for the given tree node.
     * @param node The tree node to convert
     */
    protected getNodeDescription(node: TreeEditor.Node): ModelServerReferenceDescription {
        const getRefSegment = (n: TreeEditor.Node): string =>
            n.jsonforms.property ? `@${n.jsonforms.property}` + (n.jsonforms.index ? `.${n.jsonforms.index}` : '') : '';
        let refToNode = '';
        let toCheck: TreeNode | undefined = node;
        while (toCheck && TreeEditor.Node.is(toCheck)) {
            const parentRefSeg = getRefSegment(toCheck);
            refToNode = parentRefSeg === '' ? refToNode : '/' + parentRefSeg + refToNode;
            toCheck = toCheck.parent;
        }
        const ownerRef = `${this.workspaceService.workspace?.resource}/${this.getModelIDToRequest()}#/${refToNode}`;
        return {
            eClass: node.jsonforms.type,
            $ref: ownerRef.replace('file:///', 'file:/')
        };
    }

    private getModelIDToRequest(): string {
        const rootUriLength = this.workspaceService.getWorkspaceRootUri(this.options.uri)?.toString().length ?? 0;
        return this.options.uri.toString().substring(rootUriLength + 1);
    }

    protected configureTitle(title: Title<Widget>): void {
        title.label = this.options.uri.path.base;
        title.caption = BaseTreeEditorWidget.WIDGET_LABEL;
        title.closable = true;
        title.iconClass = 'codicon coffee-icon dark-purple';
    }

    show(): void {
        super.show();
        if (this.delayedRefresh) {
            this.delayedRefresh = false;
            this.treeWidget.model.refresh();
        }
    }
}
export namespace CoffeeTreeEditorConstants {
    export const WIDGET_ID = 'json-forms-tree-editor';
    export const EDITOR_ID = 'org.eclipse.emfcloud.coffee.editor';
}
