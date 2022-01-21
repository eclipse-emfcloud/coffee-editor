/********************************************************************************
 * Copyright (c) 2020-2022 EclipseSource and others.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0, or the MIT License which is
 * available at https://opensource.org/licenses/MIT.
 *
 * SPDX-License-Identifier: EPL-2.0 OR MIT
 ********************************************************************************/
import {
    Bounds,
    boundsFeature,
    connectableFeature,
    deletableFeature,
    DiamondNode,
    fadeFeature,
    hoverFeedbackFeature,
    isEditableLabel,
    layoutableChildFeature,
    LayoutContainer,
    layoutContainerFeature,
    moveFeature,
    Nameable,
    nameFeature,
    popupFeature,
    RectangularNode,
    SEdge,
    selectFeature,
    SModelElement,
    SShapeElement,
    WithEditableLabel,
    withEditLabelFeature,
} from '@eclipse-glsp/client';

import { ActivityNodeSchema } from './model-schema';

export class TaskNode extends RectangularNode
    implements Nameable, WithEditableLabel {
    static readonly DEFAULT_FEATURES = [
        connectableFeature,
        deletableFeature,
        selectFeature,
        boundsFeature,
        moveFeature,
        layoutContainerFeature,
        fadeFeature,
        hoverFeedbackFeature,
        popupFeature,
        nameFeature,
        withEditLabelFeature
    ];
    name = "";
    duration?: number;
    taskType?: string;
    reference?: string;

    get editableLabel() {
        const headerComp = this.children.find(
            element => element.type === "comp:header"
        );
        if (headerComp) {
            const label = headerComp.children.find(
                element => element.type === "label:heading"
            );
            if (label && isEditableLabel(label)) {
                return label;
            }
        }
        return undefined;
    }
}

export function isTaskNode(element: SModelElement): element is TaskNode {
    return element instanceof TaskNode || false;
}

export class WeightedEdge extends SEdge {
    probability?: string;
}

export class ActivityNode extends DiamondNode {
    nodeType: string = ActivityNodeSchema.Type.UNDEFINED;
    size = {
        width: 32,
        height: 32
    };
    strokeWidth = 1;
}

export class Icon extends SShapeElement implements LayoutContainer {
    static readonly DEFAULT_FEATURES = [
        boundsFeature,
        layoutContainerFeature,
        layoutableChildFeature,
        fadeFeature
    ];

    commandId: string;
    layout: string;
    layoutOptions?: { [key: string]: string | number | boolean };
    bounds: Bounds;
    size = {
        width: 32,
        height: 32
    };
}
