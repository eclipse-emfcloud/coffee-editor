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

import { ExtensionContext } from 'vscode';
import { LanguageClient, LanguageClientOptions, ServerOptions, StreamInfo } from 'vscode-languageclient/node';

let client: LanguageClient;
const DEFAULT_SOCKET_OPTS = 'localhost:5017';

export function activate(_context: ExtensionContext): void {
    client = new LanguageClient('wfconfig', 'wfconfig', createServerOptions(), createClientOptions());
    client.start();
}

function createClientOptions(): LanguageClientOptions {
    return { documentSelector: [{ scheme: 'file', language: 'wfconfig' }] };
}

function createServerOptions(): ServerOptions {
    // The server is a started as a separate app
    const connectionInfo = getSocketConnectOps(getSocketOpts());
    return () => {
        // Connect to language server via socket
        const socket = net.connect(connectionInfo);
        const result: StreamInfo = {
            writer: socket,
            reader: socket
        };
        return Promise.resolve(result);
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
