/*
 * Copyright (C) 2017 TypeFox and others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 */

import { ConnectionHandler, JsonRpcConnectionHandler } from "@theia/core";
import { CodeGenServer, CODEGEN_SERVICE_PATH } from "../common/generate-protocol";
import { CoffeeCodeGenServer } from "./coffee-codegen-server";
import { ContainerModule } from "inversify";

export default new ContainerModule(bind => {
    bind(CodeGenServer).to(CoffeeCodeGenServer).inSingletonScope()
    bind(ConnectionHandler).toDynamicValue(ctx =>
        new JsonRpcConnectionHandler(CODEGEN_SERVICE_PATH, () => {
            return ctx.container.get<CodeGenServer>(CodeGenServer);
        })
    ).inSingletonScope();
});


