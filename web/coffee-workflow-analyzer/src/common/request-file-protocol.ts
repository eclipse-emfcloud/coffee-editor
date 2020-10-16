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

export const TypeNotFound = '!fileTypeNotFound';
export const FileServer = Symbol('FileServer');
export const filePath = '/services/filerequest';

export interface FileServer extends JsonRpcServer<FileClient> {
    requestFile(type: string): Promise<string>;
}

export const FileClient = Symbol('FileClient');

// eslint-disable-next-line @typescript-eslint/no-empty-interface
export interface FileClient { }

export namespace FileTypes {
    export const WORKFLOW_ANALYSIS_HTML = 'wfanalysis';
}
