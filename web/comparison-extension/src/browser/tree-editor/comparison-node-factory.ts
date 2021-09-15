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
import { injectable, inject } from 'inversify';

import { v4 } from 'uuid';
import { TreeData } from './comparison-model-service';
import { TreeEditor } from '../tree-widget/interfaces';
import { ILogger } from '@theia/core';
import { ComparisonTreeEditorWidget } from './ComparisonTreeEditorWidget';
import { ComparisonModel } from './comparison-model';
import { ComparisonTreeLabelProvider } from './ComparisonLabelProviderContribution';


/**
 * Encapsulates logic to create the tree nodes from the tree's input data.
 */
export const NodeFactory = Symbol('NodeFactory');
export interface NodeFactory {

    /**
     * Recursively creates the tree's nodes from the given data.
     *
     * @param treeData The tree's data
     * @returns The tree's shown root nodes (not to confuse with the invisible RootNode)
     */
    mapDataToNodes(treeData: TreeData): Node[];

    /**
     * Creates the corresponding TreeNode for the given data.
     *
     * @param data The instance data to map to a tree node
     * @param parent The created node's parent node
     * @param property The JSON property which this node's data is contained in
     * @param indexOrKey If the data is inserted in an array property, this is the index it is inserted at.
     *                   If the data is inserted into an object, this is the key the data is associated with.
     */
    mapData(
        data: any,
        parent?: Node,
        property?: string,
        indexOrKey?: number | string
    ): Node;

    /**
     * @param node The node to create a child for
     * @returns true if child nodes can be created
     */
    hasCreatableChildren(node: Node): boolean;
}

/**
 * Information to get the icon of an add command from an editor's label provider contribution.
 */
export interface CommandIconInfo {
    _id: 'comparison-tree-editor-command-icon-info';
    editorId: string;
    type: string;
}

export namespace CommandIconInfo {
    export function is(info: object | undefined): info is CommandIconInfo {
        return !!info && '_id' in info && 'comparison-tree-editor-command-icon-info' === info['_id'];
    }
}

@injectable()
export class ComparisonTreeNodeFactory implements TreeEditor.NodeFactory {

    constructor(
        @inject(ComparisonTreeLabelProvider) private readonly labelProvider: ComparisonTreeLabelProvider,
        @inject(ILogger) private readonly logger: ILogger) {
    }   

    mapDataToNodes(treeData: TreeEditor.TreeData): TreeEditor.Node[] {
       const nodes: TreeEditor.Node[] = [];
        (<any[]> treeData.data).forEach(data => {
            const node = this.mapData(data);
            if (node) {
                nodes.push(node);
            }
        });
        return nodes;
    }

    mapData(data: any, parent?: TreeEditor.Node | undefined, property?: string | undefined, indexOrKey?: string | number | undefined): TreeEditor.Node {
        if (!data) {
            // sanity check
            this.logger.warn('mapData called without data');
            return undefined;
        }

        let decorationData = {fontData: {}};
        if (data.color) {
            decorationData.fontData["color"] = data.color;
        }

        const node: TreeEditor.Node = {
            ...this.defaultNode(),
            editorId: ComparisonTreeEditorWidget.EDITOR_ID,
            name: this.labelProvider.getName(data),
            parent: parent,
            decorationData: decorationData,
            jsonforms: {
                type: this.getType(data.eClass, data),
                data: data,
                property: property,
                index: typeof indexOrKey === 'number' ? indexOrKey.toFixed(0) : indexOrKey
            }
        };
        
        // containments
        if (parent) {
            parent.children.push(node);
            parent.expanded = true;
        }
        if (data.children) {
            // component types
            data.children.forEach((element, idx) => {
                this.mapData(element, node, 'children', idx);
            });
        }

        return node;
    }

    protected defaultNode(): Pick<
        TreeEditor.Node,
        'children' | 'name' | 'jsonforms' | 'id' | 'icon' | 'description' | 'visible' | 'parent' | 'previousSibling' | 'nextSibling' | 'expanded' | 'selected' | 'focus' | 'decorationData'
    > {
        return {
            id: v4(),
            expanded: false,
            selected: false,
            parent: undefined,
            children: [],
            decorationData: {},
            name: undefined,
            jsonforms: undefined
        };
    }

    protected getType(type: string, data: any): string {
        if (type) {
            // given eClass
            return type;
        }
        if (data.eClass) {
            // eClass of node
            return data.eClass;
        }
        // guess
        if (data.nodes) {
            return ComparisonModel.Type.DiagramNode;
        }
        return undefined;
    }

    hasCreatableChildren(node: TreeEditor.Node): boolean {
        return node ? ComparisonModel.childrenMapping.get(node.jsonforms.type) !== undefined : false;
    }

}
