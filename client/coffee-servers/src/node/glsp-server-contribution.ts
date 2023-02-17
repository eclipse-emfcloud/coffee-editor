/*
 * Copyright (c) 2020-2023 EclipseSource and others.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0, or the MIT License which is
 * available at https://opensource.org/licenses/MIT.
 *
 * SPDX-License-Identifier: EPL-2.0 OR MIT
 */
import { getPort, GLSPSocketServerContribution, GLSPSocketServerContributionOptions } from '@eclipse-glsp/theia-integration/lib/node';
import { injectable } from '@theia/core/shared/inversify';
import { WorkflowNotationLanguage } from 'coffee-workflow-glsp-theia/lib/common';
import { join, resolve } from 'path';

export const GLSP_PORT = 5008;
export const PORT_ARG_KEY = 'WF_GLSP';
export const LOG_DIR = join(__dirname, '..', '..', 'logs');

const BACKEND_VERSION = '0.8.0-SNAPSHOT';
const SERVER_DIR_PATH = join(__dirname, '..', '..', 'servers');
const JAR_FILE = resolve(join(SERVER_DIR_PATH, `org.eclipse.emfcloud.coffee.workflow.glsp.server-${BACKEND_VERSION}-glsp.jar`));

@injectable()
export class WorkflowGLSPServerContribution extends GLSPSocketServerContribution {
    readonly id = WorkflowNotationLanguage.contributionId;

    createContributionOptions(): Partial<GLSPSocketServerContributionOptions> {
        return {
            executable: JAR_FILE,
            additionalArgs: ['--consoleLog', 'false', '--fileLog', 'true', '--logDir', LOG_DIR],
            socketConnectionOptions: {
                port: getPort(PORT_ARG_KEY, GLSP_PORT)
            }
        };
    }
}
