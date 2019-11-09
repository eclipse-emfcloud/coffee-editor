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
import { ChildrenDescriptor, ModelService } from 'jsonforms-tree-extension/lib/browser/model-service';
import { JsonFormsTree } from 'jsonforms-tree-extension/lib/browser/tree/json-forms-tree';

import { CoffeeModel } from './coffee-model';
import {
    automaticTaskView,
    brewingView,
    coffeeSchema,
    controlUnitView,
    machineView,
    manualTaskView,
    workflowView,
} from './coffee-schemas';

@injectable()
export class CoffeeModelService implements ModelService {

    constructor(@inject(ILogger) private readonly logger: ILogger) { }

    getEClassFromKey(key: string): string {
        return CoffeeModel.Type[key[0].toUpperCase() + key.slice(1)];
    }

    getSchemaForNode(node: JsonFormsTree.Node) {
        return {
            definitions: coffeeSchema.definitions,
            ...this.getSubSchemaForNode(node)
        };
    }

    private getSubSchemaForNode(node: JsonFormsTree.Node) {
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
    getUiSchemaForNode(node: JsonFormsTree.Node) {
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
            default:
                this.logger.warn("Can't find registered ui schema for type " + type);
                return undefined;
        }
    }

    getChildrenMapping(): Map<string, ChildrenDescriptor[]> {
        return CoffeeModel.childrenMapping;
    }

    getNameFromEClass(eClass: string): string {
        return CoffeeModel.Type.name(eClass);
    }
}
