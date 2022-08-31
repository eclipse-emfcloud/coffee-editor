/*
 * Copyright (c) 2020 EclipseSource and others.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0, or the MIT License which is
 * available at https://opensource.org/licenses/MIT.
 *
 * SPDX-License-Identifier: EPL-2.0 OR MIT
 */
import { ConnectionStatus, ConnectionStatusService } from '@theia/core/lib/browser/connection-status-service';
import { DefaultFrontendApplicationContribution, FrontendApplicationContribution } from '@theia/core/lib/browser/frontend-application';
import { ContainerModule, inject, injectable } from 'inversify';

import { TimeoutDialog, TimeoutDialogProps } from './coffee-timeout-dialog';

export default new ContainerModule(bind => {
    bind(TimeoutDialogProps).toConstantValue({ title: 'Coffee Editor Usage Session Timeout' });
    bind(TimeoutDialog).toSelf().inSingletonScope();
    bind(BackendWatcher).toSelf().inSingletonScope();
    bind(FrontendApplicationContribution).toService(BackendWatcher);
});

@injectable()
class BackendWatcher extends DefaultFrontendApplicationContribution {
    constructor(
        @inject(ConnectionStatusService) private readonly connectionStatusService: ConnectionStatusService,
        @inject(TimeoutDialog) private readonly timeoutDialog: TimeoutDialog
    ) {
        super();
        this.connectionStatusService.onStatusChange(state => this.onStateChange(state));
    }

    protected onStateChange(state: ConnectionStatus): void {
        if (state === ConnectionStatus.OFFLINE) {
            this.timeoutDialog.open();
        }
    }
}
