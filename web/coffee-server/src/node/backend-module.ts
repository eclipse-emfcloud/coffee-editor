/*!
 * Copyright (c) 2019-2020 EclipseSource and others.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0, or the MIT License which is
 * available at https://opensource.org/licenses/MIT.
 *
 * SPDX-License-Identifier: EPL-2.0 OR MIT
 */
import { LaunchOptions } from '@eclipse-emfcloud/modelserver-theia';
import { BackendApplicationContribution } from '@theia/core/lib/node/backend-application';
import { ContainerModule, injectable } from 'inversify';
import { join, resolve } from 'path';

import { GLSPLaunchOptions, GLSPServerLauncher } from './glsp-server-launcher';

export default new ContainerModule((bind, _unbind, isBound, rebind) => {
    if (isBound(LaunchOptions)) {
        rebind(LaunchOptions).to(CoffeeLaunchOptions).inSingletonScope();
    } else {
        bind(LaunchOptions).to(CoffeeLaunchOptions).inSingletonScope();
    }
    bind(GLSPLaunchOptions).to(CoffeeGlspLaunchOptions).inSingletonScope();
    bind(BackendApplicationContribution).to(GLSPServerLauncher);
});

@injectable()
export class CoffeeLaunchOptions implements LaunchOptions {
    isRunning = false;
    baseURL = 'api/v1/';
    serverPort = 8081;
    hostname = 'localhost';
    jarPath = resolve(join(__dirname, '..', '..', 'build', 'com.eclipsesource.coffee.modelserver-0.1.0-SNAPSHOT-standalone.jar'));
    additionalArgs = ['--errorsOnly'];
}

@injectable()
export class CoffeeGlspLaunchOptions implements GLSPLaunchOptions {
    isRunning: false;
    serverPort: 5008;
    hostname: 'localhost';
    jarPath = resolve(join(__dirname, '..', '..', 'build', 'com.eclipsesource.workflow.glsp.server-0.0.1-SNAPSHOT-glsp.jar'));
}
