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
import URI from '@theia/core/lib/common/uri';
import { TerminalService } from '@theia/terminal/lib/browser/base/terminal-service';
import { inject, injectable } from 'inversify';

@injectable()
export class JUnitRunService {
    constructor(@inject(TerminalService) protected readonly terminalService: TerminalService) {

    }
    public runTest(uri: URI): void {
        const projectName = this.deriveProjectName(uri);
        const packageName = this.derivePackageName(uri);
        const binDirectory = this.deriveBinDirectory(uri);
        if (projectName && packageName && binDirectory) {
            this.terminalService.newTerminal({
                title: 'JUnit Terminal',
                cwd: binDirectory.toString(),
                destroyTermOnClose: false
            }).then(terminalWidget => {
                terminalWidget.start().then(number => {
                    this.terminalService.activateTerminal(terminalWidget);
                    terminalWidget.sendText('java -cp .:../lib/* org.junit.runner.JUnitCore ' + packageName + '\n');
                });
            });
        }
    }

    private findSourceDirectory(javaUri: URI): URI | undefined {
        let sourceDir = javaUri;
        while (!sourceDir.path.isRoot && sourceDir.path.name !== 'src' && sourceDir.path.name !== 'src-gen') {
            sourceDir = sourceDir.parent;
        }
        if (sourceDir.path.name === 'src' || sourceDir.path.name === 'src-gen') {
            return sourceDir;
        }
        return undefined;
    }

    private deriveBinDirectory(javaUri: URI): URI | undefined {
        const sourceDir = this.findSourceDirectory(javaUri);
        if (sourceDir) {
            return sourceDir.parent.resolve('bin');
        }
        return undefined;
    }

    private deriveProjectName(javaUri: URI): string | undefined {
        const sourceDir = this.findSourceDirectory(javaUri);
        if (sourceDir) {
            return sourceDir.parent.path.name;
        }
        return undefined;
    }

    private derivePackageName(javaUri: URI): string | undefined {
        const sourceDir = this.findSourceDirectory(javaUri);
        if (sourceDir) {
            return javaUri.toString().replace(sourceDir.toString() + '/', '').replace('.java', '').split('/').join('.');
        }
        return undefined;
    }
}
