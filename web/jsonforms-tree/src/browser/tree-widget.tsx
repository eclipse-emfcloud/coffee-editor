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
import { Emitter, MenuPath } from '@theia/core';
import { ConfirmDialog, ExpandableTreeNode, TreeModel } from '@theia/core/lib/browser';
import { ContextMenuRenderer } from '@theia/core/lib/browser/context-menu-renderer';
import { TreeNode } from '@theia/core/lib/browser/tree/tree';
import { NodeProps, TreeProps, TreeWidget } from '@theia/core/lib/browser/tree/tree-widget';
import { inject, injectable, postConstruct } from 'inversify';
import * as React from 'react';
import { v4 } from 'uuid';

import { TreeEditor } from './interfaces';

export interface AddCommandProperty {
  node: TreeEditor.Node,
  property: string,
  type: string
}

export interface JsonFormsTreeAnchor {
  x: number,
  y: number,
  node: TreeEditor.Node,
  onClick: (property: string, eClass: string) => void
}

export namespace JsonFormsTreeContextMenu {
  export const CONTEXT_MENU: MenuPath = ['json-forms-tree-context-menu'];
  export const ADD_MENU: MenuPath = ['json-forms-tree-add-menu'];
}

@injectable()
export class JsonFormsTreeWidget extends TreeWidget {
  protected onTreeWidgetSelectionEmitter = new Emitter<
    readonly Readonly<TreeEditor.Node>[]
  >();
  protected onDeleteEmitter = new Emitter<Readonly<TreeEditor.Node>>();
  protected onAddEmitter = new Emitter<Readonly<AddCommandProperty>>();
  protected data: TreeEditor.TreeData;

