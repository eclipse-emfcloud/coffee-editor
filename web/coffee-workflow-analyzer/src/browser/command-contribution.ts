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
import {
    Command,
    CommandContribution,
    CommandRegistry,
    MenuContribution,
    MenuModelRegistry,
    SelectionService,
} from '@theia/core/lib/common';
import URI from '@theia/core/lib/common/uri';
import { UriAwareCommandHandler, UriCommandHandler } from '@theia/core/lib/common/uri-command-handler';
import { EDITOR_CONTEXT_MENU } from '@theia/editor/lib/browser';
import { inject, injectable } from 'inversify';

import { AnalysisService } from './analysis-service';

export const ANALYZE_COMMAND: Command = {
    id: 'workflow.analyze.command',
    label: 'Analyze workflow model'
};

@injectable()
export class WorkflowCommandContribution implements CommandContribution, MenuContribution {

    constructor(
        @inject(SelectionService) private readonly selectionService: SelectionService,
        @inject(AnalysisService) private readonly analysisService: AnalysisService,
    ) { }

    registerMenus(menus: MenuModelRegistry): void {
        menus.registerMenuAction([...EDITOR_CONTEXT_MENU, '0_addition'], {
            commandId: ANALYZE_COMMAND.id,
            label: 'Perform Analysis'
        });
    }
    registerCommands(registry: CommandRegistry): void {
        registry.registerCommand(ANALYZE_COMMAND, this.newUriAwareCommandHandler({
            execute: (uri: URI) => this.analysisService.analyze(uri),
            isVisible: (uri: URI) => uri.toString().endsWith('wfconfig'),
            isEnabled: (uri: URI) => uri.toString().endsWith('wfconfig')
        }),
        );
    }

    protected newUriAwareCommandHandler(handler: UriCommandHandler<URI>): UriAwareCommandHandler<URI> {
        return new UriAwareCommandHandler(this.selectionService, handler);

    }
}
