/********************************************************************************
 * Copyright (c) 2019 EclipseSource and others.
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
import { ProcessErrorEvent } from '@theia/process/lib/node/process';
import { ProcessManager } from '@theia/process/lib/node/process-manager';
import { RawProcess, RawProcessFactory } from '@theia/process/lib/node/raw-process';
import * as cp from 'child_process';
import { inject, injectable, optional } from 'inversify';

export const GLSPLaunchOptions = Symbol.for('LaunchOptions');
export interface GLSPLaunchOptions {
    isRunning: boolean
    serverPort: number
    hostname: string
    jarPath?: string
    additionalArgs?: string[];
}

@injectable()
export class GLSPServerLauncher implements BackendApplicationContribution {
    @inject(GLSPLaunchOptions) @optional() protected readonly launchOptions: GLSPLaunchOptions;
    @inject(RawProcessFactory) protected readonly processFactory: RawProcessFactory;
    @inject(ProcessManager) protected readonly processManager: ProcessManager;
    @inject(ILogger) private readonly logger: ILogger;

    initialize() {
        if (!this.launchOptions.isRunning && !this.start()) {
            this.logError('Error during model server startup');
        }
    }

    start(): boolean {
        if (!this.launchOptions.isRunning) {
            return this.startServer();
        }
        return true;
    }

    protected startServer(): boolean {
        if (this.launchOptions.jarPath) {
            let args = ['-jar', this.launchOptions.jarPath, '--port', `${this.launchOptions.serverPort}`];
            if (this.launchOptions.additionalArgs) {
                args = [...args, ...this.launchOptions.additionalArgs];
            }
            this.spawnProcessAsync('java', args);
        } else {
            this.logError('Could not start GLSP server. No path to executable is specified');
        }
        return true;
    }
    protected spawnProcessAsync(command: string, args?: string[], options?: cp.SpawnOptions): Promise<RawProcess> {
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

    protected logError(data: string | Buffer) {
        if (data) {
            this.logger.error(`ModelServerBackendContribution: ${data}`);
        }
    }

    protected logInfo(data: string | Buffer) {
        if (data) {
            this.logger.info(`ModelServerBackendContribution: ${data}`);
        }
    }

}
