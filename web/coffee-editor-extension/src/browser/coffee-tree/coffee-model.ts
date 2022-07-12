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
import { TreeEditor } from '@eclipse-emfcloud/theia-tree-editor';
import URI from '@theia/core/lib/common/uri';

export namespace CoffeeModel {
    export namespace Type {
        export const AutomaticTask = 'http://www.eclipse.org/emfcloud/coffee/model#//AutomaticTask';
        export const BrewingUnit = 'http://www.eclipse.org/emfcloud/coffee/model#//BrewingUnit';
        export const ControlUnit = 'http://www.eclipse.org/emfcloud/coffee/model#//ControlUnit';
        export const Decision = 'http://www.eclipse.org/emfcloud/coffee/model#//Decision';
        export const Dimension = 'http://www.eclipse.org/emfcloud/coffee/model#//Dimension';
        export const DipTray = 'http://www.eclipse.org/emfcloud/coffee/model#//DipTray';
        export const Display = 'http://www.eclipse.org/emfcloud/coffee/model#//Display';
        export const Flow = 'http://www.eclipse.org/emfcloud/coffee/model#//Flow';
        export const Fork = 'http://www.eclipse.org/emfcloud/coffee/model#//Fork';
        export const Join = 'http://www.eclipse.org/emfcloud/coffee/model#//Join';
        export const Machine = 'http://www.eclipse.org/emfcloud/coffee/model#//Machine';
        export const ManualTask = 'http://www.eclipse.org/emfcloud/coffee/model#//ManualTask';
        export const Merge = 'http://www.eclipse.org/emfcloud/coffee/model#//Merge';
        export const Node = 'http://www.eclipse.org/emfcloud/coffee/model#//Node';
        export const Processor = 'http://www.eclipse.org/emfcloud/coffee/model#//Processor';
        export const RAM = 'http://www.eclipse.org/emfcloud/coffee/model#//RAM';
        export const Task = 'http://www.eclipse.org/emfcloud/coffee/model#//Task';
        export const WaterTank = 'http://www.eclipse.org/emfcloud/coffee/model#//WaterTank';
        export const WeightedFlow = 'http://www.eclipse.org/emfcloud/coffee/model#//WeightedFlow';
        export const Workflow = 'http://www.eclipse.org/emfcloud/coffee/model#//Workflow';

        export function name(type: string): string {
            return new URI(type).fragment.substring(2);
        }
    }

    const components = [Type.Machine, Type.ControlUnit, Type.BrewingUnit, Type.DipTray, Type.WaterTank];

    const nodes = [Type.AutomaticTask, Type.Decision, Type.ManualTask, Type.Merge];

    const flows: string[] = [
        // TODO adding edges is currently not possible because they require a source and target
        // Type.Flow,
        // Type.WeightedFlow
    ];

    /** Maps types to their creatable children */
    export const childrenMapping: Map<string, TreeEditor.ChildrenDescriptor[]> = new Map([
        [
            Type.BrewingUnit,
            [
                {
                    property: 'children',
                    children: components
                }
            ]
        ],
        [
            Type.ControlUnit,
            [
                {
                    property: 'children',
                    children: components
                }
            ]
        ],
        [
            Type.DipTray,
            [
                {
                    property: 'children',
                    children: components
                }
            ]
        ],
        [
            Type.Machine,
            [
                {
                    property: 'children',
                    children: components
                },
                {
                    property: 'workflows',
                    children: [Type.Workflow]
                }
            ]
        ],
        [
            Type.WaterTank,
            [
                {
                    property: 'children',
                    children: components
                }
            ]
        ],
        [
            Type.Workflow,
            [
                {
                    property: 'flows',
                    children: flows
                },
                {
                    property: 'nodes',
                    children: nodes
                }
            ]
        ]
    ]);
}
