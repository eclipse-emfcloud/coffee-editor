import * as net from 'net';
import * as path from 'path';
import * as glob from 'glob';
import { injectable, inject } from "inversify";
import { WorkflowAnalyzer } from "../common/workflow-analyze-protocol";
import { RawProcess, RawProcessFactory } from '@theia/process/lib/node/raw-process';
import { isString } from 'util';

@injectable()
export class WorkflowAnalyzerServer implements WorkflowAnalyzer {
    constructor(@inject(RawProcessFactory)
    protected readonly processFactory: RawProcessFactory) { }
    analyze(wfUri: string, wfConfigUri: string): Promise<string> {
        const serverPath = path.resolve(__dirname, '..', '..', 'server');
        const jarPaths = glob.sync('**/plugins/org.eclipse.equinox.launcher_*.jar', { cwd: serverPath });
        if (jarPaths.length === 0) {
            throw new Error('The Java server launcher is not found.');
        }

        const jarPath = path.resolve(serverPath, jarPaths[0]);
        const command = 'java';
        const args: string[] = [];

        args.push(
            '-jar', jarPath,
        );

        return new Promise((resolve) => {
            return this.getSocket(command, args).then(socket => {
                console.log('[WorkflowAnalyzer] Send Request for ' + wfUri + ' and ' + wfConfigUri)
                const request = {'WF-URI':wfUri,'WFConfig-URI':wfConfigUri };
                socket.write(JSON.stringify(request)+'\n');

                socket.on('data', data => {
                    return resolve(data.toString());
                });
            });
        });
    }
    private async getSocket(command: string, args: string[]):Promise<net.Socket> {
        const server: net.Server = await this.startSocketServer();
        const socket = this.accept(server);
        const address = server.address();

        if (isString(address) || !address) {
            throw new Error("Wrong type of socket!");
        }
        args.push(
            '-host', address.address,
            '-port', address.port.toString(),
        );
        console.log('[WorkflowAnalyzer] Spawn Process with Command ' + command + ' and arguments ' + args)
        this.spawnProcess(command, args);
        return socket;
    }
    dispose(): void {
        //do nothing
    }
    setClient(): void {
        //do nothing
    }

    private spawnProcess(command: string, args?: string[]): RawProcess {
        const rawProcess = this.processFactory({ command, args });
        rawProcess.process.once('error', this.onDidFailSpawnProcess.bind(this));
        const stderr = rawProcess.process.stderr;
        if(stderr)
            stderr.on('data', this.logError.bind(this));
        return rawProcess;
    }
    protected onDidFailSpawnProcess(error: Error): void {
        console.error(JSON.stringify(error));
    }

    protected logError(data: string | Buffer) {
        if (data) {
            console.error(`Code Gen: ${data}`);
        }
    }

    protected startSocketServer(): Promise<net.Server> {
        return new Promise(resolve => {
            const server = net.createServer();
            server.addListener('listening', () =>
                resolve(server)
            );
            // allocate ports dynamically
            server.listen(0, '127.0.0.1');
            console.log('[WorkflowAnalyzer] Server Started')
        });
    }

    protected accept(server: net.Server): Promise<net.Socket> {
        return new Promise((resolve, reject) => {
            server.on('error', reject);
            server.on('connection', socket => {
                console.log('[WorkflowAnalyzer] Socket Started')
                // stop accepting new connections
                server.close();
                resolve(socket);
            });
        });
    }

}