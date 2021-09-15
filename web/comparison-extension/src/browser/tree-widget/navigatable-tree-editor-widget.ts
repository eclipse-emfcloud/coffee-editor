/********************************************************************************
 * Copyright (c) 2021 EclipseSource and others.
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
import { ILogger } from '@theia/core';
import { Navigatable, Title, Widget } from '@theia/core/lib/browser';
import URI from '@theia/core/lib/common/uri';
import { WorkspaceService } from '@theia/workspace/lib/browser';

import { MasterTreeWidget } from './master-tree-widget';
import { BaseTreeEditorWidget } from './tree-editor-widget';

export const NavigatableTreeEditorOptions = Symbol(
    'NavigatableTreeEditorOptions'
);
export interface NavigatableTreeEditorOptions {
    uri: URI;
}

export abstract class NavigatableTreeEditorWidget extends BaseTreeEditorWidget implements Navigatable {

    constructor(
        protected readonly myTreeWidgetModel1: MasterTreeWidget,
        protected readonly myTreeWidgetModel2: MasterTreeWidget,
        protected readonly treeWidget3: MasterTreeWidget,
        protected readonly workspaceService: WorkspaceService,
        protected readonly logger: ILogger,
        readonly widget_id: string,
        protected readonly options: NavigatableTreeEditorOptions
    ) {
        super(
            myTreeWidgetModel1,
            myTreeWidgetModel2,
            treeWidget3,
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

    protected configureTitle(title: Title<Widget>): void {
        title.label = "this.options.uri.path.base";
        title.caption = "this.options.uri.toString()";
        title.closable = true;
    }
}
