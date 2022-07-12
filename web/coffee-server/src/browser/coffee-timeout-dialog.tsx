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
import { Message } from '@theia/core/lib/browser//widgets/widget';
import { DialogProps } from '@theia/core/lib/browser/dialogs';
import { ReactDialog } from '@theia/core/lib/browser/dialogs/react-dialog';
import { inject, injectable, postConstruct } from 'inversify';
import * as React from 'react';

@injectable()
export class TimeoutDialogProps extends DialogProps {}

@injectable()
export class TimeoutDialog extends ReactDialog<void> {
    protected readonly okButton: HTMLButtonElement;

    constructor(@inject(TimeoutDialogProps) protected readonly props: TimeoutDialogProps) {
        super(props);
        this.appendAcceptButton('Ok');
    }

    @postConstruct()
    protected async init(): Promise<void> {
        this.update();
    }

    protected render(): React.ReactNode {
        return (
            <div>
                <h1>
                    Your session has <b>Timed Out</b>!
                </h1>
                <div>
                    Connection to the server has been lost.
                    <br />
                    You probably have reached the online demo usage timeout (30 minutes).
                    <p>
                        Please go to the <a href='https://eclipsesource.com/coffee-editor'>starting page</a> to start a new session.
                    </p>
                </div>
            </div>
        );
    }

    protected onAfterAttach(msg: Message): void {
        super.onAfterAttach(msg);
        this.update();
    }

    get value(): undefined {
        return undefined;
    }
}
