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
package com.eclipsesource.workflow;

import java.util.List;

import com.eclipsesource.workflow.impl.WorkflowGraph;

public interface IWorkflowGraph {
	IWorkflowGraph NULL_GRAPH = new WorkflowGraph("null");
	
	String getId();
	
	List<IWorkflowTask> getTasks();
	
	boolean addTask(IWorkflowTask task);
	
	boolean removeTask(IWorkflowTask task);
}
