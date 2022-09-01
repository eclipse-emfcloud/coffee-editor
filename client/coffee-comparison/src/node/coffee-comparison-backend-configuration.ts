/*
 * Copyright (c) 2022 EclipseSource and others.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0, or the MIT License which is
 * available at https://opensource.org/licenses/MIT.
 *
 * SPDX-License-Identifier: EPL-2.0 OR MIT
 */
import { ComparisonExtensionConfiguration } from '@eclipsesource/comparison-extension/lib/browser/comparison-extension-configuration';
import { injectable } from 'inversify';
import * as path from 'path';

@injectable()
export class CoffeeComparisonBackendConfiguration extends ComparisonExtensionConfiguration {
    fileExtensions = ['.coffee'];

    getModelJarPath(): string {
        return path.resolve(
            __dirname,
            '..',
            '..',
            '..',
            '..',
            'backend',
            'plugins',
            'org.eclipse.emfcloud.coffee.model',
            'target',
            'org.eclipse.emfcloud.coffee.model-0.1.0-SNAPSHOT.jar'
        );
    }

    getModelPackageName(): string {
        return 'org.eclipse.emfcloud.coffee.CoffeePackage';
    }

    override supportGraphicalComparison(): boolean {
        return true;
    }
}
