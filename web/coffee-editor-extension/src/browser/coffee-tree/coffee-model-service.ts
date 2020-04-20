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

import { CoffeeModel } from './coffee-model';
import {
    automaticTaskView,
    brewingView,
    coffeeSchema,
    controlUnitView,
    decisionView,
    dipTrayView,
    flowView,
    machineView,
    manualTaskView,
    mergeView,
    waterTankView,
    weightedFlowView,
    workflowView,
} from './coffee-schemas';

@injectable()
export class CoffeeModelService implements TreeEditor.ModelService {

    constructor(@inject(ILogger) private readonly logger: ILogger) { }

    getDataForNode(node: TreeEditor.Node) {
        return node.jsonforms.data;
    }

    getSchemaForNode(node: TreeEditor.Node) {
        return {
            definitions: coffeeSchema.definitions,
            ...this.getSubSchemaForNode(node)
        };
    }

    private getSubSchemaForNode(node: TreeEditor.Node) {
        const schema = this.getSchemaForType(node.jsonforms.type);
        if (schema) {
            return schema;
        }
        // there is no type, try to guess
        if (node.jsonforms.data.nodes) {
            return coffeeSchema.definitions.workflow;
        }
        return undefined;
    }
    private getSchemaForType(type: string) {
        if (!type) {
            return undefined;
        }
        const schema = Object.entries(coffeeSchema.definitions)
            .map(entry => entry[1])
            .find(
                definition =>
                    definition.properties && definition.properties.eClass.const === type
            );
        if (!schema) {
            this.logger.warn("Can't find definition schema for type " + type);
        }
        return schema;
    }
    getUiSchemaForNode(node: TreeEditor.Node) {
        const schema = this.getUiSchemaForType(node.jsonforms.type);
        if (schema) {
            return schema;
        }
        // there is no type, try to guess
        if (node.jsonforms.data.nodes) {
            return workflowView;
        }
        return undefined;
    }

    private getUiSchemaForType(type: string) {
        if (!type) {
            return undefined;
        }
        switch (type) {
            case CoffeeModel.Type.Machine:
                return machineView;
            case CoffeeModel.Type.ControlUnit:
                return controlUnitView;
            case CoffeeModel.Type.BrewingUnit:
                return brewingView;
            case CoffeeModel.Type.AutomaticTask:
                return automaticTaskView;
            case CoffeeModel.Type.ManualTask:
                return manualTaskView;
            case CoffeeModel.Type.DipTray:
                return dipTrayView;
            case CoffeeModel.Type.WaterTank:
                return waterTankView;
            case CoffeeModel.Type.Flow:
                return flowView;
            case CoffeeModel.Type.WeightedFlow:
                return weightedFlowView;
            case CoffeeModel.Type.Decision:
                return decisionView;
            case CoffeeModel.Type.Merge:
                return mergeView;
            default:
                this.logger.warn("Can't find registered ui schema for type " + type);
                return undefined;
        }
    }

    getChildrenMapping(): Map<string, TreeEditor.ChildrenDescriptor[]> {
        return CoffeeModel.childrenMapping;
    }

    getNameForType(eClass: string): string {
        return CoffeeModel.Type.name(eClass);
    }
}
