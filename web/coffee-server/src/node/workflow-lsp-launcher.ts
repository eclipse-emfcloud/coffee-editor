/********************************************************************************
 * Copyright (c) 2020 EclipseSource and others.
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
 ********************************************************************************/
import { ILogger } from '@theia/core';
import { BackendApplicationContribution } from '@theia/core/lib/node';
import { inject, injectable } from '@theia/core/shared/inversify';
import { ProcessErrorEvent } from '@theia/process/lib/node/process';
import { RawProcess, RawProcessFactory } from '@theia/process/lib/node/raw-process';
import * as cp from 'child_process';
import * as process from 'process';

import { getJarPath, inDebugMode } from './backend-module';

@injectable()
export class WorkflowLSPServerLauncher implements BackendApplicationContribution {
    @inject(RawProcessFactory) protected readonly processFactory: RawProcessFactory;
    @inject(ILogger) private readonly logger: ILogger;

    initialize(): void {
        if (inDebugMode()) {
            return;
        }
        const command = 'java';

        const jarPath = getJarPath('lsp');
        if (jarPath.length === 0) {
            throw new Error('[WorkflowDSL] Server launcher not found.');
        }
        const args: string[] = ['-jar', jarPath, '-startSocket'];

        this.logger.info('[WorkflowDSL] Spawn Server Process from ' + jarPath);
        const spawnedProcess = this.spawnProcessAsync(command, args, {
            detached: true,
            shell: true,
            stdio: ['inherit', 'pipe']
        });
        process.on('beforeExit', () => {
            spawnedProcess.then(p => p.kill());
        });
    }

    protected async spawnProcessAsync(command: string, args?: string[], options?: cp.SpawnOptions): Promise<RawProcess> {
        // delay start as we need the model server to be started
        await new Promise(r => setTimeout(r, 10000));
        const rawProcess = this.processFactory({ command, args, options });
        rawProcess.errorStream.on('data', this.logError.bind(this));
        rawProcess.outputStream.on('data', this.logInfo.bind(this));
        return new Promise<RawProcess>((resolve, reject) => {
            rawProcess.onError((error: ProcessErrorEvent) => {
                this.onDidFailSpawnProcess(error);
                if (error.code === 'ENOENT') {
                    const guess = command.split(/\s+/).shift();
                    if (guess) {
                        reject(new Error(`Failed to spawn ${guess}\nPerhaps it is not on the PATH.`));
                        return;
                    }
                }
                reject(error);
            });
            process.nextTick(() => resolve(rawProcess));
        });
    }

    protected onDidFailSpawnProcess(error: Error | ProcessErrorEvent): void {
        this.logError(error.message);
    }

    protected logError(data: string | Buffer): void {
        if (data) {
            this.logger.error(`WorkflowLSPServerLauncher: ${data}`);
        }
    }

    protected logInfo(data: string | Buffer): void {
        if (data) {
            this.logger.info(`WorkflowLSPServerLauncher: ${data}`);
        }
    }

}
