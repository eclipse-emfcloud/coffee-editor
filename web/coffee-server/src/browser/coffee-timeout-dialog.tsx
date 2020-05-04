/*!
 * Copyright (C) 2020 EclipseSource and others.
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
import { Message } from '@theia/core/lib/browser//widgets/widget';
import { DialogProps } from '@theia/core/lib/browser/dialogs';
import { ReactDialog } from '@theia/core/lib/browser/dialogs/react-dialog';
import { inject, injectable, postConstruct } from 'inversify';
import * as React from 'react';

@injectable()
export class TimeoutDialogProps extends DialogProps { }

@injectable()
export class TimeoutDialog extends ReactDialog<void> {
  protected readonly okButton: HTMLButtonElement;

  constructor(
    @inject(TimeoutDialogProps) protected readonly props: TimeoutDialogProps
  ) {
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
            Please go to the{' '}
            <a href='https://eclipsesource.com/coffee-editor'>
              starting page
            </a>{' '}
            to start a new session.
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
