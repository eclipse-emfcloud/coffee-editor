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
import { ILogger } from '@theia/core';
import { BackendApplicationContribution } from '@theia/core/lib/node';
import { ProcessErrorEvent } from '@theia/process/lib/node/process';
import { RawProcess, RawProcessFactory } from '@theia/process/lib/node/raw-process';
import * as cp from 'child_process';
import { Application } from 'express';
import * as glob from 'glob';
import { inject, injectable } from 'inversify';
import * as net from 'net';
import * as path from 'path';
import * as rpc from 'vscode-jsonrpc';
import { createSocketConnection } from 'vscode-ws-jsonrpc/lib/server';

import { WorkflowAnalysisClient, WorkflowAnalyzer } from '../common/workflow-analyze-protocol';

// const DEFAULT_PORT = 8024;

@injectable()
export class WorkflowAnalysisServer implements WorkflowAnalyzer, BackendApplicationContribution {

    private connection?: rpc.MessageConnection;
    private client?: WorkflowAnalysisClient;

    constructor(
        @inject(RawProcessFactory) protected readonly processFactory: RawProcessFactory,
        @inject(ILogger) private readonly logger: ILogger) { }

    initialize(): void {
        const port = this.getPort();
        if (port) {
            this.connect(port);
        } else {
            const command = 'java';

            const jarPath = this.getJarPath();
            const args: string[] = ['-jar', jarPath];
            this.spawnProcessAsync(command, args).then(process => {
                this.connection = rpc.createMessageConnection(process.outputStream, process.inputStream);
                this.connection.listen();
            });
        }
    }

    private getPort(): number | undefined {
        const arg = process.argv.filter(a => a.startsWith('--WF_ANALYZER='))[0];
        if (!arg) {
            return undefined;
        } else {
            return Number.parseInt(arg.substring('--WF_ANALYZER='.length), 10);
        }
    }
    private getJarPath(): string {
        const serverPath = path.resolve(__dirname, '..', '..', 'server');
        const jarPaths = glob.sync('**/plugins/org.eclipse.equinox.launcher_*.jar', { cwd: serverPath });
        if (jarPaths.length === 0) {
            throw new Error('[WorkflowAnalyzer] Server launcher not found.');
        }
        const jarPath = path.resolve(serverPath, jarPaths[0]);
        return jarPath;
    }

    private async connect(port: number): Promise<void> {
        const socket = new net.Socket();
        const connection = createSocketConnection(socket, socket, () => {
            this.logger.info('[WorkflowAnalyzer] Socket connection disposed');
            socket.destroy();
        });
        socket.connect(port!);
        this.connection = rpc.createMessageConnection(connection.reader, connection.writer);
        this.connection.listen();
    }

    onStop(app?: Application): void {
        this.dispose();
    }

    dispose(): void {
        if (this.connection) {
            this.connection.dispose();
        }
    }

    protected spawnProcessAsync(command: string, args?: string[], options?: cp.SpawnOptions): Promise<RawProcess> {
        const rawProcess = this.processFactory({ command, args, options });
        rawProcess.errorStream.on('data', this.showError.bind(this));
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

    protected onDidFailSpawnProcess(error: Error): void {
        if (this.client) {
            this.client.reportStatus({ status: 'error', message: error.message });
        }
    }

    protected showError(data: string | Buffer): void {
        if (data) {
            if (this.client) {
                this.client.reportStatus({ status: 'error', message: data.toString() });
            }
        }
    }
    setClient(client: WorkflowAnalysisClient): void {
        this.client = client;
    }

    async analyze(wfUri: string, wfConfigUri: string): Promise<string> {
        return new Promise((resolve, reject) => {
            if (this.connection) {
                this.connection.sendRequest(this.createRunAnalysisRequest(), wfUri, wfConfigUri)
                    .then(r => resolve(r), e => reject(e));
            } else {
                reject(new Error('No connection to model analysis server'));
            }
        });
    }

    public createRunAnalysisRequest(): rpc.RequestType2<string, string, string, void, void> {
        return new rpc.RequestType2<string, string, string, void, void>('runAnalysis');
    }

}
