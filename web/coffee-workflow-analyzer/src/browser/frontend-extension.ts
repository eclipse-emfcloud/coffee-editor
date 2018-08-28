/**
 * Generated using theia-extension-generator
 */
import { LanguageClientContribution } from '@theia/languages/lib/browser';
import { WorkflowClientContribution } from './language-contribution';
import { ContainerModule, injectable } from "inversify";
import { WorkflowCommandContribution } from './command-contribution';
import { CommandContribution, MenuContribution } from '@theia/core';
import { ThemeService } from '@theia/core/lib/browser/theming'
import { OpenHandler, WebSocketConnectionProvider } from '@theia/core/lib/browser';
import { AnalysisEditorOpenHandler, WorkflowFileLocationMapper } from './editor-contribution';
import { LocationMapper } from '@theia/mini-browser/lib/browser/location-mapper-service';
import { IFileServer, filePath, IFileClient } from '../common/request-file-protocol';

const LIGHT_THEME_ID = "light"
export default new ContainerModule(bind => {
    ThemeService.get().setCurrentTheme(LIGHT_THEME_ID)
    bind(OpenHandler).to(AnalysisEditorOpenHandler)
    bind(LocationMapper).to(WorkflowFileLocationMapper)
    bind(WorkflowCommandContribution).toSelf().inSingletonScope();
    bind(CommandContribution).toDynamicValue(ctx => ctx.container.get(WorkflowCommandContribution)).inSingletonScope();
    bind(MenuContribution).toDynamicValue(ctx => ctx.container.get(WorkflowCommandContribution)).inSingletonScope();

    bind(WorkflowClientContribution).toSelf().inSingletonScope();
    bind(LanguageClientContribution).toDynamicValue(ctx => ctx.container.get(WorkflowClientContribution))

  

    bind(WorkflowFileClient).toSelf()
    bind(IFileServer).toDynamicValue(ctx => {
        const connection = ctx.container.get(WebSocketConnectionProvider);
        const client = ctx.container.get(WorkflowFileClient)
        return connection.createProxy<IFileServer>(filePath, client);
    }).inSingletonScope();

});

@injectable()
export class WorkflowFileClient implements IFileClient{

}