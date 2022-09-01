/*
 * Copyright (c) 2022 EclipseSource and others.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0, or the MIT License which is
 * available at https://opensource.org/licenses/MIT.
 *
 * SPDX-License-Identifier: EPL-2.0 OR MIT
 */
import { ILogger } from '@theia/core/lib/common/logger';
import { BackendApplicationContribution } from '@theia/core/lib/node/backend-application';
import { inject, injectable } from '@theia/core/shared/inversify';
import { ProcessErrorEvent } from '@theia/process/lib/node/process';
import { RawProcess, RawProcessFactory } from '@theia/process/lib/node/raw-process';
import { SpawnOptions } from 'child_process';
import { Application } from 'express';
import { sync } from 'glob';
import { join, resolve as resolvePath } from 'path';
import * as process from 'process';

@injectable()
export abstract class EquinoxServer implements BackendApplicationContribution {
    protected abstract serverName: string;

    @inject(RawProcessFactory) protected readonly processFactory: RawProcessFactory;
    @inject(ILogger) private readonly logger: ILogger;

    protected getEquinoxJarPath(serverDir: string): string {
        const serverPath = resolvePath(join(__dirname, '..', '..', 'servers', serverDir));
        const jarPaths = sync('**/plugins/org.eclipse.equinox.launcher_*.jar', { cwd: serverPath });
        const jarPath = resolvePath(serverPath, jarPaths[0]);
        return jarPath;
    }

    protected inDebugMode(): boolean {
        const args = process.argv.filter(a => a.startsWith('--debug'));
        return args.length > 0;
    }

    protected async spawnProcessAsync(command: string, args?: string[], options?: SpawnOptions, delayStart = true): Promise<RawProcess> {
        if (delayStart) {
            // delay start as we need the model server to be started
            await new Promise(r => setTimeout(r, 10000));
        }
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

    protected spawnProcess(command: string, args?: string[]): RawProcess | undefined {
        const rawProcess = this.processFactory({ command, args });
        if (rawProcess.process === undefined) {
            return undefined;
        }
        rawProcess.process.on('error', this.onDidFailSpawnProcess.bind(this));
        const stderr = rawProcess.process.stderr;
        if (stderr) {
            stderr.on('data', this.logError.bind(this));
        }
        return rawProcess;
    }

    protected onDidFailSpawnProcess(error: Error | ProcessErrorEvent): void {
        this.logError(error.message);
    }

    protected logError(data: string | Buffer): void {
        if (data) {
            this.logger.error(`[${this.serverName}]: ${data}`);
        }
    }

    protected logInfo(data: string | Buffer): void {
        if (data) {
            this.logger.info(`[${this.serverName}]: ${data}`);
        }
    }

    onStop(_app?: Application): void {
        this.dispose();
    }

    dispose(): void {
        // do nothing
    }

    setClient(_client?: any): void {
        // do nothing
    }
}
