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
import { inject, injectable } from 'inversify';
import { TreeEditor } from 'theia-tree-editor';
import { v4 } from 'uuid';

@injectable()
export class PreferencesTreeNodeFactory implements TreeEditor.NodeFactory {

    constructor(
        @inject(TreeEditor.LabelProvider) private readonly labelProvider: TreeEditor.LabelProvider,
        @inject(ILogger) private readonly logger: ILogger) {
    }

    mapDataToNodes(treeData: TreeEditor.TreeData): TreeEditor.Node[] {
        const node = this.mapData(treeData.data);
        if (node) {
            return [node];
        }
        return [];
    }

    mapData(data: any, parent?: TreeEditor.Node, property?: string, indexOrKey?: number | string): TreeEditor.Node {
        if (!data) {
            // sanity check
            this.logger.warn('mapData called without data');
            return undefined;
        }
        const node = {
            ...this.defaultNode(),
            name: this.labelProvider.getName(data),
            parent: parent,
            jsonforms: {
                type: this.getType(data),
                data: data,
                property: property,
                index: typeof indexOrKey === 'number' ? indexOrKey.toFixed(0) : indexOrKey
            }
        };
        // add to parent node if present
        if (parent) {
            parent.children.push(node);
            parent.expanded = true;
        }
        if (typeof data.properties === 'object' && data.properties !== null) {
            Object.keys(data.properties).forEach(key => {
                // TODO is there a better way to store the property name?!
                data.properties[key].name = key;
                this.mapData(data.properties[key], node, 'properties');
            });
        }
        return node;
    }

    hasCreatableChildren(node: TreeEditor.Node): boolean {
        // TODO implement hasCreatableChildren if necessary
        return false;
    }

    protected defaultNode(): Pick<
        TreeEditor.Node,
        'id' | 'expanded' | 'selected' | 'parent' | 'decorationData' | 'children'
    > {
        return {
            id: v4(),
            expanded: false,
            selected: false,
            parent: undefined,
            decorationData: {},
            children: []
        };
    }

    protected getType(data: any): string {
        // TODO: implement type determination if needed
        return undefined;
    }

}
