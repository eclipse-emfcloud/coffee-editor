/*!
 * Copyright (c) 2019-2020 EclipseSource and others.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0, or the MIT License which is
 * available at https://opensource.org/licenses/MIT.
 *
 * SPDX-License-Identifier: EPL-2.0 OR MIT
 */
import { ConnectionHandler, JsonRpcConnectionHandler } from '@theia/core';
import { BackendApplicationContribution } from '@theia/core/lib/node';
import { ContainerModule } from 'inversify';

import { FileClient, filePath, FileServer } from '../common/request-file-protocol';
import { WorkflowAnalysisClient, workflowServicePath } from '../common/workflow-analyze-protocol';
import { WorkflowFileServer } from './file-server';
import { WorkflowAnalysisServer } from './workflow-analysis-server';

export default new ContainerModule(bind => {
    bind(FileServer).to(WorkflowFileServer).inSingletonScope();
    bind(ConnectionHandler).toDynamicValue(ctx =>
        new JsonRpcConnectionHandler<FileClient>(filePath, client => {
            const fileServer = ctx.container.get<FileServer>(FileServer);
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
