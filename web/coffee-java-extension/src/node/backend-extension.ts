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
import { ConnectionHandler, JsonRpcConnectionHandler } from '@theia/core';
import { BackendApplicationContribution } from '@theia/core/lib/node';
import { ContainerModule } from 'inversify';

import { CODEGEN_SERVICE_PATH, CodeGenServer } from '../common/generate-protocol';
import { CoffeeCodeGenServer } from './coffee-codegen-server';

export default new ContainerModule(bind => {
    bind(CoffeeCodeGenServer).toSelf().inSingletonScope();
    bind(BackendApplicationContribution).toService(CoffeeCodeGenServer);
    bind(ConnectionHandler).toDynamicValue(ctx =>
        new JsonRpcConnectionHandler(CODEGEN_SERVICE_PATH, () =>
            ctx.container.get<CodeGenServer>(CoffeeCodeGenServer))
    ).inSingletonScope();
});
