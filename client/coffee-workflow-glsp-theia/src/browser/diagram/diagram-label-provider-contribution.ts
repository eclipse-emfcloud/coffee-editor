/*
 * Copyright (c) 2019-2022 EclipseSource and others.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0, or the MIT License which is
 * available at https://opensource.org/licenses/MIT.
 *
 * SPDX-License-Identifier: EPL-2.0 OR MIT
 */
import { codiconCSSString } from '@eclipse-glsp/client';
import { UriSelection } from '@theia/core';
import { LabelProviderContribution } from '@theia/core/lib/browser';
import URI from '@theia/core/lib/common/uri';
import { FileStat } from '@theia/filesystem/lib/common/files';
import { injectable } from 'inversify';

import { WorkflowNotationLanguage } from '../../common/workflow-language';

@injectable()
export class WorkflowDiagramLabelProviderContribution implements LabelProviderContribution {
    canHandle(uri: object): number {
        let toCheck: any = uri;
        if (FileStat.is(toCheck)) {
            toCheck = toCheck.resource;
        } else if (UriSelection.is(uri)) {
            toCheck = UriSelection.getUri(uri);
        }
        if (toCheck instanceof URI) {
            if (toCheck.path.ext === WorkflowNotationLanguage.fileExtensions[0]) {
                return 1000;
            }
        }
        return 0;
    }

    getIcon(): string {
        return codiconCSSString('type-hierarchy-sub') + ' default-file-icon';
    }
}
