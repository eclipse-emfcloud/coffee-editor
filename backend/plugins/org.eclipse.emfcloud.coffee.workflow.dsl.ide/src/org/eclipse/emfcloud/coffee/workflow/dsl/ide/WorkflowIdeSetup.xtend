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

import org.eclipse.emfcloud.coffee.workflow.dsl.WorkflowRuntimeModule
import org.eclipse.emfcloud.coffee.workflow.dsl.WorkflowStandaloneSetup
import com.google.inject.Guice
import org.eclipse.xtext.util.Modules2

/**
 * Initialization support for running Xtext languages as language servers.
 */
class WorkflowIdeSetup extends WorkflowStandaloneSetup {

	override createInjector() {
		Guice.createInjector(Modules2.mixin(new WorkflowRuntimeModule, new WorkflowIdeModule))
	}
	
}
