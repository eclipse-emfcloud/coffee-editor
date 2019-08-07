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
import { BackendApplicationContribution } from '@theia/core/lib/node/backend-application';
import { RawProcess, RawProcessFactory } from '@theia/process/lib/node/raw-process';
import { Application } from 'express';
import * as glob from 'glob';
import { inject, injectable } from 'inversify';
import * as path from 'path';

import { CodeGenServer } from '../common/generate-protocol';

@injectable()
export class CoffeeCodeGenServer implements CodeGenServer, BackendApplicationContribution {

    constructor(
        @inject(RawProcessFactory) protected readonly processFactory: RawProcessFactory,
        @inject(ILogger) private readonly logger: ILogger) { }

    generateCode(sourceFile: string, targetFolder: string, packageName: string): Promise<string> {
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
            '-targetFolder', targetFolder,
            '-source', sourceFile,
            '-packageName', packageName
        );

        return new Promise(resolve => {
            const process = this.spawnProcess(command, args);
            if (process === undefined || process.process === undefined) {
                resolve('Process not spawned');
                return;
            }
            process.process.on('exit', code => {
                switch (code) {
                    case 0: resolve('OK'); break;
                    case -10: resolve('Target Folder Parameter missing'); break;
                    case -11: resolve('Source File Parameter missing'); break;
                    case -12: resolve('Package Name Parameter missing'); break;
                    case -20: resolve('Encoding not found, check Server Log!'); break;
                    case -30: resolve('IO Exception occurred, check Server Log!'); break;
                    default: resolve('UNKNOWN ERROR'); break;
                }
            });
        });
    }

    onStop(app?: Application): void {
        this.dispose();
    }

    dispose(): void {
        // do nothing
    }

    setClient(): void {
        // do nothing
    }

    private spawnProcess(command: string, args?: string[]): RawProcess | undefined {
        const rawProcess = this.processFactory({ command, args });
        if (rawProcess.process === undefined) {
            return undefined;
        }
        rawProcess.process.on('error', this.onDidFailSpawnProcess.bind(this));
        const stderr = rawProcess.process.stderr;
        if (stderr) {
            stderr.on('data', this.logError.bind(this));
        }
        return rawProcess;
    }

    protected onDidFailSpawnProcess(error: Error): void {
        this.logger.error(error);
    }

    protected logError(data: string | Buffer) {
        if (data) {
            this.logger.error(`Code Gen: ${data}`);
        }
    }

}
