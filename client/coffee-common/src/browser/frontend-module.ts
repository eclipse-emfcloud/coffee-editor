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
import '../../src/browser/css/branding.css';

import { FrontendApplicationContribution } from '@theia/core/lib/browser';
import { ContainerModule } from '@theia/core/shared/inversify';
import { CoffeeCommonFrontendContribution } from './frontend-contribution';

export default new ContainerModule(bind => {
    bind(FrontendApplicationContribution).to(CoffeeCommonFrontendContribution).inSingletonScope();
});
