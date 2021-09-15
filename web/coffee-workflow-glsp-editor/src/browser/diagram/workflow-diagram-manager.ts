/********************************************************************************
 * Copyright (c) 2020 EclipseSource and others.
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
 ********************************************************************************/
import {
    EnableToolPaletteAction,
    InitializeClientSessionAction,
    RequestTypeHintsAction,
    SetEditModeAction
} from '@eclipse-glsp/client';
import {
    DiagramWidgetOptions,
    GLSPDiagramManager,
    GLSPDiagramWidget,
    GLSPNotificationManager,
    GLSPTheiaDiagramServer,
    GLSPTheiaSprottyConnector,
    GLSPWidgetOpenerOptions,
    GLSPWidgetOptions
} from '@eclipse-glsp/theia-integration/lib/browser';
import { MessageService } from '@theia/core';
import { WidgetManager, WidgetOpenerOptions } from '@theia/core/lib/browser';
import URI from '@theia/core/lib/common/uri';
import { EditorManager } from '@theia/editor/lib/browser';
import { inject, injectable } from 'inversify';
import { DiagramServer, ModelSource, RequestModelAction, TYPES } from 'sprotty';
import { DiagramWidget, TheiaFileSaver } from 'sprotty-theia/lib';

import { WorkflowNotationLanguage } from '../../common/workflow-language';
import { WorkflowGLSPDiagramClient } from './workflow-glsp-diagram-client';
import { WorkflowGLSPServerOpenerOptions } from './workflow-glsp-server-options';

@injectable()
export class WorkflowDiagramManager extends GLSPDiagramManager {
    readonly diagramType = WorkflowNotationLanguage.DiagramType;
    readonly iconClass = 'fa fa-project-diagram';
    readonly label = WorkflowNotationLanguage.Label + ' Editor';

    private _diagramConnector: GLSPTheiaSprottyConnector;

    async createWidget(options?: any): Promise<DiagramWidget> {
        if (DiagramWidgetOptions.is(options)) {
            const clientId = this.createClientId();
            const config = this.diagramConfigurationRegistry.get(options.diagramType);
            const diContainer = config.createContainer(clientId);
            const diagramWidget = new WorkflowDiagramWidget(options, clientId + '_widget', diContainer, this.editorPreferences, this.diagramConnector);
            return diagramWidget;
        }
        throw Error('DiagramWidgetFactory needs DiagramWidgetOptions but got ' + JSON.stringify(options));
    }

    constructor(
        @inject(WorkflowGLSPDiagramClient) diagramClient: WorkflowGLSPDiagramClient,
        @inject(TheiaFileSaver) fileSaver: TheiaFileSaver,
        @inject(WidgetManager) widgetManager: WidgetManager,
        @inject(EditorManager) editorManager: EditorManager,
        @inject(MessageService) messageService: MessageService,
        @inject(GLSPNotificationManager) notificationManager: GLSPNotificationManager) {
        super();
        this._diagramConnector = new GLSPTheiaSprottyConnector({
            diagramClient,
            fileSaver,
            editorManager,
            widgetManager,
            diagramManager: this,
            messageService,
            notificationManager
        });
    }

    protected createWidgetOptions(uri: URI, options?: GLSPWidgetOpenerOptions): DiagramWidgetOptions & GLSPWidgetOptions {
        const widgetOptions = super.createWidgetOptions(uri.withoutQuery(), options);
        const queryOptions = this.createQueryOptions(uri);
        const serverOptions = this.createServerOptions(options);
        if (options && options.widgetOptions && (options.widgetOptions as any).editMode) {
            (widgetOptions as any).editMode = (options.widgetOptions as any).editMode;
        }
        return {
            ...options?.widgetOptions,
            ...widgetOptions,
            ...queryOptions,
            ...serverOptions
        };
    }

    protected createServerOptions(options?: WidgetOpenerOptions): Record<string, any> {
        if (WorkflowGLSPServerOpenerOptions.is(options)) {
            return options.serverOptions;
        }
        return {};
    }

    protected createQueryOptions(uri: URI): Record<string, any> {
        const queryOptions: Record<string, any> = {};
        if (!uri.query || uri.query.indexOf('=') < 0) {
            return queryOptions;
        }
        const keyValuePairs = uri.query.split('&');
        for (const keyValuePair of keyValuePairs) {
            const keyValue = keyValuePair.split('=');
            queryOptions[keyValue[0]] = keyValue[1];
        }
        return queryOptions;
    }

    get fileExtensions(): string[] {
        return [WorkflowNotationLanguage.FileExtension];
    }
    get diagramConnector(): GLSPTheiaSprottyConnector | undefined {
        return this._diagramConnector;
    }

    createWidgetFromURI(uri: URI, options?: WidgetOpenerOptions): Promise<GLSPDiagramWidget> {
        const uriString = uri.toString();
        const notationString = uriString.substr(0, uriString.lastIndexOf('.')) + '.coffeenotation';
        const notationUri = new URI(notationString);
        return this.getOrCreateWidget(notationUri, options) as Promise<GLSPDiagramWidget>;
    }
}

export class WorkflowDiagramWidget extends GLSPDiagramWidget {
    // eslint-disable-next-line @typescript-eslint/explicit-function-return-type
    protected initializeSprotty() {
        const modelSource = this.diContainer.get<ModelSource>(TYPES.ModelSource);
        if (modelSource instanceof DiagramServer) {
            modelSource.clientId = this.id;
        }
        if (modelSource instanceof GLSPTheiaDiagramServer && this.connector) {
            this.connector.connect(modelSource);
        }

        this.disposed.connect(() => {
            if (modelSource instanceof GLSPTheiaDiagramServer && this.connector) {
                this.connector.disconnect(modelSource);
            }
        });

        this.actionDispatcher.dispatch(new InitializeClientSessionAction(this.widgetId));

        this.requestModelOptions = {
            sourceUri: this.uri.path.toString(),
            needsClientLayout: `${this.viewerOptions.needsClientLayout}`,
            ... this.options
        };
        this.actionDispatcher.dispatch(new RequestModelAction(this.requestModelOptions));
        this.actionDispatcher.dispatch(new RequestTypeHintsAction(this.options.diagramType));
        if ((this.options as any).editMode === 'editable') {
            this.actionDispatcher.dispatch(new EnableToolPaletteAction());
            this.actionDispatcher.dispatch(new SetEditModeAction('editable'));
        } else {
            this.actionDispatcher.dispatch(new SetEditModeAction('readonly'));
        }
    }
}
