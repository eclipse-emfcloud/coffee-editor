/********************************************************************************
 * Copyright (c) 2020-2021 EclipseSource and others.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0, or the MIT License which is
 * available at https://opensource.org/licenses/MIT.
 *
 * SPDX-License-Identifier: EPL-2.0 OR MIT
 ********************************************************************************/
import { ContainerModule } from 'inversify';
import { TYPES } from 'sprotty';

import { TaskEditor } from './direct-task-editor';

export const directTaskEditor = new ContainerModule(
    (bind, _unbind, isBound) => {
        bind(TaskEditor)
            .toSelf()
            .inSingletonScope();
        bind(TYPES.IUIExtension).toService(TaskEditor);
    }
);
