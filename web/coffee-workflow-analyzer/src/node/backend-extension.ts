import { ConnectionHandler, JsonRpcConnectionHandler } from "@theia/core";
import { BackendApplicationContribution } from "@theia/core/lib/node";
import { ContainerModule } from "inversify";

import { filePath, IFileClient, IFileServer } from "../common/request-file-protocol";
import { WorkflowAnalysisClient, workflowServicePath } from "../common/workflow-analyze-protocol";
import { WorkflowFileServer } from "./file-server";
import { WorkflowAnalysisServer } from "./workflow-analysis-server";

export default new ContainerModule(bind => {
    bind(IFileServer).to(WorkflowFileServer).inSingletonScope()
    bind(ConnectionHandler).toDynamicValue(ctx =>
        new JsonRpcConnectionHandler<IFileClient>(filePath, client => {
            const fileServer = ctx.container.get<IFileServer>(IFileServer);
            fileServer.setClient(client);
            return fileServer;
        })
    ).inSingletonScope();

    bind(WorkflowAnalysisServer).toSelf().inSingletonScope();
    bind(BackendApplicationContribution).toService(WorkflowAnalysisServer);

    bind(ConnectionHandler).toDynamicValue(ctx =>
        new JsonRpcConnectionHandler<WorkflowAnalysisClient>(workflowServicePath, client => {
            const analysisServer = ctx.container.get<WorkflowAnalysisServer>(WorkflowAnalysisServer);
            analysisServer.setClient(client);
            return analysisServer;
        })
    ).inSingletonScope();
});



