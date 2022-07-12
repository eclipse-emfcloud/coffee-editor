/*
 * Copyright (c) 2019 EclipseSource and others.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0, or the MIT License which is
 * available at https://opensource.org/licenses/MIT.
 *
 * SPDX-License-Identifier: EPL-2.0 OR MIT
 */
import { ModelServerClient } from '@eclipse-emfcloud/modelserver-theia/lib/common';
import { Args } from '@eclipse-glsp/client';
import { MaybePromise } from '@eclipse-glsp/protocol';
import { BaseGLSPClientContribution } from '@eclipse-glsp/theia-integration/lib/browser';
import { WorkspaceService } from '@theia/workspace/lib/browser';
import { inject, injectable } from 'inversify';

import { WorkflowNotationLanguage } from '../common/workflow-language';

export interface WorkflowInitializeOptions {
    timestamp: Date;
    modelserverURL: string;
    workspaceRoot?: string;
}

@injectable()
export class WorkflowGLSPClientContribution extends BaseGLSPClientContribution {
    @inject(ModelServerClient) protected readonly modelServerBackend: ModelServerClient;
    @inject(WorkspaceService) protected readonly workspaceService: WorkspaceService;

    readonly id = WorkflowNotationLanguage.contributionId;
    readonly fileExtensions = WorkflowNotationLanguage.fileExtensions;

    protected createInitializeOptions(): MaybePromise<Args | undefined> {
        // const workspaceRoot = this.workspaceService.roots.then(roots => roots[0].resource);

        return {
            ['timestamp']: new Date().toString(),
            ['modelServerURL']: 'http://localhost:8081/api/v1/'
            // ['workspaceRoot']: workspaceRoot.toString()
        };
    }
}
