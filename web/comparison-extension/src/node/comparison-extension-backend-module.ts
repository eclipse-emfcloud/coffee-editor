/********************************************************************************
 * Copyright (c) 2021 EclipseSource and others.
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
 ********************************************************************************/
import { ConnectionHandler, JsonRpcConnectionHandler } from "@theia/core";
import { ContainerModule } from "inversify";
import { ComparisonExtensionConfiguration } from "../browser/comparison-extension-configuration";
import { BackendClient, COMPARISON_BACKEND_PATH, ComparisonBackendService} from "../common/protocol";
import { ComparisonBackendServiceImpl } from "./comparison-backend-service";
import { ComparisonServerExtensionConnection } from "./comparison-server-extension-connection";

export default new ContainerModule(bind => {
    console.log("starting backend");
    bind(ComparisonBackendService).to(ComparisonBackendServiceImpl).inSingletonScope()
    bind(ConnectionHandler).toDynamicValue(ctx =>
        new JsonRpcConnectionHandler<BackendClient>(COMPARISON_BACKEND_PATH, client => {
            return ctx.container.get<ComparisonBackendServiceImpl>(ComparisonBackendService);
        })
    ).inSingletonScope();

    bind(ComparisonExtensionConfiguration).toSelf().inSingletonScope();
    bind(ComparisonServerExtensionConnection).toSelf().inSingletonScope();
});


    