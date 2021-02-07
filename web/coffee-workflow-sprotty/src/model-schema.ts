/********************************************************************************
 * Copyright (c) 2019-2021 EclipseSource and others.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0, or the MIT License which is
 * available at https://opensource.org/licenses/MIT.
 *
 * SPDX-License-Identifier: EPL-2.0 OR MIT
 ********************************************************************************/
import { SEdgeSchema, SNodeSchema } from '@eclipse-glsp/client';

export namespace ActivityNodeSchema {
    export namespace Type {
        export const INITIAL = "initalNode";
        export const FINAL = "finalNode";
        export const DECISION = "decisionNode";
        export const MERGE = "mergeNode";
        export const JOIN = "joinNode";
        export const FORK = "forkNode";
        export const UNDEFINED = "undefined";
    }
}

export interface TaskNodeSchema extends SNodeSchema {
    name?: string;
    duration?: number;
    taskType?: string;
    reference?: string;
}

export interface WeightedEdgeSchema extends SEdgeSchema {
    probability?: string;
}

export interface ActivityNodeSchema extends SNodeSchema {
    nodeType: string;
}
