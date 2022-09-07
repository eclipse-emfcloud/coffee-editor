/*
 * Copyright (c) 2019-2022 EclipseSource and others.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0, or the MIT License which is
 * available at https://opensource.org/licenses/MIT.
 *
 * SPDX-License-Identifier: EPL-2.0 OR MIT
 */
import { ModelServerCommand } from '@eclipse-emfcloud/modelserver-client';

export namespace CommandUtil {
    export const SEMANTIC_ELEMENT_ID = 'semanticElementId';
    export const POSITION_X = 'positionX';
    export const POSITION_Y = 'positionY';
}

export namespace AddAutomatedTaskCommandContribution {
    export const TYPE = 'addAutomatedTaskContribution';

    export function create(): ModelServerCommand {
        const addCommand = new ModelServerCommand(TYPE);
        addCommand.setProperty(CommandUtil.POSITION_X, '0.0');
        addCommand.setProperty(CommandUtil.POSITION_Y, '0.0');
        return addCommand;
    }
}

export namespace AddManualTaskCommandContribution {
    export const TYPE = 'addManualTaskContribution';

    export function create(): ModelServerCommand {
        const addCommand = new ModelServerCommand(TYPE);
        addCommand.setProperty(CommandUtil.POSITION_X, '0.0');
        addCommand.setProperty(CommandUtil.POSITION_Y, '0.0');
        return addCommand;
    }
}

export namespace AddDecisionNodeCommandContribution {
    export const TYPE = 'addDecisionNodeContribution';

    export function create(): ModelServerCommand {
        const addCommand = new ModelServerCommand(TYPE);
        addCommand.setProperty(CommandUtil.POSITION_X, '0.0');
        addCommand.setProperty(CommandUtil.POSITION_Y, '0.0');
        return addCommand;
    }
}

export namespace AddMergeNodeCommandContribution {
    export const TYPE = 'addMergeNodeContribution';

    export function create(): ModelServerCommand {
        const addCommand = new ModelServerCommand(TYPE);
        addCommand.setProperty(CommandUtil.POSITION_X, '0.0');
        addCommand.setProperty(CommandUtil.POSITION_Y, '0.0');
        return addCommand;
    }
}

export namespace RemoveNodeCommandContribution {
    export const TYPE = 'removeNode';

    export function create(semanticElementId: string): ModelServerCommand {
        const removeCommand = new ModelServerCommand(TYPE);
        removeCommand.setProperty(CommandUtil.SEMANTIC_ELEMENT_ID, semanticElementId);
        return removeCommand;
    }
}

export namespace RemoveFlowCommandContribution {
    export const TYPE = 'removeFlow';

    export function create(semanticElementId: string): ModelServerCommand {
        const removeCommand = new ModelServerCommand(TYPE);
        removeCommand.setProperty(CommandUtil.SEMANTIC_ELEMENT_ID, semanticElementId);
        return removeCommand;
    }
}