  constructor(
    @inject(TreeProps) readonly props: TreeProps,
    @inject(TreeModel) readonly model: TreeModel,
    @inject(ContextMenuRenderer) readonly contextMenuRenderer: ContextMenuRenderer,
    @inject(TreeEditor.LabelProvider) readonly labelProvider: TreeEditor.LabelProvider,
    @inject(TreeEditor.NodeFactory) protected readonly nodeFactory: TreeEditor.NodeFactory
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
    } as TreeEditor.RootNode;
  }

  @postConstruct()
  protected init() {
    super.init();

    this.addClass('tree-container');

    this.toDispose.push(this.onTreeWidgetSelectionEmitter);
    this.toDispose.push(this.onDeleteEmitter);
    this.toDispose.push(this.onAddEmitter);
    this.toDispose.push(
      this.model.onSelectionChanged(e => {
        this.onTreeWidgetSelectionEmitter.fire(e as readonly Readonly<
          TreeEditor.Node
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
    if (!TreeEditor.Node.is(node)) {
      return deco;
    }

    const addPlus = this.nodeFactory.hasCreatableChildren(node);
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

  /**
   * Creates a handler for the delete button of a tree node.
   * @param node The tree node to create a remove handler for
   */
  private createRemoveHandler(node: TreeEditor.Node): (event: React.MouseEvent<HTMLDivElement, MouseEvent>) => void {
    return event => {
      event.stopPropagation();
      const dialog = new ConfirmDialog({
        title: 'Delete Node?',
        msg: 'Are you sure you want to delete the selected node?'
      });
      dialog.open().then(remove => {
        if (remove && node.parent && node.parent && TreeEditor.Node.is(node.parent)) {
          this.onDeleteEmitter.fire(node);
        }
      });
    };
  }

  private createAddHandler(node: TreeEditor.Node): (event: React.MouseEvent<HTMLDivElement, MouseEvent>) => void {
    return event => {
      const addHandler = (property: string, eClass: string) => this.onAddEmitter.fire({ node, property, type: eClass });
      const treeAnchor: JsonFormsTreeAnchor = {
        x: event.nativeEvent.x,
        y: event.nativeEvent.y,
        node: node,
        onClick: addHandler
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
      TreeEditor.RootNode.is(this.model.root) &&
      this.model.root.children.length > 0 &&
      TreeEditor.Node.is(this.model.root.children[0])
    ) {
      this.model.selectNode(this.model.root.children[0] as TreeEditor.Node);
      this.model.refresh();
    }
  }

  public findNode(propIndexPaths: { property: string, index?: string }[]): TreeEditor.Node {
    const rootNode = this.model.root as TreeEditor.RootNode;
    return propIndexPaths.reduce((parent, segment) => {
      const fitting = parent.children.filter(n => TreeEditor.Node.is(n) && n.jsonforms.property === segment.property && n.jsonforms.index === segment.index);
      return fitting[0] as TreeEditor.Node;
    }, rootNode.children[0] as TreeEditor.Node);
  }

  public select(paths: string[]): void {
    if (paths.length === 0) {
      return;
    }
    const rootNode = this.model.root as TreeEditor.Node;
    const toSelect = paths.reduceRight((node, path) => node.children.find(value => value.name === path), rootNode) as TreeEditor.Node;
    this.model.selectNode(toSelect);
    this.model.refresh();
  }

  get onSelectionChange(): import('@theia/core').Event<
    readonly Readonly<TreeEditor.Node>[]
    > {
    return this.onTreeWidgetSelectionEmitter.event;
  }
  get onDelete(): import('@theia/core').Event<Readonly<TreeEditor.Node>> {
    return this.onDeleteEmitter.event;
  }
  get onAdd(): import('@theia/core').Event<Readonly<AddCommandProperty>> {
    return this.onAddEmitter.event;
  }

  protected async refreshModelChildren(): Promise<void> {
    if (this.model.root && TreeEditor.RootNode.is(this.model.root)) {
      const newTree =
        !this.data || this.data.error ? [] : this.nodeFactory.mapDataToNodes(this.data);
      this.model.root.children = newTree;
      this.model.refresh();
    }
  }

  protected defaultNode(): Pick<
    TreeEditor.Node,
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

  protected isExpandable(node: TreeNode): node is ExpandableTreeNode {
    return TreeEditor.Node.is(node) && node.children.length > 0;
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
  public updateDataForNode(node: TreeEditor.Node, data: any) {
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
  public updateDataForSubtree(node: TreeEditor.Node, data: any) {
    Object.assign(node.jsonforms.data, data);
    const newNode = this.nodeFactory.mapData(data);
    node.name = newNode.name;
    node.children = newNode.children;
    this.model.refresh();
  }

  /**
   * Creates new tree nodes for the given data and adds them to the given node.
   *
   * @param node The node to add children to
   * @param data The data array to generate the new tree nodes from
   * @param property The property of the parent data which will contain the new nodes.
   */
  public addChildren(node: TreeEditor.Node, data: any[], property: string) {
    const currentValue = node.jsonforms.data[property];
    let index = 0;
    if (Array.isArray(currentValue)) {
      index = currentValue.length;
    }
    data.map((d, i) => this.nodeFactory.mapData(d, node, property, index + i));
    this.updateIndex(node, property);
    this.model.refresh();
  }

  public removeChildren(node: TreeEditor.Node, indices: number[], property: string) {
    const toDelete = node.children.filter(n =>
      TreeEditor.Node.is(n) &&
      n.jsonforms.property === property &&
      indices.includes(Number(n.jsonforms.index))
    ).map(n => node.children.indexOf(n));
    toDelete.forEach(i => node.children.splice(i, 1));
    this.updateIndex(node, property);
    this.model.refresh();
  }
  private updateIndex(node: TreeEditor.Node, property: string) {
    let realIndex = 0;
    node.children.forEach((n, i) => {
      if (TreeEditor.Node.is(n) && n.jsonforms.property === property) {
        n.jsonforms.index = realIndex.toString();
        realIndex++;
      }
    });
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
