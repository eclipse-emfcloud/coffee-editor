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
import { injectable, inject } from "inversify";
import { TheiaFileSaver, DiagramWidgetRegistry } from "theia-glsp/lib";
import { WorkflowLanguage } from "../../common/workflow-language";
import { GLSPTheiaSprottyConnector, GraphicalLanguageClientContribution, GLSPDiagramManager, GLSPPaletteContribution } from "glsp-theia-extension/lib/browser";
import { WorkflowGLClientContribution } from "../language/workflow-gl-client-contribution";
import { EditorManager } from "@theia/editor/lib/browser";
import { ThemeManager } from "./thememanager";
import { read } from "fs";



@injectable()
export class WorkflowDiagramManager extends GLSPDiagramManager {
    readonly diagramType = WorkflowLanguage.DiagramType;
    readonly iconClass = "fa fa-project-diagram";
    readonly label = WorkflowLanguage.Label + " Editor";

    private _diagramConnector: GLSPTheiaSprottyConnector;

    constructor(
        @inject(WorkflowGLClientContribution)
        readonly languageClientContribution: GraphicalLanguageClientContribution,
        @inject(TheiaFileSaver)
        readonly theiaFileSaver: TheiaFileSaver,
        @inject(EditorManager)
        readonly editorManager: EditorManager,
        @inject(DiagramWidgetRegistry)
        readonly diagramWidgetRegistry: DiagramWidgetRegistry,
        @inject(ThemeManager)
        readonly themeManager: ThemeManager,
        @inject(GLSPPaletteContribution) readonly paletteContribution: GLSPPaletteContribution) {
        super();

    }

    get diagramConnector() {
        if (!this._diagramConnector) {
            this._diagramConnector = new GLSPTheiaSprottyConnector(
                this.languageClientContribution,
                this.theiaFileSaver,
                this.editorManager,
                this.diagramWidgetRegistry,
                this.paletteContribution)
            this.themeManager.initialize();

        }
        return this._diagramConnector
    }
}