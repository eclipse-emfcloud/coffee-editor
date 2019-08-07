/*!
 * Copyright (C) 2019 EclipseSource and others.
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
import { DisposableCollection, ILogger, MessageService, Progress } from '@theia/core';
import { PreferenceService } from '@theia/core/lib/browser';
import URI from '@theia/core/lib/common/uri';
import { Workspace } from '@theia/languages/lib/browser';
import { inject, injectable } from 'inversify';

import { CodeGenServer } from '../common/generate-protocol';

@injectable()
export class GenerateCodeService {
    private readonly AUTO_CODEGEN_PREFERENCE: string = 'editor.autoGenerateCode';
    private readonly toDispose = new DisposableCollection();
    constructor(
        @inject(Workspace) private readonly workspace: Workspace,
        @inject(PreferenceService) private readonly preferenceService: PreferenceService,
        @inject(CodeGenServer) private readonly codeGenServer: CodeGenServer,
        @inject(MessageService) protected readonly messageService: MessageService,
        @inject(ILogger) private readonly logger: ILogger
    ) {
        const event = this.workspace.onDidSaveTextDocument;
        if (event) {
            this.toDispose.push(event(textDocument => {
                const fileUri = new URI(textDocument.uri);
                if (this.isWorkflowFile(fileUri)) {
                    this.logger.info(`Saved ${fileUri.path.base}, autogenerate is set to: ${this.isAutoGenerateOn()}`);
                    if (this.isAutoGenerateOn()) {
                        this.logger.info(`Generate code for ${fileUri}`);
                        this.generateCode(fileUri);
                    }
                }
            }));
        }
    }

    public generateCode(uri: URI): void {
        this.messageService.showProgress(
            { text: `Generating code for ${uri.parent.relative(uri)}`, options: { cancelable: false } }
        ).then(progress => this.doGenerateCode(uri, progress));
    }

    private doGenerateCode(uri: URI, progress: Progress) {
        const generationDirectory = uri.parent;
        const packageName = generationDirectory.path.name;
        const sourceFile = uri.toString();
        const target = generationDirectory.toString();
        this.codeGenServer.generateCode(sourceFile, target, packageName)
            .finally(() => progress.cancel());
    }

    dispose(): void {
        this.toDispose.dispose();
    }

    public isAutoGenerateOn(): boolean {
        const autoSave = this.preferenceService.get(this.AUTO_CODEGEN_PREFERENCE);
        return autoSave === 'on' || autoSave === undefined;
    }

    public async toggleAutoGenerate(): Promise<void> {
        this.preferenceService.set(this.AUTO_CODEGEN_PREFERENCE, this.isAutoGenerateOn() ? 'off' : 'on');
    }

    public isWorkflowFile(fileUri: URI): boolean {
        return fileUri.toString().endsWith('.coffee');
    }
}
