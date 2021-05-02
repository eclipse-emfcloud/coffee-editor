/*!
 * Copyright (c) 2021 EclipseSource and others.
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

import { CODEGEN_SERVICE_PATH, CodeGenCppServer } from '../common/generate-protocol';
import { CppGenerationCommandContribution } from './command-contribution';
import { GenerateCppCodeService } from './generate-code-service';

export default new ContainerModule(bind => {
    bind(GenerateCppCodeService).toSelf().inSingletonScope();
    bind(CppGenerationCommandContribution).toSelf().inSingletonScope();
    [CommandContribution, MenuContribution].forEach(s => bind(s).toService(CppGenerationCommandContribution));
    bind(CodeGenCppServer).toDynamicValue(ctx => {
        const connection = ctx.container.get(WebSocketConnectionProvider);
        return connection.createProxy<CodeGenCppServer>(CODEGEN_SERVICE_PATH);
    }).inSingletonScope();
});
