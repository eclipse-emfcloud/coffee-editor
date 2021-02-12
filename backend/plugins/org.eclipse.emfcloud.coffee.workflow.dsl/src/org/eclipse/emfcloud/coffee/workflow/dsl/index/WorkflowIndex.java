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

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.eclipse.emfcloud.coffee.CoffeeFactory;
import org.eclipse.emfcloud.coffee.Machine;
import org.eclipse.emfcloud.coffee.Task;
import org.eclipse.emfcloud.coffee.Workflow;

import com.google.inject.Singleton;

@Singleton
public class WorkflowIndex implements IWorkflowIndex {	
	private static Map<URI, Machine> machines = new HashMap<>();
	
	@Override
	public void putGraph(String uri, Machine graph) {
		if(graph != null && uri != null) {
			try {
				machines.put(new URI(uri), graph);
			} catch (URISyntaxException e) {
			}
		}
	}
	
	@Override
	public void removeGraph(String uri) {
		try {
			machines.remove(new URI(uri));
		} catch (URISyntaxException e) {
		}
	}
	
	@Override
	public Collection<Machine> getMachines() {
		return Collections.unmodifiableCollection(machines.values());
	}
	
	@Override
	public Optional<Machine> getMachine(String graphId) {
		return machines.values().stream().filter(graph -> graph.getName().equals(graphId)).findAny();
	}
	@Override
	public Collection<Workflow> getWorkflows(String machine) {
		 return getMachine(machine).orElse(CoffeeFactory.eINSTANCE.createMachine()).getWorkflows();
	}
	
	@Override
	public Optional<Workflow> getWorkflow(String machine, String workflow) {
		return getWorkflows(machine).stream().filter(w -> w.getName().equals(workflow)).findAny();
	}
		
	@Override
	public List<Task> getTasks(String machine, String workflow) {
		return getWorkflow(machine, workflow).map(w -> w.getNodes().stream().filter(Task.class::isInstance).map(Task.class::cast).collect(Collectors.toList())).orElse(Collections.emptyList());
	}
	
	@Override
	public Optional<Task> getTask(String machine, String workflow, String taskName) {
		return getTasks(machine, workflow).stream().filter(task -> task.getName().equals(taskName)).findFirst();
	}
}
