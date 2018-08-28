import { injectable } from "inversify";
import { BaseLanguageServerContribution, IConnection } from "@theia/languages/lib/node";
import { createSocketConnection } from "vscode-ws-jsonrpc/lib/server";
import * as net from 'net';

function getPort(): number | undefined {
    let arg = process.argv.filter(arg => arg.startsWith('--WF_LSP='))[0]
    console.error("Socket "+arg);
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

    start(clientConnection: IConnection): void {
        let socketPort = getPort();
        console.error("Socket "+socketPort);
        if (socketPort) {
            const socket = new net.Socket()
            const serverConnection = createSocketConnection(socket, socket, () => {
                socket.destroy()
            });
            this.forward(clientConnection, serverConnection)
            socket.connect(socketPort)
        } else {
            console.error("Error when trying to connect to Workflow language server");
        }
    }

    protected onDidFailSpawnProcess(error: Error): void {
        super.onDidFailSpawnProcess(error);
        console.error("Error starting Workflow language server.", error)
    }

}
