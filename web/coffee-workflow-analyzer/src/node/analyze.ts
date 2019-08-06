import { RawProcess, RawProcessFactory } from "@theia/process/lib/node/raw-process";
import * as glob from "glob";
import { inject, injectable } from "inversify";
import * as net from "net";
import * as path from "path";
import * as rpc from "vscode-jsonrpc";
import { createSocketConnection } from "vscode-ws-jsonrpc/lib/server";

import { WorkflowAnalysisClient, WorkflowAnalyzer } from "../common/workflow-analyze-protocol";

@injectable()
export class WorkflowAnalyzerServer implements WorkflowAnalyzer {

    private startedServer: boolean = false;
    private connection?: rpc.MessageConnection = undefined;
    private client?: WorkflowAnalysisClient;

    constructor(@inject(RawProcessFactory) protected readonly processFactory: RawProcessFactory) { }

    analyze(wfUri: string, wfConfigUri: string): Promise<string> {
        this.connection = this.connect();
        return new Promise((resolve, reject) => {
            this.connection!
                .sendRequest(this.createRunAnalysisRequest(), wfUri, wfConfigUri)
                .then(r => resolve(r), e => reject(e));
        });
    }

    private connect(): rpc.MessageConnection {
        let port = this.getPort();
        if (!port && !this.startedServer) {
            port = 8024;
            this.startServer(port);
        }

        const socket = new net.Socket();
        const connection = createSocketConnection(socket, socket, () => {
            console.log('[WorkflowAnalyzer] Socket Connection Disposed.')
            socket.destroy()
        });
        socket.connect(port!);

        const messageConnection = rpc.createMessageConnection(connection.reader, connection.writer);
        messageConnection.listen();
        return messageConnection;
    }

    private getPort(): number | undefined {
        const arg = process.argv.filter(arg => arg.startsWith('--WF_ANALYZER='))[0];
        if (!arg) {
            return undefined;
        } else {
            return Number.parseInt(arg.substring('--WF_ANALYZER='.length), 10);
        }
    }

    private startServer(port: number) {
        const serverPath = path.resolve(__dirname, '..', '..', 'server');
        const jarPaths = glob.sync('**/plugins/org.eclipse.equinox.launcher_*.jar', { cwd: serverPath });
        if (jarPaths.length === 0) {
            throw new Error('The Java server launcher is not found.');
        }
        const jarPath = path.resolve(serverPath, jarPaths[0]);
        const command = 'java';
        const args: string[] = [];
        args.push('-jar', jarPath);
        args.push('-host', 'localhost', '-port', port.toString());
        console.log('[WorkflowAnalyzer] Spawn Process with Command ' + command + ' and arguments ' + args);
        this.spawnProcess(command, args);
        this.startedServer = true;
    }

    public createRunAnalysisRequest(): rpc.RequestType2<string, string, string, void, void> {
        this.showError("this is a test error");
        return new rpc.RequestType2<string, string, string, void, void>('runAnalysis');
    }

    dispose(): void {
        if (this.connection) {
            this.connection.dispose();
        }
    }

    setClient(client: WorkflowAnalysisClient): void {
        this.client = client;
    }

    private spawnProcess(command: string, args?: string[]): RawProcess | undefined {
        const rawProcess = this.processFactory({ command, args });
        if (rawProcess.process === undefined) {
            return undefined;
        }
        rawProcess.process.once('error', this.onDidFailSpawnProcess.bind(this));
        const stderr = rawProcess.process.stderr;
        if (stderr)
            stderr.on('data', this.showError.bind(this));
        return rawProcess;
    }

    protected onDidFailSpawnProcess(error: Error): void {
        if (this.client) {
            this.client.reportStatus({ status: 'error', message: error.message });
        }
    }

    protected showError(data: string | Buffer) {
        if (data) {
            if (this.client) {
                this.client.reportStatus({ status: 'error', message: data.toString() });
            }
        }
    }

}