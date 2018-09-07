/**
 * Generated using theia-extension-generator
 */

import { ContainerModule } from "inversify";
import { JavaGenerationCommandContribution } from './command-contribution';
import { CommandContribution, MenuContribution } from '@theia/core';
import { JUnitRunService } from "./junit-run-service";
import { GenerateCodeService } from "./generate-code-service";

export default new ContainerModule(bind => {
    bind(JUnitRunService).toSelf().inSingletonScope();
    bind(GenerateCodeService).toSelf().inSingletonScope();
    bind(JavaGenerationCommandContribution).toSelf().inSingletonScope();
    [CommandContribution, MenuContribution].forEach(s => bind(s).to(JavaGenerationCommandContribution));
});
