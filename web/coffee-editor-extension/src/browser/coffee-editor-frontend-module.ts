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
import 'jsonforms-tree-extension/style/index.css';

import { CommandContribution, MenuContribution } from '@theia/core';
import { LabelProviderContribution, NavigatableWidgetOptions, OpenHandler, WidgetFactory } from '@theia/core/lib/browser';
import URI from '@theia/core/lib/common/uri';
import { ContainerModule } from 'inversify';
import { JsonFormsTree } from 'jsonforms-tree-extension/lib//browser/tree/json-forms-tree';
import { createJsonFormsTreeWidget } from 'jsonforms-tree-extension/lib//browser/util';
import { JsonFormsTreeEditorWidgetOptions } from 'jsonforms-tree-extension/lib/browser/editor/json-forms-tree-editor-widget';
import { JSONFormsWidget } from 'jsonforms-tree-extension/lib/browser/editor/json-forms-widget';
import { ModelService } from 'jsonforms-tree-extension/lib/browser/model-service';
import { JsonFormsTreeWidget } from 'jsonforms-tree-extension/lib/browser/tree/json-forms-tree-widget';

import { CoffeeTreeEditorContribution } from './coffee-editor-tree-contribution';
import { CoffeeModelService } from './coffee-tree/coffee-model-service';
import { CoffeeTreeNodeFactory } from './coffee-tree/coffee-node-factory';
import { CoffeeTreeEditorWidget } from './coffee-tree/coffee-tree-editor-widget';
import { CoffeeTreeLabelProvider } from './coffee-tree/coffee-tree-label-provider';
import { CoffeeLabelProviderContribution } from './CoffeeLabelProvider';

export default new ContainerModule(bind => {
  bind(ModelService).to(CoffeeModelService);
  bind(LabelProviderContribution).to(CoffeeLabelProviderContribution);
  bind(JsonFormsTreeWidget).toDynamicValue(context =>
    createJsonFormsTreeWidget(context.container)
  );
  bind(JSONFormsWidget).toSelf();
  bind(OpenHandler).to(CoffeeTreeEditorContribution);
  bind(MenuContribution).to(CoffeeTreeEditorContribution);
  bind(CommandContribution).to(CoffeeTreeEditorContribution);
  bind(CoffeeTreeEditorWidget).toSelf();
  bind(JsonFormsTree.LabelProvider).to(CoffeeTreeLabelProvider);
  bind(JsonFormsTree.NodeFactory).to(CoffeeTreeNodeFactory);

  bind<WidgetFactory>(WidgetFactory).toDynamicValue(context => ({
    id: CoffeeTreeEditorWidget.WIDGET_ID,
    createWidget: (options: NavigatableWidgetOptions) => {
      const { container } = context;
      const child = container.createChild();

      const uri = new URI(options.uri);
      child
        .bind<JsonFormsTreeEditorWidgetOptions>(
          JsonFormsTreeEditorWidgetOptions
        )
        .toConstantValue({
          uri: uri
        });

      return child.get(CoffeeTreeEditorWidget);
    }
  }));
});
