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
import { interfaces } from 'inversify';

import { TreeEditor } from './interfaces';
import { JSONFormsWidget } from './json-forms-widget';
import { JsonFormsTreeEditorWidget } from './tree-editor-widget';
import { JsonFormsTreeContextMenu, JsonFormsTreeWidget } from './tree-widget';

export const JSON_FORMS_TREE_PROPS = <TreeProps>{
    ...defaultTreeProps,
    contextMenuPath: JsonFormsTreeContextMenu.CONTEXT_MENU,
    multiSelect: false,
    search: false
};

function createJsonFormsTreeWidget(
    parent: interfaces.Container
): JsonFormsTreeWidget {
    const treeContainer = createTreeContainer(parent);

    treeContainer.unbind(TreeWidget);
    treeContainer.bind(JsonFormsTreeWidget).toSelf();
    treeContainer.rebind(TreeProps).toConstantValue(JSON_FORMS_TREE_PROPS);
    return treeContainer.get(JsonFormsTreeWidget);
}

/**
 * Creates a new inversify container to create tree editor widgets using the given customizations.
 * If further services are needed than the given ones, these must either be bound in the parent container
 * or to the returned container before a tree editor widget is requested.
 *
 * Note that this method does not create a singletion tree editor but returns a new instance whenever an instace is requested.
 *
 * @param parent The parent inversify container
 * @param treeEditorWidget The concrete tree editor widget to create
 * @param modelService The tree editor's model service
 * @param labelProvider The tree editor's label provider
 * @param nodeFactory The tree editor's node factory
 */
export function createBasicTreeContainter(
    parent: interfaces.Container,
    treeEditorWidget: interfaces.Newable<JsonFormsTreeEditorWidget>,
    modelService: interfaces.Newable<TreeEditor.ModelService>,
    labelProvider: interfaces.Newable<TreeEditor.LabelProvider>,
    nodeFactory: interfaces.Newable<TreeEditor.NodeFactory>): interfaces.Container {

    const container = parent.createChild();
    container.bind(TreeEditor.ModelService).to(modelService);
    container.bind(TreeEditor.LabelProvider).to(labelProvider);
    container.bind(TreeEditor.NodeFactory).to(nodeFactory);
    container.bind(JSONFormsWidget).toSelf();
    container.bind(JsonFormsTreeWidget).toDynamicValue(context =>
        createJsonFormsTreeWidget(context.container));
    container.bind(treeEditorWidget).toSelf();

    return container;
}

export function generateAddCommands(modelService: TreeEditor.ModelService): Map<string, Map<string, Command>> {
    const creatableTypes: Set<TreeEditor.ChildrenDescriptor> = Array.from(modelService.getChildrenMapping(), ([_key, value]) => value)
        // get flat array of child descriptors
        .reduce((acc, val) => acc.concat(val), [])
        // unify by adding to set
        .reduce((acc, val) => acc.add(val), new Set<TreeEditor.ChildrenDescriptor>());

    // Create a command for every eclass which can be added to at least one model object
    const commandMap: Map<string, Map<string, Command>> = new Map();
    Array.from(creatableTypes).forEach(desc => {
        const classCommandMap: Map<string, Command> = new Map();
        desc.children.forEach(eclass => {
            const name = modelService.getNameForType(eclass);
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
