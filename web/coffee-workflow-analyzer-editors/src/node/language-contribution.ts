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
import * as glob from 'glob';
import { inject, injectable } from 'inversify';
import * as net from 'net';
import * as path from 'path';
import { createSocketConnection } from 'vscode-ws-jsonrpc/lib/server';

@injectable()
export class WorkflowContribution extends BaseLanguageServerContribution {

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
        if (socketPort) {
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

    private getJarPath() {
        const serverPath = path.resolve(__dirname, '..', '..', 'server');
        const jarPaths = glob.sync('**/plugins/org.eclipse.equinox.launcher_*.jar', { cwd: serverPath });
        if (jarPaths.length === 0) {
            throw new Error('[WorkflowDSL] Server launcher not found.');
        }
        const jarPath = path.resolve(serverPath, jarPaths[0]);
        return jarPath;
    }

}
