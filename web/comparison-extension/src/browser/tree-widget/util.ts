/********************************************************************************
 * Copyright (c) 2021 EclipseSource and others.
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
 ********************************************************************************/
import { Command } from '@theia/core';
import {
    createTreeContainer,
    defaultTreeProps,
    TreeProps,
    TreeWidget as TheiaTreeWidget
} from '@theia/core/lib/browser/tree';
import { interfaces } from 'inversify';

import { TreeEditor } from './interfaces';
import { MasterTreeWidget, TreeContextMenu } from './master-tree-widget';
import { BaseTreeEditorWidget } from './tree-editor-widget';

export const TREE_PROPS = {
    ...defaultTreeProps,
    contextMenuPath: TreeContextMenu.CONTEXT_MENU,
    multiSelect: false,
    search: false
} as TreeProps;

function createTreeWidget(
    parent: interfaces.Container
): MasterTreeWidget {
    const treeContainer = createTreeContainer(parent);

    treeContainer.unbind(TheiaTreeWidget);
    treeContainer.bind(MasterTreeWidget).toSelf();
    treeContainer.rebind(TreeProps).toConstantValue(TREE_PROPS);
    return treeContainer.get(MasterTreeWidget);
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
 * @param nodeFactory The tree editor's node factory
 */
export function createBasicTreeContainter(
    parent: interfaces.Container,
    treeEditorWidget: interfaces.Newable<BaseTreeEditorWidget>,
    modelService: interfaces.Newable<TreeEditor.ModelService>,
    nodeFactory: interfaces.Newable<TreeEditor.NodeFactory>): interfaces.Container {

    const container = parent.createChild();
    container.bind(TreeEditor.ModelService).to(modelService);
    container.bind(TreeEditor.NodeFactory).to(nodeFactory);
    container.bind(MasterTreeWidget).toDynamicValue(context => createTreeWidget(context.container));
    container.bind(treeEditorWidget).toSelf();

    return container;
}

/**
 * Creates a new map based on the model service's children mapping.
 * The created map maps from property name to a new Map.
 * The inner map maps from type identifier to the command used to create a new instance of the type.
 *
 * Basically, this creates add commands for all types that can be created in properties, grouped by property name.
 *
 * Important: Just because an object has a property of a name in the returned mapping,
 * this does not mean the type can be created. To see if a command can be used to create an object's child,
 * the returned mapping needs to be cross-referenced with the model service's children mapping again.
 *
 * @param modelService The tree editor's model service
 */
export function generateAddCommands(modelService: TreeEditor.ModelService): Map<string, Map<string, Command>> {
    const creatableTypes: Set<TreeEditor.ChildrenDescriptor> = Array.from(modelService.getChildrenMapping(), ([_key, value]) => value)
        // get flat array of child descriptors
        .reduce((acc, val) => acc.concat(val), [])
        // unify by adding to set
        .reduce((acc, val) => acc.add(val), new Set<TreeEditor.ChildrenDescriptor>());

    // Create a command for every type which can be added to at least one model object
    const commandMap: Map<string, Map<string, Command>> = new Map();
    Array.from(creatableTypes).forEach(desc => {
        const classCommandMap: Map<string, Command> = new Map();
        desc.children.forEach(type => {
            const name = modelService.getNameForType(type);
            const command = {
                id: 'json-forms-tree.add.' + name,
                label: name
            };
            classCommandMap.set(type, command);
        });
        commandMap.set(desc.property, classCommandMap);
    });

    return commandMap;
}
