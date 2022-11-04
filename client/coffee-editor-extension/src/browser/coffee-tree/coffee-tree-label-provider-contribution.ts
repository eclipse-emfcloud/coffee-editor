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

import {
    AutomaticTask,
    BrewingUnit,
    ControlUnit,
    Decision,
    Dimension,
    DipTray,
    Display,
    Flow,
    Fork,
    Join,
    Machine,
    ManualTask,
    Merge,
    Node,
    Processor,
    RAM,
    Task,
    WaterTank,
    WeightedFlow,
    Workflow
} from './coffee-model';
import { CoffeeTreeEditorConstants } from './coffee-tree-editor-widget';

const ICON_CLASSES: Map<string, string> = new Map([
    [AutomaticTask.$type, 'settings-gear'],
    [BrewingUnit.$type, 'flame'],
    [ControlUnit.$type, 'server'],
    [Decision.$type, 'chevron-up'],
    [Dimension.$type, 'move'],
    [DipTray.$type, 'inbox'],
    [Display.$type, 'tv'],
    [Flow.$type, 'chrome-minimize'],
    [Fork.$type, 'repo-forked'],
    [Join.$type, 'repo-forked rotate-180'],
    [Machine.$type, 'server-process'],
    [ManualTask.$type, 'account'],
    [Merge.$type, 'chevron-down'],
    [Node.$type, 'circle'],
    [Processor.$type, 'circuit-board'],
    [RAM.$type, 'memory'],
    [Task.$type, 'checklist'],
    [WaterTank.$type, 'beaker'],
    [WeightedFlow.$type, 'grabber'],
    [Workflow.$type, 'type-hierarchy-sub']
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
                iconClass = ICON_CLASSES.get(Flow.$type);
            }
        }

        return iconClass ? codicon(iconClass) : codicon(UNKNOWN_ICON);
    }

    public getName(element: object): string | undefined {
        const data = TreeEditor.Node.is(element) ? element.jsonforms.data : element;

        if (Machine.is(data) || Workflow.is(data) || Task.is(data) || AutomaticTask.is(data) || ManualTask.is(data)) {
            return data.name || this.getNameForType(data.$type);
        }
        return this.getNameForType(data.$type);
    }

    private getNameForType(type: string): string {
        return new URI(type).fragment.substring(2);
    }
}
