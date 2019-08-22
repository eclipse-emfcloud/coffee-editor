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
import { Command, CommandContribution, CommandRegistry, MenuContribution, MenuModelRegistry } from '@theia/core';
import { ApplicationShell, NavigatableWidgetOpenHandler, OpenerService } from '@theia/core/lib/browser';
import URI from '@theia/core/lib/common/uri';
import { inject, injectable } from 'inversify';

import { JsonFormsTreeEditorWidget } from './json-forms-tree-editor/json-forms-tree-editor-widget';
import { JsonFormsTree } from './json-forms-tree/json-forms-tree';
import {
  AddCommandHandler,
  JsonFormsTreeCommands,
  JsonFormsTreeContextMenu,
  OpenWorkflowDiagramCommandHandler,
} from './json-forms-tree/json-forms-tree-container';

@injectable()
export class CoffeeTreeEditorContribution extends NavigatableWidgetOpenHandler<JsonFormsTreeEditorWidget> implements CommandContribution, MenuContribution {
  @inject(ApplicationShell) protected shell: ApplicationShell;
  @inject(OpenerService) protected opener: OpenerService;
  @inject(JsonFormsTree.LabelProvider) protected labelProvider: JsonFormsTree.LabelProvider;

  readonly id = JsonFormsTreeEditorWidget.WIDGET_ID;
  readonly label = JsonFormsTreeEditorWidget.WIDGET_LABEL;
  private commandMap: Map<string, Map<string, Command>>;

  /**
   * @returns maps property name to EClass identifiers to their corresponding add command
   */
  private getCommandMap(): Map<string, Map<string, Command>> {
    if (!this.commandMap) {
      this.commandMap = JsonFormsTreeCommands.generateAddCommands();
    }
    return this.commandMap;
  }

  canHandle(uri: URI): number {
    if (
      uri.path.ext === '.coffee'
    ) {
      return 1000;
    }
    return 0;
  }

  registerCommands(commands: CommandRegistry): void {
    commands.registerCommand(
      JsonFormsTreeCommands.OPEN_WORKFLOW_DIAGRAM,
      new OpenWorkflowDiagramCommandHandler(this.shell, this.opener));

    this.getCommandMap().forEach((value, property, _map) => {
      value.forEach((command, eClass) => commands.registerCommand(command, new AddCommandHandler(property, eClass)));
    });
  }

  registerMenus(menus: MenuModelRegistry): void {
    menus.registerMenuAction(JsonFormsTreeContextMenu.CONTEXT_MENU, {
      commandId: JsonFormsTreeCommands.OPEN_WORKFLOW_DIAGRAM.id,
      label: JsonFormsTreeCommands.OPEN_WORKFLOW_DIAGRAM.label
    });

    this.getCommandMap().forEach((value, _property, _map) => {
      value.forEach((command, eClass) => {
        menus.registerMenuAction(JsonFormsTreeContextMenu.ADD_MENU, {
          commandId: command.id,
          label: command.label,
          icon: this.labelProvider.getIconClass(eClass)
        });
      });
    });
  }

}
