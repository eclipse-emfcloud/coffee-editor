/**
 * Generated using theia-extension-generator
 */
import { ContainerModule } from "inversify";
import { WorkflowCommandContribution } from './command-contribution';
import { CommandContribution, MenuContribution } from '@theia/core';
import { ThemeService } from '@theia/core/lib/browser/theming'

const LIGHT_THEME_ID = "light"
export default new ContainerModule(bind => {
    ThemeService.get().setCurrentTheme(LIGHT_THEME_ID)

    bind(WorkflowCommandContribution).toSelf().inSingletonScope();
    bind(CommandContribution).toDynamicValue(ctx => ctx.container.get(WorkflowCommandContribution)).inSingletonScope();
    bind(MenuContribution).toDynamicValue(ctx => ctx.container.get(WorkflowCommandContribution)).inSingletonScope();

});
