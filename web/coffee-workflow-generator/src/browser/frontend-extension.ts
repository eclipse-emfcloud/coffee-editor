/**
 * Generated using theia-extension-generator
 */
import { ContainerModule } from "inversify";
import { WorkflowGenerateCommandContribution } from './command-contribution';
import { CommandContribution, MenuContribution } from '@theia/core';

export default new ContainerModule(bind => {
    bind(WorkflowGenerateCommandContribution).toSelf().inSingletonScope();
    bind(CommandContribution).toDynamicValue(ctx => ctx.container.get(WorkflowGenerateCommandContribution)).inSingletonScope();
    bind(MenuContribution).toDynamicValue(ctx => ctx.container.get(WorkflowGenerateCommandContribution)).inSingletonScope();
});
