/********************************************************************************
 * Copyright (c) 2019 EclipseSource and others.
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
 ********************************************************************************/
import { getPort } from '@eclipse-glsp/protocol';
import {
    JavaSocketServerContribution,
    JavaSocketServerLaunchOptions,
    START_UP_COMPLETE_MSG
} from '@eclipse-glsp/theia-integration/lib/node';
import { ILogger } from '@theia/core';
import * as fs from 'fs';
import { inject, injectable } from 'inversify';
import { join } from 'path';

import { WorkflowNotationLanguage } from '../common/workflow-language';
import { findEquinoxLauncher } from './equinox';

export const PORT_ARG_KEY = 'WORKFLOW_NOTATION_LSP';
export const SERVER_DIR = join(__dirname, '..', '..', '..', 'coffee-server', 'server');
export const GLSP_JAR_FILE = join(SERVER_DIR, 'glsp');

@injectable()
export class WorkflowGLSPServerContribution extends JavaSocketServerContribution {

    @inject(ILogger) private readonly logger: ILogger;

    readonly id = WorkflowNotationLanguage.contributionId;

    createLaunchOptions(): Partial<JavaSocketServerLaunchOptions> {
        return {
            jarPath: GLSP_JAR_FILE,
            additionalArgs: ['--consoleLog', 'true'],
            socketConnectionOptions: {
                port: getPort(PORT_ARG_KEY)
            }
        };
    }

    async launch(): Promise<void> {
        if (!fs.existsSync(this.launchOptions.jarPath)) {
            throw new Error(`Could not launch GLSP server. The given jar path is not valid: ${this.launchOptions.jarPath}`);
        }
        if (isNaN(this.launchOptions.socketConnectionOptions.port)) {
            throw new Error(`Could not launch GLSP Server. The given server port is not a number: ${this.launchOptions.socketConnectionOptions.port}`);
        }
        let args = ['-jar', findEquinoxLauncher(this.launchOptions.jarPath), '--port', `${this.launchOptions.socketConnectionOptions.port}`];
        if (this.launchOptions.additionalArgs) {
            args = [...args, ...this.launchOptions.additionalArgs];
        }

        await this.spawnProcessAsync('java', args, undefined);
        return this.onReady;
    }
    protected processLogInfo(data: string | Buffer): void {
        if (data) {
            const message = data.toString();
            if (message.startsWith(START_UP_COMPLETE_MSG)) {
                this.resolveReady();
            }
            this.logger.info(`WorkflowGLSPServerContribution: ${data}`);
        }
    }

    protected processLogError(data: string | Buffer): void {
        if (data) {
            this.logger.error(`WorkflowGLSPServerContribution: ${data}`);
        }
    }

}
