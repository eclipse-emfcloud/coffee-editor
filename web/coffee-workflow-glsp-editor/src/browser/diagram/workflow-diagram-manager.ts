/*
 * Copyright (c) 2020-2022 EclipseSource and others.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0, or the MIT License which is
 * available at https://opensource.org/licenses/MIT.
 *
 * SPDX-License-Identifier: EPL-2.0 OR MIT
 */
import { Args, EnableToolPaletteAction, RequestTypeHintsAction, SetEditModeAction, codiconCSSString } from '@eclipse-glsp/client';

import { DiagramServer, ModelSource, RequestModelAction, TYPES } from 'sprotty';
import {
    DiagramWidgetOptions,
    GLSPDiagramManager,
    GLSPDiagramWidget,
    GLSPTheiaDiagramServer,
    GLSPWidgetOpenerOptions,
    GLSPWidgetOptions
} from '@eclipse-glsp/theia-integration/lib/browser';
import { WidgetOpenerOptions } from '@theia/core/lib/browser';
import URI from '@theia/core/lib/common/uri';
import { WorkspaceService } from '@theia/workspace/lib/browser';
import { inject, injectable, postConstruct } from 'inversify';

import { WorkflowNotationLanguage } from '../../common/workflow-language';
import { WorkflowGLSPServerOpenerOptions } from './workflow-glsp-server-options';

export const DIAGRAM_ICON_CLASS = codiconCSSString('type-hierarchy-sub');

export interface WorkflowDiagramWidgetOptions extends DiagramWidgetOptions, GLSPWidgetOptions {
    workspaceRoot: string;
}

@injectable()
export class WorkflowDiagramManager extends GLSPDiagramManager {
    @inject(WorkspaceService) workspaceService: WorkspaceService;

    readonly diagramType = WorkflowNotationLanguage.diagramType;
    readonly label = WorkflowNotationLanguage.label + ' Editor';

    private workspaceRoot: string;

    @postConstruct()
    protected async initialize(): Promise<void> {
        super.initialize();
        this.workspaceService.roots.then(roots => (this.workspaceRoot = roots[0].resource.toString()));
    }

    // protected createWidgetOptions(uri: URI, options?: GLSPWidgetOpenerOptions): WorkflowDiagramWidgetOptions {
    //     const widgetOptions = super.createWidgetOptions(uri.withoutQuery(), options);
    //     const queryOptions = this.createQueryOptions(uri);
    //     const serverOptions = this.createServerOptions(options);
    //     return {
    //         ...widgetOptions,
    //         ...queryOptions,
    //         ...serverOptions,
    //         workspaceRoot: this.workspaceRoot
    //     };
    // }

    protected createWidgetOptions(uri: URI, options?: GLSPWidgetOpenerOptions): DiagramWidgetOptions & GLSPWidgetOptions {
        const widgetOptions = super.createWidgetOptions(uri.withoutQuery(), options);
        const queryOptions = this.createQueryOptions(uri);
        const serverOptions = this.createServerOptions(options);
        if (options?.widgetOptions && (options.widgetOptions as any).editMode) {
            (widgetOptions as any).editMode = (options.widgetOptions as any).editMode;
        }
        return {
            ...options?.widgetOptions,
            ...widgetOptions,
            ...queryOptions,
            ...serverOptions,
            workspaceRoot: this.workspaceRoot
        } as WorkflowDiagramWidgetOptions;
    }

    protected createWidgetId(options: DiagramWidgetOptions): string {
        const widgetId = `${this.diagramType}:${options.uri}`;
        for (const widget of this.shell.widgets) {
            if (widget instanceof GLSPDiagramWidget) {
                if (widget.widgetId === widgetId) {
                    widget.close();
                }
            }
        }
        return widgetId;
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
        return WorkflowNotationLanguage.fileExtensions;
    }

    get iconClass(): string {
        return DIAGRAM_ICON_CLASS;
    }

    createWidgetFromURI(uri: URI, options?: WidgetOpenerOptions): Promise<GLSPDiagramWidget> {
        const uriString = uri.toString();
        const notationString = uriString.substr(0, uriString.lastIndexOf('.')) + '.notation';
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

        this.requestModelOptions = {
            sourceUri: this.uri.path.toString(),
            needsClientLayout: `${this.viewerOptions.needsClientLayout}`,
            ...this.options
        } as Args;
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
