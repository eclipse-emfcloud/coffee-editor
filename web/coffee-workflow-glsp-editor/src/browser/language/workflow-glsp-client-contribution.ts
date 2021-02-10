/********************************************************************************
 * Copyright (c) 2019 EclipseSource and others.
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
import { ModelServerClient } from '@eclipse-emfcloud/modelserver-theia/lib/common';
import { GLSPClient } from '@eclipse-glsp/protocol';
import { BaseGLSPClientContribution } from '@eclipse-glsp/theia-integration/lib/browser';
import { WorkspaceService } from '@theia/workspace/lib/browser';
import { inject, injectable } from 'inversify';

import { WorkflowNotationLanguage } from '../../common/workflow-language';

export interface WorkflowInitializeOptions {
    timestamp: Date;
    modelserverURL: string;
    workspaceRoot?: string;
}

@injectable()
export class WorkflowGLSPClientContribution extends BaseGLSPClientContribution {
    @inject(ModelServerClient) protected readonly modelServerBackend: ModelServerClient;
    @inject(WorkspaceService) protected readonly workspaceService: WorkspaceService;
    readonly id = WorkflowNotationLanguage.Id;
    readonly name = WorkflowNotationLanguage.Name;
    readonly fileExtensions = [WorkflowNotationLanguage.FileExtension];

    protected async createInitializeOptions(): Promise<WorkflowInitializeOptions> {
        const options = await this.modelServerBackend.getLaunchOptions();
        const workspaceRoot = await this.workspaceService.roots.then(roots => roots[0].uri);

        return {
            timestamp: new Date(),
            modelserverURL: `http://${options.hostname}:${options.serverPort}/${options.baseURL}`,
            workspaceRoot
        };
    }

    protected onReady(languageClient: GLSPClient): void {
        this._glspClient = languageClient;
        this.resolveReady(this._glspClient);
        // only resolve ready once and do not re-generate the ready promise
    }
}
