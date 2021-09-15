/*!
 * Copyright (C) 2021 EclipseSource and others.
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
import { CommandContribution } from '@theia/core/lib/common';
import { ComparisonExtensionConfiguration } from 'comparison-extension/lib/browser/comparison-extension-configuration';
import { GraphicalComparisonOpener } from 'comparison-extension/lib/browser/graphical/graphical-comparison-opener';
import { ContainerModule, interfaces } from 'inversify';

import { CoffeeComparisonExtensionConfiguration } from './coffee-comparison-extension-configuration';
import { CoffeeGitIntegration } from './coffee-git-integration';
import { CoffeeGraphicalComparisonOpener } from './coffee-graphical-comparison-opener';

/**
 * Generated using theia-extension-generator
 */
export default new ContainerModule((bind: interfaces.Bind, unbind: interfaces.Unbind, isBound: interfaces.IsBound, rebind: interfaces.Rebind) => {
    rebind(ComparisonExtensionConfiguration).to(
        CoffeeComparisonExtensionConfiguration
    );
    rebind(GraphicalComparisonOpener).to(CoffeeGraphicalComparisonOpener);
    bind(CoffeeGitIntegration).toSelf().inSingletonScope();
    bind(CommandContribution).toService(CoffeeGitIntegration);
});
