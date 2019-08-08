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
import { Emitter, ILogger } from '@theia/core';
import { ConfirmDialog, ExpandableTreeNode, TreeModel } from '@theia/core/lib/browser';
import { ContextMenuRenderer } from '@theia/core/lib/browser/context-menu-renderer';
import { TreeNode } from '@theia/core/lib/browser/tree/tree';
import { NodeProps, TreeProps, TreeWidget } from '@theia/core/lib/browser/tree/tree-widget';
import { inject, injectable, postConstruct } from 'inversify';
import * as React from 'react';
import { v4 } from 'uuid';

import { CoffeeModel } from './coffee-model';
import { JsonFormsTree } from './json-forms-tree';
import { JsonFormsTreeAnchor, JsonFormsTreeContextMenu } from './json-forms-tree-container';
import { JsonFormsTreeLabelProvider } from './json-forms-tree-label-provider';

@injectable()
export class JsonFormsTreeWidget extends TreeWidget {
  protected onTreeWidgetSelectionEmitter = new Emitter<
    readonly Readonly<JsonFormsTree.Node>[]
  >();
  protected data: {
    error: boolean;
    data?: any;
  };

  constructor(
    @inject(TreeProps) readonly props: TreeProps,
    @inject(TreeModel) readonly model: TreeModel,
    @inject(ContextMenuRenderer)
    readonly contextMenuRenderer: ContextMenuRenderer,
    @inject(JsonFormsTreeLabelProvider)
    readonly labelProvider: JsonFormsTreeLabelProvider,
    @inject(ILogger) private readonly logger: ILogger
  ) {
    super(props, model, contextMenuRenderer);
    this.id = JsonFormsTreeWidget.WIDGET_ID;
    this.title.label = JsonFormsTreeWidget.WIDGET_LABEL;
    this.title.caption = JsonFormsTreeWidget.WIDGET_LABEL;
    this.addClass(JsonFormsTreeWidget.Styles.JSONFORMS_TREE_CLASS);

    model.root = {
      id: JsonFormsTreeWidget.WIDGET_ID,
      name: JsonFormsTreeWidget.WIDGET_LABEL,
      parent: undefined,
      visible: false,
      children: []
    } as JsonFormsTree.RootNode;
  }

  @postConstruct()
  protected init() {
    super.init();

    this.addClass('tree-container');

    this.toDispose.push(this.onTreeWidgetSelectionEmitter);
    this.toDispose.push(
      this.model.onSelectionChanged(e => {
        this.onTreeWidgetSelectionEmitter.fire(e as readonly Readonly<
          JsonFormsTree.Node
        >[]);
      })
    );
  }

  /** Overrides method in TreeWidget */
  protected handleClickEvent(
    node: TreeNode | undefined,
    event: React.MouseEvent<HTMLElement>
  ): void {
    const x = event.target as HTMLElement;
    if (x.classList.contains('node-button')) {
      // Don't do anything because the event is handled in the button's handler
      return;
    }
    super.handleClickEvent(node, event);
  }

  /*
   * Overrides TreeWidget.renderTailDecorations
   * Add a add child and a remove button.
   */
  protected renderTailDecorations(
    node: TreeNode,
    props: NodeProps
  ): React.ReactNode {
    const deco = super.renderTailDecorations(node, props);
    if (!JsonFormsTree.Node.is(node)) {
      return deco;
    }

    const addPlus = this.hasCreatableChildren(node.jsonforms.type);
    return (
      <React.Fragment>
        {deco}
        <div className='node-buttons'>
          {addPlus ? (
            <div
              className='node-button far fa-plus-square'
              onClick={this.createAddHandler(node)}
            />
          ) : (
              ''
            )}
          <div
            className='node-button far fa-minus-square'
            onClickCapture={this.createRemoveHandler(node)}
          />
        </div>
      </React.Fragment>
    );
  }

  private hasCreatableChildren(type: string) {
    return CoffeeModel.childrenMapping.get(type) !== undefined;
  }

  /**
   * Creates a handler for the delete button of a tree node.
   * @param node The tree node to create a remove handler for
   */
  private createRemoveHandler(node: JsonFormsTree.Node): (event: React.MouseEvent<HTMLDivElement, MouseEvent>) => void {
    return event => {
      event.stopPropagation();
      const dialog = new ConfirmDialog({
        title: 'Delete Node?',
        msg: 'Are you sure you want to delete the selected node?'
      });
      dialog.open().then(remove => {
        if (remove && node.parent && node.parent && JsonFormsTree.Node.is(node.parent)) {
          const prop = node.jsonforms.property;
          console.log('Remove node ' + node.name + ' from parent property ' + prop);
          if (node.jsonforms.index) {
            // multi ref
            // create remove command
          } else {
            // create remove command
          }

          // TODO send command to model server
        }
      });
    };
  }

  private createAddHandler(node: JsonFormsTree.Node): (event: React.MouseEvent<HTMLDivElement, MouseEvent>) => void {
    return event => {
      const treeAnchor: JsonFormsTreeAnchor = {
        x: event.nativeEvent.x,
        y: event.nativeEvent.y,
        node: node
      };
      this.contextMenuRenderer.render(JsonFormsTreeContextMenu.ADD_MENU, treeAnchor);
    };
  }

