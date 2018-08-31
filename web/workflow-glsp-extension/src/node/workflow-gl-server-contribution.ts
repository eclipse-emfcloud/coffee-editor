/*******************************************************************************
 * Copyright (c) 2018 Tobias Ortmayr.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *   
 * Contributors:
 * 	Tobias Ortmayr - initial API and implementation
 ******************************************************************************/
import { injectable } from "inversify";
import { IConnection } from "@theia/languages/lib/node"
import { createSocketConnection } from 'vscode-ws-jsonrpc/lib/server'
import * as net from 'net'
import { WorkflowLanguage } from "../common/workflow-language";
import { BaseGraphicalLanguageServerContribution } from 'glsp-theia-extension/lib/node'
import { spawn } from "child_process";

function getPort(): number | undefined {
    let arg = process.argv.filter(arg => arg.startsWith('--WORKFLOW_LSP='))[0]
    if (!arg) {
        return undefined
    } else {
        return Number.parseInt(arg.substring('--WORKFLOW_LSP='.length), 10)
    }
}
@injectable()
export class WorkflowGLServerContribution extends BaseGraphicalLanguageServerContribution {
    readonly id = WorkflowLanguage.Id
    readonly name = WorkflowLanguage.Name

    serverStarted = false

    readonly description = {
        id: 'workflow',
        name: 'Workflow',
        documentSelector: ['workflow'],
        fileEvents: [
            '**/*.workflow'
        ]
    }

    start(clientConnection: IConnection): void {
        console.log('[WorkflowGL] Start Server for Client Connection.')
        let socketPort = getPort();
        if (socketPort) {
            if (!this.serverStarted) {
                const command = 'java';
                const jarPath = '~/.glsp-workflow/workflow-example-0.0.1-SNAPSHOT-glsp.jar';
                const args: string[] = [];
                args.push(
                    '-jar', jarPath
                );

                console.log('[WorkflowGL] Spawn Server Process from ' + jarPath + '.')
                const child = spawn(command, args, {
                    detached: true,
                    shell: true,
                    stdio: 'inherit'
                });
                child.unref();
            }

            const socket = new net.Socket()
            console.log('[WorkflowGL] Create Socket Connection at ' + socketPort + '.')
            const serverConnection = createSocketConnection(socket, socket, () => {
                console.log('[WorkflowGL] Socket Connection Disposed.')
                socket.destroy()
            });
            console.log('[WorkflowGL] Forward Client Connections.')
            this.forward(clientConnection, serverConnection)
            socket.connect(socketPort)
            this.serverStarted = true;
            console.log('[WorkflowGL] Client Connection Started.')
        } else {
            console.log('[WorkflowGL] Unable to connect to Workflow Graphical Language Server: No Socket Port.')
        }
    }
}