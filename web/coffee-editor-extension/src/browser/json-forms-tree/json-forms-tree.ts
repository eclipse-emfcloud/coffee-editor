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
