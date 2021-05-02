/*!
 * Copyright (c) 2021 EclipseSource and others.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0, or the MIT License which is
 * available at https://opensource.org/licenses/MIT.
 *
 * SPDX-License-Identifier: EPL-2.0 OR MIT
 */
import { JsonRpcServer } from '@theia/core/lib/common/messaging';

export const CodeGenCppServer = Symbol('CodeGenCppServer');
export const CODEGEN_SERVICE_PATH = '/services/codegen/cpp';

export interface CodeGenCppServer extends JsonRpcServer<undefined> {
    generateCode(sourceFile: string, targetFolder: string, packageName: string): Promise<string>;
}
