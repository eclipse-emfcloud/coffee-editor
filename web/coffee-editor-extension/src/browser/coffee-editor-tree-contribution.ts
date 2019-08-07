import { CommandContribution, CommandRegistry, MenuContribution, MenuModelRegistry, Command } from "@theia/core";
import { ApplicationShell, NavigatableWidgetOpenHandler, OpenerService } from "@theia/core/lib/browser";
import URI from "@theia/core/lib/common/uri";
import { inject, injectable } from "inversify";

import { JsonFormsTreeEditorWidget } from "./json-forms-tree-editor/json-forms-tree-editor-widget";
import { JsonFormsTreeLabelProvider } from './json-forms-tree/json-forms-tree-label-provider';
import {
  AddCommandHandler,
  JsonFormsTreeCommands,
  JsonFormsTreeContextMenu,
  OpenWorkflowDiagramCommandHandler
} from "./json-forms-tree/json-forms-tree-container";

@injectable()
export class CoffeeTreeEditorContribution extends NavigatableWidgetOpenHandler<JsonFormsTreeEditorWidget> implements CommandContribution, MenuContribution {
  @inject(ApplicationShell) protected shell: ApplicationShell;
  @inject(OpenerService) protected opener: OpenerService;
  @inject(JsonFormsTreeLabelProvider) protected labelProvider: JsonFormsTreeLabelProvider;

  readonly id = JsonFormsTreeEditorWidget.WIDGET_ID;
  readonly label = JsonFormsTreeEditorWidget.WIDGET_LABEL;
  private commandMap: Map<string, Command>;

  /**
   * @returns maps EClass identifiers to their corresponding add command
   */
  private getCommandMap(): Map<string, Command> {
    if (!this.commandMap) {
      this.commandMap = JsonFormsTreeCommands.generateAddCommands();
    }
    return this.commandMap;
  }

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

      this.getCommandMap().forEach((value, key, _map) => {
        commands.registerCommand(value, new AddCommandHandler(key));
      });
  }

  registerMenus(menus: MenuModelRegistry): void {
    menus.registerMenuAction(JsonFormsTreeContextMenu.CONTEXT_MENU, {
      commandId: JsonFormsTreeCommands.OPEN_WORKFLOW_DIAGRAM.id,
      label: JsonFormsTreeCommands.OPEN_WORKFLOW_DIAGRAM.label
    });

    this.getCommandMap().forEach((value, key, _map) => {
      menus.registerMenuAction(JsonFormsTreeContextMenu.ADD_MENU, {
        commandId: value.id,
        label: value.label,
        icon: this.labelProvider.getIconClass(key)
      })
    });
  }

}
