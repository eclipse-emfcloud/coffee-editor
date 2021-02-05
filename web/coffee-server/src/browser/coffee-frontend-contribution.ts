/********************************************************************************
 * Copyright (c) 2021 EclipseSource and others.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0, or the MIT License which is
 * available at https://opensource.org/licenses/MIT.
 *
 * SPDX-License-Identifier: EPL-2.0 OR MIT
 ********************************************************************************/
import { ModelServerFrontendContribution } from '@eclipse-emfcloud/modelserver-theia/lib/browser';
import { injectable } from 'inversify';

@injectable()
export class CoffeeModelServerFrontendContribution extends ModelServerFrontendContribution {

    // #FIXME: if theia is updated to at least 1.5.0 this contribution can be removed again
    async setup(): Promise<void> {
        this.workspaceService.onWorkspaceChanged(workspace => {
            if (workspace[0] && workspace[0].uri) {
                const workspaceRoot = workspace[0].uri.toString();
                this.modelServerClient.configure({ workspaceRoot });
            }
        });
    }
}
