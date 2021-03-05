/*******************************************************************************
 * Copyright (c) 2019-2020 EclipseSource and others.
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0, or the MIT License which is
 * available at https://opensource.org/licenses/MIT.
 * 
 * SPDX-License-Identifier: EPL-2.0 OR MIT
 ******************************************************************************/
package org.eclipse.emfcloud.coffee.workflow.dsl

import org.eclipse.emfcloud.coffee.workflow.dsl.index.IWorkflowIndex
import org.eclipse.emfcloud.coffee.workflow.dsl.index.WorkflowIndex

/**
 * Use this class to register components to be used at runtime / without the Equinox extension registry.
 */
class WorkflowRuntimeModule extends AbstractWorkflowRuntimeModule {
	def Class<? extends IWorkflowIndex> bindIWorkflowIndex() {
		return WorkflowIndex;
	}
}
