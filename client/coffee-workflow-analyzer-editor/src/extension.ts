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
/* eslint-disable import/no-unresolved */
import * as net from 'net';
import * as vscode from 'vscode';
import { SocketMessageReader, SocketMessageWriter } from 'vscode-jsonrpc';
import { LanguageClient, LanguageClientOptions, ServerOptions } from 'vscode-languageclient';

let client: LanguageClient;
const DEFAULT_SOCKET_OPTS = 'localhost:5017';

export function activate(_context: vscode.ExtensionContext): void {
    client = new LanguageClient('wfconfig', 'wfconfig', createServerOptions(), createClientOptions());
    client.start();
}

function createClientOptions(): LanguageClientOptions {
    return { documentSelector: [{ scheme: 'file', language: 'wfconfig' }] };
}

function createServerOptions(): ServerOptions {
    return async () => {
        const socket = new net.Socket();
        const transport = { reader: new SocketMessageReader(socket), writer: new SocketMessageWriter(socket) };
        socket.connect(getSocketConnectOps(getSocketOpts()));
        return transport;
    };
}

function getSocketOpts(): string {
    return process.env.WF_CONFIG_LSP ? process.env.WF_CONFIG_LSP : DEFAULT_SOCKET_OPTS;
}

function getSocketConnectOps(connectionString: string): net.TcpSocketConnectOpts {
    const fragments = connectionString.split(':');
    if (fragments.length !== 2) {
        throw new Error(`Connection string didn't follow <host>:<port> format: ${connectionString}`);
    }
    return {
        host: fragments[0],
        port: Number.parseInt(fragments[1], 10)
    };
}

// eslint-disable-next-line @typescript-eslint/no-empty-function
export function deactivate(): void {
    if (client) {
        client.stop();
    }
}
