/*
 * Copyright (c) 2020 EclipseSource and others.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0, or the MIT License which is
 * available at https://opensource.org/licenses/MIT.
 *
 * SPDX-License-Identifier: EPL-2.0 OR MIT
 */
import { AbstractViewContribution, CommonMenus, FrontendApplication, FrontendApplicationContribution } from '@theia/core/lib/browser';
import { FrontendApplicationStateService } from '@theia/core/lib/browser/frontend-application-state';
import { CommandRegistry, MaybePromise, MenuModelRegistry } from '@theia/core/lib/common';
import { FileNavigatorContribution } from '@theia/navigator/lib/browser/navigator-contribution';
import { inject, injectable } from 'inversify';

import { WelcomePageWidget } from './welcome-page-widget';

export const WelcomePageCommand = {
    id: WelcomePageWidget.ID,
    label: WelcomePageWidget.LABEL
};

@injectable()
export class CoffeeWelcomePageContribution extends AbstractViewContribution<WelcomePageWidget> implements FrontendApplicationContribution {
    @inject(FrontendApplicationStateService)
    protected readonly stateService: FrontendApplicationStateService;

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
        this.stateService.reachedState('ready').then(a => this.openView({ reveal: true }));
    }

    initializeLayout(app: FrontendApplication): MaybePromise<void> {
        this.fileNavigatorContribution.openView({ reveal: true });
    }

    override registerCommands(registry: CommandRegistry): void {
        registry.registerCommand(WelcomePageCommand, {
            execute: () => this.openView({ reveal: true })
        });
    }

    override registerMenus(menus: MenuModelRegistry): void {
        menus.registerMenuAction(CommonMenus.HELP, {
            commandId: WelcomePageCommand.id,
            label: WelcomePageCommand.label,
            order: 'a11'
        });
    }
}
