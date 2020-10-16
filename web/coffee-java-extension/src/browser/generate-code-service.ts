/*!
 * Copyright (c) 2019-2020 EclipseSource and others.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0, or the MIT License which is
 * available at https://opensource.org/licenses/MIT.
 *
 * SPDX-License-Identifier: EPL-2.0 OR MIT
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

    private doGenerateCode(uri: URI, progress: Progress): void {
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
