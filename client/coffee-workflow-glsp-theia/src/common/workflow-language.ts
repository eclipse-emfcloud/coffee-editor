/*
 * Copyright (c) 2019-2022 EclipseSource and others.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0, or the MIT License which is
 * available at https://opensource.org/licenses/MIT.
 *
 * SPDX-License-Identifier: EPL-2.0 OR MIT
 */
import { GLSPDiagramLanguage } from '@eclipse-glsp/theia-integration';

export const WorkflowNotationLanguage: GLSPDiagramLanguage = {
    contributionId: 'WorkflowNotation',
    label: 'Workflow Notation diagram',
    diagramType: 'workflow-diagram-notation',
    iconClass: 'codicon codicon-type-hierarchy-sub',
    fileExtensions: ['.notation']
};
