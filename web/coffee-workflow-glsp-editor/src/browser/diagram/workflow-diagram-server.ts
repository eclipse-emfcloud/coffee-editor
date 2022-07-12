/*
 * Copyright (c) 2021 EclipseSource and others.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0, or the MIT License which is
 * available at https://opensource.org/licenses/MIT.
 *
 * SPDX-License-Identifier: EPL-2.0 OR MIT
 */
import { ActionHandlerRegistry } from '@eclipse-glsp/client/lib';
import { GLSPTheiaDiagramServer } from '@eclipse-glsp/theia-integration/lib/browser';
import { injectable } from 'inversify';

@injectable()
export class WorkflowGLSPTheiaDiagramServer extends GLSPTheiaDiagramServer {
    initialize(registry: ActionHandlerRegistry): void {
        super.initialize(registry);
    }
}
