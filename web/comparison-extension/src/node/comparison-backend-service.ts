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
import { injectable, inject } from "inversify";
import { ComparisonServerExtensionConnection } from "./comparison-server-extension-connection";

@injectable()
export class ComparisonBackendServiceImpl {

    constructor(
        @inject(ComparisonServerExtensionConnection) private readonly serverConnection: ComparisonServerExtensionConnection) { }

    getNewComparison(left: string, right: string, origin: string, merges: string): Promise<string>{
        if (origin.trim() === "") origin = "undefined";
        return new Promise((resolve, reject) => {
            this.serverConnection.compare(left, right, origin, merges).then(response => {
                resolve(response);
            }).catch(err => reject(err));
        });
    }

    getHighlight(left: string, right: string, origin: string): Promise<string>{
        if (origin.trim() === "") origin = "undefined";
        return new Promise((resolve, reject) => {
            this.serverConnection.highlight(left, right, origin).then(response => {
                resolve(response);
            }).catch(err => reject(err));
        });
    }

    merge(left: string, right: string, origin: string, merges: string, mergeConflicts: string): Promise<string> {
        if (origin.trim() === "") origin = "undefined";
        return new Promise((resolve, reject) => {
            this.serverConnection.merge(left, right, origin, merges, mergeConflicts).then(response => {
                resolve(response);
            }).catch(err => reject(err));
        });
    }
}
