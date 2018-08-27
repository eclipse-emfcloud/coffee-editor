/*******************************************************************************
 * Copyright (c) 2018 Tobias Ortmayr.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *   
 * Contributors:
 * 	Tobias Ortmayr - initial API and implementation
 ******************************************************************************/
import { DiagramConfiguration, TheiaKeyTool } from "theia-glsp/lib"
import { createWorkflowDiagramContainer } from "workflow-sprotty/lib"
import { TYPES, KeyTool } from "glsp-sprotty/lib"
import { Container, injectable } from "inversify";
import { WorkflowLanguage } from "../../common/workflow-language";
import { GLSPTheiaDiagramServer } from 'glsp-theia-extension/lib/browser'

@injectable()
export class WorkflowDiagramConfiguration implements DiagramConfiguration {
    diagramType: string = WorkflowLanguage.DiagramType

    createContainer(widgetId: string): Container {
        const container = createWorkflowDiagramContainer(widgetId);
        container.bind(TYPES.ModelSource).to(GLSPTheiaDiagramServer)
        container.rebind(KeyTool).to(TheiaKeyTool).inSingletonScope()
        return container;
    }

}