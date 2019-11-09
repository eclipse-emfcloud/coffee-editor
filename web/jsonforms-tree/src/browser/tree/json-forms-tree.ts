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
import {
  CompositeTreeNode,
  ExpandableTreeNode,
  SelectableTreeNode,
  TreeDecoration,
  TreeNode,
} from '@theia/core/lib/browser/tree';

export namespace JsonFormsTree {
  export interface RootNode extends CompositeTreeNode { }

  export namespace RootNode {
    export function is(node: TreeNode | undefined): node is RootNode {
      return !!node;
    }
  }

  export interface Node
    extends CompositeTreeNode,
    ExpandableTreeNode,
    SelectableTreeNode,
    TreeDecoration.DecoratedTreeNode {
    children: TreeNode[];
    name: string;
    jsonforms: {
      type: string;
      property: string;
      index?: string;
      data: any;
    };
  }

  export interface TreeData {
    error: boolean;
    data?: any;
  }

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
     * @param currentData The current instance data to map to a tree node
     * @param parent This node's parent node
     * @param property The JSON property which this node's data is contained in
     * @param type The type of the node
     * @param index The index which this node's data is contained in the parent property
     */
    mapData(
      currentData: any,
      parent?: Node,
      property?: string,
      eClass?: string,
      index?: number
    ): Node;

    /**
     * @param node The node to create a child for
     * @returns true if child nodes can be created
     */
    hasCreatableChildren(node: Node): boolean;
  }

  /**
   * Label provider for the tree providing icons and display names for tree nodes.
   */
  export const LabelProvider = Symbol('LabelProvider');
  export interface LabelProvider {
    /**
     * @param node The tree node or the node's type to get the icon css for
     * @return the css class(es) specifying the tree node's icon
     */
    getIconClass(node: TreeNode | string): string;

    /**
     * @param data The display name for the given data
     */
    getName(data: any): string;
  }

  export namespace Node {
    export function is(node: TreeNode | undefined): node is Node {
      if (!!node && 'jsonforms' in node) {
        const { jsonforms } = node;
        return !!jsonforms;
      }
      return false;
    }

    export function hasType(node: TreeNode | undefined, type: string): node is Node {
      return is(node) && node.jsonforms.type === type;
    }
  }
}
