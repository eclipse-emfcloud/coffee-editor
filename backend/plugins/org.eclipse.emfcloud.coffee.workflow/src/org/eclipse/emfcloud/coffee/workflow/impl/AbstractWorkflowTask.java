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

import org.eclipse.emfcloud.coffee.workflow.IWorkflowTask;

public abstract class AbstractWorkflowTask implements IWorkflowTask {

	private String id;
	private String name;
	private int duration;

	public AbstractWorkflowTask(String id, String name, int duration) {
		this.id = id;
		this.name = name;
		this.duration = duration;		
	}
	
	@Override
	public String getId() {
		return id;
	}
	
	@Override
	public String getName() {
		return name;
	}
	
	@Override
	public int getDuration() {
		return duration;
	}
}
