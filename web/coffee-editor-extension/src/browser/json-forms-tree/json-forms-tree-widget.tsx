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

  protected mapDataToNodes(): [JsonFormsTree.Node] {
    const node = this.mapMachineData();
    return [node];
  }

  protected mapMachineData(): JsonFormsTree.Node {
    const currentData = this.data;
    const node = {
      id: v4(),
      name: currentData.name,
      parent: undefined,
      expanded: false,
      selected: false,
      decorationData: {},
      children: [],
      jsonforms: {
        type: "machine",
        data: currentData
      }
    };
    if (currentData.activities) {
      currentData.activities.forEach(element => {
        this.mapActivityData(element, node);
      });
    }
    if (currentData.children) {
      currentData.children.forEach(element => {
        this.mapData(element, node);
      });
    }
    return node;
  }

  protected mapActivityData(currentData: any, parent: JsonFormsTree.Node) {
    const node = {
      id: v4(),
      name: currentData.name,
      parent: parent,
      expanded: false,
      selected: false,
      decorationData: {},
      children: [],
      jsonforms: {
        type: "activity",
        data: currentData
      }
    };
    parent.children.push(node);
    parent.expanded = true;
  }

  protected mapData(currentData: any, parent: JsonFormsTree.Node) {
    if (!(currentData && currentData.eClass)) {
      // sanity check
      console.log("Don't know how to map data:");
      console.log(currentData);
      return;
    }
    let node;
    switch (currentData.eClass) {
      case "http://www.eclipse.org/emfforms/example/coffeemodel#//BrewingUnit":
        node = {
          id: v4(),
          name: "Brewing Unit",
          parent: parent,
          expanded: false,
          selected: false,
          decorationData: {},
          children: [],
          jsonforms: {
            type: "brewing-unit",
            data: currentData
          }
        };
        parent.children.push(node);
        parent.expanded = true;
        if (currentData.activities) {
          currentData.activities.forEach(element => {
            this.mapActivityData(element, node);
          });
        }
        if (currentData.children) {
          currentData.children.forEach(element => {
            this.mapData(element, node);
          });
        }
        break;
      case "http://www.eclipse.org/emfforms/example/coffeemodel#//ControlUnit":
        node = {
          id: v4(),
          name: "Control Unit",
          parent: parent,
          expanded: false,
          selected: false,
          decorationData: {},
          children: [],
          jsonforms: {
            type: "control-unit",
            data: currentData
          }
        };
        parent.children.push(node);
        parent.expanded = true;
        if (currentData.activities) {
          currentData.activities.forEach(element => {
            this.mapActivityData(element, node);
          });
        }
        if (currentData.children) {
          currentData.children.forEach(element => {
            this.mapData(element, node);
          });
        }
        break;
      default:
        console.log("Don't know how to map eClass " + currentData.eClass);
    }
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
