/*!
 * Copyright (C) 2019 EclipseSource and others.
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
 */
import { ILogger } from '@theia/core';
import { Navigatable } from '@theia/core/lib/browser';
import URI from '@theia/core/lib/common/uri';
import { WorkspaceService } from '@theia/workspace/lib/browser';

import { JSONFormsWidget } from './json-forms-widget';
import { JsonFormsTreeEditorWidget } from './tree-editor-widget';
import { JsonFormsTreeWidget } from './tree-widget';

export const NavigatableTreeEditorOptions = Symbol(
    'JsonFormsTreeEditorWidgetOptions'
);
export interface NavigatableTreeEditorOptions {
    uri: URI;
}

export abstract class NavigatableTreeEditorWidget extends JsonFormsTreeEditorWidget implements Navigatable {

    constructor(
        protected readonly treeWidget: JsonFormsTreeWidget,
        protected readonly formWidget: JSONFormsWidget,
        protected readonly workspaceService: WorkspaceService,
        protected readonly logger: ILogger,
        readonly widget_id: string,
        protected readonly options: NavigatableTreeEditorOptions,
    ) {
        super(
            treeWidget,
            formWidget,
            workspaceService,
            logger,
            widget_id
        );
    }

    /** The uri of the editor's resource. */
    get uri(): URI {
        return this.options.uri;
    }

    getResourceUri(): URI | undefined {
        return this.uri;
    }

    createMoveToUri(resourceUri: URI): URI | undefined {
        return this.options.uri && this.options.uri.withPath(resourceUri.path);
    }

}
