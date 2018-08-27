/*******************************************************************************
 * Copyright (c) 2018 Tobias Ortmayr.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *   
 * Contributors:
 * 	Tobias Ortmayr - initial API and implementation
 ******************************************************************************/
import { SNodeSchema, SEdgeSchema } from "sprotty/lib";

export namespace ActivityNodeSchema {
    export namespace Type {
        export const INITIAL = 'initalNode'
        export const FINAL = 'finalNode'
        export const DECISION = 'decisionNode'
        export const MERGE = 'mergeNode'
        export const JOIN = 'joinNode'
        export const FORK = 'forkNode'
        export const UNDEFINED = "undefined"
    }
}
export interface TaskNodeSchema extends SNodeSchema {
    name?: string
    duration?: number
    taskType?: string
    reference?: string
}

export interface WeightedEdgeSchema extends SEdgeSchema {
    probability?: string
}

export interface ActivityNodeSchema extends SNodeSchema {
    nodeType: string
}
