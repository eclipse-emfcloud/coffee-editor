import { AbstractViewContribution } from '@theia/core/lib/browser';
import { TabBarToolbarContribution, TabBarToolbarRegistry } from '@theia/core/lib/browser/shell/tab-bar-toolbar';
import { Command, CommandRegistry, MenuModelRegistry, SelectionService } from '@theia/core/lib/common';
import URI from '@theia/core/lib/common/uri';
import { UriAwareCommandHandler, UriCommandHandler } from '@theia/core/lib/common/uri-command-handler';
import { FileNavigatorContribution, NavigatorContextMenu } from '@theia/navigator/lib/browser/navigator-contribution';
import { WorkspaceService } from '@theia/workspace/lib/browser';
import { inject, injectable } from 'inversify';

import { ComparisonExtensionConfiguration } from '../comparison-extension-configuration';
import { ComparisonOrderDialog } from '../comparison-order-dialog';
import { GraphicalComparisonOpener } from './graphical-comparison-opener';
import { GraphicalComparisonWidget, GraphicalComparisonWidgetOptions } from './graphical-comparison-widget';

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
export namespace GraphicalComparisonCommands {
    export const FILE_COMPARE_GRAPHICALLY: Command = {
        id: 'file.compare.graphical',
        category: "Comparison",
        label: 'Compare graphically'
    };
    export const FILE_COMPARE_GRAPHICALLY_OPEN: Command = {
        id: 'file.compare.graphical.open'
    };
}

export namespace ScmNavigatorMoreToolbarGroups {
    export const SCM = '3_navigator_scm';
}

@injectable()
export class GraphicalComparisonContribution extends AbstractViewContribution<GraphicalComparisonWidget> implements TabBarToolbarContribution {

    @inject(CommandRegistry)
    protected readonly commandRegistry: CommandRegistry;

    @inject(FileNavigatorContribution)
    protected readonly fileNavigatorContribution: FileNavigatorContribution;

    @inject(WorkspaceService)
    protected readonly workspaceService: WorkspaceService;

    constructor(
        @inject(SelectionService) protected readonly selectionService: SelectionService,
        @inject(GraphicalComparisonOpener) protected readonly graphicalOpener: GraphicalComparisonOpener,
        @inject(ComparisonExtensionConfiguration) protected readonly config: ComparisonExtensionConfiguration) {

        super({
            widgetId: GraphicalComparisonWidget.WIDGET_ID,
            widgetName: GraphicalComparisonWidget.WIDGET_LABEL,
            defaultWidgetOptions: {
                area: 'main'
            }
        });
    }

    registerMenus(menus: MenuModelRegistry): void {
        menus.registerMenuAction(NavigatorContextMenu.COMPARE, {
            commandId: GraphicalComparisonCommands.FILE_COMPARE_GRAPHICALLY.id
        });
    }

    registerCommands(commands: CommandRegistry): void {
        commands.registerCommand(GraphicalComparisonCommands.FILE_COMPARE_GRAPHICALLY, this.newMultiUriAwareCommandHandler({
            isVisible: uris => this.showGraphicallyCommand(uris),
            isEnabled: uris => this.showGraphicallyCommand(uris),
            execute: async uris => {
                const [left, right] = uris;
                const dialog: ComparisonOrderDialog = new ComparisonOrderDialog(String(left), String(right));
                dialog.open().then(() => {
                    commands.executeCommand(GraphicalComparisonCommands.FILE_COMPARE_GRAPHICALLY_OPEN.id, dialog.getLeft(), dialog.getRight());
                });
            }
        }));
        commands.registerCommand(GraphicalComparisonCommands.FILE_COMPARE_GRAPHICALLY_OPEN, {
            execute: (left, right) => {
                this.graphicalOpener.getHighlights(left, right).then(async (highlights: any) => {
                    const leftWidget = await this.graphicalOpener.getLeftDiagram(new URI(left), highlights);
                    const rightWidget = await this.graphicalOpener.getRightDiagram(new URI(right), highlights);
                    const options: GraphicalComparisonWidgetOptions = {
                        left: leftWidget,
                        right: rightWidget
                    };
                    this.showGraphicalComparisonWidget(options);
                });
            }
        });
    }

    showGraphicallyCommand(uris: URI[]): boolean {
        if (this.config.supportGraphicalComparison()) {
            if (uris.length !== 2) return false;
            return this.config.canHandle(uris[0]) && this.config.canHandle(uris[1]);
        }
        return false;
    }

    registerToolbarItems(registry: TabBarToolbarRegistry): void {
        this.fileNavigatorContribution.registerMoreToolbarItem({
            id: GraphicalComparisonCommands.FILE_COMPARE_GRAPHICALLY.id,
            command: GraphicalComparisonCommands.FILE_COMPARE_GRAPHICALLY.id,
            tooltip: GraphicalComparisonCommands.FILE_COMPARE_GRAPHICALLY.label,
            group: ScmNavigatorMoreToolbarGroups.SCM,
        });
    }

    async showGraphicalComparisonWidget(options: GraphicalComparisonWidgetOptions): Promise<GraphicalComparisonWidget> {
        const widget = await this.widget;
        widget.setContent(options);
        return this.openView({
            activate: true
        });
    }

    protected newMultiUriAwareCommandHandler(handler: UriCommandHandler<URI[]>): UriAwareCommandHandler<URI[]> {
        return new UriAwareCommandHandler(this.selectionService, handler, { multi: true });
    }
}
