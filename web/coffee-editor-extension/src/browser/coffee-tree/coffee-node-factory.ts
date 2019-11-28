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
import { JsonFormsTree } from 'jsonforms-tree-extension/lib/browser/tree/json-forms-tree';
import { v4 } from 'uuid';

import { CoffeeModel } from './coffee-model';

@injectable()
export class CoffeeTreeNodeFactory implements JsonFormsTree.NodeFactory {

    constructor(
        @inject(JsonFormsTree.LabelProvider) private readonly labelProvider: JsonFormsTree.LabelProvider,
        @inject(ILogger) private readonly logger: ILogger) {
    }

    mapDataToNodes(treeData: JsonFormsTree.TreeData): JsonFormsTree.Node[] {
        const node = this.mapData(treeData.data);
        if (node) {
            return [node];
        }
        return [];
    }

    mapData(data: any, parent?: JsonFormsTree.Node, property?: string, index?: number): JsonFormsTree.Node {
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
                type: this.getType(data.eClass, data),
                data: data,
                property: property,
                index: index !== undefined ? index.toFixed(0) : undefined
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

    hasCreatableChildren(node: JsonFormsTree.Node): boolean {
        return node ? CoffeeModel.childrenMapping.get(node.jsonforms.type) !== undefined : false;
    }

    protected defaultNode(): Pick<
        JsonFormsTree.Node,
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
