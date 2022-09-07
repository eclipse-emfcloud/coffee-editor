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
import { TheiaModelServerClientV2 } from '@eclipse-emfcloud/modelserver-theia';
import { ContainerModule } from '@theia/core/shared/inversify';
import { CoffeeModelServerClient } from '../common/coffee-tree-model-server-api';

import { DefaultCoffeeModelServerClient } from './coffee-tree-model-server-client';

export default new ContainerModule((bind, _unbind, isBound, rebind) => {
    bind(CoffeeModelServerClient).to(DefaultCoffeeModelServerClient).inSingletonScope();
    if (isBound(TheiaModelServerClientV2)) {
        rebind(TheiaModelServerClientV2).toService(CoffeeModelServerClient);
    } else {
        bind(TheiaModelServerClientV2).to(DefaultCoffeeModelServerClient).inSingletonScope();
    }
});
