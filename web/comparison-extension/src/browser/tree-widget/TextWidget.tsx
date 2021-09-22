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
import { injectable, postConstruct } from 'inversify';
import { ReactWidget } from '@theia/core/lib/browser';

@injectable()
export class TextWidget extends ReactWidget {
    protected text: string;
    
    constructor(text: string) {
        super();
        this.id = 'text-test-widget';
        this.addClass('small-widget-view');
        this.title.closable = true;
        this.text = text;
    }

    protected render(): React.ReactNode {
        return <h3>{this.text}</h3>;
    }

    @postConstruct()
    protected init(): void {
        this.update();
    }
}
