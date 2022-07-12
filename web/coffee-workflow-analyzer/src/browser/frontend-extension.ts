/*
 * Copyright (c) 2019-2020 EclipseSource and others.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0, or the MIT License which is
 * available at https://opensource.org/licenses/MIT.
 *
 * SPDX-License-Identifier: EPL-2.0 OR MIT
 */
import { CommandContribution, MenuContribution } from '@theia/core';
import { WebSocketConnectionProvider } from '@theia/core/lib/browser';
import { LocationMapper } from '@theia/mini-browser/lib/browser/location-mapper-service';
import { ContainerModule, injectable } from 'inversify';

import { FileClient, filePath, FileServer } from '../common/request-file-protocol';
import { WorkflowAnalyzer, workflowServicePath } from '../common/workflow-analyze-protocol';
import { AnalysisService, WorkflowAnalysisClientImpl } from './analysis-service';
import { WorkflowCommandContribution } from './command-contribution';
import { WorkflowFileLocationMapper } from './location-mapper';

export default new ContainerModule(bind => {
    bind(AnalysisService).toSelf().inSingletonScope();
    bind(WorkflowCommandContribution).toSelf().inSingletonScope();

    bind(WorkflowFileClient).toSelf();
    bind(FileServer)
        .toDynamicValue(ctx => {
            const connection = ctx.container.get(WebSocketConnectionProvider);
            const client = ctx.container.get(WorkflowFileClient);
            return connection.createProxy<FileServer>(filePath, client);
        })
        .inSingletonScope();

    bind(WorkflowAnalysisClientImpl).toSelf().inSingletonScope();
    bind(WorkflowAnalyzer)
        .toDynamicValue(ctx => {
            const connection = ctx.container.get(WebSocketConnectionProvider);
            const client = ctx.container.get(WorkflowAnalysisClientImpl);
            return connection.createProxy<WorkflowAnalyzer>(workflowServicePath, client);
        })
        .inSingletonScope();

    bind(LocationMapper).to(WorkflowFileLocationMapper);

    [CommandContribution, MenuContribution].forEach(s => bind(s).to(WorkflowCommandContribution));
});
@injectable()
export class WorkflowFileClient implements FileClient {}
