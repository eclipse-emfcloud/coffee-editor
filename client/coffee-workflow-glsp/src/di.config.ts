/*
 * Copyright (c) 2019-2023 EclipseSource, Christian W. Damus, and others.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0, or the MIT License which is
 * available at https://opensource.org/licenses/MIT.
 *
 * SPDX-License-Identifier: EPL-2.0 OR MIT
 */
import 'balloon-css/balloon.min.css';
import 'sprotty/css/edit-label.css';
import '../css/diagram.css';

import {
    configureDefaultModelElements,
    configureModelElement,
    ConsoleLogger,
    createDiagramContainer,
    DeleteElementContextMenuItemProvider,
    DiamondNodeView,
    editLabelFeature,
    GridSnapper,
    LogLevel,
    overrideViewerOptions,
    RectangularNodeView,
    RevealNamedElementActionProvider,
    RoundedCornerNodeView,
    SCompartment,
    SCompartmentView,
    SEdge,
    SLabel,
    SLabelView,
    StructureCompartmentView,
    TYPES
} from '@eclipse-glsp/client';
import { DefaultTypes } from '@eclipse-glsp/protocol';
import { Container, ContainerModule } from 'inversify';

import { directTaskEditor } from './direct-task-editing/di.config';
import { ActivityNode, CategoryNode, Icon, TaskNode, WeightedEdge } from './model';
import { IconView, WorkflowEdgeView } from './workflow-views';

const workflowDiagramModule = new ContainerModule((bind, unbind, isBound, rebind) => {
    rebind(TYPES.ILogger).to(ConsoleLogger).inSingletonScope();
    rebind(TYPES.LogLevel).toConstantValue(LogLevel.warn);
    bind(TYPES.ISnapper).to(GridSnapper);
    bind(TYPES.ICommandPaletteActionProvider).to(RevealNamedElementActionProvider);
    bind(TYPES.IContextMenuItemProvider).to(DeleteElementContextMenuItemProvider);
    const context = { bind, unbind, isBound, rebind };

    configureDefaultModelElements(context);
    configureModelElement(context, 'task:automated', TaskNode, RoundedCornerNodeView);
    configureModelElement(context, 'task:manual', TaskNode, RoundedCornerNodeView);
    configureModelElement(context, 'label:heading', SLabel, SLabelView, { enable: [editLabelFeature] });
    configureModelElement(context, 'comp:comp', SCompartment, SCompartmentView);
    configureModelElement(context, 'comp:header', SCompartment, SCompartmentView);
    configureModelElement(context, 'label:icon', SLabel, SLabelView);
    configureModelElement(context, DefaultTypes.EDGE, SEdge, WorkflowEdgeView);
    configureModelElement(context, 'edge:weighted', WeightedEdge, WorkflowEdgeView);
    configureModelElement(context, 'icon', Icon, IconView);
    configureModelElement(context, 'activityNode:merge', ActivityNode, DiamondNodeView);
    configureModelElement(context, 'activityNode:decision', ActivityNode, DiamondNodeView);
    configureModelElement(context, 'activityNode:fork', ActivityNode, RectangularNodeView);
    configureModelElement(context, 'activityNode:join', ActivityNode, RectangularNodeView);

    configureModelElement(context, 'category', CategoryNode, RoundedCornerNodeView);
    configureModelElement(context, 'struct', SCompartment, StructureCompartmentView);
});

export default function createContainer(widgetId: string): Container {
    const container = createDiagramContainer(workflowDiagramModule, directTaskEditor);
    overrideViewerOptions(container, {
        baseDiv: widgetId,
        hiddenDiv: widgetId + '_hidden'
    });
    return container;
}
