/*!
 * Copyright (C) 2021 EclipseSource and others.
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
 */
import { FrontendApplication, OpenerService } from '@theia/core/lib/browser';
import { WidgetManager } from '@theia/core/lib/browser/widget-manager';
import { CommandContribution, CommandRegistry, MessageService, SelectionService } from '@theia/core/lib/common';
import URI from '@theia/core/lib/common/uri';
import { UriCommandHandler } from '@theia/core/lib/common/uri-command-handler';
import { FileSystem } from '@theia/filesystem/lib/common';
import { GitDiffCommands } from '@theia/git/lib/browser/diff/git-diff-contribution';
import { DirtyDiffManager } from '@theia/git/lib/browser/dirty-diff/dirty-diff-manager';
import { GitQuickOpenService } from '@theia/git/lib/browser/git-quick-open-service';
import { ScmService } from '@theia/scm/lib/browser/scm-service';
import { WorkspaceService } from '@theia/workspace/lib/browser';
import { WorkspaceRootUriAwareCommandHandler } from '@theia/workspace/lib/browser/workspace-commands';
import { GraphicalComparisonCommands } from 'comparison-extension/lib/browser/graphical/graphical-comparison-contribution';
import { GraphicalComparisonOpener } from 'comparison-extension/lib/browser/graphical/graphical-comparison-opener';
import { inject, injectable } from 'inversify';

@injectable()
export class CoffeeGitIntegration implements CommandContribution {

    @inject(WorkspaceService)
    protected readonly workspaceService: WorkspaceService;

    constructor(
        @inject(SelectionService) protected readonly selectionService: SelectionService,
        @inject(WidgetManager) protected readonly widgetManager: WidgetManager,
        @inject(FrontendApplication) protected readonly app: FrontendApplication,
        @inject(GitQuickOpenService) protected readonly quickOpenService: GitQuickOpenService,
        @inject(FileSystem) protected readonly fileSystem: FileSystem,
        @inject(OpenerService) protected openerService: OpenerService,
        @inject(MessageService) protected readonly notifications: MessageService,
        @inject(ScmService) protected readonly scmService: ScmService,
        @inject(GraphicalComparisonOpener) protected readonly graphicalOpener: GraphicalComparisonOpener,
        @inject(DirtyDiffManager) protected readonly diffManager: DirtyDiffManager
    ) {

    }

    registerCommands(commands: CommandRegistry): void {
        commands.unregisterCommand(GitDiffCommands.OPEN_FILE_DIFF);
        commands.registerCommand(GitDiffCommands.OPEN_FILE_DIFF, this.newWorkspaceRootUriAwareCommandHandler({
            isVisible: uri => true,
            isEnabled: uri => true,
            execute: async fileUri => {
                await this.quickOpenService.chooseTagsAndBranches(
                    async (fromRevision, toRevision) => {
                        const oldVersion = /* toRevision + ':' + */fileUri.toString();
                        console.log(oldVersion);
                        const newVersion = fileUri.toString();
                        console.log(newVersion);
                        commands.executeCommand(GraphicalComparisonCommands.FILE_COMPARE_GRAPHICALLY_OPEN.id,
                            oldVersion,
                            newVersion);
                    });
            }
        }));
    }
    // commands.executeCommand(GraphicalComparisonCommands.FILE_COMPARE_GRAPHICALLY_OPEN.id,
    //    'file:///home/simon/Git/coffee-editor/backend/examples/SuperBrewer3000/superbrewer3000.coffee',
    //    'file:///home/simon/Git/coffee-editor/backend/examples/SuperBrewer3000/superbrewer4000.coffee');

    protected newWorkspaceRootUriAwareCommandHandler(handler: UriCommandHandler<URI>): WorkspaceRootUriAwareCommandHandler {
        return new WorkspaceRootUriAwareCommandHandler(this.workspaceService, this.selectionService, handler);
    }
}
