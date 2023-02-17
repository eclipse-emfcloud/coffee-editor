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
import { ComparisonExtensionConfiguration } from '@eclipsesource/comparison-extension/lib/browser/comparison-extension-configuration';
import { codicon } from '@theia/core/lib/browser';
import { injectable } from 'inversify';
import * as path from 'path';

const BACKEND_VERSION = '0.8.0-SNAPSHOT';

@injectable()
export class CoffeeComparisonFrontendConfiguration extends ComparisonExtensionConfiguration {
    fileExtensions = ['.coffee'];

    override getDiffViewIcon(): string {
        return codicon('diff');
    }

    override getGraphicalDiffViewIcon(): string {
        return codicon('files');
    }

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
            `org.eclipse.emfcloud.coffee.model-${BACKEND_VERSION}.jar`
        );
    }

    getModelPackageName(): string {
        return 'org.eclipse.emfcloud.coffee.CoffeePackage';
    }

    override supportGraphicalComparison(): boolean {
        return true;
    }
}
