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
import { ModelServerReferenceDescription } from '@modelserver/theia/lib/common';
import { BaseWidget, Message, Navigatable, Saveable, SplitPanel, TreeNode, Widget } from '@theia/core/lib/browser';
import { Emitter, Event, ILogger } from '@theia/core/lib/common';
import URI from '@theia/core/lib/common/uri';
import { WorkspaceService } from '@theia/workspace/lib/browser/workspace-service';
import { debounce, isEqual } from 'lodash';
import * as React from 'react';
import * as ReactDOM from 'react-dom';

import { JsonFormsTree } from '../tree/json-forms-tree';
import { AddCommandProperty, JsonFormsTreeWidget } from '../tree/json-forms-tree-widget';
import { JSONFormsWidget } from './json-forms-widget';

export const JsonFormsTreeEditorWidgetOptions = Symbol(
  'JsonFormsTreeEditorWidgetOptions'
);
export interface JsonFormsTreeEditorWidgetOptions {
  uri: URI;
}

export abstract class JsonFormsTreeEditorWidget extends BaseWidget
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
    protected readonly options: JsonFormsTreeEditorWidgetOptions,
    protected readonly treeWidget: JsonFormsTreeWidget,
    protected readonly formWidget: JSONFormsWidget,
    protected readonly workspaceService: WorkspaceService,
    protected readonly logger: ILogger,
    readonly widget_id: string
  ) {
    super();
    this.id = widget_id;
    this.setTitle();
    this.splitPanel = new SplitPanel();
    this.addClass('json-forms-tree-editor');
    this.splitPanel.addClass('json-forms-tree-editor-sash');
    this.treeWidget.addClass('json-forms-tree-editor-tree');
    this.formWidget.addClass('json-forms-tree-editor-forms');
    this.formWidget.onChange(
      debounce(data => {
        if (
          !this.selectedNode ||
          !this.selectedNode.jsonforms ||
          isEqual(this.selectedNode.jsonforms.data, data)
        ) {
          return;
        }
        const node = this.getNodeDescription(this.selectedNode);
        this.handleFormUpdate(data, node);
      }, 250)
    );
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
  }
  public uri(): URI {
    return this.options.uri;
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
  protected getNodeDescription(
    node: JsonFormsTree.Node
  ): ModelServerReferenceDescription {
    const getRefSegment = (n: JsonFormsTree.Node) =>
      n.jsonforms.property
        ? `@${n.jsonforms.property}` +
          (n.jsonforms.index ? `.${n.jsonforms.index}` : '')
        : '';
    let refToNode = '';
    let toCheck: TreeNode = node;
    while (toCheck && JsonFormsTree.Node.is(toCheck)) {
      const parentRefSeg = getRefSegment(toCheck);
      refToNode =
        parentRefSeg === '' ? refToNode : '/' + parentRefSeg + refToNode;
      toCheck = toCheck.parent;
    }
    const ownerRef = `${
      this.workspaceService.workspace.uri
    }/${this.getModelIDToRequest()}#/${refToNode}`;
    return {
      eClass: node.jsonforms.type,
      $ref: ownerRef.replace('file:///', 'file:/')
    };
  }
  protected abstract deleteNode(node: Readonly<JsonFormsTree.Node>): void;
  protected abstract addNode({
    node,
    eClass,
    property
  }: AddCommandProperty): void;

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
  protected abstract handleFormUpdate(
    data: any,
    node: ModelServerReferenceDescription
  ): void;

  public save(): void {
    // do nothing by default
  }

  protected setTitle(): void {
    this.title.label = this.options.uri.path.base;
    this.title.caption = JsonFormsTreeEditorWidget.WIDGET_LABEL;
    this.title.closable = true;
    this.title.iconClass = 'fa coffee-icon dark-purple';
  }
}

export namespace JsonFormsTreeEditorWidget {
  export const WIDGET_LABEL = 'JSONForms Tree Editor';

  export namespace Styles {
    export const JSONFORMS_TREE_EDITOR_CLASS = 'json-forms-tree-editor';
  }
}
