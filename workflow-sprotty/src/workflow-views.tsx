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
import * as snabbdom from "snabbdom-jsx"
import { TaskNode, WeightedEdge, Icon } from "./model";
import { RenderingContext, RectangularNodeView, PolylineEdgeView, IView, SShapeElement } from "sprotty/lib";
import { VNode } from "snabbdom/vnode";
import { ActivityNode } from "./model";

const JSX = { createElement: snabbdom.svg }

export class TaskNodeView extends RectangularNodeView {
    render(node: TaskNode, context: RenderingContext): VNode {
        const rcr = this.getRoundedCornerRadius(node)
        const graph = <g>
            <rect class-sprotty-node={true} class-task={true}
                class-automated={node.taskType === 'automated'}
                class-manual={node.taskType === 'manual'}
                class-mouseover={node.hoverFeedback} class-selected={node.selected}
                x={0} y={0} rx={rcr} ry={rcr}
                width={Math.max(0, node.bounds.width)} height={Math.max(0, node.bounds.height)}></rect>
            {context.renderChildren(node)}
        </g>;
        return graph


    }

    protected getRoundedCornerRadius(node: SShapeElement): number {
        return 5;
    }
}

export class ActivityNodeView extends RectangularNodeView {
    render(node: ActivityNode, context: RenderingContext): VNode {
        // In this context, the coordinates (0,0) mark the upper left corner of
        // the node, thus we shift all elements by the radius of the circle.
        const hw = node.bounds.width / 2
        const hh = node.bounds.height / 2
        const graph = <g>
            <rect class-sprotty-node={true} class-activity-node={true} class-mouseover={node.hoverFeedback} class-selected={node.selected}
                x={0} y={0}
                width={Math.max(0, node.bounds.width)} height={Math.max(0, node.bounds.height)} transform={`rotate(45,${hw},${hh})`}></rect>
            {context.renderChildren(node)}

        </g>;
        return graph

    }
}


export class WeightedEdgeView extends PolylineEdgeView {

    render(edge: Readonly<WeightedEdge>, context: RenderingContext): VNode {
        const route = edge.route();
        if (route.length === 0)
            return this.renderDanglingEdge("Cannot compute route", edge, context);

        return <g class-sprotty-edge={true}
            class-weighted={true}
            class-low={edge.probability === 'low'}
            class-medium={edge.probability === 'medium'}
            class-high={edge.probability === 'high'}
            class-mouseover={edge.hoverFeedback}>
            {this.renderLine(edge, route, context)}
            {this.renderAdditionals(edge, route, context)}
            {context.renderChildren(edge, { route })}
        </g>;
    }

}


export class IconView implements IView {

    render(element: Icon, context: RenderingContext): VNode {
        const radius = this.getRadius();
        return <g>
            <circle class-sprotty-icon={true} r={radius} cx={radius} cy={radius}></circle>
            {context.renderChildren(element)}
        </g>;
    }

    getRadius() {
        return 16;
    }
}
