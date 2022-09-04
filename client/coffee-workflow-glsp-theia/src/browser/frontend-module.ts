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
import {
    ContainerContext,
    GLSPClientContribution,
    GLSPTheiaFrontendModule,
    registerDiagramManager,
    TheiaGLSPConnector
} from '@eclipse-glsp/theia-integration/lib/browser';
import { CommandContribution, MenuContribution } from '@theia/core';
import { LabelProviderContribution } from '@theia/core/lib/browser';
import { DiagramConfiguration } from 'sprotty-theia/lib';

import { WorkflowNotationLanguage } from '../common/workflow-language';
import { WorkflowDiagramConfiguration } from './diagram/diagram-configuration';
import { WorkflowDiagramLabelProviderContribution } from './diagram/diagram-label-provider-contribution';
import { WorkflowDiagramManager } from './diagram/diagram-manager';
import { WorkflowTaskEditCommandContribution, WorkflowTaskEditMenuContribution } from './diagram/task-editing-context-menu';
import { WorkflowTheiaGLSPConnector } from './diagram/theia-glsp-connector';
import { WorkflowGLSPClientContribution } from './workflow-glsp-client-contribution';

export class WorkflowTheiaFrontendModule extends GLSPTheiaFrontendModule {
    readonly diagramLanguage = WorkflowNotationLanguage;

    override bindTheiaGLSPConnector(context: ContainerContext): void {
        context.bind(TheiaGLSPConnector).toDynamicValue(dynamicContext => {
            const connector = dynamicContext.container.resolve(WorkflowTheiaGLSPConnector);
            connector.doConfigure(this.diagramLanguage);
            return connector;
        });
    }

    bindDiagramConfiguration(context: ContainerContext): void {
        context.bind(DiagramConfiguration).to(WorkflowDiagramConfiguration);
    }

    override bindGLSPClientContribution(context: ContainerContext): void {
        context.bind(GLSPClientContribution).to(WorkflowGLSPClientContribution);
    }

    override configure(context: ContainerContext): void {
        // custom workflow commands and menus
        context.bind(CommandContribution).to(WorkflowTaskEditCommandContribution);
        context.bind(MenuContribution).to(WorkflowTaskEditMenuContribution);
        // custom workflow label provider
        context.bind(LabelProviderContribution).to(WorkflowDiagramLabelProviderContribution);
    }

    override configureDiagramManager(context: ContainerContext): void {
        registerDiagramManager(context.bind, WorkflowDiagramManager);
    }
}

export default new WorkflowTheiaFrontendModule();
