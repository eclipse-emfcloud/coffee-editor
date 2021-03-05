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
package org.eclipse.emfcloud.coffee.workflow.dsl.index;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.eclipse.emfcloud.coffee.Machine;
import org.eclipse.emfcloud.coffee.Task;
import org.eclipse.emfcloud.coffee.Workflow;



public interface IWorkflowIndex {
	void putGraph(String uri, Machine graph);

	void removeGraph(String uri);

	Collection<Machine> getMachines();
	
	Optional<Machine> getMachine(String machine);
	
	Collection<Workflow> getWorkflows(String machine);
	
	Optional<Workflow> getWorkflow(String machine, String workflow);
	
	List<Task> getTasks(String machine, String workflow);
	
	Optional<Task> getTask(String machine, String workflow, String task);
}
