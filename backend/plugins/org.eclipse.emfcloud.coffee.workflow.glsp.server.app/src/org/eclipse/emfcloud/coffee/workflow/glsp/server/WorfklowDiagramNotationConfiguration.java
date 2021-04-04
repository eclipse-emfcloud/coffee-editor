/*******************************************************************************
 * Copyright (c) 2019-2021 EclipseSource and others.
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0, or the MIT License which is
 * available at https://opensource.org/licenses/MIT.
 * 
 * SPDX-License-Identifier: EPL-2.0 OR MIT
 ******************************************************************************/
package org.eclipse.emfcloud.coffee.workflow.glsp.server;

import static org.eclipse.emfcloud.coffee.workflow.glsp.server.util.ModelTypes.ALL_NODES;
import static org.eclipse.emfcloud.coffee.workflow.glsp.server.util.ModelTypes.COMP_HEADER;
import static org.eclipse.emfcloud.coffee.workflow.glsp.server.util.ModelTypes.CONTROL_NODES;
import static org.eclipse.emfcloud.coffee.workflow.glsp.server.util.ModelTypes.DECISION_NODE;
import static org.eclipse.emfcloud.coffee.workflow.glsp.server.util.ModelTypes.ICON;
import static org.eclipse.emfcloud.coffee.workflow.glsp.server.util.ModelTypes.LABEL_HEADING;
import static org.eclipse.emfcloud.coffee.workflow.glsp.server.util.ModelTypes.LABEL_ICON;
import static org.eclipse.emfcloud.coffee.workflow.glsp.server.util.ModelTypes.LABEL_TEXT;
import static org.eclipse.emfcloud.coffee.workflow.glsp.server.util.ModelTypes.TASKS;
import static org.eclipse.emfcloud.coffee.workflow.glsp.server.util.ModelTypes.WEIGHTED_EDGE;
import static org.eclipse.glsp.graph.DefaultTypes.EDGE;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emfcloud.coffee.workflow.glsp.server.wfgraph.WfgraphPackage;
import org.eclipse.glsp.graph.DefaultTypes;
import org.eclipse.glsp.graph.GraphPackage;
import org.eclipse.glsp.server.diagram.DiagramConfiguration;
import org.eclipse.glsp.server.diagram.EdgeTypeHint;
import org.eclipse.glsp.server.diagram.ShapeTypeHint;

public class WorfklowDiagramNotationConfiguration implements DiagramConfiguration {

	@Override
	public String getDiagramType() {
		return "workflow-diagram-notation";
	}

	@Override
	public Map<String, EClass> getTypeMappings() {
		Map<String, EClass> mappings = DefaultTypes.getDefaultTypeMappings();
		mappings.put(LABEL_HEADING, GraphPackage.Literals.GLABEL);
		mappings.put(LABEL_TEXT, GraphPackage.Literals.GLABEL);
		mappings.put(COMP_HEADER, GraphPackage.Literals.GCOMPARTMENT);
		mappings.put(LABEL_ICON, GraphPackage.Literals.GLABEL);
		mappings.put(WEIGHTED_EDGE, GraphPackage.Literals.GEDGE);
		mappings.put(ICON, WfgraphPackage.Literals.ICON);
		CONTROL_NODES.forEach(type -> mappings.put(type, WfgraphPackage.Literals.ACTIVITY_NODE));
		TASKS.forEach(type -> mappings.put(type, WfgraphPackage.Literals.TASK_NODE));
		return mappings;
	}

	@Override
	public List<ShapeTypeHint> getNodeTypeHints() {
		List<ShapeTypeHint> nodeHints = new ArrayList<>();
		TASKS.stream().map(this::createDefaultShapeTypeHint).forEach(nodeHints::add);
		CONTROL_NODES.stream().map(this::createDefaultNodeTypeHint).forEach(nodeHints::add);
		return nodeHints;
	}

	@Override
	public List<EdgeTypeHint> getEdgeTypeHints() {
		List<EdgeTypeHint> edgeHints = new ArrayList<>();
		edgeHints.add(createDefaultEdgeTypeHint(EDGE));
		EdgeTypeHint weightedEdgeHint = DiagramConfiguration.super.createDefaultEdgeTypeHint(WEIGHTED_EDGE);
		weightedEdgeHint.setSourceElementTypeIds(List.of(DECISION_NODE));
		weightedEdgeHint.setTargetElementTypeIds(List.copyOf(TASKS));
		edgeHints.add(weightedEdgeHint);
		return edgeHints;
	}

	@Override
	public EdgeTypeHint createDefaultEdgeTypeHint(final String elementId) {
		EdgeTypeHint hint = DiagramConfiguration.super.createDefaultEdgeTypeHint(elementId);
		hint.setSourceElementTypeIds(List.copyOf(ALL_NODES));
		hint.setTargetElementTypeIds(List.copyOf(ALL_NODES));
		return hint;
	}
	
	ShapeTypeHint createDefaultShapeTypeHint(String shapeType) {
		return new ShapeTypeHint(shapeType, true, true, false, false);
	}
	
}
