/*!
 * Copyright (C) 2019 EclipseSource and others.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * This Source Code may also be made available under the following Secondary
 * Licenses when the conditions for such availability set forth in the Eclipse
 * Public License v. 2.0 are satisfied: GNU General Public License, version 2
 * with the GNU Classpath Exception which is available at
 * https://www.gnu.org/software/classpath/license.html.
 *
 * SPDX-License-Identifier: EPL-2.0 OR GPL-2.0 WITH Classpath-exception-2.0
 */
import { ModelServerSubscriptionService } from '@modelserver/theia/lib/browser';
import { ModelServerClient, ModelServerCommand } from '@modelserver/theia/lib/common';
import { BaseWidget, Message, Navigatable, Saveable, SplitPanel, TreeNode, Widget } from '@theia/core/lib/browser';
import { Emitter, Event, ILogger } from '@theia/core/lib/common';
import URI from '@theia/core/lib/common/uri';
import { WorkspaceService } from '@theia/workspace/lib/browser/workspace-service';
import { inject, injectable } from 'inversify';
import { isEqual } from 'lodash';
import * as React from 'react';
import * as ReactDOM from 'react-dom';

import { JsonFormsTree } from '../json-forms-tree/json-forms-tree';
import { AddCommandProperty, JsonFormsTreeWidget } from '../json-forms-tree/json-forms-tree-widget';
import { JSONFormsWidget } from './json-forms-widget';

export const JsonFormsTreeEditorWidgetOptions = Symbol(
  'JsonFormsTreeEditorWidgetOptions'
);
export interface JsonFormsTreeEditorWidgetOptions {
  uri: URI;
}

