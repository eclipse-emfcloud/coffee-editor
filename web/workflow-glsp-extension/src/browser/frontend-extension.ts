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
import { ContainerModule, interfaces } from "inversify";
import { LanguageClientContribution } from "@theia/languages/lib/browser"
import { WorkflowGLClientContribution } from "./language/workflow-gl-client-contribution";
import { DiagramConfiguration, DiagramManagerProvider, DiagramManager } from "theia-glsp/lib";
import { WorkflowDiagramConfiguration } from "./diagram/di.config";
import { WorkflowDiagramManager } from "./diagram/workflow-diagram-manager.";
import { WorkflowLanguage } from "../common/workflow-language";
import { FrontendApplicationContribution, OpenHandler } from "@theia/core/lib/browser";
import { ThemeManager } from "./diagram/thememanager";
import { GraphicalLanguageClientContribution } from "glsp-theia-extension/lib/browser";

export default new ContainerModule((bind: interfaces.Bind, unbind: interfaces.Unbind, isBound: interfaces.IsBound, rebind: interfaces.Rebind) => {
    bind(WorkflowGLClientContribution).toSelf().inSingletonScope()
    bind(GraphicalLanguageClientContribution).toDynamicValue(ctx => ctx.container.get(WorkflowGLClientContribution)).inSingletonScope();

    bind(DiagramConfiguration).to(WorkflowDiagramConfiguration).inSingletonScope()
    bind(DiagramManagerProvider).toProvider<DiagramManager>(context => {
        return () => {
            return new Promise<DiagramManager>((resolve) =>
                resolve(context.container.get(WorkflowDiagramManager))
            )
        }
    }).whenTargetNamed(WorkflowLanguage.DiagramType)

    bind(WorkflowDiagramManager).toSelf().inSingletonScope()
    bind(FrontendApplicationContribution).toDynamicValue(context =>
        context.container.get(WorkflowDiagramManager))
    bind(OpenHandler).toDynamicValue(context => context.container.get(WorkflowDiagramManager))
    bind(ThemeManager).toSelf().inSingletonScope()


})

