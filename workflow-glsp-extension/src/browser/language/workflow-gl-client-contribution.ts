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
import { injectable } from "inversify";
import { BaseGraphicalLanguageClientContribution } from "glsp-theia-extension/lib/browser"
import { WorkflowLanguage } from "../../common/workflow-language";
@injectable()
export class WorkflowGLClientContribution extends BaseGraphicalLanguageClientContribution {
    readonly id = WorkflowLanguage.Id
    readonly name = WorkflowLanguage.Name
}