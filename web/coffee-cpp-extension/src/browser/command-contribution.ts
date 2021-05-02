/*!
 * Copyright (c) 2021 EclipseSource and others.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0, or the MIT License which is
 * available at https://opensource.org/licenses/MIT.
 *
 * SPDX-License-Identifier: EPL-2.0 OR MIT
 */
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

import { GenerateCppCodeService } from './generate-code-service';

export const CODEGEN_COMMAND: Command = {
    id: 'workflow.generate.cpp.code.command',
    label: 'Generate C++ Workflow code'
};

@injectable()
export class CppGenerationCommandContribution implements CommandContribution, MenuContribution {

    constructor(
        @inject(SelectionService) protected readonly selectionService: SelectionService,
        @inject(CommandService) protected readonly commandService: CommandService,
        @inject(GenerateCppCodeService) protected readonly generateCodeService: GenerateCppCodeService
    ) { }

    registerMenus(menus: MenuModelRegistry): void {
        menus.registerMenuAction([...['navigator-context-menu'], '0_addition'], {
            commandId: CODEGEN_COMMAND.id
        });
    }

    registerCommands(registry: CommandRegistry): void {
        registry.registerCommand(CODEGEN_COMMAND, this.newUriAwareCommandHandler({
            execute: uri => this.generateCodeService.generateCode(uri),
            isVisible: uri => this.generateCodeService.isWorkflowFile(uri),
            isEnabled: uri => this.generateCodeService.isWorkflowFile(uri)
        }));
    }

    private newUriAwareCommandHandler(handler: UriCommandHandler<URI>): UriAwareCommandHandler<URI> {
        return new UriAwareCommandHandler(this.selectionService, handler);
    }

}
