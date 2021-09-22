/********************************************************************************
 * Copyright (c) 2021 EclipseSource and others.
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
 ********************************************************************************/
import { Emitter, MenuPath } from '@theia/core';
import { ExpandableTreeNode, TreeModel } from '@theia/core/lib/browser';
import { ContextMenuRenderer } from '@theia/core/lib/browser/context-menu-renderer';
import { TreeNode } from '@theia/core/lib/browser/tree/tree';
import { NodeProps, TreeProps } from '@theia/core/lib/browser/tree/tree-widget';
import { inject, injectable, postConstruct } from 'inversify';
import * as React from 'react';
import { v4 } from 'uuid';

import { TreeEditor } from './interfaces';
import { TreeWidgetWithTitle } from './tree-widget-with-title';

export interface AddCommandProperty {
    /** The node to add a new child to. */
    node: TreeEditor.Node;
    /** The property to add a new child to. */
    property: string;
    /** The type identifier of the new child to create. */
    type: string;
}

export interface TreeAnchor {
    x: number;
    y: number;
    node: TreeEditor.Node;
    onClick: (property: string, type: string) => void;
}

export namespace TreeContextMenu {
    export const CONTEXT_MENU: MenuPath = ['theia-tree-editor-tree-context-menu'];
    export const ADD_MENU: MenuPath = ['theia-tree-editor-tree-add-menu'];
}

@injectable()
export class MasterTreeWidget extends TreeWidgetWithTitle {
    protected onTreeWidgetSelectionEmitter = new Emitter<
        readonly Readonly<TreeEditor.Node>[]
    >();
    //protected onDeleteEmitter = new Emitter<Readonly<TreeEditor.Node>>();
    //protected onAddEmitter = new Emitter<Readonly<AddCommandProperty>>();
    protected data: TreeEditor.TreeData;

    constructor(
        @inject(TreeProps) readonly props: TreeProps,
        @inject(TreeModel) readonly model: TreeModel,
        @inject(ContextMenuRenderer) readonly contextMenuRenderer: ContextMenuRenderer,
        @inject(TreeEditor.NodeFactory) protected readonly nodeFactory: TreeEditor.NodeFactory
    ) {
        super(props, model, contextMenuRenderer);
        this.id = MasterTreeWidget.WIDGET_ID;
        this.title.label = MasterTreeWidget.WIDGET_LABEL;
        this.title.caption = MasterTreeWidget.WIDGET_LABEL;

        model.root = {
            id: MasterTreeWidget.WIDGET_ID,
            name: MasterTreeWidget.WIDGET_LABEL,
            parent: undefined,
            visible: false,
            children: []
        } as TreeEditor.RootNode;
    }

    @postConstruct()
    protected init(): void {
        super.init();

        this.toDispose.push(this.onTreeWidgetSelectionEmitter);
        //this.toDispose.push(this.onDeleteEmitter);
        //this.toDispose.push(this.onAddEmitter);
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


        //const addPlus = this.nodeFactory.hasCreatableChildren(node);
        // Do not render remove button for root nodes. Root nodes have depth 0.
        //const addRemoveButton = props.depth > 0;

        return (
            <React.Fragment>
                {deco}
            </React.Fragment>
        );
    }

    public async setData(data: TreeEditor.TreeData): Promise<void> {
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

    public findNode(propIndexPaths: { property: string; index?: string }[]): TreeEditor.Node {
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

    protected async refreshModelChildren(): Promise<void> {
        if (this.model.root && TreeEditor.RootNode.is(this.model.root)) {
            const newTree =
                !this.data || this.data.error ? [] : this.nodeFactory.mapDataToNodes(this.data);
            this.model.root.children = newTree;
            this.model.refresh();
        }
    }

    protected defaultNode(): Pick<TreeEditor.Node, 'id' | 'expanded' | 'selected' | 'parent' | 'decorationData' | 'children'> {
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
                <div className={this.labelProvider.getIcon(node)} />
            </div>
        );
    }

    /**
     * Updates the data of the given node with the new data. Refreshes the tree if necessary.
     * Note that this method will only work properly if only data relevant for this node was changed.
     * If data of the subtree was changed, too, please call updateDataForSubtree instead.
     */
    public updateDataForNode(node: TreeEditor.Node, data: any): void {
        const oldName = this.labelProvider.getName(node);
        Object.assign(node.jsonforms.data, data);
        const newName = this.labelProvider.getName(node);
        if (oldName !== newName) {
            node.name = newName;
            this.model.refresh();
        }
    }

    /**
     * Updates the data of the given node and recreates its whole subtree. Refreshes the tree.
     */
    public updateDataForSubtree(node: TreeEditor.Node, data: any): void {
        Object.assign(node.jsonforms.data, data);
        const newNode = this.nodeFactory.mapData(data);
        node.name = newNode.name;
        node.children = newNode.children;
        this.model.refresh();
    }
}

// eslint-disable-next-line no-redeclare
export namespace MasterTreeWidget {
    export const WIDGET_ID = 'theia-tree-editor-tree';
    export const WIDGET_LABEL = 'Theia Tree Editor - Tree';
}
