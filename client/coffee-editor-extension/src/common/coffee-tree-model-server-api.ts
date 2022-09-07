/*
 * Copyright (c) 2022 EclipseSource and others.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0, or the MIT License which is
 * available at https://opensource.org/licenses/MIT.
 *
 * SPDX-License-Identifier: EPL-2.0 OR MIT
 */
import { ModelUpdateResult, PatchOrCommand } from '@eclipse-emfcloud/modelserver-client';
import { TheiaModelServerClientV2 } from '@eclipse-emfcloud/modelserver-theia/lib/common';

export const CoffeeModelServerClient = Symbol('CoffeeModelServerClient');
export interface CoffeeModelServerClient extends TheiaModelServerClientV2 {
    myedit(modeluri: string, patchOrCommand: PatchOrCommand): Promise<ModelUpdateResult>;
}
