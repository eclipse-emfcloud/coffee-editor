/*!
 * Copyright (C) 2021 EclipseSource and others.
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
 */
import { GLSPDiagramWidget } from '@eclipse-glsp/theia-integration/lib/browser';
import URI from '@theia/core/lib/common/uri';
import { WorkflowDiagramManager } from 'coffee-workflow-glsp-editor/lib/browser/diagram/workflow-diagram-manager';
import { GraphicalComparisonOpener } from 'comparison-extension/lib/browser/graphical/graphical-comparison-opener';
import { ComparisonBackendService } from 'comparison-extension/lib/common/protocol';
import { inject, injectable } from 'inversify';

@injectable()
export class CoffeeGraphicalComparisonOpener extends GraphicalComparisonOpener {
    constructor(
        @inject(WorkflowDiagramManager)
        private readonly diagramManager: WorkflowDiagramManager,
        @inject(ComparisonBackendService)
        readonly comparisonBackendService: ComparisonBackendService
    ) {
        super(comparisonBackendService);
    }

    async getLeftDiagram(uri: URI, highlights: any): Promise<GLSPDiagramWidget> {
        const options: any = {
            widgetOptions: {
                editMode: 'readonly',
                highlights: highlights,
                useStaticIds: true
            }
        };

        return this.diagramManager.createWidgetFromURI(uri, options);
    }

    async getRightDiagram(uri: URI, highlights: any): Promise<GLSPDiagramWidget> {
        const options: any = {
            widgetOptions: {
                editMode: 'editable',
                highlights: highlights,
                useStaticIds: true
            }
        };

        return this.diagramManager.createWidgetFromURI(uri, options);
    }
}
