/**
 * Generated using theia-extension-generator
 */
import { CommandContribution, MenuContribution } from "@theia/core";
import { OpenHandler, WebSocketConnectionProvider } from "@theia/core/lib/browser";
import { LocationMapper } from "@theia/mini-browser/lib/browser/location-mapper-service";
import { ContainerModule, injectable } from "inversify";

import { filePath, IFileClient, IFileServer } from "../common/request-file-protocol";
import { WorkflowAnalyzer, workflowServicePath } from "../common/workflow-analyze-protocol";
import { AnalysisService, WorkflowAnalysisClientImpl } from "./analysis-service";
import { WorkflowCommandContribution } from "./command-contribution";
import { AnalysisEditorOpenHandler, WorkflowFileLocationMapper } from "./editor-contribution";

export default new ContainerModule(bind => {
    bind(AnalysisService).toSelf().inSingletonScope();
    bind(WorkflowCommandContribution).toSelf().inSingletonScope();

    bind(WorkflowFileClient).toSelf()
    bind(IFileServer).toDynamicValue(ctx => {
        const connection = ctx.container.get(WebSocketConnectionProvider);
        const client = ctx.container.get(WorkflowFileClient)
        return connection.createProxy<IFileServer>(filePath, client);
    }).inSingletonScope();

    bind(WorkflowAnalysisClientImpl).toSelf().inSingletonScope();
    bind(WorkflowAnalyzer).toDynamicValue(ctx => {
        const connection = ctx.container.get(WebSocketConnectionProvider);
        const client = ctx.container.get(WorkflowAnalysisClientImpl)
        return connection.createProxy<WorkflowAnalyzer>(workflowServicePath, client);
    }).inSingletonScope();

    bind(OpenHandler).to(AnalysisEditorOpenHandler);
    bind(LocationMapper).to(WorkflowFileLocationMapper);

    [CommandContribution, MenuContribution].forEach(s => bind(s).to(WorkflowCommandContribution));

});
@injectable()
export class WorkflowFileClient implements IFileClient {

}