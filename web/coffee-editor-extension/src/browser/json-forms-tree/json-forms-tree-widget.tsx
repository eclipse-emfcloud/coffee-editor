import { inject, injectable, postConstruct } from "inversify";
import { TreeNode } from "@theia/core/lib/browser/tree/tree";
import { ContextMenuRenderer } from "@theia/core/lib/browser/context-menu-renderer";
import {
  TreeWidget,
  TreeProps
} from "@theia/core/lib/browser/tree/tree-widget";
import { JsonFormsTree } from "./json-forms-tree";
import { TreeModel, ExpandableTreeNode } from "@theia/core/lib/browser";
import { v4 } from "uuid";
import { Emitter, Event } from "@theia/core";
import { instance } from "../models/coffee-schemas";
import URI from "@theia/core/lib/common/uri";

@injectable()
export class JsonFormsTreeWidget extends TreeWidget {
  protected onTreeWidgetSelectionEmitter = new Emitter<
    readonly Readonly<JsonFormsTree.Node>[]
  >();
  protected data: any;

  constructor(
    @inject(TreeProps) readonly props: TreeProps,
    @inject(TreeModel) readonly model: TreeModel,
    @inject(ContextMenuRenderer)
    readonly contextMenuRenderer: ContextMenuRenderer
  ) {
    super(props, model, contextMenuRenderer);
    this.id = JsonFormsTreeWidget.WIDGET_ID;
    this.title.label = JsonFormsTreeWidget.WIDGET_LABEL;
    this.title.caption = JsonFormsTreeWidget.WIDGET_LABEL;
    this.addClass(JsonFormsTreeWidget.Styles.JSONFORMS_TREE_CLASS);

    model.selectNextNode;

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

    this.addClass("tree-container");

    this.toDispose.push(this.onTreeWidgetSelectionEmitter);
    this.toDispose.push(
      this.model.onSelectionChanged(e => {
        this.onTreeWidgetSelectionEmitter.fire(e as readonly Readonly<
          JsonFormsTree.Node
        >[]);
      })
    );

    this.fillTree().then(() => {
      // select first node if it exists
      if (
        this.model.root &&
        JsonFormsTree.RootNode.is(this.model.root) &&
        this.model.root.children.length > 0 &&
        JsonFormsTree.Node.is(this.model.root.children[0])
      ) {
        this.model.selectNode(this.model.root
          .children[0] as JsonFormsTree.Node);
        this.model.refresh();
      }
    });
  }

  get onSelectionChange(): Event<readonly Readonly<JsonFormsTree.Node>[]> {
    return this.onTreeWidgetSelectionEmitter.event;
  }

  async fillTree(): Promise<void> {
    await this.requestData();
    this.refreshModelChildren();
  }

  async requestData(): Promise<void> {
    this.data = instance;
  }

  protected async refreshModelChildren(): Promise<void> {
    if (this.model.root && JsonFormsTree.RootNode.is(this.model.root)) {
      this.model.root.children = this.mapDataToNodes();
      this.model.refresh();
    }
  }

  protected mapDataToNodes(): JsonFormsTree.Node[] {
    const node = this.mapData(this.data);
    if (node) {
      return [node];
    }
    return [];
  }

  protected defaultNode(): Pick<
    JsonFormsTree.Node,
    "id" | "expanded" | "selected" | "parent" | "decorationData" | "children"
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

  protected getName(currentData: any) {
    switch (currentData.eClass) {
      case "http://www.eclipsesource.com/modelserver/example/coffeemodel#//Task":
      case "http://www.eclipsesource.com/modelserver/example/coffeemodel#//AutomaticTask":
      case "http://www.eclipsesource.com/modelserver/example/coffeemodel#//ManualTask":
      case "http://www.eclipsesource.com/modelserver/example/coffeemodel#//Machine":
        return currentData.name;
      default:
        // TODO query title of schema
        const fragment = new URI(currentData.eClass).fragment;
        if (fragment.startsWith("//")) {
          return fragment.substring(2);
        }
        return fragment;
    }
  }

  protected mapData(
    currentData: any,
    parent?: JsonFormsTree.Node
  ): JsonFormsTree.Node {
    if (!(currentData && currentData.eClass)) {
      // sanity check
      console.log("Don't know how to map data:");
      console.log(currentData);
      return undefined;
    }
    const node = {
      ...this.defaultNode(),
      name: this.getName(currentData),
      parent: parent,
      jsonforms: {
        type: currentData.eClass,
        data: currentData
      }
    };
    // containments
    if (parent) {
      parent.children.push(node);
      parent.expanded = true;
    }
    if (currentData.children) {
      // component types
      currentData.children.forEach(element => {
        this.mapData(element, node);
      });
    }
    if (currentData.workflows) {
      // machine type
      currentData.workflows.forEach(element => {
        this.mapData(element, node);
      });
    }
    if (currentData.ram) {
      // controlunit type
      currentData.ram.forEach(element => {
        this.mapData(element, node);
      });
    }
    if (currentData.nodes) {
      // workflow type
      currentData.nodes.forEach(element => {
        this.mapData(element, node);
      });
    }
    if (currentData.flows) {
      // workflow type
      currentData.flows.forEach(element => {
        this.mapData(element, node);
      });
    }
    return node;
  }

  protected isExpandable(node: TreeNode): node is ExpandableTreeNode {
    return JsonFormsTree.Node.is(node) && node.children.length > 0;
  }
}

export namespace JsonFormsTreeWidget {
  export const WIDGET_ID = "json-forms-tree";
  export const WIDGET_LABEL = "JSONForms Tree";

  /**
   * CSS styles for the `JSONForms Hierarchy` widget.
   */
  export namespace Styles {
    export const JSONFORMS_TREE_CLASS = "json-forms-tree";
  }
}