  public async setData(data: any) {
    this.data = data;
    await this.refreshModelChildren();
  }

  public selectFirst(): void {
    if (
      this.model.root &&
      JsonFormsTree.RootNode.is(this.model.root) &&
      this.model.root.children.length > 0 &&
      JsonFormsTree.Node.is(this.model.root.children[0])
    ) {
      this.model.selectNode(this.model.root.children[0] as JsonFormsTree.Node);
      this.model.refresh();
    }
  }

  public select(node: JsonFormsTree.Node): void {
    this.model.selectNode(node);
    this.model.refresh();
  }

  get onSelectionChange(): import('@theia/core').Event<
    readonly Readonly<JsonFormsTree.Node>[]
    > {
    return this.onTreeWidgetSelectionEmitter.event;
  }

  protected async refreshModelChildren(): Promise<void> {
    if (this.model.root && JsonFormsTree.RootNode.is(this.model.root)) {
      const newTree =
        !this.data || this.data.error ? [] : this.mapDataToNodes();
      this.model.root.children = newTree;
      this.model.refresh();
    }
  }

  protected mapDataToNodes(): JsonFormsTree.Node[] {
    const node = this.mapData(this.data.data);
    if (node) {
      return [node];
    }
    return [];
  }

  protected defaultNode(): Pick<
    JsonFormsTree.Node,
    'id' | 'expanded' | 'selected' | 'parent' | 'decorationData' | 'children'
  > {
    return {
      id: v4(),
      expanded: false,
      selected: false,
      parent: undefined,
      decorationData: {},
      children: []
    };
  }

  protected getEClass(eClass: any, currentData: any) {
    // FIXME: eClass should always be sent from server

    if (eClass) {
      // given eClass
      return eClass;
    }
    if (currentData.eClass) {
      // eClass of node
      return currentData.eClass;
    }
    // guess
    if (currentData.nodes) {
      return CoffeeModel.Type.Workflow;
    }
    return undefined;
  }

  protected mapData(
    currentData: any,
    parent?: JsonFormsTree.Node,
    property?: string,
    eClass?: string,
    index?: number
  ): JsonFormsTree.Node {
    if (!currentData) {
      // sanity check
      this.logger.warn('mapData called without data');
      return undefined;
    }
    const node = {
      ...this.defaultNode(),
      name: this.labelProvider.getName(currentData),
      parent: parent,
      jsonforms: {
        type: this.getEClass(eClass, currentData),
        data: currentData,
        property: property,
        index: index ? index.toFixed(0) : undefined
      }
    };
    // containments
    if (parent) {
      parent.children.push(node);
      parent.expanded = true;
    }
    if (currentData.children) {
      // component types
      currentData.children.forEach((element, idx) => {
        this.mapData(element, node, 'children', undefined, idx);
      });
    }
    if (currentData.workflows) {
      // machine type
      currentData.workflows.forEach((element, idx) => {
        this.mapData(
          element,
          node,
          'workflows',
          CoffeeModel.Type.Workflow,
          idx
        );
      });
    }
    if (currentData.nodes) {
      // workflow type
      currentData.nodes.forEach((element, idx) => {
        this.mapData(element, node, 'nodes', undefined, idx);
      });
    }
    if (currentData.flows) {
      // workflow type
      currentData.flows.forEach((element, idx) => {
        this.mapData(element, node, 'flows', undefined, idx);
      });
    }
    return node;
  }

  protected isExpandable(node: TreeNode): node is ExpandableTreeNode {
    return JsonFormsTree.Node.is(node) && node.children.length > 0;
  }

  protected renderIcon(node: TreeNode): React.ReactNode {
    return (
      <div className='tree-icon-container'>
        <div className={this.labelProvider.getIconClass(node)} />
      </div>
    );
  }

  /**
   * Updates the data of the given node with the new data. Refreshes the tree if necessary.
   * Note that this method will only work properly if only data relevant for this node was changed.
   * If data of the subtree was changed too please call updateDataForSubtree instead.
   */
  public updateDataForNode(node: JsonFormsTree.Node, data: any) {
    Object.assign(node.jsonforms.data, data);
    const newName = this.labelProvider.getName(data);
    if (node.name !== newName) {
      node.name = newName;
      this.model.refresh();
    }
  }

  /**
   * Updates the data of the given node and recreates its whole subtree. Refreshes the tree.
   */
  public updateDataForSubtree(node: JsonFormsTree.Node, data: any) {
    Object.assign(node.jsonforms.data, data);
    const newNode = this.mapData(data);
    node.name = newNode.name;
    node.children = newNode.children;
    this.model.refresh();
  }
}

export namespace JsonFormsTreeWidget {
  export const WIDGET_ID = 'json-forms-tree';
  export const WIDGET_LABEL = 'JSONForms Tree';

  /**
   * CSS styles for the `JSONForms Hierarchy` widget.
   */
  export namespace Styles {
    export const JSONFORMS_TREE_CLASS = 'json-forms-tree';
  }
}
