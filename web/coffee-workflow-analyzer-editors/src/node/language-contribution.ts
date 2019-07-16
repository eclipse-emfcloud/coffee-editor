import { injectable } from "inversify";
import { BaseLanguageServerContribution, IConnection } from "@theia/languages/lib/node";
import { createSocketConnection } from "vscode-ws-jsonrpc/lib/server";
import * as net from 'net';
import * as path from 'path';
import * as glob from 'glob';
import { spawn } from "child_process";
import { ProcessErrorEvent } from "@theia/process/lib/node/process";

function getPort(): number | undefined {
    let arg = process.argv.filter(arg => arg.startsWith('--WF_LSP='))[0]
    if (!arg) {
        return undefined
    } else {
        return Number.parseInt(arg.substring('--WF_LSP='.length))
    }
}
@injectable()
export class WorkflowContribution extends BaseLanguageServerContribution {


    readonly description = {
        id: this.id,
        name: this.name,
        documentSelector: ['wfconfig'],
        fileEvents: [
            '**/*.wfconfig'
        ]
    }

    readonly id = "wfconfig";
    readonly name = "WFCONFIG";
    serverConnection: IConnection|undefined = undefined;
    serverStarted = false;

    start(clientConnection: IConnection): void {
        console.log('[WorkflowDSL] Start Server for Client Connection.')
        let socketPort = getPort();
        if (socketPort) {
            if (!this.serverStarted) {
                const command = 'java';
                
                var serverPath = path.resolve(__dirname, '..', '..', 'server');
                var jarPaths = glob.sync('**/plugins/org.eclipse.equinox.launcher_*.jar', { cwd: serverPath });
                if (jarPaths.length === 0) {
                    throw new Error('[WorkflowDSL] Server launcher not found.');
                }
                var jarPath = path.resolve(serverPath, jarPaths[0]);
                const args: string[] = [];
                args.push(
                    '-jar', jarPath
                );

                console.log('[WorkflowDSL] Spawn Server Process from ' + jarPath + '.')
                const child = spawn(command, args, {
                    detached: true,
                    shell: true,
                    stdio: 'inherit'
                });
                child.unref();

                this.serverStarted = true;
            }

            const socket = new net.Socket()
            console.log('[WorkflowDSL] Create Socket Connection at ' + socketPort + '.')
            this.serverConnection = createSocketConnection(socket, socket, () => {
                console.log('[WorkflowDSL] Socket Connection Disposed.')
                socket.destroy()
            });
            console.log('[WorkflowDSL] Forward Client Connections.')
            this.forward(clientConnection, this.serverConnection as IConnection)
            socket.connect(socketPort)
            console.log('[WorkflowDSL] Client Connection Started.')
        } else {
            console.error('[WorkflowDSL] Unable to connect to Workflow Graphical Language Server: No Socket Port.')
        }
    }

    protected onDidFailSpawnProcess(error: ProcessErrorEvent): void {
        super.onDidFailSpawnProcess(error);
        console.error("[WorkflowDSL] Error starting Workflow language server.", error)
    }

}
