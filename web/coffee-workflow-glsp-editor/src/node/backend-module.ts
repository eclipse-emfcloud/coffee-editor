/*
 * Copyright (c) 2019 EclipseSource and others.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0, or the MIT License which is
 * available at https://opensource.org/licenses/MIT.
 *
 * SPDX-License-Identifier: EPL-2.0 OR MIT
 */
import { GLSPServerContribution } from '@eclipse-glsp/theia-integration/lib/node';
import { ContainerModule } from 'inversify';

import { WorkflowGLSPServerContribution } from './workflow-glsp-server-contribution';

export default new ContainerModule(bind => {
    bind(GLSPServerContribution).to(WorkflowGLSPServerContribution).inSingletonScope();
});
