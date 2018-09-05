/**
 * Generated using theia-extension-generator
 */
import { ThemeService } from '@theia/core/lib/browser/theming';
import { LanguageClientContribution } from '@theia/languages/lib/browser';
import { ContainerModule } from "inversify";
import { WorkflowClientContribution } from './language-contribution';

const LIGHT_THEME_ID = "light"
export default new ContainerModule(bind => {
    ThemeService.get().setCurrentTheme(LIGHT_THEME_ID)

    bind(WorkflowClientContribution).toSelf().inSingletonScope();
    bind(LanguageClientContribution).toDynamicValue(ctx => ctx.container.get(WorkflowClientContribution))
});