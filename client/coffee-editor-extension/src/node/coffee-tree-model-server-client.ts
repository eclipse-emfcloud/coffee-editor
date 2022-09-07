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
import {
    AddCommand,
    CompoundCommand,
    Format,
    ModelServerCommand,
    ModelUpdateResult,
    PatchOrCommand,
    RemoveCommand,
    SetCommand
} from '@eclipse-emfcloud/modelserver-client';
import { TheiaBackendModelServerClientV2 } from '@eclipse-emfcloud/modelserver-theia/lib/node';
import { injectable } from 'inversify';

@injectable()
export class DefaultCoffeeModelServerClient extends TheiaBackendModelServerClientV2 {
    override async edit(modeluri: string, patchOrCommand: PatchOrCommand, format?: Format): Promise<ModelUpdateResult> {
        if (ModelServerCommand.is(patchOrCommand)) {
            return super.edit(modeluri, this.ensureCommandPrototype(patchOrCommand), format);
        }
        return super.edit(modeluri, patchOrCommand, format);
    }

    protected ensureCommandPrototype<T extends ModelServerCommand>(command: T): T {
        if (SetCommand.is(command)) {
            Object.setPrototypeOf(command, SetCommand.prototype);
        } else if (AddCommand.is(command)) {
            Object.setPrototypeOf(command, AddCommand.prototype);
        } else if (RemoveCommand.is(command)) {
            Object.setPrototypeOf(command, RemoveCommand.prototype);
        } else if (CompoundCommand.is(command)) {
            Object.setPrototypeOf(command, CompoundCommand.prototype);
            (command as CompoundCommand).commands.forEach(this.ensureCommandPrototype);
        }
        return Object.setPrototypeOf(command, ModelServerCommand.prototype);
    }
}
