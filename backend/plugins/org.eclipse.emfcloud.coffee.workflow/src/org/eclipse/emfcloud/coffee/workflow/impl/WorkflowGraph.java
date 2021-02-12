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

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emfcloud.coffee.workflow.IWorkflowGraph;
import org.eclipse.emfcloud.coffee.workflow.IWorkflowTask;

public class WorkflowGraph implements IWorkflowGraph {
	public static WorkflowGraph NULL_GRAPH = new WorkflowGraph("null");	
	
	private String id;
	private List<IWorkflowTask> tasks = new ArrayList<>();

	public WorkflowGraph(String id) {
		this.id = id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	@Override
	public String getId() {
		return id;
	}

	@Override
	public List<IWorkflowTask> getTasks() {
		return tasks;
	}

	@Override
	public boolean addTask(IWorkflowTask task) {
		return tasks.add(task);
	}

	@Override
	public boolean removeTask(IWorkflowTask task) {
		return tasks.remove(task);
	}

}
