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
import { LabelProvider } from '@theia/core/lib/browser';
import { inject, injectable } from 'inversify';
import { TreeEditor } from 'theia-tree-editor';
import { v4 } from 'uuid';

import { CoffeeModel } from './coffee-model';
import { CoffeeTreeEditorWidget } from './coffee-tree-editor-widget';

@injectable()
export class CoffeeTreeNodeFactory implements TreeEditor.NodeFactory {

    constructor(
        @inject(LabelProvider) private readonly labelProvider: LabelProvider,
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
        const node: TreeEditor.Node = {
            ...this.defaultNode(),
            editorId: CoffeeTreeEditorWidget.EDITOR_ID,
            name: this.labelProvider.getName(data), // TODO correct? name even necessary?
            parent: parent,
            jsonforms: {
                type: this.getType(data.eClass, data),
                data: data,
                property: property,
                index: typeof indexOrKey === 'number' ? indexOrKey.toFixed(0) : indexOrKey
            }
        };
        // containments
        if (parent) {
            parent.children.push(node);
            parent.expanded = true;
        }
        if (data.children) {
            // component types
            data.children.forEach((element, idx) => {
                this.mapData(element, node, 'children', idx);
            });
        }
        if (data.workflows) {
            // machine type
            data.workflows.forEach((element, idx) => {
                element.eClass = CoffeeModel.Type.Workflow;
                this.mapData(element, node, 'workflows', idx);
            });
        }
        if (data.nodes) {
            // workflow type
            data.nodes.forEach((element, idx) => {
                this.mapData(element, node, 'nodes', idx);
            });
        }
        if (data.flows) {
            // workflow type
            data.flows.forEach((element, idx) => {
                this.mapData(element, node, 'flows', idx);
            });
        }
        return node;
    }

    hasCreatableChildren(node: TreeEditor.Node): boolean {
        return node ? CoffeeModel.childrenMapping.get(node.jsonforms.type) !== undefined : false;
    }

    protected defaultNode(): Pick<
        TreeEditor.Node,
        'children' | 'name' | 'jsonforms' | 'id' | 'icon' | 'description' | 'visible' | 'parent' | 'previousSibling' | 'nextSibling' | 'expanded' | 'selected' | 'focus'
    > {
        return {
            id: v4(),
            expanded: false,
            selected: false,
            parent: undefined,
            children: [],
            name: undefined,
            jsonforms: undefined
        };
    }

    protected getType(type: string, data: any): string {
        // TODO: eClass should always be sent from server

        if (type) {
            // given eClass
            return type;
        }
        if (data.eClass) {
            // eClass of node
            return data.eClass;
        }
        // guess
        if (data.nodes) {
            return CoffeeModel.Type.Workflow;
        }
        return undefined;
    }

}
