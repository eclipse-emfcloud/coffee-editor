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
package org.eclipse.emfcloud.coffee.workflow.glsp.server.handler.operation;


import org.eclipse.emfcloud.coffee.CoffeePackage;
import org.eclipse.emfcloud.coffee.workflow.glsp.server.util.ModelTypes;

public class CreateAutomatedTaskHandler extends AbstractCreateTaskHandler {

	public CreateAutomatedTaskHandler() {
		super(ModelTypes.AUTOMATED_TASK, CoffeePackage.Literals.AUTOMATIC_TASK);
	}

	@Override
	public String getLabel() {
		return "Automated Task";
	}

}
