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
import { TreeNode } from '@theia/core/lib/browser/tree/tree';
import URI from '@theia/core/lib/common/uri';
import { injectable } from 'inversify';

import { CoffeeModel } from '../json-forms-tree/coffee-model';
import { JsonFormsTree } from '../json-forms-tree/json-forms-tree';

const DEFAULT_COLOR = 'black';

const ICON_CLASSES: Map<string, string> = new Map([
  [CoffeeModel.Type.AutomaticTask, 'fa-cog ' + DEFAULT_COLOR],
  [CoffeeModel.Type.BrewingUnit, 'fa-burn ' + DEFAULT_COLOR],
  [CoffeeModel.Type.Component, 'fa-cube ' + DEFAULT_COLOR],
  [CoffeeModel.Type.ControlUnit, 'fa-server ' + DEFAULT_COLOR],
  [CoffeeModel.Type.Decision, 'fa-code-branch fa-rotate-90 ' + DEFAULT_COLOR],
  [CoffeeModel.Type.Dimension, 'fa-arrows-alt ' + DEFAULT_COLOR],
  [CoffeeModel.Type.DipTray, 'fa-inbox ' + DEFAULT_COLOR],
  [CoffeeModel.Type.Display, 'fa-tv ' + DEFAULT_COLOR],
  [CoffeeModel.Type.Flow, 'fa-exchange-alt ' + DEFAULT_COLOR],
  [CoffeeModel.Type.Fork, 'fa-code-branch fa-rotate-90 ' + DEFAULT_COLOR],
  [CoffeeModel.Type.Join, 'fa-code-branch fa-rotate-270 ' + DEFAULT_COLOR],
  [CoffeeModel.Type.Machine, 'fa-cogs ' + DEFAULT_COLOR],
  [CoffeeModel.Type.ManualTask, 'fa-wrench ' + DEFAULT_COLOR],
  [CoffeeModel.Type.Merge, 'fa-code-branch fa-rotate-270 ' + DEFAULT_COLOR],
  [CoffeeModel.Type.Node, 'fa-circle ' + DEFAULT_COLOR],
  [CoffeeModel.Type.Processor, 'fa-microchip ' + DEFAULT_COLOR],
  [CoffeeModel.Type.RAM, 'fa-memory ' + DEFAULT_COLOR],
  [CoffeeModel.Type.Task, 'fa-tasks ' + DEFAULT_COLOR],
  [CoffeeModel.Type.WaterTank, 'fa-water ' + DEFAULT_COLOR],
  [CoffeeModel.Type.WeightedFlow, 'fa-exchange-alt light-orange'],
]);

/* Icon for unknown types */
const UNKNOWN_ICON = 'fa-question-circle ' + DEFAULT_COLOR;

@injectable()
export class CoffeeTreeLabelProvider implements JsonFormsTree.LabelProvider {

  public getIconClass(node: TreeNode | string): string {
    let iconClass: string;
    if (typeof node === 'string') {
      iconClass = ICON_CLASSES.get(node);
    } else if (JsonFormsTree.Node.is(node)) {
      iconClass = ICON_CLASSES.get(node.jsonforms.type);
    }

    return iconClass ? 'fa ' + iconClass : 'far ' + UNKNOWN_ICON;
  }

  public getName(data: any): string {
    if (data.eClass) {
      switch (data.eClass) {
        case 'http://www.eclipsesource.com/modelserver/example/coffeemodel#//Task':
        case 'http://www.eclipsesource.com/modelserver/example/coffeemodel#//AutomaticTask':
        case 'http://www.eclipsesource.com/modelserver/example/coffeemodel#//ManualTask':
        case 'http://www.eclipsesource.com/modelserver/example/coffeemodel#//Machine':
          return data.name;
        default:
          // TODO query title of schema
          const fragment = new URI(data.eClass).fragment;
          if (fragment.startsWith('//')) {
            return fragment.substring(2);
          }
          return fragment;
      }
    }
    // guess
    if (data.nodes) {
      return data.name || 'Workflow';
    }
    return undefined;
  }
}
