/**
 * Generated using theia-extension-generator
 */
import { CommandContribution, MenuContribution } from '@theia/core';
import { WebSocketConnectionProvider } from '@theia/core/lib/browser';
import { OpenHandler } from '@theia/core/lib/browser';
import { LocationMapper } from '@theia/mini-browser/lib/browser/location-mapper-service';

import { ContainerModule, injectable } from "inversify";
import { filePath, IFileClient, IFileServer } from '../common/request-file-protocol';
import { AnalysisService } from './analysis-service';
import { AnalysisEditorOpenHandler, WorkflowFileLocationMapper } from './editor-contribution';
import { WorkflowCommandContribution } from './command-contribution';
import { WorkflowAnalyzer, workflowServicePath } from '../common/workflow-analyze-protocol';

export default new ContainerModule(bind => {
    bind(AnalysisService).toSelf().inSingletonScope();
    bind(WorkflowCommandContribution).toSelf().inSingletonScope();

    bind(WorkflowFileClient).toSelf()
    bind(IFileServer).toDynamicValue(ctx => {
        const connection = ctx.container.get(WebSocketConnectionProvider);
        const client = ctx.container.get(WorkflowFileClient)
        return connection.createProxy<IFileServer>(filePath, client);
    }).inSingletonScope();

    bind(WorkflowAnalyzer).toDynamicValue(ctx => {
        const connection = ctx.container.get(WebSocketConnectionProvider);
        return connection.createProxy<WorkflowAnalyzer>(workflowServicePath);
    }).inSingletonScope();


    bind(OpenHandler).to(AnalysisEditorOpenHandler);
    bind(LocationMapper).to(WorkflowFileLocationMapper);

    [CommandContribution, MenuContribution].forEach(s => bind(s).to(WorkflowCommandContribution));

});
@injectable()
export class WorkflowFileClient implements IFileClient{

}