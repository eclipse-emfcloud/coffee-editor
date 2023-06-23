/*
 * Copyright (c) 2020-2023 EclipseSource and others.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0, or the MIT License which is
 * available at https://opensource.org/licenses/MIT.
 *
 * SPDX-License-Identifier: EPL-2.0 OR MIT
 */
import { BackendApplicationContribution } from '@theia/core/lib/node';
import { injectable } from '@theia/core/shared/inversify';
import { platform } from 'os';
import { on } from 'process';

import { EquinoxServer } from './equinox-server';

@injectable()
export class WorkflowLSPServer extends EquinoxServer implements BackendApplicationContribution {
    protected serverName = 'WorkflowLSPServer';

    initialize(): void {
        if (this.inDebugMode()) {
            return;
        }
        // ensure jar is run in background
        const command = platform() === 'win32' ? 'javaw' : 'java';

        const jarPath = this.getEquinoxJarPath('wf-lsp');
        if (jarPath.length === 0) {
            throw new Error('WorkflowLSPServer launcher not found.');
        }
        const args: string[] = ['-jar', jarPath, '-startSocket'];

        this.logInfo('Spawn Server Process from ' + jarPath);
        const spawnedProcess = this.spawnProcessAsync(command, args, {
            detached: true,
            shell: true,
            stdio: ['inherit', 'pipe']
        });
        on('beforeExit', () => {
            spawnedProcess.then(p => p.kill());
        });
    }
}
