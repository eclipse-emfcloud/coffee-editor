import { ILogger } from "@theia/core";
import { BaseLanguageServerContribution, IConnection } from "@theia/languages/lib/node";
import { ProcessErrorEvent } from "@theia/process/lib/node/process";
import { spawn } from "child_process";
import * as glob from "glob";
import { inject, injectable } from "inversify";
import * as net from "net";
import * as path from "path";
import { createSocketConnection } from "vscode-ws-jsonrpc/lib/server";

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

    @inject(ILogger) private readonly logger: ILogger;

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
    serverConnection: IConnection | undefined = undefined;
    serverStarted = false;

    start(clientConnection: IConnection): void {
        this.logger.info('[WorkflowDSL] Start Server for Client Connection.');
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

                this.logger.info('[WorkflowDSL] Spawn Server Process from ' + jarPath);
                const child = spawn(command, args, {
                    detached: true,
                    shell: true,
                    stdio: 'inherit'
                });
                child.unref();

                this.serverStarted = true;
            }

            const socket = new net.Socket()
            this.logger.info('[WorkflowDSL] Create Socket Connection at ' + socketPort);
            this.serverConnection = createSocketConnection(socket, socket, () => {
                this.logger.info('[WorkflowDSL] Socket Connection Disposed');
                socket.destroy()
            });
            this.logger.info('[WorkflowDSL] Forward Client Connections.');
            this.forward(clientConnection, this.serverConnection as IConnection)
            socket.connect(socketPort)
            this.logger.info('[WorkflowDSL] Client Connection Started.');
        } else {
            this.logger.error('[WorkflowDSL] Unable to connect to Workflow Graphical Language Server: No Socket Port');
        }
    }

    protected onDidFailSpawnProcess(error: ProcessErrorEvent): void {
        super.onDidFailSpawnProcess(error);
        this.logger.error("[WorkflowDSL] Error starting Workflow language server", error);
    }

}
