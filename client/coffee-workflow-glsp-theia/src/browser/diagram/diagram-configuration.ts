/*
 * Copyright (c) 2019-2023 EclipseSource and others.
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
import { configureDiagramServer, GLSPDiagramConfiguration, GLSPTheiaDiagramServer } from '@eclipse-glsp/theia-integration/lib/browser';

import { connectTheiaMarkerManager } from '@eclipse-glsp/theia-integration/lib/browser/diagram/theia-marker-manager';
import { createWorkflowDiagramContainer } from 'coffee-workflow-glsp';
import { Container, injectable } from 'inversify';

import { WorkflowNotationLanguage } from '../../common/workflow-language';

@injectable()
export class WorkflowDiagramConfiguration extends GLSPDiagramConfiguration {
    diagramType: string = WorkflowNotationLanguage.diagramType;

    doCreateContainer(widgetId: string): Container {
        const container = createWorkflowDiagramContainer(widgetId);
        container.load(labelEditModule);
        configureDiagramServer(container, GLSPTheiaDiagramServer);
        connectTheiaMarkerManager(container, this.theiaMarkerManager, this.diagramType);
        return container;
    }
}
