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
import 'sprotty-theia/css/theia-sprotty.css';

import { labelEditModule } from '@eclipse-glsp/client/lib';
import { configureDiagramServer, GLSPDiagramConfiguration, TheiaDiagramServer } from '@eclipse-glsp/theia-integration';
import {
    connectTheiaMarkerManager,
    TheiaMarkerManager,
    TheiaMarkerManagerFactory
} from '@eclipse-glsp/theia-integration/lib/browser/diagram/theia-marker-manager';
import { createWorkflowDiagramContainer } from 'coffee-workflow-glsp';
import { Container, inject, injectable } from 'inversify';

import { WorkflowNotationLanguage } from '../../common/workflow-language';
import { WorkflowGLSPTheiaDiagramServer } from './workflow-diagram-server';

@injectable()
export class WorkflowDiagramConfiguration extends GLSPDiagramConfiguration {
    @inject(TheiaMarkerManagerFactory) protected readonly theiaMarkerManager: () => TheiaMarkerManager;

    diagramType: string = WorkflowNotationLanguage.diagramType;

    doCreateContainer(widgetId: string): Container {
        const container = createWorkflowDiagramContainer(widgetId);
        container.load(labelEditModule);
        configureDiagramServer(container, WorkflowGLSPTheiaDiagramServer);
        connectTheiaMarkerManager(container, this.theiaMarkerManager, this.diagramType);
        container.bind(TheiaDiagramServer).toService(WorkflowGLSPTheiaDiagramServer);
        return container;
    }
}
