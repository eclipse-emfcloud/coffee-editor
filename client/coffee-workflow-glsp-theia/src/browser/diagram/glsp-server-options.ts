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
import { OpenerOptions } from '@theia/core/lib/browser';

export interface WorkflowGLSPServerOpenerOptions extends OpenerOptions {
    serverOptions: any;
}

export namespace WorkflowGLSPServerOpenerOptions {
    export function is(options: any): options is WorkflowGLSPServerOpenerOptions {
        return options && options.serverOptions;
    }
}
