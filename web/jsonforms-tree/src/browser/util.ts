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
import { Command } from '@theia/core';
import { createTreeContainer, defaultTreeProps, TreeProps, TreeWidget } from '@theia/core/lib/browser/tree';
import { Container, interfaces } from 'inversify';

import { ChildrenDescriptor, ModelService } from './model-service';
import { JsonFormsTreeContextMenu, JsonFormsTreeWidget } from './tree/json-forms-tree-widget';

function createJsonFormsTreeContainer(parent: interfaces.Container): Container {
    const child = createTreeContainer(parent);

    child.unbind(TreeWidget);
    child.bind(JsonFormsTreeWidget).toSelf();
    child.rebind(TreeProps).toConstantValue(JSON_FORMS_TREE_PROPS);
    return child;
}

export const JSON_FORMS_TREE_PROPS = <TreeProps>{
    ...defaultTreeProps,
    contextMenuPath: JsonFormsTreeContextMenu.CONTEXT_MENU,
    multiSelect: false,
    search: false
};

export function createJsonFormsTreeWidget(
    parent: interfaces.Container
): JsonFormsTreeWidget {
    return createJsonFormsTreeContainer(parent).get(JsonFormsTreeWidget);
}

export function generateAddCommands(modelService: ModelService): Map<string, Map<string, Command>> {
    const creatableTypes: Set<ChildrenDescriptor> = Array.from(modelService.getChildrenMapping(), ([_key, value]) => value)
        // get flat array of child descriptors
        .reduce((acc, val) => acc.concat(val), [])
        // unify by adding to set
        .reduce((acc, val) => acc.add(val), new Set<ChildrenDescriptor>());

    // Create a command for every eclass which can be added to at least one model object
    const commandMap: Map<string, Map<string, Command>> = new Map();
    Array.from(creatableTypes).forEach(desc => {
        const classCommandMap: Map<string, Command> = new Map();
        desc.children.forEach(eclass => {
            const name = modelService.getNameFromEClass(eclass);
            const command = {
                id: 'json-forms-tree.add.' + name,
                label: name
            };
            classCommandMap.set(eclass, command);
        });
        commandMap.set(desc.property, classCommandMap);
    });

    return commandMap;
}
