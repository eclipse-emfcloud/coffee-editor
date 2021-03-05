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
package org.eclipse.emfcloud.coffee.workflow.impl;

import org.eclipse.emfcloud.coffee.workflow.IAutomaticWorkflowTask;

public class AutomaticWorkflowTask extends AbstractWorkflowTask implements IAutomaticWorkflowTask {

	private String component;

	public AutomaticWorkflowTask(String id, String name, int duration, String component) {
		super(id, name, duration);
		this.component = component;
	}

	@Override
	public String getComponent() {
		return component;
	}

	@Override
	public boolean isManual() {
		return false;
	}

}
