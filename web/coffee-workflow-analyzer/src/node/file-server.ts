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
import { injectable } from 'inversify';
import * as path from 'path';

import { FileClient, FileServer, FileTypes, TypeNotFound } from '../common/request-file-protocol';

@injectable()
export class WorkflowFileServer implements FileServer {
    protected client?: FileClient;

    requestFile(type: string): Promise<string> {
        if (type === FileTypes.WORKFLOW_ANALYSIS_HTML) {
            const fileName = path.resolve(__dirname, '../../wf-analyzer-web-app/index.html');
            return Promise.resolve('file://' + fileName);
        }
        return Promise.resolve(TypeNotFound);
    }
    dispose(): void {
        // no-op
    }
    setClient(client?: FileClient): void {
        this.client = client;
    }
}
