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
import URI from '@theia/core/lib/common/uri';

import { ChildrenDescriptor } from './json-forms-tree-container';

export namespace CoffeeModel {
    export namespace Type {
        export const AutomaticTask = 'http://www.eclipsesource.com/modelserver/example/coffeemodel#//AutomaticTask';
        export const BrewingUnit = 'http://www.eclipsesource.com/modelserver/example/coffeemodel#//BrewingUnit';
        export const Component = 'http://www.eclipsesource.com/modelserver/example/coffeemodel#//Component';
        export const ControlUnit = 'http://www.eclipsesource.com/modelserver/example/coffeemodel#//ControlUnit';
        export const Decision = 'http://www.eclipsesource.com/modelserver/example/coffeemodel#//Decision';
        export const Dimension = 'http://www.eclipsesource.com/modelserver/example/coffeemodel#//Dimension';
        export const DipTray = 'http://www.eclipsesource.com/modelserver/example/coffeemodel#//DipTray';
        export const Display = 'http://www.eclipsesource.com/modelserver/example/coffeemodel#//Display';
        export const Flow = 'http://www.eclipsesource.com/modelserver/example/coffeemodel#//Flow';
        export const Fork = 'http://www.eclipsesource.com/modelserver/example/coffeemodel#//Fork';
        export const Join = 'http://www.eclipsesource.com/modelserver/example/coffeemodel#//Join';
        export const Machine = 'http://www.eclipsesource.com/modelserver/example/coffeemodel#//Machine';
        export const ManualTask = 'http://www.eclipsesource.com/modelserver/example/coffeemodel#//ManualTask';
        export const Merge = 'http://www.eclipsesource.com/modelserver/example/coffeemodel#//Merge';
        export const Node = 'http://www.eclipsesource.com/modelserver/example/coffeemodel#//Node';
        export const Processor = 'http://www.eclipsesource.com/modelserver/example/coffeemodel#//Processor';
        export const RAM = 'http://www.eclipsesource.com/modelserver/example/coffeemodel#//RAM';
        export const Task = 'http://www.eclipsesource.com/modelserver/example/coffeemodel#//Task';
        export const WaterTank = 'http://www.eclipsesource.com/modelserver/example/coffeemodel#//WaterTank';
        export const WeightedFlow = 'http://www.eclipsesource.com/modelserver/example/coffeemodel#//WeightedFlow';
        export const Workflow = 'http://www.eclipsesource.com/modelserver/example/coffeemodel#//Workflow';

        export function name(type: string): string {
            return new URI(type).fragment.substring(2);
        }
    }

    const components = [
        Type.Component,
        Type.Machine,
        Type.ControlUnit,
        Type.BrewingUnit,
        Type.DipTray,
        Type.WaterTank
    ];

    const nodes = [
        Type.AutomaticTask,
        Type.Decision,
        Type.Fork,
        Type.Join,
        Type.ManualTask,
        Type.Merge
    ];

    const flows = [
        Type.Flow,
        Type.WeightedFlow
    ];

    /** Maps types to their creatable children */
    export const childrenMapping: Map<string, ChildrenDescriptor[]> = new Map([
        [
            Type.BrewingUnit, [
                {
                    property: 'children',
                    children: components
                }
            ]
        ],
        [
            Type.Component, [
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
            Type.ControlUnit, [
                {
                    property: 'children',
                    children: components
                }
            ]
        ],
        [
            Type.DipTray, [
                {
                    property: 'children',
                    children: components
                }
            ]
        ],
        [
            Type.Machine, [
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
            Type.WaterTank, [
                {
                    property: 'children',
                    children: components
                }
            ]
        ],
        [
            Type.Workflow, [
                {
                    property: 'flows',
                    children: flows
                },
                {
                    property: 'nodes',
                    children: nodes
                }
            ]
        ],
    ]);

}
