/*!
 * Copyright (C) 2019 EclipseSource and others.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * This Source Code may also be made available under the following Secondary
 * Licenses when the conditions for such availability set forth in the Eclipse
 * Public License v. 2.0 are satisfied: GNU General Public License, version 2
 * with the GNU Classpath Exception which is available at
 * https://www.gnu.org/software/classpath/license.html.
 *
 * SPDX-License-Identifier: EPL-2.0 OR GPL-2.0 WITH Classpath-exception-2.0
 */
import { ConnectionHandler, JsonRpcConnectionHandler } from '@theia/core';
import { BackendApplicationContribution } from '@theia/core/lib/node';
import { ContainerModule } from 'inversify';

import { filePath, IFileClient, IFileServer } from '../common/request-file-protocol';
import { WorkflowAnalysisClient, workflowServicePath } from '../common/workflow-analyze-protocol';
import { WorkflowFileServer } from './file-server';
import { WorkflowAnalysisServer } from './workflow-analysis-server';

export default new ContainerModule(bind => {
    bind(IFileServer).to(WorkflowFileServer).inSingletonScope();
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
