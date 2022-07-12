/*
 * Copyright (c) 2022 EclipseSource and others.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0, or the MIT License which is
 * available at https://opensource.org/licenses/MIT.
 *
 * SPDX-License-Identifier: EPL-2.0 OR MIT
 */
import { ModelServerClient } from '@eclipse-emfcloud/modelserver-theia/lib/common';
import { FrontendApplication, OpenerService } from '@theia/core/lib/browser';
import { WidgetManager } from '@theia/core/lib/browser/widget-manager';
import { Command, CommandContribution, CommandRegistry, MessageService, SelectionService } from '@theia/core/lib/common';
import URI from '@theia/core/lib/common/uri';
import { UriCommandHandler } from '@theia/core/lib/common/uri-command-handler';
import { GitDiffCommands } from '@theia/git/lib/browser/diff/git-diff-contribution';
import { DirtyDiffManager } from '@theia/git/lib/browser/dirty-diff/dirty-diff-manager';
import { GitQuickOpenService } from '@theia/git/lib/browser/git-quick-open-service';
import { Git } from '@theia/git/lib/common';
import { ScmService } from '@theia/scm/lib/browser/scm-service';
import { WorkspaceService } from '@theia/workspace/lib/browser';
import { WorkspaceRootUriAwareCommandHandler } from '@theia/workspace/lib/browser/workspace-commands';
import { GraphicalComparisonCommands } from '@eclipsesource/comparison-extension/lib/browser/graphical/graphical-comparison-contribution';
import { GraphicalComparisonOpener } from '@eclipsesource/comparison-extension/lib/browser/graphical/graphical-comparison-opener';
import { ComparisonCommands } from '@eclipsesource/comparison-extension/lib/browser/tree-comparison-contribution';
import { inject, injectable } from 'inversify';
import { FileService } from '@theia/filesystem/lib/browser/file-service';
import { BinaryBuffer } from '@theia/core/lib/common/buffer';

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
        @inject(SelectionService)
        protected readonly selectionService: SelectionService,
        @inject(WidgetManager) protected readonly widgetManager: WidgetManager,
        @inject(FrontendApplication)
        protected readonly app: FrontendApplication,
        @inject(GitQuickOpenService)
        protected readonly quickOpenService: GitQuickOpenService,
        @inject(FileService) protected readonly fileSystem: FileService,
        @inject(OpenerService) protected openerService: OpenerService,
        @inject(MessageService)
        protected readonly notifications: MessageService,
        @inject(ScmService) protected readonly scmService: ScmService,
        @inject(GraphicalComparisonOpener)
        protected readonly graphicalOpener: GraphicalComparisonOpener,
        @inject(DirtyDiffManager)
        protected readonly diffManager: DirtyDiffManager,
        @inject(Git) protected readonly git: Git
    ) {}

    registerCommands(commands: CommandRegistry): void {
        commands.unregisterCommand(GitDiffCommands.OPEN_FILE_DIFF);
        GitDiffCommands.OPEN_FILE_DIFF.label = 'Compare with HEAD';
        commands.registerCommand(
            GitDiffCommands.OPEN_FILE_DIFF,
            this.newWorkspaceRootUriAwareCommandHandler({
                isVisible: uri => uri.toString().endsWith('.coffee') || uri.toString().endsWith('.notation'),
                isEnabled: uri => true,
                execute: async fileUri => {
                    const isCoffeeFile = fileUri.toString().endsWith('.coffee');
                    const currentCoffeePath = isCoffeeFile ? fileUri.toString() : fileUri.toString().replace('.notation', '.coffee');
                    const currentNotationPath = currentCoffeePath.replace('.coffee', '.notation');

                    // Real file paths
                    const coffeeFileName = currentCoffeePath.substring(currentCoffeePath.lastIndexOf('/') + 1);
                    const notationFileName = coffeeFileName.substring(0, coffeeFileName.lastIndexOf('.')) + '.notation';

                    // Calculate workspace folder
                    const workspace = currentCoffeePath.substring(0, currentCoffeePath.lastIndexOf('/'));

                    // Get info about HEAD commit
                    const repository = await this.git.repositories(workspace, {
                        maxCount: 1
                    });
                    const sh = await this.git.revParse(repository[0], { ref: 'HEAD' });

                    // File path for head file in ./help subfolder
                    const headCoffeePath =
                        workspace +
                        '/.help/' +
                        coffeeFileName.substring(0, coffeeFileName.lastIndexOf('.')) +
                        '@' +
                        sh?.slice(0, 7) +
                        '.coffee';
                    const headNotationPath =
                        workspace +
                        '/.help/' +
                        notationFileName.substring(0, notationFileName.lastIndexOf('.')) +
                        '@' +
                        sh?.slice(0, 7) +
                        '.notation';

                    // File path for the copy of real file in the ./help subfolder
                    const copyCoffeePath =
                        workspace + '/.help/' + coffeeFileName.substring(0, coffeeFileName.lastIndexOf('.')) + '@current.coffee';
                    const copyNotationPath =
                        workspace + '/.help/' + notationFileName.substring(0, notationFileName.lastIndexOf('.')) + '@current.notation';

                    // Copy the current files to the help directory
                    await this.fileSystem.copy(new URI(currentCoffeePath), new URI(copyCoffeePath), { overwrite: true });
                    await this.fileSystem.copy(new URI(currentNotationPath), new URI(copyNotationPath), { overwrite: true });

                    // get the file contents of the head commit
                    const headCoffeeFile = await this.git.show(repository[0], currentCoffeePath, { commitish: 'HEAD' });
                    const headNotationFile = await this.git.show(repository[0], workspace + '/' + notationFileName, { commitish: 'HEAD' });

                    // write them to the ./help directory
                    await this.writeToFile(new URI(headCoffeePath), headCoffeeFile);
                    await this.writeToFile(new URI(headNotationPath), headNotationFile);

                    if (this.workspaceService.workspace) {
                        await this.modelServerApi.configure({ workspaceRoot: this.workspaceService.workspace.resource.toString() });
                    }

                    if (isCoffeeFile) {
                        commands.executeCommand(ComparisonCommands.FILE_COMPARE_TREE_OPEN.id, headCoffeePath, copyCoffeePath);
                    } else {
                        commands.executeCommand(
                            GraphicalComparisonCommands.FILE_COMPARE_GRAPHICALLY_OPEN.id,
                            headCoffeePath,
                            copyCoffeePath
                        );
                    }
                }
            })
        );
    }

    protected async writeToFile(path: URI, content: string): Promise<void> {
        if (await this.fileSystem.exists(path)) {
            await this.fileSystem.delete(path);
        }
        await this.fileSystem.createFile(path, BinaryBuffer.fromString(content));
    }

    protected newWorkspaceRootUriAwareCommandHandler(handler: UriCommandHandler<URI>): WorkspaceRootUriAwareCommandHandler {
        return new WorkspaceRootUriAwareCommandHandler(this.workspaceService, this.selectionService, handler);
    }
}
