/*
 * Copyright (C) 2017 TypeFox and others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 */
import { ConnectionHandler, JsonRpcConnectionHandler } from "@theia/core";
import { ContainerModule } from "inversify";

import { filePath, IFileClient, IFileServer } from "../common/request-file-protocol";
import { WorkflowAnalysisClient, WorkflowAnalyzer, workflowServicePath } from "../common/workflow-analyze-protocol";
import { WorkflowAnalyzerServer } from "./analyze";
import { WorkflowFileServer } from "./file-server";

export default new ContainerModule(bind => {
    bind(IFileServer).to(WorkflowFileServer).inSingletonScope()
    bind(ConnectionHandler).toDynamicValue(ctx =>
        new JsonRpcConnectionHandler<IFileClient>(filePath, client => {
            const fileServer = ctx.container.get<IFileServer>(IFileServer);
            fileServer.setClient(client);
            return fileServer;
        })
    ).inSingletonScope();

    bind(WorkflowAnalyzer).to(WorkflowAnalyzerServer).inSingletonScope();
    bind(ConnectionHandler).toDynamicValue(ctx =>
        new JsonRpcConnectionHandler<WorkflowAnalysisClient>(workflowServicePath, client => {
            const analysisServer = ctx.container.get<WorkflowAnalyzer>(WorkflowAnalyzer);
            analysisServer.setClient(client);
            return analysisServer;
        })
    ).inSingletonScope();
});



