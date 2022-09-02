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
import { codicon, LabelProviderContribution } from '@theia/core/lib/browser';
import URI from '@theia/core/lib/common/uri';
import { injectable } from 'inversify';

import { CoffeeModel } from './coffee-model';
import { CoffeeTreeEditorConstants } from './coffee-tree-editor-widget';

const ICON_CLASSES: Map<string, string> = new Map([
    [CoffeeModel.Type.AutomaticTask, 'settings-gear'],
    [CoffeeModel.Type.BrewingUnit, 'flame'],
    [CoffeeModel.Type.ControlUnit, 'server'],
    [CoffeeModel.Type.Decision, 'chevron-up'],
    [CoffeeModel.Type.Dimension, 'move'],
    [CoffeeModel.Type.DipTray, 'inbox'],
    [CoffeeModel.Type.Display, 'tv'],
    [CoffeeModel.Type.Flow, 'chrome-minimize'],
    [CoffeeModel.Type.Fork, 'repo-forked'],
    [CoffeeModel.Type.Join, 'repo-forked rotate-180'],
    [CoffeeModel.Type.Machine, 'server-process'],
    [CoffeeModel.Type.ManualTask, 'account'],
    [CoffeeModel.Type.Merge, 'chevron-down'],
    [CoffeeModel.Type.Node, 'circle'],
    [CoffeeModel.Type.Processor, 'circuit-board'],
    [CoffeeModel.Type.RAM, 'memory'],
    [CoffeeModel.Type.Task, 'checklist'],
    [CoffeeModel.Type.WaterTank, 'beaker'],
    [CoffeeModel.Type.WeightedFlow, 'grabber'],
    [CoffeeModel.Type.Workflow, 'type-hierarchy-sub']
]);

/* Icon for unknown types */
const UNKNOWN_ICON = 'circle-slash';

@injectable()
export class CoffeeTreeLabelProvider implements LabelProviderContribution {
    public canHandle(element: object): number {
        if (
            (TreeEditor.Node.is(element) || TreeEditor.CommandIconInfo.is(element)) &&
            element.editorId === CoffeeTreeEditorConstants.EDITOR_ID
        ) {
            return 1000;
        }
        return 0;
    }

    public getIcon(element: object): string | undefined {
        let iconClass: string | undefined;
        if (TreeEditor.CommandIconInfo.is(element)) {
            iconClass = ICON_CLASSES.get(element.type);
        } else if (TreeEditor.Node.is(element)) {
            iconClass = ICON_CLASSES.get(element.jsonforms.type);
            if (!iconClass && element.jsonforms.property === 'flows') {
                iconClass = ICON_CLASSES.get(CoffeeModel.Type.Flow);
            }
        }

        return iconClass ? codicon(iconClass) : codicon(UNKNOWN_ICON);
    }

    public getName(element: object): string | undefined {
        const data = TreeEditor.Node.is(element) ? element.jsonforms.data : element;
        if (data.eClass) {
            switch (data.eClass) {
                case CoffeeModel.Type.Task:
                case CoffeeModel.Type.AutomaticTask:
                case CoffeeModel.Type.ManualTask:
                case CoffeeModel.Type.Machine:
                    return data.name || this.getTypeName(data.eClass);
                default:
                    // TODO query title of schema
                    return this.getTypeName(data.eClass);
            }
        }
        // guess
        if (data.nodes) {
            return data.name || 'Workflow';
        }
        // ugly guess, fix in modelserver
        if (data.source && data.target) {
            return 'Flow';
        }
        return undefined;
    }
    private getTypeName(eClass: string): string {
        const fragment = new URI(eClass).fragment;
        if (fragment.startsWith('//')) {
            return fragment.substring(2);
        }
        return fragment;
    }
}
