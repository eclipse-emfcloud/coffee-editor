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
package org.eclipse.emfcloud.coffee.workflow.dsl.ide

import org.eclipse.emfcloud.coffee.workflow.dsl.ide.contentassist.WorkflowIdeContentProposalProvider
import org.eclipse.xtext.ide.editor.contentassist.IdeContentProposalProvider

/**
 * Use this class to register ide components.
 */
class WorkflowIdeModule extends AbstractWorkflowIdeModule {
	
	def Class<? extends IdeContentProposalProvider> bindIdeContentProposalProvider() {
		WorkflowIdeContentProposalProvider
	}
}
