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
import { Command, CommandHandler, MenuPath } from '@theia/core';
import { ApplicationShell, OpenerService } from '@theia/core/lib/browser';
import { createTreeContainer, defaultTreeProps, TreeProps, TreeWidget } from '@theia/core/lib/browser/tree';
import URI from '@theia/core/lib/common/uri';
import { Container, interfaces } from 'inversify';

import { JsonFormsTreeEditorWidget } from '../json-forms-tree-editor/json-forms-tree-editor-widget';
import { CoffeeModel } from './coffee-model';
import { JsonFormsTree } from './json-forms-tree';
import { JsonFormsTreeWidget } from './json-forms-tree-widget';

export interface ChildrenDescriptor {
  property: string;
  children: string[];
}

export namespace JsonFormsTreeContextMenu {
  export const CONTEXT_MENU: MenuPath = ['json-forms-tree-context-menu'];
  export const ADD_MENU: MenuPath = ['json-forms-tree-add-menu'];
}

export namespace JsonFormsTreeCommands {
  export const OPEN_WORKFLOW_DIAGRAM: Command = {
    id: 'workflow.open',
    label: 'Open Workflow Diagram'
  };

  export function generateAddCommands(): Map<string, Map<string, Command>> {
    const creatableTypes: Set<ChildrenDescriptor> = Array.from(CoffeeModel.childrenMapping, ([_key, value]) => value)
      // get flat array of child descriptors
      .reduce((acc, val) => acc.concat(val), [])
      // unify by adding to set
      .reduce((acc, val) => acc.add(val), new Set<ChildrenDescriptor>());

    // Create a command for every eclass which can be added to at least one model object
    const commandMap: Map<string, Map<string, Command>> = new Map();
    Array.from(creatableTypes).forEach(desc => {
      const classCommandMap: Map<string, Command> = new Map();
      desc.children.forEach(eclass => {
        const name = CoffeeModel.Type.name(eclass);
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
}

export interface JsonFormsTreeAnchor {
  x: number,
  y: number,
  node: JsonFormsTree.Node,
  onClick: (property: string, eClass: string) => void
}

export class AddCommandHandler implements CommandHandler {

  constructor(private readonly property: string, private readonly eclass: string) {
  }

  execute(treeAnchor: JsonFormsTreeAnchor) {
    treeAnchor.onClick(this.property, this.eclass);
  }

  isVisible(treeAnchor: JsonFormsTreeAnchor): boolean {
    if (!treeAnchor) {
      return false;
    }
    return CoffeeModel.childrenMapping.get(treeAnchor.node.jsonforms.type)
      .map(desc => desc.children)
      .reduce((acc, val) => acc.concat(val), [])
      .reduce((acc, val) => acc.add(val), new Set<string>())
      .has(this.eclass);
  }
}

export class OpenWorkflowDiagramCommandHandler implements CommandHandler {
  constructor(protected readonly shell: ApplicationShell,
    protected readonly openerService: OpenerService) {
  }

  execute() {
    const editorWidget = this.getTreeEditorWidget();
    if (editorWidget) {
      const workflowNode = this.getSelectedWorkflow(editorWidget);
      if (workflowNode) {
        const notationUri = this.getNotationUri(editorWidget);
        this.openerService.getOpener(notationUri).then(opener => opener.open(notationUri, this.createServerOptions(workflowNode)));
      }
    }
  }

  isVisible(): boolean {
    return this.getSelectedWorkflow(this.getTreeEditorWidget()) !== undefined;
  }

  getTreeEditorWidget(): JsonFormsTreeEditorWidget | undefined {
    const currentWidget = this.shell.currentWidget;
    if (currentWidget instanceof JsonFormsTreeEditorWidget) {
      return currentWidget;
    }
    return undefined;
  }

  getSelectedWorkflow(widget: JsonFormsTreeEditorWidget): JsonFormsTree.Node | undefined {
    if (widget && JsonFormsTree.Node.hasType(widget.selectedNode, CoffeeModel.Type.Workflow)) {
      return widget.selectedNode;
    }
    return undefined;
  }

  getNotationUri(widget: JsonFormsTreeEditorWidget): URI {
    const coffeeUri = widget.uri();
    const coffeeNotationUri = coffeeUri.parent.resolve(coffeeUri.displayName + 'notation');
    return coffeeNotationUri;
  }

  createServerOptions(node: JsonFormsTree.Node) {
    return {
      serverOptions: {
        workflowIndex: node.jsonforms.index
      }
    };
  }
}

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
