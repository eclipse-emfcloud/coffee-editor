/*
 * Copyright (c) 2021-2022 EclipseSource and others.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0, or the MIT License which is
 * available at https://opensource.org/licenses/MIT.
 *
 * SPDX-License-Identifier: EPL-2.0 OR MIT
 */
import { Args } from '@eclipse-glsp/protocol';
import { GLSPDiagramLanguage, TheiaDiagramServer } from '@eclipse-glsp/theia-integration';
import { BaseTheiaGLSPConnector } from '@eclipse-glsp/theia-integration/lib/browser/diagram/base-theia-glsp-connector';
import { injectable } from 'inversify';

import { WorkflowNotationLanguage } from '../../common/workflow-language';
import { getCoffeeUriString } from './diagram-utils';

@injectable()
export class WorkflowTheiaGLSPConnector extends BaseTheiaGLSPConnector {
    private _diagramType: string = WorkflowNotationLanguage.diagramType;
    private _contributionId: string = WorkflowNotationLanguage.contributionId;

    doConfigure(diagramLanguage: GLSPDiagramLanguage): void {
        this._contributionId = diagramLanguage.contributionId;
        this._diagramType = diagramLanguage.diagramType;
        this.initialize();
    }

    get diagramType(): string {
        if (!this._diagramType) {
            throw new Error('No diagramType has been set for this WorkflowTheiaGLSPConnector');
        }
        return this._diagramType;
    }

    get contributionId(): string {
        if (!this._contributionId) {
            throw new Error('No contributionId has been set for this WorkflowTheiaGLSPConnector');
        }
        return this._contributionId;
    }

    protected override initialize(): void {
        if (this._diagramType && this._contributionId) {
            super.initialize();
        }
    }

    override disposeClientSessionArgs(diagramServer: TheiaDiagramServer): Args | undefined {
        return {
            ['sourceUri']: getCoffeeUriString(diagramServer.sourceUri)
        };
    }
}
