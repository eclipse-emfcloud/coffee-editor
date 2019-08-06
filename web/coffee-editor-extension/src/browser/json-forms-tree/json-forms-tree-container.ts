import { Command, CommandHandler, MenuPath } from "@theia/core";
import { ApplicationShell, OpenerService } from "@theia/core/lib/browser";
import { createTreeContainer, defaultTreeProps, TreeProps, TreeWidget } from "@theia/core/lib/browser/tree";
import URI from "@theia/core/lib/common/uri";
import { Container, interfaces } from "inversify";

import { JsonFormsTreeEditorWidget } from "../json-forms-tree-editor/json-forms-tree-editor-widget";
import { CoffeeModel } from "./coffee-model";
import { JsonFormsTree } from "./json-forms-tree";
import { JsonFormsTreeWidget } from "./json-forms-tree-widget";

export namespace JsonFormsTreeContextMenu {
  export const CONTEXT_MENU: MenuPath = ['json-forms-tree-context-menu'];
}

export namespace JsonFormsTreeCommands {
  export const OPEN_WORKFLOW_DIAGRAM: Command = {
    id: 'workflow.open',
    label: 'Open Workflow Diagram'
  };
}

export class OpenWorkflowDiagramCommandHandler implements CommandHandler {
  constructor(protected readonly shell: ApplicationShell,
    protected readonly openerService: OpenerService) {
  }

  execute(...args: any[]) {
    const editorWidget = this.getTreeEditorWidget();
    if (editorWidget) {
      const workflowNode = this.getSelectedWorkflow(editorWidget);
      if (workflowNode) {
        const notationUri = this.getNotationUri(editorWidget);
        this.openerService.getOpener(notationUri).then(opener => opener.open(notationUri, this.createServerOptions(workflowNode)));
      }
    }
  }

  isVisible?(...args: any[]): boolean {
    return this.getSelectedWorkflow(this.getTreeEditorWidget()) !== undefined;
  }

  getTreeEditorWidget(): JsonFormsTreeEditorWidget | undefined {
    const currentWidget = this.shell.currentWidget;
    if (currentWidget instanceof JsonFormsTreeEditorWidget) {
      return currentWidget;
    }
    return undefined;
  }

  getSelectedWorkflow(widget: JsonFormsTreeEditorWidget): JsonFormsTree.Node | undefined {
    if (widget && JsonFormsTree.Node.hasType(widget.selectedNode, CoffeeModel.Type.Workflow)) {
      return widget.selectedNode;
    }
    return undefined;
  }

  getNotationUri(widget: JsonFormsTreeEditorWidget): URI {
    const coffeeUri = widget.uri();
    const coffeeNotationUri = coffeeUri.parent.resolve(coffeeUri.displayName + "notation");
    return coffeeNotationUri;
  }

  createServerOptions(node: JsonFormsTree.Node) {
    return {
      serverOptions: {
        workflowIndex: node.jsonforms.index
      }
    }
  }
}

function createJsonFormsTreeContainer(parent: interfaces.Container): Container {
  const child = createTreeContainer(parent);

  child.unbind(TreeWidget);
  child.bind(JsonFormsTreeWidget).toSelf();
  child.rebind(TreeProps).toConstantValue(JSON_FORMS_TREE_PROPS);
  return child;
}

export const JSON_FORMS_TREE_PROPS = <TreeProps>{
  ...defaultTreeProps,
  contextMenuPath: JsonFormsTreeContextMenu.CONTEXT_MENU,
  multiSelect: false,
  search: false
};

export function createJsonFormsTreeWidget(
  parent: interfaces.Container
): JsonFormsTreeWidget {
  return createJsonFormsTreeContainer(parent).get(JsonFormsTreeWidget);
}
