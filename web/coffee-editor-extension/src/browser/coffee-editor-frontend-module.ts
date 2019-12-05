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
import { createBasicTreeContainter, NavigatableTreeEditorOptions } from 'jsonforms-tree-extension';

import { CoffeeTreeEditorContribution } from './coffee-editor-tree-contribution';
import { CoffeeModelService } from './coffee-tree/coffee-model-service';
import { CoffeeTreeNodeFactory } from './coffee-tree/coffee-node-factory';
import { CoffeeTreeEditorWidget } from './coffee-tree/coffee-tree-editor-widget';
import { CoffeeTreeLabelProvider } from './coffee-tree/coffee-tree-label-provider';
import { CoffeeLabelProviderContribution } from './CoffeeLabelProvider';

export default new ContainerModule(bind => {
  // Bind Theia IDE contributions
  bind(LabelProviderContribution).to(CoffeeLabelProviderContribution);
  bind(OpenHandler).to(CoffeeTreeEditorContribution);
  bind(MenuContribution).to(CoffeeTreeEditorContribution);
  bind(CommandContribution).to(CoffeeTreeEditorContribution);

  // bind to themselves because we use it outside of the editor widget, too.
  bind(CoffeeModelService).toSelf();
  bind(CoffeeTreeLabelProvider).toSelf();

  bind<WidgetFactory>(WidgetFactory).toDynamicValue(context => ({
    id: CoffeeTreeEditorWidget.WIDGET_ID,
    createWidget: (options: NavigatableWidgetOptions) => {

      const treeContainer = createBasicTreeContainter(
        context.container,
        CoffeeTreeEditorWidget,
        CoffeeModelService,
        CoffeeTreeLabelProvider,
        CoffeeTreeNodeFactory
      );

      // Bind options
      const uri = new URI(options.uri);
      treeContainer.bind(NavigatableTreeEditorOptions).toConstantValue({ uri });

      return treeContainer.get(CoffeeTreeEditorWidget);
    }
  }));
});
