/**
 * Generated using theia-extension-generator
 */

import { CommandContribution, MenuContribution } from '@theia/core';
import { WebSocketConnectionProvider } from "@theia/core/lib/browser";
import { CodeGenServer, CODEGEN_SERVICE_PATH } from "../common/generate-protocol";
import { ContainerModule } from "inversify";
import { JavaGenerationCommandContribution } from './command-contribution';
import { GenerateCodeService } from "./generate-code-service";
import { JUnitRunService } from "./junit-run-service";

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