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
import '../../src/browser/style/index.css';

import { bindViewContribution, FrontendApplicationContribution, WidgetFactory } from '@theia/core/lib/browser';
import { ContainerModule } from 'inversify';

import { CoffeeWelcomePageContribution } from './coffee-welcome-page-contribution';
import { WelcomePageWidget } from './welcome-page-widget';

/**
 * Generated using theia-extension-generator
 */
export default new ContainerModule(bind => {
    bindViewContribution(bind, CoffeeWelcomePageContribution);
    bind(FrontendApplicationContribution).toService(CoffeeWelcomePageContribution);
    bind(WelcomePageWidget).toSelf();
    bind(WidgetFactory)
        .toDynamicValue(context => ({
            id: WelcomePageWidget.ID,
            createWidget: () => context.container.get<WelcomePageWidget>(WelcomePageWidget)
        }))
        .inSingletonScope();
});
