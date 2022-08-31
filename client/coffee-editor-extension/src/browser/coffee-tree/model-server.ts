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
import { ModelServerCommand, ModelServerCommandPackage, ModelServerObject } from '@eclipse-emfcloud/modelserver-client';

export const ID_PROP = '@id';

export class AddAutomatedTaskCommand extends ModelServerCommand {
    static readonly TYPE = 'addAutomatedTaskContribution';
    static override readonly URI = ModelServerCommandPackage.NS_URI + '#//CompoundCommand';
    override eClass = AddAutomatedTaskCommand.URI;

    constructor() {
        super(AddAutomatedTaskCommand.TYPE, { positionX: '0.0', positionY: '0.0' });
    }

    static override is(object?: any): object is ModelServerCommand {
        return (
            ModelServerObject.is(object) &&
            object.eClass === AddAutomatedTaskCommand.URI &&
            'type' in object &&
            typeof object['type'] === 'string' &&
            object['type'] === AddAutomatedTaskCommand.TYPE &&
            'commands' in object
        );
    }
}

export class AddManualTaskCommand extends ModelServerCommand {
    static readonly TYPE = 'addManualTaskContribution';
    static override readonly URI = ModelServerCommandPackage.NS_URI + '#//CompoundCommand';
    override eClass = AddManualTaskCommand.URI;

    constructor() {
        super(AddManualTaskCommand.TYPE, { positionX: '0.0', positionY: '0.0' });
    }

    static override is(object?: any): object is ModelServerCommand {
        return (
            ModelServerObject.is(object) &&
            object.eClass === AddManualTaskCommand.URI &&
            'type' in object &&
            typeof object['type'] === 'string' &&
            object['type'] === AddManualTaskCommand.TYPE &&
            'commands' in object
        );
    }
}

export class AddDecisionNodeCommand extends ModelServerCommand {
    static readonly TYPE = 'addDecisionNodeContribution';
    static override readonly URI = ModelServerCommandPackage.NS_URI + '#//CompoundCommand';
    override eClass = AddDecisionNodeCommand.URI;

    constructor() {
        super(AddDecisionNodeCommand.TYPE, { positionX: '0.0', positionY: '0.0' });
    }

    static override is(object?: any): object is ModelServerCommand {
        return (
            ModelServerObject.is(object) &&
            object.eClass === AddDecisionNodeCommand.URI &&
            'type' in object &&
            typeof object['type'] === 'string' &&
            object['type'] === AddDecisionNodeCommand.TYPE &&
            'commands' in object
        );
    }
}

export class AddMergeNodeCommand extends ModelServerCommand {
    static readonly TYPE = 'addMergeNodeContribution';
    static override readonly URI = ModelServerCommandPackage.NS_URI + '#//CompoundCommand';
    override eClass = AddMergeNodeCommand.URI;

    constructor() {
        super(AddMergeNodeCommand.TYPE, { positionX: '0.0', positionY: '0.0' });
    }

    static override is(object?: any): object is ModelServerCommand {
        return (
            ModelServerObject.is(object) &&
            object.eClass === AddMergeNodeCommand.URI &&
            'type' in object &&
            typeof object['type'] === 'string' &&
            object['type'] === AddMergeNodeCommand.TYPE &&
            'commands' in object
        );
    }
}

export class AddFlowCommand extends ModelServerCommand {
    static readonly TYPE = 'addFlowContribution';
    static override readonly URI = ModelServerCommandPackage.NS_URI + '#//CompoundCommand';
    override eClass = AddFlowCommand.URI;

    constructor() {
        super(AddFlowCommand.TYPE);
    }

    static override is(object?: any): object is ModelServerCommand {
        return (
            ModelServerObject.is(object) &&
            object.eClass === AddFlowCommand.URI &&
            'type' in object &&
            typeof object['type'] === 'string' &&
            object['type'] === AddFlowCommand.TYPE &&
            'commands' in object
        );
    }
}

export class AddWeightedFlowCommand extends ModelServerCommand {
    static readonly TYPE = 'addWeightedFlowContribution';
    static override readonly URI = ModelServerCommandPackage.NS_URI + '#//CompoundCommand';
    override eClass = AddWeightedFlowCommand.URI;

    constructor() {
        super(AddWeightedFlowCommand.TYPE);
    }

    static override is(object?: any): object is ModelServerCommand {
        return (
            ModelServerObject.is(object) &&
            object.eClass === AddWeightedFlowCommand.URI &&
            'type' in object &&
            typeof object['type'] === 'string' &&
            object['type'] === AddWeightedFlowCommand.TYPE &&
            'commands' in object
        );
    }
}

export class SetTaskNameCommand extends ModelServerCommand {
    static readonly TYPE = 'setTaskName';
    static override readonly URI = ModelServerCommandPackage.NS_URI + '#//CompoundCommand';
    override eClass = SetTaskNameCommand.URI;

    constructor(taskId: string, newName: string) {
        super(SetTaskNameCommand.TYPE, { semanticUriFragment: taskId, newName });
    }

    static override is(object?: any): object is ModelServerCommand {
        return (
            ModelServerObject.is(object) &&
            object.eClass === SetTaskNameCommand.URI &&
            'type' in object &&
            typeof object['type'] === 'string' &&
            object['type'] === SetTaskNameCommand.TYPE &&
            'commands' in object
        );
    }
}
