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

import { MasterTreeWidget, TreeEditor } from '@eclipse-emfcloud/theia-tree-editor';
import { injectable } from '@theia/core/shared/inversify';

@injectable()
export class CoffeeMasterTreeWidget extends MasterTreeWidget {
    override select(paths: string[]): void {
        if (paths.length === 0) {
            return;
        }
        const rootNode = this.model.root as TreeEditor.Node;
        // eslint-disable-next-line @typescript-eslint/ban-ts-comment
        // @ts-ignore
        const toSelect = paths.reduceRight((node, path) => node.children.find(value => value.id === path), rootNode) as TreeEditor.Node;
        this.model.selectNode(toSelect);
        this.model.refresh();
        this.refreshModelChildren();
    }
}
