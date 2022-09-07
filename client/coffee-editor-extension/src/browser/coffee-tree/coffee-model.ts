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
import { AnyObject, isArray, isObject, isString, ModelServerObjectV2 } from '@eclipse-emfcloud/modelserver-client';
import { TreeEditor } from '@eclipse-emfcloud/theia-tree-editor';

const $typeBase = 'http://www.eclipse.org/emfcloud/coffee/model#//';

export type JsonPrimitiveType = string | boolean | number | object;
export namespace JsonPrimitiveType {
    export function is(object: unknown): object is JsonPrimitiveType {
        return (
            object !== undefined &&
            (typeof object === 'string' || typeof object === 'boolean' || typeof object === 'number' || typeof object === 'object')
        );
    }
}

export interface Identifiable extends ModelServerObjectV2, AnyObject {
    id: string;
}

export namespace Identifiable {
    export function is(object: unknown): object is Identifiable {
        return AnyObject.is(object) && isString(object, '$id') && isString(object, 'id');
    }
}

export interface Component extends Identifiable {
    children?: Component[];
    parent?: Component;
}
export namespace Component {
    export const $type = `${$typeBase}Component`;

    export function is(object: unknown): object is Component {
        return Identifiable.is(object);
    }
}

export interface Machine extends Component {
    children?: Component[];
    name: string;
    workflows: Workflow[];
}
export namespace Machine {
    export const $type = `${$typeBase}Machine`;

    export function is(object: unknown): object is Machine {
        return Identifiable.is(object) && $type === object.$type && isString(object, 'name') && isArray(object, 'workflows');
    }
}

export interface ControlUnit extends Component {
    processor?: Processor;
    dimension?: Dimension;
    ram?: RAM[];
    display?: Display;
    userDescription?: string;
}
export namespace ControlUnit {
    export const $type = `${$typeBase}ControlUnit`;

    export function is(object: unknown): object is ControlUnit {
        return Identifiable.is(object) && $type === object.$type;
    }
}

export interface BrewingUnit extends Component {}
export namespace BrewingUnit {
    export const $type = `${$typeBase}BrewingUnit`;

    export function is(object: unknown): object is BrewingUnit {
        return Identifiable.is(object) && $type === object.$type;
    }
}

export interface DipTray extends Component {}
export namespace DipTray {
    export const $type = `${$typeBase}DipTray`;

    export function is(object: unknown): object is DipTray {
        return Identifiable.is(object) && $type === object.$type;
    }
}

export interface WaterTank extends Component {}
export namespace WaterTank {
    export const $type = `${$typeBase}WaterTank`;

    export function is(object: unknown): object is WaterTank {
        return Identifiable.is(object) && $type === object.$type;
    }
}

export interface Processor extends Identifiable {
    vendor?: string;
    clockSpeed?: number;
    numberOfCores?: number;
    socketconnectorType?: SocketConnectorType;
    thermalDesignPower?: number;
    manufactoringProcess?: ManufactoringProcess;
}
export namespace Processor {
    export const $type = `${$typeBase}Processor`;

    export function is(object: unknown): object is WaterTank {
        return Identifiable.is(object);
        // && isString(object, 'vendor') && isNumber(object, 'clockSpeed') && isNumber(object, 'thermalDesignPower')
    }
}

// eslint-disable-next-line no-shadow
export enum SocketConnectorType {
    A1T = 0,
    Z51 = 1
}

// eslint-disable-next-line no-shadow
export enum ManufactoringProcess {
    nm18 = 0,
    nm25 = 1
}

export interface Dimension extends Identifiable {
    width?: number;
    height?: number;
    length?: number;
}
export namespace Dimension {
    export const $type = `${$typeBase}Dimension`;

    export function is(object: unknown): object is Dimension {
        return Identifiable.is(object); // && isNumber(object, 'width') && isNumber(object, 'height') && isNumber(object, 'length');
    }
}

export interface RAM extends Identifiable {
    clockSpeed?: number;
    size?: number;
    ramType?: RamType;
}
export namespace RAM {
    export const $type = `${$typeBase}RAM`;

    export function is(object: unknown): object is RAM {
        return Identifiable.is(object); // && isNumber(object, 'clockSpeed') && isNumber(object, 'size');
    }
}

// eslint-disable-next-line no-shadow
export enum RamType {
    'SODIMM' = 0,
    'SIDIMM' = 1
}

