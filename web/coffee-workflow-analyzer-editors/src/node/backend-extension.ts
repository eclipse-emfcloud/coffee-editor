/*!
 * Copyright (c) 2019-2020 EclipseSource and others.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0, or the MIT License which is
 * available at https://opensource.org/licenses/MIT.
 *
 * SPDX-License-Identifier: EPL-2.0 OR MIT
 */
import { LanguageServerContribution } from '@theia/languages/lib/node';
import { ContainerModule } from 'inversify';

import { WorkflowContribution } from './language-contribution';

export default new ContainerModule(bind => {
    bind(LanguageServerContribution).to(WorkflowContribution).inSingletonScope();
});
