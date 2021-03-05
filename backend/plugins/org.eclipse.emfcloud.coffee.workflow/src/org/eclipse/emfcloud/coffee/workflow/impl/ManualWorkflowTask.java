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

import org.eclipse.emfcloud.coffee.workflow.IManualWorkflowTask;

public class ManualWorkflowTask extends AbstractWorkflowTask implements IManualWorkflowTask {

	private String actor;

	public ManualWorkflowTask(String id, String name, int duration, String actor) {
		super(id, name, duration);
		this.actor = actor;
	}

	@Override
	public String getActor() {
		return actor;
	}

	@Override
	public boolean isManual() {
		return true;
	}

}