export interface Display extends Identifiable {
    width?: number;
    height?: number;
}
export namespace Display {
    export const $type = `${$typeBase}Display`;

    export function is(object: unknown): object is Display {
        return Identifiable.is(object); // && isNumber(object, 'width') && isNumber(object, 'height');
    }
}

export interface Workflow extends Identifiable {
    name: string;
    nodes?: Node[];
    flows?: Flow[];
}
export namespace Workflow {
    export const $type = `${$typeBase}Workflow`;

    export function is(object: unknown): object is Workflow {
        return Identifiable.is(object) && isString(object, 'name') && isArray(object, 'nodes'); // && isArray(object, 'flows');
    }
}

export interface Node extends Identifiable {}
export namespace Node {
    export const $type = `${$typeBase}Node`;

    export function is(object: unknown): object is Node {
        return Identifiable.is(object);
    }
}

export interface Task extends Node {
    name: string;
    duration?: number;
}
export namespace Task {
    export const $type = `${$typeBase}Task`;

    export function is(object: unknown): object is Task {
        return Identifiable.is(object) && isString(object, 'name');
    }
}

export interface AutomaticTask extends Task {
    component?: Component;
}
export namespace AutomaticTask {
    export const $type = `${$typeBase}AutomaticTask`;

    export function is(object: unknown): object is AutomaticTask {
        return Task.is(object) && $type === object.$type;
    }
}

export interface ManualTask extends Task {
    actor?: string;
}
export namespace ManualTask {
    export const $type = `${$typeBase}ManualTask`;

    export function is(object: unknown): object is ManualTask {
        return Task.is(object) && $type === object.$type;
    }
}

export interface Fork extends Node {}
export namespace Fork {
    export const $type = `${$typeBase}Fork`;

    export function is(object: unknown): object is Fork {
        return Task.is(object) && $type === object.$type;
    }
}

export interface Join extends Node {}
export namespace Join {
    export const $type = `${$typeBase}Join`;

    export function is(object: unknown): object is Join {
        return Task.is(object) && $type === object.$type;
    }
}

export interface Decision extends Node {}
export namespace Decision {
    export const $type = `${$typeBase}Decision`;

    export function is(object: unknown): object is Decision {
        return Task.is(object) && $type === object.$type;
    }
}

export interface Merge extends Node {}
export namespace Merge {
    export const $type = `${$typeBase}Merge`;

    export function is(object: unknown): object is Merge {
        return Task.is(object) && $type === object.$type;
    }
}

export interface Flow extends Identifiable {
    source: Node;
    target: Node;
}
export namespace Flow {
    export const $type = `${$typeBase}Flow`;

    export function is(object: unknown): object is Flow {
        return Identifiable.is(object) && isObject(object, 'source') && isObject(object, 'target');
    }
}

export interface WeightedFlow extends Flow {
    probability?: Probability;
}
export namespace WeightedFlow {
    export const $type = `${$typeBase}WeightedFlow`;

    export function is(object: unknown): object is WeightedFlow {
        return Flow.is(object) && $type === object.$type;
    }
}

// eslint-disable-next-line no-shadow
export enum Probability {
    low = 0,
    medium = 1,
    high = 2
}

export namespace CoffeeModel {
    const components = [ControlUnit.$type, BrewingUnit.$type, DipTray.$type, WaterTank.$type];
    const nodes: string[] = [AutomaticTask.$type, ManualTask.$type, Decision.$type, Merge.$type];
    // TODO adding flows is currently not implemented, as it is more complex to set source and target
    const flows: string[] = [
        /* Flow.$type, WeightedFlow.$type*/
    ];

    /** Maps types to their creatable children */
    export const childrenMapping: Map<string, TreeEditor.ChildrenDescriptor[]> = new Map([
        [
            Machine.$type,
            [
                { property: 'children', children: components },
                { property: 'workflows', children: [Workflow.$type] }
            ]
        ],
        [BrewingUnit.$type, [{ property: 'children', children: components }]],
        [ControlUnit.$type, [{ property: 'children', children: components }]],
        [DipTray.$type, [{ property: 'children', children: components }]],
        [WaterTank.$type, [{ property: 'children', children: components }]],
        [
            Workflow.$type,
            [
                { property: 'flows', children: flows },
                { property: 'nodes', children: nodes }
            ]
        ]
    ]);
}
