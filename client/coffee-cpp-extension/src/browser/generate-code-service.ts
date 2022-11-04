/*
 * Copyright (c) 2021-2022 EclipseSource and others.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0, or the MIT License which is
 * available at https://opensource.org/licenses/MIT.
 *
 * SPDX-License-Identifier: EPL-2.0 OR MIT
 */
import { DisposableCollection, MessageService, Progress } from '@theia/core';
import URI from '@theia/core/lib/common/uri';
import { inject, injectable } from 'inversify';

import { CppCodeGenServer } from '../common/generate-protocol';

@injectable()
export class GenerateCppCodeService {
    private readonly toDispose = new DisposableCollection();

    // FIXME
    // @inject(TaskService) private readonly taskService: TaskService
    constructor(
        @inject(CppCodeGenServer) private readonly codeGenServer: CppCodeGenServer,
        @inject(MessageService) protected readonly messageService: MessageService
    ) {}

    public generateCode(uri: URI): void {
        this.messageService
            .showProgress({ text: `Generating code for ${uri.parent.relative(uri)}`, options: { cancelable: false } })
            .then(progress => this.doGenerateCode(uri, progress));
    }

    private doGenerateCode(uri: URI, progress: Progress): void {
        const generationDirectory = uri.parent;
        const packageName = generationDirectory.path.name;
        const sourceFile = uri.toString();
        const target = generationDirectory.toString();
        this.codeGenServer.generateCode(sourceFile, target, packageName).finally(() => {
            progress.cancel();
            // FIXME reimplement code gen without @theia/languages
            // this.taskService.getTasks().then(tasks => {
            //     const task = tasks.find(t => t.label === 'Binary build');
            //     if (task) {
            //         this.taskService.runTask(task);
            //     }
            // });
        });
    }

    dispose(): void {
        this.toDispose.dispose();
    }

    public isWorkflowFile(fileUri: URI): boolean {
        return fileUri.toString().endsWith('.coffee');
    }
}
