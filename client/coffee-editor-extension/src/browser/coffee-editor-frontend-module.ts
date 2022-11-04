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
import '@eclipse-emfcloud/theia-tree-editor/style/forms.css';
import '@eclipse-emfcloud/theia-tree-editor/style/index.css';
import '../../css/coffee-tree-editor.css';

import {
    BaseTreeEditorWidget,
    DetailFormWidget,
    NavigatableTreeEditorOptions,
    TreeEditor,
    TREE_PROPS
} from '@eclipse-emfcloud/theia-tree-editor';
import { CommandContribution, MenuContribution } from '@theia/core';
import {
    createTreeContainer,
    LabelProviderContribution,
    NavigatableWidgetOptions,
    OpenHandler,
    WidgetFactory
} from '@theia/core/lib/browser';
import { TreeProps, TreeWidget as TheiaTreeWidget } from '@theia/core/lib/browser/tree';
import URI from '@theia/core/lib/common/uri';
import { ContainerModule } from 'inversify';

import {
    ModelServerSubscriptionClient,
    ModelServerSubscriptionClientV2,
    ModelServerSubscriptionServiceV2
} from '@eclipse-emfcloud/modelserver-theia/lib/browser';
import { interfaces } from '@theia/core/shared/inversify';
import { CoffeeTreeEditorContribution } from './coffee-editor-tree-contribution';
import { CoffeeLabelProviderContribution } from './coffee-tree-label-provider';
import { CoffeeMasterTreeWidget } from './coffee-tree/coffee-master-tree-widget';
import { CoffeeModelService } from './coffee-tree/coffee-model-service';
import { CoffeeTreeNodeFactory } from './coffee-tree/coffee-node-factory';
import { CoffeeTreeEditorConstants, CoffeeTreeEditorWidget } from './coffee-tree/coffee-tree-editor-widget';
import { CoffeeTreeLabelProvider } from './coffee-tree/coffee-tree-label-provider-contribution';

export default new ContainerModule((bind, _unbind, isBound, rebind) => {
    // Bind Theia IDE contributions
    bind(LabelProviderContribution).to(CoffeeLabelProviderContribution);
    bind(OpenHandler).to(CoffeeTreeEditorContribution);
    bind(MenuContribution).to(CoffeeTreeEditorContribution);
    bind(CommandContribution).to(CoffeeTreeEditorContribution);
    bind(LabelProviderContribution).to(CoffeeTreeLabelProvider);

    // Bind ModelServerSubscription services
    bind(ModelServerSubscriptionClientV2).toSelf().inSingletonScope();
    bind(ModelServerSubscriptionServiceV2).toService(ModelServerSubscriptionClientV2);
    if (isBound(ModelServerSubscriptionClient)) {
        rebind(ModelServerSubscriptionClient).toService(ModelServerSubscriptionClientV2);
    } else {
        bind(ModelServerSubscriptionClient).toService(ModelServerSubscriptionClientV2);
    }

    // bind to themselves because we use it outside of the editor widget, too.
    bind(CoffeeModelService).toSelf().inSingletonScope();
    bind(CoffeeTreeLabelProvider).toSelf().inSingletonScope();

    bind<WidgetFactory>(WidgetFactory).toDynamicValue(context => ({
        id: CoffeeTreeEditorConstants.WIDGET_ID,
        createWidget: (options: NavigatableWidgetOptions) => {
            const treeContainer = createBasicTreeContainer(
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

function createTreeWidget(parent: interfaces.Container): CoffeeMasterTreeWidget {
    const treeContainer = createTreeContainer(parent);

    treeContainer.unbind(TheiaTreeWidget);
    treeContainer.bind(CoffeeMasterTreeWidget).toSelf();
    treeContainer.rebind(TreeProps).toConstantValue(TREE_PROPS);
    return treeContainer.get(CoffeeMasterTreeWidget);
}

export function createBasicTreeContainer(
    parent: interfaces.Container,
    treeEditorWidget: interfaces.Newable<BaseTreeEditorWidget>,
    modelService: interfaces.Newable<TreeEditor.ModelService>,
    nodeFactory: interfaces.Newable<TreeEditor.NodeFactory>
): interfaces.Container {
    const container = parent.createChild();
    container.bind(TreeEditor.ModelService).to(modelService);
    container.bind(TreeEditor.NodeFactory).to(nodeFactory);
    container.bind(DetailFormWidget).toSelf();
    container.bind(CoffeeMasterTreeWidget).toDynamicValue(context => createTreeWidget(context.container));
    container.bind(treeEditorWidget).toSelf();

    return container;
}
