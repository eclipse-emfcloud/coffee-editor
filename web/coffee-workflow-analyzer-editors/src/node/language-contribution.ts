/*!
 * Copyright (C) 2019 EclipseSource and others.
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
 */
import { ILogger } from '@theia/core';
import { BaseLanguageServerContribution, IConnection } from '@theia/languages/lib/node';
import { RawProcess } from '@theia/process/lib/node/raw-process';
import * as glob from 'glob';
import { inject, injectable } from 'inversify';
import * as net from 'net';
import * as path from 'path';
import { createSocketConnection } from 'vscode-ws-jsonrpc/lib/server';

@injectable()
export class WorkflowContribution extends BaseLanguageServerContribution {

    private startedServer: boolean = false;
    @inject(ILogger) private readonly logger: ILogger;

    readonly description = {
        id: this.id,
        name: this.name,
        documentSelector: ['wfconfig'],
        fileEvents: [
            '**/*.wfconfig'
        ]
    };

    readonly id = 'wfconfig';
    readonly name = 'WFCONFIG';

    getPort(): number | undefined {
        const arg = process.argv.filter(argument => argument.startsWith('--WF_LSP='))[0];
        if (!arg) {
            return undefined;
        } else {
            return Number.parseInt(arg.substring('--WF_LSP='.length));
        }
    }

    async start(clientConnection: IConnection): Promise<void> {
        this.logger.info('[WorkflowDSL] Start Server for Client Connection.');
        const socketPort = this.getPort();
        if (socketPort && !this.startedServer) {
            this.startServer().then(() => this.connect(clientConnection, socketPort));
        } else if (socketPort) {
            this.connect(clientConnection, socketPort);
        } else {
            const command = 'java';

            const jarPath = this.getJarPath();
            const args: string[] = ['-jar', jarPath];
            const serverConnection = await this.createProcessStreamConnectionAsync(command, args);
            this.forward(clientConnection, serverConnection);
        }
    }
    private async connect(clientConnection: IConnection, socketPort: number) {
        const socket = new net.Socket();
        const serverConnection = createSocketConnection(socket, socket, () => {
            this.logger.info('[WorkflowDSL] Socket connection disposed');
            socket.destroy();
        });
        socket.connect(socketPort);
        this.forward(clientConnection, serverConnection);
    }
    private async startServer() {
        const jarPath = this.getJarPath();
        const command = 'java';
        const args: string[] = [];
        args.push('-jar', jarPath);
        args.push('-startSocket');
        this.logger.info('[WorkflowDSL] Spawn Process with command ' + command + ' and arguments ' + args);
        const process = await this.spawnProcessAsync(command, args);
        this.logger.info('[WorkflowDSL] Spawned process, waiting for server to be ready');
        await this.waitUntilServerReady(process);
        this.logger.info('[WorkflowDSL] Server communicated to be ready');
        this.startedServer = true;
    }
    private getJarPath() {
        const serverPath = path.resolve(__dirname, '..', '..', 'server');
        const jarPaths = glob.sync('**/plugins/org.eclipse.equinox.launcher_*.jar', { cwd: serverPath });
        if (jarPaths.length === 0) {
            throw new Error('[WorkflowDSL] Server launcher not found.');
        }
        const jarPath = path.resolve(serverPath, jarPaths[0]);
        return jarPath;
    }

    private waitUntilServerReady(process: RawProcess): Promise<any> {
        return new Promise<any>(resolve =>
            process.outputStream.on('data', data => {
                const message = String.fromCharCode.apply(undefined, data);
                this.logger.info('[WorkflowDSL] Server output: ' + message);
                if (message.includes('Ready')) {
                    return resolve(data);
                }
            })
        );
    }
}
