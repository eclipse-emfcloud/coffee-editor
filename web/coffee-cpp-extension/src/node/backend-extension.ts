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
import { ConnectionHandler, JsonRpcConnectionHandler } from '@theia/core';
import { BackendApplicationContribution } from '@theia/core/lib/node';
import { ContainerModule } from 'inversify';

import { CODEGEN_SERVICE_PATH, CodeGenCppServer } from '../common/generate-protocol';
import { CoffeeCodeGenCppServer } from './coffee-codegen-server';

export default new ContainerModule(bind => {
    bind(CoffeeCodeGenCppServer).toSelf().inSingletonScope();
    bind(BackendApplicationContribution).toService(CoffeeCodeGenCppServer);
    bind(ConnectionHandler).toDynamicValue(ctx =>
        new JsonRpcConnectionHandler(CODEGEN_SERVICE_PATH, () =>
            ctx.container.get<CodeGenCppServer>(CoffeeCodeGenCppServer))
    ).inSingletonScope();
});
