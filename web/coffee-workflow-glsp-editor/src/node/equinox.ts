/*
 * Copyright (c) 2021 EclipseSource and others.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0, or the MIT License which is
 * available at https://opensource.org/licenses/MIT.
 *
 * SPDX-License-Identifier: EPL-2.0 OR MIT
 */
import * as glob from 'glob';
import * as path from 'path';

export function findEquinoxLauncher(productPath: string): string {
    const jarPaths = glob.sync('**/plugins/org.eclipse.equinox.launcher_*.jar', { cwd: productPath });
    if (jarPaths.length === 0) {
        throw new Error('The eclipse.equinox.launcher is not found. ');
    }
    const jarPath = path.resolve(productPath, jarPaths[0]);
    return jarPath;
}
