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
import { CommandContribution } from '@theia/core/lib/common';
import { ComparisonExtensionConfiguration } from '@eclipsesource/comparison-extension/lib/browser/comparison-extension-configuration';
import { GraphicalComparisonOpener } from '@eclipsesource/comparison-extension/lib/browser/graphical/graphical-comparison-opener';
import { ContainerModule, interfaces } from 'inversify';

import { CoffeeComparisonFrontendConfiguration } from './coffee-comparison-frontend-configuration';
import { CoffeeGitIntegration } from './coffee-git-integration';
import { CoffeeGraphicalComparisonOpener } from './coffee-graphical-comparison-opener';

/**
 * Generated using theia-extension-generator
 */
export default new ContainerModule(
    (bind: interfaces.Bind, unbind: interfaces.Unbind, isBound: interfaces.IsBound, rebind: interfaces.Rebind) => {
        bind(ComparisonExtensionConfiguration).to(CoffeeComparisonFrontendConfiguration).inSingletonScope();
        rebind(GraphicalComparisonOpener).to(CoffeeGraphicalComparisonOpener);
        bind(CoffeeGitIntegration).toSelf().inSingletonScope();
        bind(CommandContribution).toService(CoffeeGitIntegration);
    }
);
