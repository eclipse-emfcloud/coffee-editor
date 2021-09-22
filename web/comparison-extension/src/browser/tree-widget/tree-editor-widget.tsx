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
import { Title } from '@phosphor/widgets';
import { BaseWidget, Message, Saveable, SplitPanel, Widget } from '@theia/core/lib/browser';
import { Emitter, Event, ILogger } from '@theia/core/lib/common';
import { WorkspaceService } from '@theia/workspace/lib/browser/workspace-service';
import { injectable, postConstruct } from 'inversify';

import { TreeEditor } from './interfaces';
import { AddCommandProperty, MasterTreeWidget } from './master-tree-widget';
import { TreeActionWidget } from './tree-action-widget';

@injectable()
export abstract class BaseTreeEditorWidget extends BaseWidget implements Saveable {
    public dirty = false;
    public autoSave: 'off';
    private splitPanelMain: SplitPanel;
    private splitPanelOverview: SplitPanel;
    private splitPanelModels: SplitPanel;
    protected actionWidget: TreeActionWidget;

    protected readonly onDirtyChangedEmitter = new Emitter<void>();
    get onDirtyChanged(): Event<void> {
        return this.onDirtyChangedEmitter.event;
    }

    public selectedNode: TreeEditor.Node;

    protected instanceData: any;

    constructor(
        protected readonly treeWidgetOverview: MasterTreeWidget,
        protected readonly treeWidgetModel1: MasterTreeWidget,
        protected readonly treeWidgetModel2: MasterTreeWidget,
        
        protected readonly workspaceService: WorkspaceService,
        protected readonly logger: ILogger,
        readonly widgetId: string
    ) {
        super();
        this.id = widgetId;
        this.splitPanelMain = new SplitPanel();
        this.splitPanelOverview = new SplitPanel();
        this.splitPanelModels = new SplitPanel();
        this.actionWidget = new TreeActionWidget(this);
        
        this.splitPanelMain.orientation = "vertical";
        this.splitPanelOverview.orientation = "horizontal";
        this.splitPanelModels.orientation = "horizontal";
        this.addClass(BaseTreeEditorWidget.Styles.EDITOR);
        this.splitPanelMain.addClass(BaseTreeEditorWidget.Styles.SASH);
        this.splitPanelOverview.addClass(BaseTreeEditorWidget.Styles.SASH);
        this.splitPanelModels.addClass(BaseTreeEditorWidget.Styles.SASH);
        this.treeWidgetModel1.addClass(BaseTreeEditorWidget.Styles.TREE);
        this.treeWidgetModel2.addClass(BaseTreeEditorWidget.Styles.TREE);
        this.treeWidgetOverview.addClass(BaseTreeEditorWidget.Styles.TREE);

        this.toDispose.push(
            this.treeWidgetModel1.onSelectionChange(ev => this.treeSelectionChanged(treeWidgetModel1, ev))
        );

        this.toDispose.push(
            this.treeWidgetModel2.onSelectionChange(ev => this.treeSelectionChanged(treeWidgetModel2, ev))
        );

        this.toDispose.push(
            this.treeWidgetOverview.onSelectionChange(ev => this.treeSelectionChanged(treeWidgetOverview, ev))
        );

        this.toDispose.push(this.onDirtyChangedEmitter);
    }

    @postConstruct()
    protected init(): void {
        this.configureTitle(this.title);
    }

    protected onResize(_msg: any): void {
        if (this.splitPanelMain) {
            this.splitPanelMain.update();
        }
        if (this.splitPanelModels) {
            this.splitPanelModels.update();
        }
        if (this.splitPanelOverview) {
            this.splitPanelModels.update();
        }
    }

    protected renderError(errorMessage: string): void {
        console.log(errorMessage);
    }

    protected treeSelectionChanged(treeWidget: MasterTreeWidget, selectedNodes: readonly Readonly<TreeEditor.Node>[]): void {
        this.update();
    }

    /**
     * Sets the dirty state of this editor and notify listeners subscribed to the dirty state.
     *
     * @param dirty true if the editor is dirty
     */
    protected setDirty(dirty: boolean): void {
        if (this.dirty !== dirty) {
            this.dirty = dirty;
            this.onDirtyChangedEmitter.fire();
        }
    }

    /**
     * Delete the given node including its associated data from the tree.
     *
     * @param node The tree node to delete
     */
    protected abstract deleteNode(node: Readonly<TreeEditor.Node>): void;

    /**
     * Add a node to the tree.
     * @param node The tree node to add
     * @param type The type of the node's data
     * @param property The property containing the node's data
     */
    protected abstract addNode({
        node,
        type,
        property
    }: AddCommandProperty): void;

    protected onAfterAttach(msg: Message): void {
        this.splitPanelMain.addWidget(this.splitPanelOverview);
        this.splitPanelMain.addWidget(this.splitPanelModels);

        this.splitPanelOverview.addWidget(this.treeWidgetOverview);
        this.splitPanelOverview.addWidget(this.actionWidget);

        this.splitPanelModels.addWidget(this.treeWidgetModel1);
        this.splitPanelModels.addWidget(this.treeWidgetModel2);

        this.splitPanelMain.setRelativeSizes([1, 1]);
        this.splitPanelOverview.setRelativeSizes([1, 1]);
        this.splitPanelModels.setRelativeSizes([1, 1]);

        Widget.attach(this.splitPanelMain, this.node);
        this.treeWidgetModel1.activate();
        this.treeWidgetModel2.activate();
        this.treeWidgetOverview.activate();
        this.actionWidget.activate();
        this.actionWidget.update();
        super.onAfterAttach(msg);
    }

    protected onActivateRequest(): void {
        if (this.splitPanelMain) {
            this.splitPanelMain.node.focus();
        }
    }

    /**
     * Called when the data in the detail was changed.
     * Whether you need to manually apply the change to the tree node's referenced data
     * depends on your implementation of method 'getDataForNode' of your ModelService.
     *
     * @param data The new data for the node
     * @param node The tree node whose data will be updated
     */
    protected abstract handleFormUpdate(
        data: any,
        node: TreeEditor.Node
    ): void;

    public save(): void {
        // do nothing by default
    }

    public abstract merge(toLeft: boolean, all: boolean, conflict: boolean): void;
    public abstract undoMerge(): void;
    public abstract showGraphicalComparison(): void;

    /**
     * Configure this editor's title tab by configuring the given Title object.
     *
     * @param title The title object configuring this editor's title tab in Theia
     */
    protected abstract configureTitle(title: Title<Widget>): void;
}

// eslint-disable-next-line no-redeclare
export namespace BaseTreeEditorWidget {
    export const WIDGET_LABEL = 'Theia Tree Editor';

    export namespace Styles {
        export const EDITOR = 'theia-tree-editor';
        export const TREE = 'theia-tree-editor-tree';
        export const FORM = 'theia-tree-editor-form';
        export const SASH = 'theia-tree-editor-sash';
    }
}
