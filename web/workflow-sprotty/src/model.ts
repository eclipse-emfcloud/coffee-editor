/*******************************************************************************
 * Copyright (c) 2018 Tobias Ortmayr.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *   
 * Contributors:
 * 	Tobias Ortmayr - initial API and implementation
 ******************************************************************************/
import { RectangularNode, SEdge, LayoutContainer, SShapeElement, Bounds, boundsFeature, layoutContainerFeature, layoutableChildFeature, fadeFeature, Expandable, expandFeature, Point, SParentElement, toRadians, } from "sprotty/lib";
import { ActivityNodeSchema } from "./model-schema";

export class TaskNode extends RectangularNode implements Expandable {
    expanded: boolean
    name: string = ""
    duration?: number;
    taskType?: string;
    reference?: string;


    hasFeature(feature: symbol) {
        return feature === expandFeature || super.hasFeature(feature);
    }

}

export class WeightedEdge extends SEdge {
    probability?: string
}

export class RotatableRectangularNode extends RectangularNode {

    rotationInDegrees: number = 0

    getTranslatedAnchor(refPoint: Point, refContainer: SParentElement, edge: SEdge, offset?: number): Point {
        let cx = this.position.x + this.size.width / 2
        let cy = this.position.y + this.size.height / 2
        let translatedRefPoint = this.rotatePoint(cx, cy, -this.rotationInDegrees, refPoint)
        let originalAnchor = super.getTranslatedAnchor(translatedRefPoint, refContainer, edge, offset);
        return this.rotatePoint(cx, cy, this.rotationInDegrees, originalAnchor);
    }

    rotatePoint(cx: number, cy: number, angle: number, p: Point): Point {
        let rad = toRadians(angle)
        let s = Math.sin(rad);
        let c = Math.cos(rad);

        // translate point back to origin:
        let x = p.x;
        let y = p.y;
        x -= cx;
        y -= cy;

        // rotate point
        let xnew = x * c - y * s;
        let ynew = x * s + y * c;

        // translate point back:
        return {
            x: xnew + cx,
            y: ynew + cy
        };
    }
}

export class ActivityNode extends RotatableRectangularNode {
    nodeType: string = ActivityNodeSchema.Type.UNDEFINED
    size = {
        width: 16,
        height: 16
    };
    rotationInDegrees = 45
}


export class Icon extends SShapeElement implements LayoutContainer {
    layout: string
    layoutOptions?: { [key: string]: string | number | boolean; };
    bounds: Bounds
    size = {
        width: 32,
        height: 32
    };

    hasFeature(feature: symbol): boolean {
        return feature === boundsFeature || feature === layoutContainerFeature || feature === layoutableChildFeature || feature === fadeFeature;
    }
}
