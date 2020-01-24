/********************************************************************************
 * Copyright (C) 2018 Ericsson and others.
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
 ********************************************************************************/
import { CommandRegistry, MenuModelRegistry } from '@theia/core';
import {
    AbstractViewContribution,
    CommonCommands,
    CommonMenus,
    KeybindingRegistry,
    PreferenceScope,
} from '@theia/core/lib/browser';
import { EditorManager } from '@theia/editor/lib/browser';
import { FileSystem } from '@theia/filesystem/lib/common';
import { inject, injectable } from 'inversify';

import { PreferenceEditorContainer } from './preference-editor-container';

// import { UserStorageService } from '@theia/userstorage/lib/browser';
// import { WorkspacePreferenceProvider } from './workspace-preference-provider';

@injectable()
export class PreferenceEditorContribution extends AbstractViewContribution<PreferenceEditorContainer> {

    // @inject(UserStorageService) protected readonly userStorageService: UserStorageService;
    // @inject(PreferenceProvider) @named(PreferenceScope.Workspace) protected readonly workspacePreferenceProvider: WorkspacePreferenceProvider;
    @inject(FileSystem) protected readonly filesystem: FileSystem;
    @inject(EditorManager) protected readonly editorManager: EditorManager;

    constructor() {
        super({
            widgetId: PreferenceEditorContainer.ID,
            widgetName: 'Custom Preferences',
            defaultWidgetOptions: { area: 'main' }
        });
    }

    async registerCommands(commands: CommandRegistry): Promise<void> {
        commands.registerCommand(CommonCommands.OPEN_PREFERENCES, {
            isEnabled: () => true,
            execute: (preferenceScope = PreferenceScope.User) => this.openPreferences(preferenceScope)
        });
    }

    registerMenus(menus: MenuModelRegistry): void {
        menus.registerMenuAction(CommonMenus.FILE_SETTINGS_SUBMENU_OPEN, {
            commandId: CommonCommands.OPEN_PREFERENCES.id,
            order: 'a10'
        });
    }

    registerKeybindings(keybindings: KeybindingRegistry): void {
        keybindings.registerKeybinding({
            command: CommonCommands.OPEN_PREFERENCES.id,
            keybinding: 'ctrl+,'
        });
    }

    protected async openPreferences(preferenceScope: PreferenceScope): Promise<void> {
        // const wsUri = this.workspacePreferenceProvider.getConfigUri();
        // if (wsUri && !await this.filesystem.exists(wsUri.toString())) {
        //     await this.filesystem.createFile(wsUri.toString());
        // }

        await this.widget;
        // widget.preferenceScope = preferenceScope;
        super.openView({ activate: true });
        // widget.activatePreferenceEditor(preferenceScope);
    }

}
