/*!
 * Copyright (C) 2019 EclipseSource and others.
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
import { injectable } from 'inversify';
import * as path from 'path';

import { FileTypes, IFileClient, IFileServer, TypeNotFound } from '../common/request-file-protocol';

@injectable()
export class WorkflowFileServer implements IFileServer {
    protected client?: IFileClient;

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
    setClient(client?: IFileClient): void {
        this.client = client;
    }
}
