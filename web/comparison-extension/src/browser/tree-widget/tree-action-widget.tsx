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
import * as React from 'react';
import { injectable, postConstruct } from 'inversify';
import { ReactWidget } from '@theia/core/lib/browser';
import { BaseTreeEditorWidget } from './tree-editor-widget';

@injectable()
export class TreeActionWidget extends ReactWidget {
    protected parentView: BaseTreeEditorWidget;
    protected activateMerge = false;
    protected activateConflict = false;
    protected showGraphicalComparison = false;

    constructor(parent: BaseTreeEditorWidget) {
        super();
        this.parentView = parent;
        this.id = 'tree-settings-widget';
        this.addClass('widget-view');
        this.title.closable = true;
    }

    protected render(): React.ReactNode {
        let graphicalComparison = <></>;
        if (this.showGraphicalComparison) {
            graphicalComparison = <>
                <br/><br/>
                <button onClick={() => this.parentView.showGraphicalComparison()}>Show graphical comparison</button>
            </>;
        }

        return <div>
            <h3>{"Actions:"}</h3>
            <b>Merge</b><br/>
            <button onClick={() => this.parentView.merge(false, false, false)} disabled={!this.activateMerge}>&#129106; Merge Single</button><br/>
            <button onClick={() => this.parentView.merge(true, false, false)} disabled={!this.activateMerge}>&#10006; Discard Single</button><br/>
            <button onClick={() => this.parentView.merge(false, true, false)}>&#129106; Merge ALL </button><br/><br/>
            <br/>
            <b>Resolve conflict</b><br/>
            <button onClick={() => this.parentView.merge(true, false, true)} disabled={!this.activateConflict}>&#129104; Merge to left (keep right) </button><br/>
            <button onClick={() => this.parentView.merge(false, false, true)} disabled={!this.activateConflict}>&#129106;  Merge to right (keep left)</button><br/><br/>
            <br/>
            <b>Undo</b><br/>
            <button onClick={() => this.parentView.undoMerge()} disabled={!this.parentView.dirty}>Undo last action</button>
            {graphicalComparison}
        </div>;
    }

    public updateActivation(type: string) {
        if (type === "diff") {
            this.activateMerge = true;
            this.activateConflict = false;
        } else if (type === "conflict") {
            this.activateMerge = false;
            this.activateConflict = true;
        } else {
            this.activateMerge = false;
            this.activateConflict = false;
        }
        this.update();
    }

    public setGraphicalComparisonVisibility(visible: boolean) {
        this.showGraphicalComparison = visible;
        this.update();
    }

    @postConstruct()
    protected init(): void {
        this.update();
    }
}