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
import { sync } from 'glob';
import { ContainerModule, injectable } from 'inversify';
import { join, resolve } from 'path';

@injectable()
export class CoffeeModelServerLaunchOptions implements LaunchOptions {
    baseURL = 'api/v1/';
    serverPort = 8081;
    hostname = 'localhost';
    jarPath = getJarPath('model');
    additionalArgs = ['--errorsOnly'];
}

export default new ContainerModule((bind, _unbind, isBound, rebind) => {
    if (isBound(LaunchOptions)) {
        rebind(LaunchOptions).to(CoffeeModelServerLaunchOptions).inSingletonScope();
    } else {
        bind(LaunchOptions).to(CoffeeModelServerLaunchOptions).inSingletonScope();
    }
});

export const getJarPath = (server: string): string => {
    const serverPath = resolve(join(__dirname, '..', '..', 'server', server));
    const jarPaths = sync('**/plugins/org.eclipse.equinox.launcher_*.jar', { cwd: serverPath });
    const jarPath = resolve(serverPath, jarPaths[0]);
    return jarPath;
};
