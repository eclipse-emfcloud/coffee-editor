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
import { ModelServerClient } from '@eclipse-emfcloud/modelserver-theia/lib/common';
import { FrontendApplication, OpenerService } from '@theia/core/lib/browser';
import { WidgetManager } from '@theia/core/lib/browser/widget-manager';
import { Command, CommandContribution, CommandRegistry, MessageService, SelectionService } from '@theia/core/lib/common';
import URI from '@theia/core/lib/common/uri';
import { UriCommandHandler } from '@theia/core/lib/common/uri-command-handler';
import { FileSystem } from '@theia/filesystem/lib/common';
import { GitDiffCommands } from '@theia/git/lib/browser/diff/git-diff-contribution';
import { DirtyDiffManager } from '@theia/git/lib/browser/dirty-diff/dirty-diff-manager';
import { GitQuickOpenService } from '@theia/git/lib/browser/git-quick-open-service';
import { Git } from '@theia/git/lib/common';
import { ScmService } from '@theia/scm/lib/browser/scm-service';
import { WorkspaceService } from '@theia/workspace/lib/browser';
import { WorkspaceRootUriAwareCommandHandler } from '@theia/workspace/lib/browser/workspace-commands';
import { GraphicalComparisonCommands } from 'comparison-extension/lib/browser/graphical/graphical-comparison-contribution';
import { GraphicalComparisonOpener } from 'comparison-extension/lib/browser/graphical/graphical-comparison-opener';
import { ComparisonCommands } from 'comparison-extension/lib/browser/tree-comparison-contribution';
import { inject, injectable } from 'inversify';

export namespace GitCommands {
    export const OPEN_FILE_DIFF: Command = {
        id: 'git-coffee:open-diff',
        category: 'Git Diff',
        label: 'Compare With HEAD'
    };
}

@injectable()
export class CoffeeGitIntegration implements CommandContribution {

    @inject(WorkspaceService)
    protected readonly workspaceService: WorkspaceService;
    @inject(ModelServerClient)
    private readonly modelServerApi: ModelServerClient;

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
        @inject(DirtyDiffManager) protected readonly diffManager: DirtyDiffManager,
        @inject(Git) protected readonly git: Git
    ) {

    }

    registerCommands(commands: CommandRegistry): void {
        commands.unregisterCommand(GitDiffCommands.OPEN_FILE_DIFF);
        GitDiffCommands.OPEN_FILE_DIFF.label = 'Compare with HEAD';
        commands.registerCommand(GitDiffCommands.OPEN_FILE_DIFF, this.newWorkspaceRootUriAwareCommandHandler({
            isVisible: uri => uri.toString().endsWith('.coffee') || uri.toString().endsWith('.notation'),
            isEnabled: uri => true,
            execute: async fileUri => {
                const isCoffeeFile = fileUri.toString().endsWith('.coffee');
                const currentCoffeePath = isCoffeeFile ? fileUri.toString() : fileUri.toString().replace('.notation', '.coffee');
                const currentNotationPath = currentCoffeePath.replace('.coffee', '.notation');
                const workspace = currentCoffeePath.substr(0, currentCoffeePath.lastIndexOf('/'));
                const repository = await this.git.repositories(workspace, { maxCount: 1 });
                const sh = await this.git.revParse(repository[0], { ref: 'HEAD' });
                const coffeeFileName = currentCoffeePath.substr(currentCoffeePath.lastIndexOf('/') + 1);
                const notationFileName = coffeeFileName.substr(0, coffeeFileName.lastIndexOf('.')) + '.notation';
                const coffeeHeadFileName = coffeeFileName.substr(0, coffeeFileName.lastIndexOf('.')) + '@' + sh?.slice(0, 7) + '.coffee';
                const notationHeadFileName = notationFileName.substr(0, notationFileName.lastIndexOf('.')) + '@' + sh?.slice(0, 7) + '.notation';
                const coffeeCopyFileName = coffeeFileName.substr(0, coffeeFileName.lastIndexOf('.')) + '@current.coffee';
                const notationCopyFileName = notationFileName.substr(0, notationFileName.lastIndexOf('.')) + '@current.notation';
                const headCoffeePath = workspace + '/.help/' + coffeeHeadFileName;
                const headNotationPath = workspace + '/.help/' + notationHeadFileName;
                const copyCoffeePath = workspace + '/.help/' + coffeeCopyFileName;
                const copyNotationPath = workspace + '/.help/' + notationCopyFileName;
                const headCoffeeFile = await this.git.show(repository[0], currentCoffeePath, { commitish: 'HEAD' });
                const headNotationFile = await this.git.show(repository[0], workspace + '/' + notationFileName, { commitish: 'HEAD' });
                await this.writeToFile(headCoffeePath, headCoffeeFile);
                await this.writeToFile(headNotationPath, headNotationFile);
                await this.fileSystem.copy(currentCoffeePath, copyCoffeePath, { overwrite: true });
                await this.fileSystem.copy(currentNotationPath, copyNotationPath, { overwrite: true });
                if (this.workspaceService.workspace) {
                    this.modelServerApi.configure({ workspaceRoot: this.workspaceService.workspace.resource.toString() });
                }
                if (isCoffeeFile) {
                    commands.executeCommand(ComparisonCommands.FILE_COMPARE_TREE_OPEN.id,
                        copyCoffeePath,
                        headCoffeePath);
                } else {
                    commands.executeCommand(GraphicalComparisonCommands.FILE_COMPARE_GRAPHICALLY_OPEN.id,
                        copyCoffeePath,
                        headCoffeePath);
                }
            }
        }));
    }

    protected async writeToFile(path: string, content: string): Promise<void> {
        if (await this.fileSystem.exists(path)) {
            await this.fileSystem.delete(path);
        }
        await this.fileSystem.createFile(path, { content: content });
    }

    protected newWorkspaceRootUriAwareCommandHandler(handler: UriCommandHandler<URI>): WorkspaceRootUriAwareCommandHandler {
        return new WorkspaceRootUriAwareCommandHandler(this.workspaceService, this.selectionService, handler);
    }
}
