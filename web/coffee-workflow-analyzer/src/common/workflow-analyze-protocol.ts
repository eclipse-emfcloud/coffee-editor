/*!
 * Copyright (c) 2019-2020 EclipseSource and others.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0, or the MIT License which is
 * available at https://opensource.org/licenses/MIT.
 *
 * SPDX-License-Identifier: EPL-2.0 OR MIT
 */
import { JsonRpcServer } from '@theia/core/lib/common/messaging';

export const WorkflowAnalyzer = Symbol('WorkflowAnalyzer');
export const workflowServicePath = '/services/workflowAnalyzer';

export interface WorkflowAnalyzer extends JsonRpcServer<WorkflowAnalysisClient> {
    analyze(wfFileUri: string, wfConfigFileUri: string): Promise<string>;
}

export interface WorkflowAnalysisStatus {
    status: 'ok' | 'error';
    message: string;
}

export interface WorkflowAnalysisClient {
    reportStatus(status: WorkflowAnalysisStatus): void;
}
