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
    ModelServerClient,
    ModelServerCommandUtil,
    ModelServerReferenceDescription,
    ModelServerSubscriptionService
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

@injectable()
export class CoffeeTreeEditorWidget extends NavigatableTreeEditorWidget {
    private delayedRefresh = false;
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
            const command = modelServerMessage.data;
            // the #/ marks the beginning of the actual path, but we also want the first slash removed so +3
            const ownerPropIndexPath = command.owner.$ref
                .substring(command.owner.$ref.indexOf('#/') + 3)
                .split('/')
                .filter(v => v.length !== 0)
                .map(path => {
                    const indexSplitPos = path.indexOf('.');
                    // each property starts with an @ so we ignore it
                    return {
                        property: path.substring(1, indexSplitPos),
                        index: path.substring(indexSplitPos + 1)
                    };
                });
            let ownerNode;
            if (ownerPropIndexPath.length !== 0) {
                ownerNode = this.treeWidget.findNode(ownerPropIndexPath);
            } else {
                // TODO should be done in findNode
                ownerNode = (this.treeWidget.model.root as TreeEditor.RootNode)
                    .children[0];
            }
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
            switch (command.type) {
                case 'add': {
                    if (!objectToModify[command.feature]) {
                        objectToModify[command.feature] = [];
                    }
                    objectToModify[command.feature].push(...command.objectsToAdd);
                    this.treeWidget.addChildren(
                        ownerNode,
                        command.objectsToAdd,
                        command.feature
                    );
                    if (!this.isVisible) {
                        this.delayedRefresh = true;
                    }
                    break;
                }
                case 'remove': {
                    command.indices.forEach(i =>
                        objectToModify[command.feature].splice(i, 1)
                    );
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
        this.modelServerApi.get(this.getModelIDToRequest()).then(response => {
            if (response.statusCode === 200) {
                if (isEqual(this.instanceData, response.body)) {
                    return;
                }
                this.instanceData = response.body;
                this.treeWidget
                    .setData({ error: false, data: this.instanceData })
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

    public save(): void {
        this.logger.info('Save data to server');
        this.modelServerApi.save(this.getModelIDToRequest());
    }

    private getEClassFromKey(key: string): string {
        return CoffeeModel.Type[key[0].toUpperCase() + key.slice(1)];
    }

    protected deleteNode(node: Readonly<TreeEditor.Node>): void {
        const removeCommand = ModelServerCommandUtil.createRemoveCommand(
            this.getNodeDescription(node.parent as TreeEditor.Node),
            node.jsonforms.property,
            node.jsonforms.index ? [Number(node.jsonforms.index)] : []
        );
        this.modelServerApi.edit(this.getModelIDToRequest(), removeCommand);
    }
    protected addNode({ node, type, property }: AddCommandProperty): void {
        const addCommand = ModelServerCommandUtil.createAddCommand(
            this.getNodeDescription(node),
            property,
            [{ eClass: type }]
        );
        this.modelServerApi.edit(this.getModelIDToRequest(), addCommand);
    }

    dispose(): void {
        this.modelServerApi.unsubscribe(this.getModelIDToRequest());
        super.dispose();
    }

    protected handleFormUpdate(data: any, node: TreeEditor.Node): void {
        const modelServerNode = this.getNodeDescription(node);
        Object.keys(data)
            .filter(key => key !== 'eClass')
            .forEach(key => {
                if (
                    data[key] instanceof Object &&
                    !isEqual(node.jsonforms.data[key], data[key])
                ) {
                    const eClass = data[key].eClass || this.getEClassFromKey(key);
                    const setCommand = ModelServerCommandUtil.createSetCommand(
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
                    const setCommand = ModelServerCommandUtil.createSetCommand(
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
        const ownerRef = `${this.workspaceService.workspace.uri}/${this.getModelIDToRequest()}#/${refToNode}`;
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
