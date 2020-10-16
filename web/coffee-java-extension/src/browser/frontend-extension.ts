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
import { CommandContribution, MenuContribution } from '@theia/core';
import { WebSocketConnectionProvider } from '@theia/core/lib/browser';
import { ContainerModule } from 'inversify';

import { CODEGEN_SERVICE_PATH, CodeGenServer } from '../common/generate-protocol';
import { JavaGenerationCommandContribution } from './command-contribution';
import { GenerateCodeService } from './generate-code-service';
import { JUnitRunService } from './junit-run-service';

export default new ContainerModule(bind => {
    bind(JUnitRunService).toSelf().inSingletonScope();
    bind(GenerateCodeService).toSelf().inSingletonScope();
    bind(JavaGenerationCommandContribution).toSelf().inSingletonScope();
    [CommandContribution, MenuContribution].forEach(s => bind(s).toService(JavaGenerationCommandContribution));
    bind(CodeGenServer).toDynamicValue(ctx => {
        const connection = ctx.container.get(WebSocketConnectionProvider);
        return connection.createProxy<CodeGenServer>(CODEGEN_SERVICE_PATH);
    }).inSingletonScope();
});
