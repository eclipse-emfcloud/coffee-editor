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
package com.eclipsesource.workflow.glsp.server;

import static org.eclipse.glsp.graph.DefaultTypes.EDGE;
import static org.eclipse.glsp.graph.util.GraphUtil.point;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.eclipse.glsp.api.model.GraphicalModelState;
import org.eclipse.glsp.api.operation.kind.CreateEdgeOperation;
import org.eclipse.glsp.api.operation.kind.CreateNodeOperation;
import org.eclipse.glsp.api.operation.kind.DeleteOperation;
import org.eclipse.glsp.api.provider.CommandPaletteActionProvider;
import org.eclipse.glsp.api.types.EditorContext;
import org.eclipse.glsp.api.types.LabeledAction;
import org.eclipse.glsp.graph.GModelElement;
import org.eclipse.glsp.graph.GModelIndex;
import org.eclipse.glsp.graph.GNode;
import org.eclipse.glsp.graph.GPoint;

import com.eclipsesource.workflow.glsp.server.util.ModelTypes;
import com.eclipsesource.workflow.glsp.server.wfgraph.TaskNode;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

public class WorkflowCommandPaletteActionProvider implements CommandPaletteActionProvider {

	@Override
	public List<LabeledAction> getActions(EditorContext editorContext, GraphicalModelState modelState) {
		List<LabeledAction> actions = Lists.newArrayList();

		GModelIndex index = modelState.getIndex();
		Set<GModelElement> selectedElements = index.getAll(editorContext.getSelectedElementIds());
		GPoint lastMousePosition = editorContext.getLastMousePosition().orElse(point(0, 0));
		// Create node actions are always possible
		actions.addAll(Sets.newHashSet(
				new LabeledAction("Create Automated Task",
						Lists.newArrayList(new CreateNodeOperation(ModelTypes.AUTOMATED_TASK, lastMousePosition)),
						"fa-plus-square"),
				new LabeledAction("Create Manual Task",
						Lists.newArrayList(
								new CreateNodeOperation(ModelTypes.MANUAL_TASK, lastMousePosition)), "fa-plus-square"),
				new LabeledAction("Create Merge Node",
						Lists.newArrayList(
								new CreateNodeOperation(ModelTypes.MERGE_NODE, lastMousePosition)), "fa-plus-square"),
				new LabeledAction("Create Decision Node", Lists.newArrayList(
						new CreateNodeOperation(ModelTypes.DECISION_NODE, lastMousePosition)), "fa-plus-square")));

		// Create edge actions between two nodes
		if (selectedElements.size() == 1) {
			GModelElement element = selectedElements.iterator().next();
			if (element instanceof GNode) {
				actions.addAll(createEdgeActions((GNode) element, index.getAllByClass(TaskNode.class)));
			}
		} else if (selectedElements.size() == 2) {
			Iterator<GModelElement> iterator = selectedElements.iterator();
			GModelElement firstElement = iterator.next();
			GModelElement secondElement = iterator.next();
			if (firstElement instanceof TaskNode && secondElement instanceof TaskNode) {
				GNode firstNode = (GNode) firstElement;
				GNode secondNode = (GNode) secondElement;
				actions.add(createEdgeAction("Connect with Edge", firstNode, secondNode));
				actions.add(createWeightedEdgeAction("Connect with Weighted Edge", firstNode, secondNode));
			}
		}

		// Delete action
		if (selectedElements.size() == 1) {
			actions.add(new LabeledAction("Delete",
					Lists.newArrayList(new DeleteOperation(editorContext.getSelectedElementIds())), "fa-minus-square"));
		} else if (selectedElements.size() > 1) {
			actions.add(new LabeledAction("Delete All",
					Lists.newArrayList(new DeleteOperation(editorContext.getSelectedElementIds())), "fa-minus-square"));
		}

		return actions;
	}

	private Set<LabeledAction> createEdgeActions(final GNode source, final Set<? extends GNode> targets) {
		Set<LabeledAction> actions = Sets.newLinkedHashSet();
		// add first all edge, then all weighted edge actions to keep a nice order
		targets.forEach(node -> actions.add(createEdgeAction("Create Edge to " + getLabel(node), source, node)));
		targets.forEach(node -> actions
				.add(createWeightedEdgeAction("Create Weighted Edge to " + getLabel(node), source, node)));
		return actions;
	}

	private LabeledAction createWeightedEdgeAction(final String label, final GNode source, final GNode node) {
		return new LabeledAction(label,
				Lists.newArrayList(new CreateEdgeOperation(ModelTypes.WEIGHTED_EDGE, source.getId(), node.getId())),
				"fa-plus-square");
	}

	private LabeledAction createEdgeAction(final String label, final GNode source, final GNode node) {
		return new LabeledAction(label, Lists.newArrayList(new CreateEdgeOperation(EDGE, source.getId(), node.getId())),
				"fa-plus-square");
	}

	private String getLabel(final GNode node) {
		if (node instanceof TaskNode) {
			return ((TaskNode) node).getName();
		}
		return node.getId();
	}
}
