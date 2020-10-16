/*!
 * Copyright (c) 2019-2020 EclipseSource and others.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0, or the MIT License which is
 * available at https://opensource.org/licenses/MIT.
 *
 * SPDX-License-Identifier: EPL-2.0 OR MIT
 */
import '@eclipse-emfcloud/theia-tree-editor/style/forms.css';
import '@eclipse-emfcloud/theia-tree-editor/style/index.css';

import { createBasicTreeContainter, NavigatableTreeEditorOptions } from '@eclipse-emfcloud/theia-tree-editor';
import { CommandContribution, MenuContribution } from '@theia/core';
import { LabelProviderContribution, NavigatableWidgetOptions, OpenHandler, WidgetFactory } from '@theia/core/lib/browser';
import URI from '@theia/core/lib/common/uri';
import { ContainerModule } from 'inversify';

import { CoffeeTreeEditorContribution } from './coffee-editor-tree-contribution';
import { CoffeeModelService } from './coffee-tree/coffee-model-service';
import { CoffeeTreeNodeFactory } from './coffee-tree/coffee-node-factory';
import { CoffeeTreeEditorConstants, CoffeeTreeEditorWidget } from './coffee-tree/coffee-tree-editor-widget';
import { CoffeeTreeLabelProvider } from './coffee-tree/coffee-tree-label-provider-contribution';
import { CoffeeLabelProviderContribution } from './CoffeeLabelProvider';

export default new ContainerModule(bind => {
    // Bind Theia IDE contributions
    bind(LabelProviderContribution).to(CoffeeLabelProviderContribution);
    bind(OpenHandler).to(CoffeeTreeEditorContribution);
    bind(MenuContribution).to(CoffeeTreeEditorContribution);
    bind(CommandContribution).to(CoffeeTreeEditorContribution);
    bind(LabelProviderContribution).to(CoffeeTreeLabelProvider);

    // bind to themselves because we use it outside of the editor widget, too.
    bind(CoffeeModelService).toSelf().inSingletonScope();
    bind(CoffeeTreeLabelProvider).toSelf().inSingletonScope();

    bind<WidgetFactory>(WidgetFactory).toDynamicValue(context => ({
        id: CoffeeTreeEditorConstants.WIDGET_ID,
        createWidget: (options: NavigatableWidgetOptions) => {

            const treeContainer = createBasicTreeContainter(
                context.container,
                CoffeeTreeEditorWidget,
                CoffeeModelService,
                CoffeeTreeNodeFactory
            );

            // Bind options
            const uri = new URI(options.uri);
            treeContainer.bind(NavigatableTreeEditorOptions).toConstantValue({ uri });

            return treeContainer.get(CoffeeTreeEditorWidget);
        }
    }));
});
