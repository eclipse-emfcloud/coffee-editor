/*
 * Copyright (c) 2019-2020 EclipseSource and others.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0, or the MIT License which is
 * available at https://opensource.org/licenses/MIT.
 *
 * SPDX-License-Identifier: EPL-2.0 OR MIT
 */
import { MaybePromise } from '@theia/core';
import URI from '@theia/core/lib/common/uri';
import { BackendApplicationContribution, FileUri } from '@theia/core/lib/node';
import { Application } from 'express';
import * as fs from 'fs-extra';
import { injectable } from 'inversify';
import * as net from 'net';
import * as rpc from 'vscode-jsonrpc';
import { createSocketConnection } from 'vscode-ws-jsonrpc/lib/server';

import { WorkflowAnalysisClient, WorkflowAnalyzer } from 'coffee-workflow-analyzer/lib/common/workflow-analyze-protocol';
import { EquinoxServer } from './equinox-server';

/**
 * The return type of the `FileSystem#resolveContent` method.
 */
interface FileStatWithContent {
    /**
     * The file stat.
     */
    readonly stat: fs.Stats & { uri: string };

    /**
     * The content of the file as a UTF-8 encoded string.
     */
    readonly content: string;
}

// const DEFAULT_PORT = 8024;

@injectable()
export class WorkflowAnalysisServer extends EquinoxServer implements WorkflowAnalyzer, BackendApplicationContribution {
    /**
     * Endpoint path to handle the request for the given resource.
     */
    private static HANDLE_PATH = '/mini-browser/';

    private connection?: rpc.MessageConnection;
    private client?: WorkflowAnalysisClient;

    protected serverName = 'WorkflowAnalysisServer';

    initialize(): void {
        const port = this.getPort();
        if (port) {
            this.connect(port);
        } else {
            const command = 'java';

            const jarPath = this.getEquinoxJarPath('wf-analyzer');
            const args: string[] = ['-jar', jarPath];
            this.spawnProcessAsync(command, args, undefined, false).then(process => {
                this.connection = rpc.createMessageConnection(process.outputStream, process.inputStream);
                this.connection.listen();
            });
        }
    }

    // express server
    configure(app: Application): void {
        app.get(`${WorkflowAnalysisServer.HANDLE_PATH}*`, async (request, response) => this.response(await this.getUri(request), response));
    }

    private async response(uri: string, response: any): Promise<Response> {
        const statWithContent = await this.readContent(uri);
        if (uri.endsWith('.css')) {
            response.contentType('text/css');
        } else {
            response.contentType('text/html');
        }
        return response.send(statWithContent.content);
    }

    private async readContent(uri: string): Promise<FileStatWithContent> {
        const fsPath = FileUri.fsPath(uri);
        const [stat, content] = await Promise.all([fs.stat(fsPath), fs.readFile(fsPath, 'utf8')]);
        return { stat: Object.assign(stat, { uri }), content };
    }

    private getUri(request: any): MaybePromise<string> {
        const decodedPath = request.path.substr(WorkflowAnalysisServer.HANDLE_PATH.length);
        return new URI(FileUri.create(decodedPath).toString(true)).toString(true);
    }

    private getPort(): number | undefined {
        const arg = process.argv.filter(a => a.startsWith('--WF_ANALYZER='))[0];
        if (!arg) {
            return undefined;
        } else {
            return Number.parseInt(arg.substring('--WF_ANALYZER='.length), 10);
        }
    }

    private async connect(port: number): Promise<void> {
        const socket = new net.Socket();
        const connection = createSocketConnection(socket, socket, () => {
            this.logInfo('Socket connection disposed');
            socket.destroy();
        });
        socket.connect(port!);
        this.connection = rpc.createMessageConnection(connection.reader, connection.writer);
        this.connection.listen();
    }

    override dispose(): void {
        if (this.connection) {
            this.connection.dispose();
        }
    }

    protected override onDidFailSpawnProcess(error: Error): void {
        if (this.client) {
            this.client.reportStatus({ status: 'error', message: error.message });
        }
        super.onDidFailSpawnProcess(error);
    }

    protected override logError(data: string | Buffer): void {
        if (data) {
            if (this.client) {
                this.client.reportStatus({ status: 'error', message: data.toString() });
            }
        }
        super.logError(data);
    }

    override setClient(client: WorkflowAnalysisClient): void {
        this.client = client;
    }

    async analyze(wfUri: string, wfConfigUri: string): Promise<string> {
        return new Promise((resolve, reject) => {
            if (this.connection) {
                this.connection.sendRequest(this.createRunAnalysisRequest(), wfUri, wfConfigUri).then(
                    r => resolve(r),
                    e => reject(e)
                );
            } else {
                reject(new Error('No connection to model analysis server'));
            }
        });
    }

    public createRunAnalysisRequest(): rpc.RequestType2<string, string, string, void, void> {
        return new rpc.RequestType2<string, string, string, void, void>('runAnalysis');
    }
}
