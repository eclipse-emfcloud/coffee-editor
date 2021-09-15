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
import { injectable } from 'inversify';

import { Widget, BaseWidget, Message, StatefulWidget, SplitPanel, Title } from '@theia/core/lib/browser';
import { GLSPDiagramWidget } from "@eclipse-glsp/theia-integration/lib/browser";
import { FitToScreenAction } from '@eclipse-glsp/client';
import { DiagramWidget } from "sprotty-theia";

export const GraphicalComparisonWidgetOptions = Symbol(
    'GraphicalComparisonWidgetOptions'
);
export interface GraphicalComparisonWidgetOptions {
    left: GLSPDiagramWidget,
    right: GLSPDiagramWidget
}

@injectable()
export class GraphicalComparisonWidget extends BaseWidget implements StatefulWidget{

    protected lastFocusLeft = true;
    protected splitPanel: SplitPanel;
    protected options: GraphicalComparisonWidgetOptions;

    constructor() {
        super();
        this.id = GraphicalComparisonWidget.WIDGET_ID;

        this.addClass('widget-view');

        this.splitPanel = new SplitPanel();
        this.splitPanel.orientation = "horizontal";
        this.splitPanel.spacing = 8;
    }

    setContent(options: GraphicalComparisonWidgetOptions): void {
        this.options = options;

        this.toDispose.push(this.options.left);
        this.toDispose.push(this.options.right);

        this.splitPanel.addClass('widget-view');
        this.splitPanel.addClass('grayBackground');
        this.options.left.addClass('widget-view');
        this.options.right.addClass('widget-view');
        this.splitPanel.addWidget(this.options.left);
        this.splitPanel.addWidget(this.options.right);

        this.splitPanel.setRelativeSizes([1, 1]);
        this.options.left.activate();
        this.options.right.activate();

        this.configureTitle(this.title);
        this.update();
        this.activate();

        this.options.left.getSvgElement().then(_ => {
            this.options.left.actionDispatcher.dispatch(new FitToScreenAction([]));
        });
        this.options.right.getSvgElement().then(_ => {
            this.options.right.actionDispatcher.dispatch(new FitToScreenAction([]));
        });
        this.options.left.node.addEventListener('focusin', (event) => {
            this.lastFocusLeft = true;
        });
        this.options.right.node.addEventListener('focusin', (event) => {
            this.lastFocusLeft = false;
        });
    }

    protected onResize(_msg: any): void {
        if (this.splitPanel) {
            this.splitPanel.update();
        }
    }

    protected onActivateRequest(): void {
        if (this.splitPanel) {
            this.splitPanel.node.focus();
        }
    }

    protected onAfterAttach(msg: Message): void {
        Widget.attach(this.splitPanel, this.node);
        super.onAfterAttach(msg);
    }

    storeState(): object {
        return this.options;
    }

    restoreState(oldState: object): void {
        this.setContent(<GraphicalComparisonWidgetOptions> oldState);
    }

    protected configureTitle(title: Title<Widget>): void {
        if (this.options !== undefined) {
            title.label = this.options.left.title.label + " &#10231; " + this.options.right.title.label;
        }
        title.caption = GraphicalComparisonWidget.WIDGET_LABEL;
        title.closable = true;
        title.iconClass = 'fas fa-columns file-icon';
    }

    get diagramWidget(): DiagramWidget {
        if (this.options) {
            if (this.lastFocusLeft) {
                return this.options.left;
            } else {
                return this.options.right;
            }
        }
        return undefined;
    }
}

export namespace GraphicalComparisonWidget {
    export const WIDGET_ID = 'graphical-model-comparison-view';
    export const EDITOR_ID = 'com.eclipsesource.comparison.graphical.comparison.view';
    export const WIDGET_LABEL = "Graphical comparison view";
}
