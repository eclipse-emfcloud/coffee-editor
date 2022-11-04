/*
 * Copyright (c) 2022 EclipseSource and others.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0, or the MIT License which is
 * available at https://opensource.org/licenses/MIT.
 *
 * SPDX-License-Identifier: EPL-2.0 OR MIT
 */
import { SetUIExtensionVisibilityAction } from '@eclipse-glsp/client';
import { GLSPCommandHandler, GLSPContextMenu } from '@eclipse-glsp/theia-integration';
import { CommandContribution, CommandRegistry, MenuContribution, MenuModelRegistry } from '@theia/core';
import { ApplicationShell } from '@theia/core/lib/browser';
import { inject, injectable } from '@theia/core/shared/inversify';
import { TaskEditor } from 'coffee-workflow-glsp/lib/direct-task-editing/direct-task-editor';
import { isTaskNode } from 'coffee-workflow-glsp/lib/model';

export namespace WorkflowTaskEditingCommands {
    export const EDIT_TASK = 'glsp-workflow-edit-task';
}

@injectable()
export class WorkflowTaskEditCommandContribution implements CommandContribution {
    @inject(ApplicationShell) protected readonly shell: ApplicationShell;
    registerCommands(commands: CommandRegistry): void {
        commands.registerCommand(
            { id: WorkflowTaskEditingCommands.EDIT_TASK, label: 'Direct Edit Task' },
            new GLSPCommandHandler(this.shell, {
                actions: context => [
                    SetUIExtensionVisibilityAction.create({
                        extensionId: TaskEditor.ID,
                        visible: true,
                        contextElementsId: [context.selectedElements[0].id]
                    })
                ],
                isEnabled: context => !context.isReadonly && context.selectedElements.filter(isTaskNode).length === 1
            })
        );
    }
}

@injectable()
export class WorkflowTaskEditMenuContribution implements MenuContribution {
    static readonly EDIT = GLSPContextMenu.MENU_PATH.concat('edit');
    registerMenus(menus: MenuModelRegistry): void {
        menus.registerMenuAction(WorkflowTaskEditMenuContribution.EDIT, { commandId: WorkflowTaskEditingCommands.EDIT_TASK });
    }
}
