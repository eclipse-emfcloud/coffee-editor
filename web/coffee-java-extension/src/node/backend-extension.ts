import { ConnectionHandler, JsonRpcConnectionHandler } from "@theia/core";
import { BackendApplicationContribution } from "@theia/core/lib/node";
import { ContainerModule } from "inversify";

import { CODEGEN_SERVICE_PATH, CodeGenServer } from "../common/generate-protocol";
import { CoffeeCodeGenServer } from "./coffee-codegen-server";

export default new ContainerModule(bind => {
    bind(CoffeeCodeGenServer).toSelf().inSingletonScope();
    bind(BackendApplicationContribution).toService(CoffeeCodeGenServer);
    bind(ConnectionHandler).toDynamicValue(ctx =>
        new JsonRpcConnectionHandler(CODEGEN_SERVICE_PATH, () => {
            return ctx.container.get<CodeGenServer>(CoffeeCodeGenServer);
        })
    ).inSingletonScope();
});


