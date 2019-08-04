import {
  TreeNode,
  CompositeTreeNode,
  ExpandableTreeNode,
  SelectableTreeNode,
  TreeDecoration
} from "@theia/core/lib/browser/tree";

export namespace JsonFormsTree {
  export interface RootNode extends CompositeTreeNode {}

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
    jsonforms: {
      type?: string;
      data: any;
    };
  }

  export namespace Node {
    export function is(node: TreeNode | undefined): node is Node {
      if (!!node && "jsonforms" in node) {
        const { jsonforms } = node;
        return !!jsonforms;
      }
      return false;
    }
  }
}
