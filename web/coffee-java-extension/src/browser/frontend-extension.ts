/**
 * Generated using theia-extension-generator
 */

import { ContainerModule } from "inversify";
import { WorkflowCommandContribution } from './command-contribution';
import { CommandContribution, MenuContribution } from '@theia/core';

export default new ContainerModule(bind => {
    bind(WorkflowCommandContribution).toSelf().inSingletonScope();
    bind(CommandContribution).toDynamicValue(ctx => ctx.container.get(WorkflowCommandContribution)).inSingletonScope();
    bind(MenuContribution).toDynamicValue(ctx => ctx.container.get(WorkflowCommandContribution)).inSingletonScope();
});
