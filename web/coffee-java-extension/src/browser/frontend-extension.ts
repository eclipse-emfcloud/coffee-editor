/**
 * Generated using theia-extension-generator
 */

import { ContainerModule } from "inversify";
import { JavaGenerationCommandContribution } from './command-contribution';
import { CommandContribution, MenuContribution } from '@theia/core';

export default new ContainerModule(bind => {
    bind(JavaGenerationCommandContribution).toSelf().inSingletonScope();
    bind(CommandContribution).toDynamicValue(ctx => ctx.container.get(JavaGenerationCommandContribution)).inSingletonScope();
    bind(MenuContribution).toDynamicValue(ctx => ctx.container.get(JavaGenerationCommandContribution)).inSingletonScope();
});
