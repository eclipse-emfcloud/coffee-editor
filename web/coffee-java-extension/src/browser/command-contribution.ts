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
import { CommonMenus } from '@theia/core/lib/browser';
import {
    Command,
    CommandContribution,
    CommandRegistry,
    CommandService,
    MenuContribution,
    MenuModelRegistry,
    SelectionService,
} from '@theia/core/lib/common';
import URI from '@theia/core/lib/common/uri';
import { UriAwareCommandHandler, UriCommandHandler } from '@theia/core/lib/common/uri-command-handler';
import { inject, injectable } from 'inversify';

import { GenerateCodeService } from './generate-code-service';
import { JUnitRunService } from './junit-run-service';

export const CODEGEN_COMMAND: Command = {
    id: 'workflow.generate.code.command',
    label: 'Generate Workflow code'
};

export const AUTO_CODEGEN_COMMAND: Command = {
    id: 'workflow.autogenerate.code.command',
    label: 'Auto-Generate Code'
};

export const TEST_CODE_COMMAND: Command = {
    id: 'workflow.test.code.command',
    label: 'Run JUnit Test'
};

@injectable()
export class JavaGenerationCommandContribution implements CommandContribution, MenuContribution {

    constructor(
        @inject(SelectionService) protected readonly selectionService: SelectionService,
        @inject(CommandService) protected readonly commandService: CommandService,
        @inject(GenerateCodeService) protected readonly generateCodeService: GenerateCodeService,
        @inject(JUnitRunService) private readonly junitRunService: JUnitRunService
    ) { }

    registerMenus(menus: MenuModelRegistry): void {
        menus.registerMenuAction([...['navigator-context-menu'], '0_addition'], {
            commandId: CODEGEN_COMMAND.id
        });
        menus.registerMenuAction([...['navigator-context-menu'], '1_addition'], {
            commandId: TEST_CODE_COMMAND.id
        });
        menus.registerMenuAction([...CommonMenus.EDIT, '4_autogenerate'], {
            commandId: AUTO_CODEGEN_COMMAND.id,
            order: '6'
        });
    }

    registerCommands(registry: CommandRegistry): void {
        registry.registerCommand(AUTO_CODEGEN_COMMAND, {
            isToggled: () => this.generateCodeService.isAutoGenerateOn(),
            execute: () => this.generateCodeService.toggleAutoGenerate()
        });
        registry.registerCommand(CODEGEN_COMMAND, this.newUriAwareCommandHandler({
            execute: uri => this.generateCodeService.generateCode(uri),
            isVisible: uri => this.generateCodeService.isWorkflowFile(uri),
            isEnabled: uri => this.generateCodeService.isWorkflowFile(uri),
        }));
        registry.registerCommand(TEST_CODE_COMMAND, this.newUriAwareCommandHandler({
            execute: uri => this.junitRunService.runTest(uri),
            isVisible: uri => this.isJUnitTestFile(uri),
            isEnabled: uri => this.isJUnitTestFile(uri)
        }));
    }

    private newUriAwareCommandHandler(handler: UriCommandHandler<URI>): UriAwareCommandHandler<URI> {
        return new UriAwareCommandHandler(this.selectionService, handler);
    }

    private isJUnitTestFile(fileUri: URI): boolean {
        return fileUri.toString().endsWith('Test.java');
    }

}
