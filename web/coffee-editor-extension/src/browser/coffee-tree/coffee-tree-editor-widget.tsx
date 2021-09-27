/*!
 * Copyright (c) 2019-2020 EclipseSource and others.
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
    ModelServerClient,
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
import { WorkspaceService } from '@theia/workspace/lib/browser/workspace-service';
import { inject, injectable } from 'inversify';
import { clone, isEqual } from 'lodash';

import { CoffeeModel } from './coffee-model';
import {
    AddAutomatedTaskCommand,
    AddDecisionNodeCommand,
    AddFlowCommand,
    AddManualTaskCommand,
    AddMergeNodeCommand,
    AddWeightedFlowCommand,
    ID_PROP,
    SetTaskNameCommand
} from './model-server';

interface PathSegment { property: string; index?: string }

@injectable()
export class CoffeeTreeEditorWidget extends NavigatableTreeEditorWidget {
    private delayedRefresh = false;
    private idToPath: Map<string, PathSegment[]> = new Map<string, PathSegment[]>();

    constructor(
        @inject(MasterTreeWidget)
        readonly treeWidget: MasterTreeWidget,
        @inject(DetailFormWidget)
        readonly formWidget: DetailFormWidget,
        @inject(WorkspaceService)
        readonly workspaceService: WorkspaceService,
        @inject(ILogger) readonly logger: ILogger,
        @inject(NavigatableTreeEditorOptions)
        protected readonly options: NavigatableTreeEditorOptions,
        @inject(ModelServerClient)
        private readonly modelServerApi: ModelServerClient,
        @inject(ModelServerSubscriptionService)
        private readonly subscriptionService: ModelServerSubscriptionService
    ) {
        super(
            treeWidget,
            formWidget,
            workspaceService,
            logger,
            CoffeeTreeEditorConstants.WIDGET_ID,
            options
        );
        this.subscriptionService.onDirtyStateListener(modelServerMessage => {
            this.dirty = modelServerMessage.data as boolean;
            this.onDirtyChangedEmitter.fire();
        });
        this.subscriptionService.onFullUpdateListener(modelServerMessage => {
            this.instanceData = modelServerMessage.data;

            this.treeWidget
                .setData({ error: false, data: this.instanceData })
                .then(() => this.treeWidget.select(this.getOldSelectedPath()));

            if (!this.isVisible) {
                this.delayedRefresh = true;
            }
        });
        this.subscriptionService.onIncrementalUpdateListener(modelServerMessage => {
            const command = modelServerMessage.data.source;

            // handle custom add commands without explicit ref
            switch (command.type) {
                case AddAutomatedTaskCommand.TYPE: {
                    this.handleAddWorkflowChildCommand(CoffeeModel.Type.AutomaticTask, 'nodes', { name: 'New Task'});
                    return;
                }
                case AddManualTaskCommand.TYPE: {
                    this.handleAddWorkflowChildCommand(CoffeeModel.Type.ManualTask, 'nodes', { name: 'New Task'});
                    return;
                }
                case AddDecisionNodeCommand.TYPE: {
                    this.handleAddWorkflowChildCommand(CoffeeModel.Type.Decision, 'nodes');
                    return;
                }
                case AddMergeNodeCommand.TYPE: {
                    this.handleAddWorkflowChildCommand(CoffeeModel.Type.Merge, 'nodes');
                    return;
                }
                case AddFlowCommand.TYPE: {
                    this.handleAddWorkflowChildCommand(CoffeeModel.Type.Flow, 'flows');
                    return;
                }
                case AddWeightedFlowCommand.TYPE: {
                    this.handleAddWorkflowChildCommand(CoffeeModel.Type.WeightedFlow, 'flows');
                    return;
                }
                case SetTaskNameCommand.TYPE: {
                    const taskId = command.properties.semanticUriFragment;
                    const newName = command.properties.newName;
                    const taskPropIndexPath = this.idToPath.get(taskId);
                    const taskNode = this.treeWidget.findNode(taskPropIndexPath);
                    const data = clone(taskNode.jsonforms.data);
                    data.name = newName;
                    this.treeWidget.updateDataForNode(taskNode, data);
                    return;
                }
            }

            // id starts after the #
            const ownerId = (command.owner.$ref as string).substring((command.owner.$ref.indexOf('#') + 1));
            const ownerPropIndexPath = this.idToPath.get(ownerId);
            const ownerNode= this.treeWidget.findNode(ownerPropIndexPath);
            const objectToModify =
                ownerPropIndexPath.length === 0
                    ? this.instanceData
                    : ownerPropIndexPath.reduce(
                        (data, path) =>
                            path.index === undefined
                                ? data[path.property]
                                : data[path.property][path.index],
                        this.instanceData
                    );

            // TODO handle custom remove compound commands
            switch (command.type) {
                case 'add': {
                    if (!objectToModify[command.feature]) {
                        objectToModify[command.feature] = [];
                    }
                    const objectsToAdd: any[] = command.objectValues;
                    // Add objects and get id for each one
                    objectsToAdd.forEach(toAdd => {
                        objectToModify[command.feature].push(toAdd);
                        const addedIndex = objectToModify[command.feature].length - 1;
                        const toAddPath: PathSegment[] = [...ownerPropIndexPath, { property: command.feature, index: `${addedIndex}` }];
                        this.updateElementId(toAddPath, toAdd);
                    });
                    this.treeWidget.addChildren(
                        ownerNode,
                        objectsToAdd,
                        command.feature
                    );
                    if (!this.isVisible) {
                        this.delayedRefresh = true;
                    }
                    break;
                }
                case 'remove': {
                    command.indices.forEach(i => {
                        const removed = objectToModify[command.feature].splice(i, 1);
                        this.idToPath.delete(removed[ID_PROP]);
                    });
                    this.treeWidget.removeChildren(
                        ownerNode,
                        command.indices,
                        command.feature
                    );
                    if (!this.isVisible) {
                        this.delayedRefresh = true;
                    }
                    break;
                }
                case 'set': {
                    // maybe we can directly manipulate the data?
                    const data = clone(ownerNode.jsonforms.data);
                    // FIXME handle array changes
                    if (command.dataValues) {
                        data[command.feature] = command.dataValues[0];
                    } else {
                        data[command.feature] = command.objectsToAdd[0];
                    }
                    this.treeWidget.updateDataForNode(ownerNode, data);
                    if (!this.isVisible) {
                        this.delayedRefresh = true;
                    }
                    if (this.selectedNode === ownerNode) {
                        this.formWidget.setSelection(this.selectedNode);
                    }
                    break;
                }
                default: { /** */ }
            }
        });
        this.modelServerApi.get(this.getModelIDToRequest(), 'json').then(response => {
            if (response.statusCode === 200) {
                if (isEqual(this.instanceData, response.body)) {
                    return;
                }
                this.instanceData = response.body;
                this.treeWidget
                    .setData({ error: false, data: this.instanceData })
                    .then(() => this.initIdMap())
                    .then(() => this.treeWidget.selectFirst());
                return;
            }
            this.treeWidget.setData({ error: !!response.statusMessage });
            this.renderError(
                "An error occurred when requesting '" +
                this.getModelIDToRequest() +
                "' - Status " +
                response.statusCode +
                ' ' +
                response.statusMessage
            );
            this.instanceData = undefined;
            return;
        });
        this.modelServerApi.subscribe(this.getModelIDToRequest());
        // see https://developer.mozilla.org/en-US/docs/Web/API/WindowEventHandlers/onbeforeunload
        window.onbeforeunload = () => this.dispose();
    }
    private getOldSelectedPath(): string[] {
        const paths: string[] = [];
        if (!this.selectedNode) {
            return paths;
        }
        paths.push(this.selectedNode.name);
        let parent = this.selectedNode.parent;
        while (parent) {
            paths.push(parent.name);
            parent = parent.parent;
        }
        paths.splice(paths.length - 1, 1);
        return paths;
    }

    private toXmiPath(path: PathSegment[]): string {
        if (path.length === 0) {
            return '//';
        }
        return path.reduce((xmiPath, segment) => {
            let result = xmiPath + `/@${segment.property}`;
            if (segment.index) {
                result += `.${segment.index}`;
            }
            return result;
        }, '/');
    }

    /** Updates a data objects id from the model server */
    private updateElementId(path: PathSegment[], data: any): void {
        const xmiPath = this.toXmiPath(path);
        this.modelServerApi.getElementById(this.getModelIDToRequest(), xmiPath, 'json').then(response => {
            const newElementId = response.body[ID_PROP];
            data[ID_PROP] = newElementId;
            this.idToPath.set(newElementId, path);
        }, err => console.error('Could not update element id', xmiPath, err));
    }

    /** Initializes the map from element ids to their path segments */
    private initIdMap(): void {
        this.idToPath.clear();
        const machineNode = (this.treeWidget.model.root as TreeEditor.RootNode).children[0] as TreeEditor.Node;
        this.idToPath.set(machineNode.jsonforms.data[ID_PROP], []);
        const recursion = (node: TreeEditor.Node, path: PathSegment[]): void => {
            const nodePath = [...path, { property: node.jsonforms.property, index: node.jsonforms.index }];
            this.idToPath.set(node.jsonforms.data[ID_PROP], nodePath);
            node.children.forEach(child => recursion(child as TreeEditor.Node, nodePath));
        };
        machineNode.children.forEach(node => recursion(node as TreeEditor.Node, []));
    }

    private handleAddWorkflowChildCommand(eClass: string, property: string, additionalProps = {}): void {
        const machineNode = (this.treeWidget.model.root as TreeEditor.RootNode)
            .children[0] as TreeEditor.Node;
        // TODO Always adds to the first workflow because we currently have now way of knowing which workflow is affected.
        const workflowNode = machineNode.children.find(n =>
            (n as TreeEditor.Node).jsonforms.type === CoffeeModel.Type.Workflow
        ) as TreeEditor.Node;

        const data = { eClass, ...additionalProps };
        if (!workflowNode.jsonforms.data[property]) {
            workflowNode.jsonforms.data[property] = [];
        }
        workflowNode.jsonforms.data[property].push(data);
        this.treeWidget.addChildren(workflowNode, [data], property);

        // Get id of the new element and add it to the data and the idToPath mapping
        // new node was added at the end
        const newIndex = `${workflowNode.jsonforms.data[property].length - 1}`;
        const path = [{ property: 'workflows', index: '0' }, { property, index: newIndex }];
        this.updateElementId(path, data);
    }

    public save(): void {
        this.logger.info('Save data to server');
        this.modelServerApi.save(this.getModelIDToRequest());
    }

    private getEClassFromKey(key: string): string {
        return CoffeeModel.Type[key[0].toUpperCase() + key.slice(1)];
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
            addCommand = new AddCommand(
                this.getNodeDescription(node),
                property,
                [{ eClass: type }]
            );
        }
        this.modelServerApi.edit(this.getModelIDToRequest(), addCommand);
    }

    dispose(): void {
        this.modelServerApi.unsubscribe(this.getModelIDToRequest());
        super.dispose();
    }

    protected async handleFormUpdate(data: any, node: TreeEditor.Node): Promise<void> {
        const modelServerNode = this.getNodeDescription(node);
        Object.keys(data)
            .filter(key => key !== 'eClass' && key !== ID_PROP)
            .forEach(key => {
                if (
                    data[key] instanceof Object &&
                    !isEqual(node.jsonforms.data[key], data[key])
                ) {
                    const eClass = data[key].eClass || this.getEClassFromKey(key);
                    const setCommand = new SetCommand(
                        modelServerNode,
                        key,
                        []
                    );
                    const toAdd = clone(data[key]);
                    toAdd['eClass'] = eClass;
                    setCommand.objectsToAdd = [toAdd];
                    const ref = { eClass, $ref: '//@objectsToAdd.0' };
                    setCommand.objectValues = [ref];
                    this.modelServerApi.edit(this.getModelIDToRequest(), setCommand);
                } else {
                    const setCommand = new SetCommand(
                        modelServerNode,
                        key,
                        [data[key]]
                    );
                    this.modelServerApi.edit(this.getModelIDToRequest(), setCommand);
                }
            });
    }

    /**
     * Create the corresponding ModelServerReferenceDescription for the given tree node.
     * @param node The tree node to convert
     */
    protected getNodeDescription(
        node: TreeEditor.Node
    ): ModelServerReferenceDescription {
        const getRefSegment = (n: TreeEditor.Node): string =>
            n.jsonforms.property
                ? `@${n.jsonforms.property}` +
                (n.jsonforms.index ? `.${n.jsonforms.index}` : '')
                : '';
        let refToNode = '';
        let toCheck: TreeNode = node;
        while (toCheck && TreeEditor.Node.is(toCheck)) {
            const parentRefSeg = getRefSegment(toCheck);
            refToNode =
                parentRefSeg === '' ? refToNode : '/' + parentRefSeg + refToNode;
            toCheck = toCheck.parent;
        }
        const ownerRef = `${this.workspaceService.workspace.resource}/${this.getModelIDToRequest()}#/${refToNode}`;
        return {
            eClass: node.jsonforms.type,
            $ref: ownerRef.replace('file:///', 'file:/')
        };
    }

    private getModelIDToRequest(): string {
        const rootUriLength = this.workspaceService
            .getWorkspaceRootUri(this.options.uri)
            .toString().length;
        return this.options.uri.toString().substring(rootUriLength + 1);
    }

    protected configureTitle(title: Title<Widget>): void {
        title.label = this.options.uri.path.base;
        title.caption = BaseTreeEditorWidget.WIDGET_LABEL;
        title.closable = true;
        title.iconClass = 'fa coffee-icon dark-purple';
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
