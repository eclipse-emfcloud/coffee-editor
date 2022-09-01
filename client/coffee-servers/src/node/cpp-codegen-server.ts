/*
 * Copyright (c) 2021-2022 EclipseSource and others.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0, or the MIT License which is
 * available at https://opensource.org/licenses/MIT.
 *
 * SPDX-License-Identifier: EPL-2.0 OR MIT
 */
import { CppCodeGenServer } from 'coffee-cpp-extension/lib/common/generate-protocol';
import { injectable } from 'inversify';
import { EquinoxServer } from './equinox-server';

@injectable()
export class CoffeeCppCodeGenServer extends EquinoxServer implements CppCodeGenServer {
    protected serverName = 'CoffeeCppCodeGenServer';

    generateCode(sourceFile: string, targetFolder: string, packageName: string): Promise<string> {
        const command = 'java';

        const jarPath = this.getEquinoxJarPath('cpp-codegen');
        if (jarPath.length === 0) {
            throw new Error('CoffeeCppCodeGenServer launcher not found.');
        }
        const args: string[] = ['-jar', jarPath, '-targetFolder', targetFolder, '-source', sourceFile, '-packageName', packageName];

        return new Promise(resolve => {
            const process = this.spawnProcess(command, args);
            if (process === undefined || process.process === undefined) {
                resolve('Process not spawned');
                return;
            }
            process.process.on('exit', code => {
                switch (code) {
                    case 0:
                        resolve('OK');
                        break;
                    case -10:
                        resolve('Target Folder Parameter missing');
                        break;
                    case -11:
                        resolve('Source File Parameter missing');
                        break;
                    case -12:
                        resolve('Package Name Parameter missing');
                        break;
                    case -20:
                        resolve('Encoding not found, check Server Log!');
                        break;
                    case -30:
                        resolve('IO Exception occurred, check Server Log!');
                        break;
                    default:
                        resolve('UNKNOWN ERROR');
                        break;
                }
            });
        });
    }
}
