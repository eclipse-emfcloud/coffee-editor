/********************************************************************************
 * Copyright (c) 2021 EclipseSource and others.
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
 ********************************************************************************/
import * as React from 'react';
import { injectable } from 'inversify';
import { Message } from '@theia/core/lib/browser';
import { ReactDialog } from '@theia/core/lib/browser/dialogs/react-dialog';

export const DIALOG_TITLE = 'Choose Left, Right, Origin';
export const DIALOG_CLASS = 'comparisonDialog';
export const DIALOG_LABEL_CLASS = 'comparisonDialogInput';

@injectable()
export class ComparisonOrderDialog extends ReactDialog<void> {
    protected left: string;
    protected right: string;
    protected origin: string;
    protected showOrigin: boolean = true;

    constructor(left: string, right: string, origin: string = "") {
        super({title: DIALOG_TITLE});

        this.left = left;
        this.right = right;
        this.origin = origin;
        this.appendAcceptButton('Ok');

        if (this.origin === 'undefined' || this.origin.trim() === "") {
            this.showOrigin = false;
            this.origin = '';
        }
    }

    swapTop() {
        let tmp: string = this.left;
        this.left = this.right;
        this.right = tmp;
        this.update();
    }

    swapBottom() {
        let tmp: string = this.right;
        this.right = this.origin;
        this.origin = tmp;
        this.update();
    }

    protected render(): React.ReactNode {
        let originTags = <></>;
        if (this.showOrigin) {
            originTags = <>
                <button onClick={() => this.swapBottom()}>Swap</button><br/><br/>
                <b>Origin (common ancenstor):</b><br />
                <input type="text" value={this.origin} className={DIALOG_LABEL_CLASS} readOnly={true} dir="rtl"/>
            </>;
        }
        return <div className={DIALOG_CLASS}>
            <h3>Select which file is Left, Right and Origin</h3>
            <b>Left (new version):</b><br/>
            <input type="text" value={this.left} className={DIALOG_LABEL_CLASS} readOnly={true} dir="rtl"/><br/><br/>
            <button onClick={() => this.swapTop()}>Swap</button><br/><br/>
            <b>Right (old version):</b><br/>
            <input type="text" value={this.right} className={DIALOG_LABEL_CLASS} readOnly={true} dir="rtl"/><br/><br/>
            {originTags}
        </div>;
    }

    public getLeft(): string {
        return this.left;
    }

    public getRight(): string {
        return this.right;
    }

    public getOrigin(): string {
        return this.origin;
    }

    protected onAfterAttach(msg: Message): void {
        super.onAfterAttach(msg);
        this.update();
    }

    get value(): undefined { return undefined; }
}
