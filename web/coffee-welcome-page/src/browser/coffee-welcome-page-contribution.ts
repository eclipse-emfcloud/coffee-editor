/*!
 * Copyright (C) 2020 EclipseSource and others.
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

import { injectable, inject } from 'inversify';
import { WelcomePageWidget } from './welcome-page-widget';
import { CommandRegistry, MenuModelRegistry, MaybePromise } from '@theia/core/lib/common';
import { CommonMenus, AbstractViewContribution, FrontendApplicationContribution, FrontendApplication } from '@theia/core/lib/browser';
import { FrontendApplicationStateService } from '@theia/core/lib/browser/frontend-application-state';
import { WorkspaceService } from '@theia/workspace/lib/browser';
import { FileNavigatorContribution } from '@theia/navigator/lib/browser/navigator-contribution';

export const WelcomePageCommand = {
    id: WelcomePageWidget.ID,
    label: WelcomePageWidget.LABEL
};

@injectable()
export class CoffeeWelcomePageContribution extends AbstractViewContribution<WelcomePageWidget> implements FrontendApplicationContribution {
    @inject(FrontendApplicationStateService)
    protected readonly stateService: FrontendApplicationStateService;

    @inject(WorkspaceService)
    protected readonly workspaceService: WorkspaceService;

    @inject(FileNavigatorContribution)
    protected readonly fileNavigatorContribution: FileNavigatorContribution;

    constructor() {
        super({
            widgetId: WelcomePageWidget.ID,
            widgetName: WelcomePageWidget.LABEL,
            defaultWidgetOptions: {
                area: 'right',
                rank: 1000
            }
        });
    }

    async onStart(app: FrontendApplication): Promise<void> {
        this.stateService.reachedState('ready').then(
            a => this.openView({ reveal: true })
        );
    }

    initializeLayout(app: FrontendApplication): MaybePromise<void> {
        this.fileNavigatorContribution.openView({ reveal: true });
    }

    registerCommands(registry: CommandRegistry): void {
        registry.registerCommand(WelcomePageCommand, {
            execute: () => this.openView({ reveal: true }),
        });
    }

    registerMenus(menus: MenuModelRegistry): void {
        menus.registerMenuAction(CommonMenus.HELP, {
            commandId: WelcomePageCommand.id,
            label: WelcomePageCommand.label,
            order: 'a10'
        });
    }
}
