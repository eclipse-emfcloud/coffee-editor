/********************************************************************************
 * Copyright (c) 2021 EclipseSource and others.
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
import '../../../style/index.css';
import '../../../style/elements.css';

import { ContainerModule } from 'inversify';
import { bindViewContribution, WidgetFactory } from '@theia/core/lib/browser';
import { GraphicalComparisonWidget, GraphicalComparisonWidgetOptions } from './graphical-comparison-widget';
import { GraphicalComparisonContribution } from './graphical-comparison-contribution';
import { TabBarToolbarContribution } from '@theia/core/lib/browser/shell/tab-bar-toolbar';

export default new ContainerModule(bind => {
    bindViewContribution(bind, GraphicalComparisonContribution);
    bind(TabBarToolbarContribution).toService(GraphicalComparisonContribution);
    
    bind<WidgetFactory>(WidgetFactory).toDynamicValue(context => ({
        id: GraphicalComparisonWidget.WIDGET_ID,
        createWidget: (options: GraphicalComparisonWidgetOptions) => {
            const container = context.container.createChild();
            container.bind(GraphicalComparisonWidgetOptions).toConstantValue(options);
            container.bind(GraphicalComparisonWidget).toSelf();
            return container.get(GraphicalComparisonWidget);
        }
    }));
});
