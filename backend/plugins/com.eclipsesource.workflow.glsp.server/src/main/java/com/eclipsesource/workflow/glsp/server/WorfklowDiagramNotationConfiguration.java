/*******************************************************************************
 * Copyright (c) 2019 EclipseSource and others.
 *  
 *   This program and the accompanying materials are made available under the
 *   terms of the Eclipse Public License v. 2.0 which is available at
 *   http://www.eclipse.org/legal/epl-2.0.
 *  
 *   This Source Code may also be made available under the following Secondary
 *   Licenses when the conditions for such availability set forth in the Eclipse
 *   Public License v. 2.0 are satisfied: GNU General Public License, version 2
 *   with the GNU Classpath Exception which is available at
 *   https://www.gnu.org/software/classpath/license.html.
 *  
 *   SPDX-License-Identifier: EPL-2.0 OR GPL-2.0 WITH Classpath-exception-2.0
 ******************************************************************************/
package com.eclipsesource.workflow.glsp.server;

import static com.eclipsesource.workflow.glsp.server.util.ModelTypes.AUTOMATED_TASK;
import static com.eclipsesource.workflow.glsp.server.util.ModelTypes.COMP_HEADER;
import static com.eclipsesource.workflow.glsp.server.util.ModelTypes.DECISION_NODE;
import static com.eclipsesource.workflow.glsp.server.util.ModelTypes.ICON;
import static com.eclipsesource.workflow.glsp.server.util.ModelTypes.LABEL_HEADING;
import static com.eclipsesource.workflow.glsp.server.util.ModelTypes.LABEL_ICON;
import static com.eclipsesource.workflow.glsp.server.util.ModelTypes.LABEL_TEXT;
import static com.eclipsesource.workflow.glsp.server.util.ModelTypes.MANUAL_TASK;
import static com.eclipsesource.workflow.glsp.server.util.ModelTypes.MERGE_NODE;
import static com.eclipsesource.workflow.glsp.server.util.ModelTypes.WEIGHTED_EDGE;
import static org.eclipse.glsp.graph.DefaultTypes.EDGE;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.glsp.api.diagram.DiagramConfiguration;
import org.eclipse.glsp.api.types.EdgeTypeHint;
import org.eclipse.glsp.api.types.ShapeTypeHint;
import org.eclipse.glsp.graph.DefaultTypes;
import org.eclipse.glsp.graph.GraphPackage;

import com.eclipsesource.workflow.glsp.server.wfgraph.WfgraphPackage;

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
		mappings.put(MERGE_NODE, WfgraphPackage.Literals.ACTIVITY_NODE);
		mappings.put(DECISION_NODE, WfgraphPackage.Literals.ACTIVITY_NODE);
		mappings.put(MANUAL_TASK, WfgraphPackage.Literals.TASK_NODE);
		mappings.put(AUTOMATED_TASK, WfgraphPackage.Literals.TASK_NODE);
		return mappings;
	}

	@Override
	public List<ShapeTypeHint> getNodeTypeHints() {
		List<ShapeTypeHint> nodeHints = new ArrayList<>();
		nodeHints.add(new ShapeTypeHint(MANUAL_TASK, true, true, false, false));
		nodeHints.add(new ShapeTypeHint(AUTOMATED_TASK, true, true, false, false));
		nodeHints.add(createDefaultNodeTypeHint(DECISION_NODE));
		nodeHints.add(createDefaultNodeTypeHint(MERGE_NODE));
		return nodeHints;
	}

	@Override
	public List<EdgeTypeHint> getEdgeTypeHints() {
		List<EdgeTypeHint> edgeHints = new ArrayList<>();
		edgeHints.add(createDefaultEdgeTypeHint(EDGE));
		EdgeTypeHint weightedEdgeHint = DiagramConfiguration.super.createDefaultEdgeTypeHint(WEIGHTED_EDGE);
		weightedEdgeHint.setSourceElementTypeIds(Arrays.asList(DECISION_NODE));
		weightedEdgeHint.setTargetElementTypeIds(Arrays.asList(MANUAL_TASK, AUTOMATED_TASK));
		edgeHints.add(weightedEdgeHint);
		return edgeHints;
	}

	@Override
	public EdgeTypeHint createDefaultEdgeTypeHint(final String elementId) {
		EdgeTypeHint hint = DiagramConfiguration.super.createDefaultEdgeTypeHint(elementId);
		hint.setSourceElementTypeIds(Arrays.asList(MANUAL_TASK, AUTOMATED_TASK, DECISION_NODE, MERGE_NODE));
		hint.setTargetElementTypeIds(Arrays.asList(MANUAL_TASK, AUTOMATED_TASK, DECISION_NODE, MERGE_NODE));
		return hint;
	}
}
