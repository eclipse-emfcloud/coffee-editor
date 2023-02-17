/*
 * Copyright (c) 2022-2023 EclipseSource and others.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0, or the MIT License which is
 * available at https://opensource.org/licenses/MIT.
 *
 * SPDX-License-Identifier: EPL-2.0 OR MIT
 */
import { LaunchOptions } from '@eclipse-emfcloud/modelserver-theia/lib/node';
import { injectable } from '@theia/core/shared/inversify';
import { join, resolve } from 'path';

const SERVER_DIR_PATH = join(__dirname, '..', '..', 'servers');

const BACKEND_VERSION = '0.8.0-SNAPSHOT';
const JAR_FILE_PATH = resolve(join(SERVER_DIR_PATH, `org.eclipse.emfcloud.coffee.modelserver-${BACKEND_VERSION}-standalone.jar`));
const LOG_FILE_PATH = resolve(join(SERVER_DIR_PATH, 'model-server-log4j2-embedded.xml'));

/** Options for the `ModelServerLauncher` to use to start the Model Server */
@injectable()
export class WorkflowModelServerLaunchOptions implements LaunchOptions {
    baseURL = 'api/v2/';
    serverPort = 8081;
    hostname = 'localhost';
    jarPath = JAR_FILE_PATH;
    additionalArgs = [`-l=${LOG_FILE_PATH}`];
}
