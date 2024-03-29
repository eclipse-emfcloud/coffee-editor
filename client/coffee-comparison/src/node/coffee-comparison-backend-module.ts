/*
 * Copyright (c) 2021-2022 EclipseSource and others.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0, or the MIT License which is
 * available at https://opensource.org/licenses/MIT.
 *
 * SPDX-License-Identifier: EPL-2.0 OR MIT
 */
import { ComparisonExtensionConfiguration } from '@eclipsesource/comparison-extension/lib/browser/comparison-extension-configuration';
import { ContainerModule } from 'inversify';

import { CoffeeComparisonBackendConfiguration } from './coffee-comparison-backend-configuration';

export default new ContainerModule(bind => {
    bind(ComparisonExtensionConfiguration).to(CoffeeComparisonBackendConfiguration).inSingletonScope();
});
