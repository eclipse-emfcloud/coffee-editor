/*
 * Copyright (c) 2019-2022 EclipseSource and others.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0, or the MIT License which is
 * available at https://opensource.org/licenses/MIT.
 *
 * SPDX-License-Identifier: EPL-2.0 OR MIT
 */
import { LaunchOptions } from '@eclipse-emfcloud/modelserver-theia/lib/node';
import { GLSPServerContribution } from '@eclipse-glsp/theia-integration/lib/node';
import { ConnectionHandler, JsonRpcConnectionHandler } from '@theia/core/lib/common/messaging';
import { BackendApplicationContribution } from '@theia/core/lib/node';
import { ContainerModule } from '@theia/core/shared/inversify';
import { CppCodeGenServer, CPP_CODEGEN_SERVICE_PATH } from 'coffee-cpp-extension/lib/common/generate-protocol';
import { JavaCodeGenServer, JAVA_CODEGEN_SERVICE_PATH } from 'coffee-java-extension/lib/common/generate-protocol';
import { WorkflowAnalysisClient, workflowServicePath } from 'coffee-workflow-analyzer/lib/common/workflow-analyze-protocol';

import { CoffeeCppCodeGenServer } from './cpp-codegen-server';
import { WorkflowGLSPServerContribution } from './glsp-server-contribution';
import { CoffeeJavaCodeGenServer } from './java-codegen-server';
import { WorkflowModelServerLaunchOptions } from './model-server-launch-options';
import { WorkflowAnalysisServer } from './workflow-analysis-server';
import { WorkflowLSPServer } from './workflow-dsl-lsp-server';

export default new ContainerModule((bind, _unbind, isBound, rebind) => {
    // Model Server
    if (isBound(LaunchOptions)) {
        rebind(LaunchOptions).to(WorkflowModelServerLaunchOptions).inSingletonScope();
    } else {
        bind(LaunchOptions).to(WorkflowModelServerLaunchOptions).inSingletonScope();
    }

    // GLSP Server
    bind(WorkflowGLSPServerContribution).toSelf().inSingletonScope();
    bind(GLSPServerContribution).toService(WorkflowGLSPServerContribution);

    // Workflow DSL LSP Server
    bind(WorkflowLSPServer).toSelf().inSingletonScope();
    bind(BackendApplicationContribution).toService(WorkflowLSPServer);

    // Workflow Analysis Server
    bind(WorkflowAnalysisServer).toSelf().inSingletonScope();
    bind(BackendApplicationContribution).toService(WorkflowAnalysisServer);
    bind(ConnectionHandler)
        .toDynamicValue(
            ctx =>
                new JsonRpcConnectionHandler<WorkflowAnalysisClient>(workflowServicePath, client => {
                    const analysisServer = ctx.container.get<WorkflowAnalysisServer>(WorkflowAnalysisServer);
                    analysisServer.setClient(client);
                    return analysisServer;
                })
        )
        .inSingletonScope();

    // Java Codegen Server
    bind(CoffeeJavaCodeGenServer).toSelf().inSingletonScope();
    bind(BackendApplicationContribution).toService(CoffeeJavaCodeGenServer);
    bind(ConnectionHandler)
        .toDynamicValue(
            ctx =>
                new JsonRpcConnectionHandler(JAVA_CODEGEN_SERVICE_PATH, () => ctx.container.get<JavaCodeGenServer>(CoffeeJavaCodeGenServer))
        )
        .inSingletonScope();

    // CPP Codegen Server
    bind(CoffeeCppCodeGenServer).toSelf().inSingletonScope();
    bind(BackendApplicationContribution).toService(CoffeeCppCodeGenServer);
    bind(ConnectionHandler)
        .toDynamicValue(
            ctx => new JsonRpcConnectionHandler(CPP_CODEGEN_SERVICE_PATH, () => ctx.container.get<CppCodeGenServer>(CoffeeCppCodeGenServer))
        )
        .inSingletonScope();
});
