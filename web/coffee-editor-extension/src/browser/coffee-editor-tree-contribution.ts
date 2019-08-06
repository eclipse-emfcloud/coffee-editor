import { CommandContribution, CommandRegistry, MenuContribution, MenuModelRegistry } from "@theia/core";
import { ApplicationShell, NavigatableWidgetOpenHandler, OpenerService } from "@theia/core/lib/browser";
import URI from "@theia/core/lib/common/uri";
import { inject, injectable } from "inversify";

import { JsonFormsTreeEditorWidget } from "./json-forms-tree-editor/json-forms-tree-editor-widget";
import {
  JsonFormsTreeCommands,
  JsonFormsTreeContextMenu,
  OpenWorkflowDiagramCommandHandler
} from "./json-forms-tree/json-forms-tree-container";

@injectable()
export class CoffeeTreeEditorContribution extends NavigatableWidgetOpenHandler<JsonFormsTreeEditorWidget> implements CommandContribution, MenuContribution {
  @inject(ApplicationShell) protected shell: ApplicationShell;
  @inject(OpenerService) protected opener: OpenerService;

  readonly id = JsonFormsTreeEditorWidget.WIDGET_ID;
  readonly label = JsonFormsTreeEditorWidget.WIDGET_LABEL;

  canHandle(uri: URI): number {
    if (
      uri.path.ext === ".coffee"
    ) {
      return 1000;
    }
    return 0;
  }

  registerCommands(commands: CommandRegistry): void {
    commands.registerCommand(
      JsonFormsTreeCommands.OPEN_WORKFLOW_DIAGRAM,
      new OpenWorkflowDiagramCommandHandler(this.shell, this.opener));
  }

  registerMenus(menus: MenuModelRegistry): void {
    menus.registerMenuAction(JsonFormsTreeContextMenu.CONTEXT_MENU, {
      commandId: JsonFormsTreeCommands.OPEN_WORKFLOW_DIAGRAM.id,
      label: JsonFormsTreeCommands.OPEN_WORKFLOW_DIAGRAM.label
    });
  }

}