@injectable()
export class JsonFormsTreeEditorWidget extends BaseWidget
  implements Navigatable, Saveable {
  public dirty: boolean = false;
  public autoSave: 'off';
  private splitPanel: SplitPanel;

  protected readonly onDirtyChangedEmitter = new Emitter<void>();
  get onDirtyChanged(): Event<void> {
    return this.onDirtyChangedEmitter.event;
  }

  public selectedNode: JsonFormsTree.Node;

  protected instanceData: any;

  constructor(
    @inject(JsonFormsTreeEditorWidgetOptions)
    protected readonly options: JsonFormsTreeEditorWidgetOptions,
    @inject(JsonFormsTreeWidget)
    private readonly treeWidget: JsonFormsTreeWidget,
    @inject(JSONFormsWidget)
    private readonly formWidget: JSONFormsWidget,
    @inject(ModelServerClient)
    protected readonly modelServerApi: ModelServerClient,
    @inject(WorkspaceService)
    protected readonly workspaceService: WorkspaceService,
    @inject(ILogger) private readonly logger: ILogger,
    @inject(ModelServerSubscriptionService)
    private readonly suscriptionService: ModelServerSubscriptionService
  ) {
    super();
    this.id = JsonFormsTreeEditorWidget.WIDGET_ID;
    this.title.label = options.uri.path.base;
    this.title.caption = JsonFormsTreeEditorWidget.WIDGET_LABEL;
    this.title.closable = true;
    this.title.iconClass = 'fa coffee-icon dark-purple';
    this.splitPanel = new SplitPanel();
    this.addClass('json-forms-tree-editor');
    this.splitPanel.addClass('json-forms-tree-editor-sash');
    this.treeWidget.addClass('json-forms-tree-editor-tree');
    this.formWidget.addClass('json-forms-tree-editor-forms');
    this.formWidget.onChange(data => {
      if (isEqual(this.selectedNode.jsonforms.data, data)) {
        return;
      }
      this.treeWidget.updateDataForNode(this.selectedNode, data);
      this.modelServerApi.update(this.getModelIDToRequest(), this.instanceData);
    });
    this.toDispose.push(
      this.treeWidget.onSelectionChange(ev => this.treeSelectionChanged(ev))
    );
    this.toDispose.push(
      this.treeWidget.onDelete(node => this.deleteNode(node))
    );
    this.toDispose.push(
      this.treeWidget.onAdd(addProp => this.addNode(addProp))
    );

    this.toDispose.push(this.onDirtyChangedEmitter);
    this.suscriptionService.onDirtyStateListener(dirtyState => {
      this.dirty = dirtyState;
      this.onDirtyChangedEmitter.fire();
    });
    this.suscriptionService.onFullUpdateListener(fullUpdate => {
      this.instanceData = fullUpdate;

      this.treeWidget
        .setData({ error: false, data: this.instanceData })
        .then(() => this.treeWidget.select(this.getOldSelectedPath()));
    });
    this.suscriptionService.onIncrementalUpdateListener(incrementalUpdate => {
      // TODO handle incremental
      this.modelServerApi.get(this.getModelIDToRequest()).then(response => {
        if (response.statusCode === 200) {
          if (isEqual(this.instanceData, response.body)) {
            return;
          }
          this.instanceData = response.body;
          this.treeWidget
            .setData({ error: false, data: this.instanceData })
            .then(() => this.treeWidget.select(this.getOldSelectedPath()));
          return;
        }
      });
      console.log(incrementalUpdate);
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
      this.treeWidget.setData({ error: response.statusMessage });
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
  public uri(): URI {
    return this.options.uri;
  }

  public save(): void {
    this.logger.info('Save data to server');
    this.modelServerApi.save(this.getModelIDToRequest());
  }

  protected onResize(_msg: any) {
    if (this.splitPanel) {
      this.splitPanel.update();
    }
  }

  getModelIDToRequest(): string {
    const rootUriLength = this.workspaceService
      .getWorkspaceRootUri(this.options.uri)
      .toString().length;
    return this.options.uri.toString().substring(rootUriLength + 1);
  }

  getResourceUri(): URI | undefined {
    return this.options.uri;
  }

  createMoveToUri(resourceUri: URI): URI | undefined {
    return this.options.uri && this.options.uri.withPath(resourceUri.path);
  }

  protected renderError(errorMessage: string): void {
    ReactDOM.render(
      <React.Fragment>{errorMessage}</React.Fragment>,
      this.formWidget.node
    );
  }

  protected treeSelectionChanged(
    selectedNodes: readonly Readonly<JsonFormsTree.Node>[]
  ) {
    if (selectedNodes.length === 0) {
      this.selectedNode = undefined;
    } else {
      this.selectedNode = selectedNodes[0];
      this.formWidget.setSelection(this.selectedNode);
    }
    this.update();
  }
  private getNodeDescription(node: JsonFormsTree.Node): { eClass: string, $ref: string } {
    const getRefSegment = (n: JsonFormsTree.Node) => n.jsonforms.property ? (`@${n.jsonforms.property}` + (n.jsonforms.index ? `.${n.jsonforms.index}` : '')) : '';
    let refToNode = '';
    let toCheck: TreeNode = node;
    while (toCheck && JsonFormsTree.Node.is(toCheck)) {
      const parentRefSeg = getRefSegment(toCheck);
      refToNode = parentRefSeg === '' ? refToNode : ('/' + parentRefSeg + refToNode);
      toCheck = toCheck.parent;
    }
    const ownerRef = `${this.workspaceService.workspace.uri}/${this.getModelIDToRequest()}#/${refToNode}`;
    return {
      eClass: node.jsonforms.type, $ref: ownerRef.replace('file:///', 'file:/')
    };
  }
  private deleteNode(node: Readonly<JsonFormsTree.Node>): void {
    const removeCommand: ModelServerCommand = {
      eClass: 'http://www.eclipsesource.com/schema/2019/modelserver/command#//Command',
      type: 'remove',
      owner: this.getNodeDescription(node.parent as JsonFormsTree.Node),
      feature: node.jsonforms.property,
      indices: node.jsonforms.index ? [Number(node.jsonforms.index)] : []
    };
    this.modelServerApi.edit(this.getModelIDToRequest(), removeCommand);
  }
  private addNode({ node, eClass, property }: AddCommandProperty): void {
    const addCommand: ModelServerCommand = {
      eClass: 'http://www.eclipsesource.com/schema/2019/modelserver/command#//Command',
      type: 'add',
      owner: this.getNodeDescription(node),
      feature: property,
      objectValues: [{ eClass, $ref: '//@objectsToAdd.0' }],
      objectsToAdd: [{ eClass }]

    };
    this.modelServerApi.edit(this.getModelIDToRequest(), addCommand);
  }

  protected onAfterAttach(msg: Message): void {
    this.splitPanel.addWidget(this.treeWidget);
    this.splitPanel.addWidget(this.formWidget);
    this.splitPanel.setRelativeSizes([1, 4]);
    Widget.attach(this.splitPanel, this.node);
    this.treeWidget.activate();
    super.onAfterAttach(msg);
  }

  protected onActivateRequest() {
    if (this.splitPanel) {
      this.splitPanel.node.focus();
    }
  }
  dispose() {
    this.modelServerApi.unsubscribe(this.getModelIDToRequest());
    super.dispose();
  }
}

export namespace JsonFormsTreeEditorWidget {
  export const WIDGET_ID = 'json-forms-tree-editor';
  export const WIDGET_LABEL = 'JSONForms Tree Editor';

  export namespace Styles {
    export const JSONFORMS_TREE_EDITOR_CLASS = 'json-forms-tree-editor';
  }
}
