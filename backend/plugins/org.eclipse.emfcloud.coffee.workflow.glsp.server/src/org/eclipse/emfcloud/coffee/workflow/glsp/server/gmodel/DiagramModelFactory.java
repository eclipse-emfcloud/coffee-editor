package org.eclipse.emfcloud.coffee.workflow.glsp.server.gmodel;

import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.emfcloud.coffee.Workflow;
import org.eclipse.emfcloud.coffee.workflow.glsp.server.model.WorkflowModelState;
import org.eclipse.emfcloud.modelserver.glsp.notation.Diagram;
import org.eclipse.glsp.graph.GGraph;
import org.eclipse.glsp.graph.GModelElement;

public class DiagramModelFactory extends GModelFactory {

	public DiagramModelFactory(final WorkflowModelState modelState) {
		super(modelState);
	}

	@Override
	public GGraph create(final Diagram notationDiagram) {
		GGraph graph = getOrCreateRoot();

		if (notationDiagram.getSemanticElement().getResolvedElement() != null) {
			Workflow workflowModel = (Workflow) notationDiagram.getSemanticElement().getResolvedElement();

			graph.setId(toId(workflowModel));

			// Add Nodes
			List<GModelElement> nodeElements = workflowModel.getNodes().stream().map(node -> nodeFactory.create(node))
					.collect(Collectors.toList());
			graph.getChildren().addAll(nodeElements);

			// Add Flows
			List<GModelElement> flowElements = workflowModel.getFlows().stream().map(flow -> flowFactory.create(flow))
					.collect(Collectors.toList());
			graph.getChildren().addAll(flowElements);

		}
		return graph;

	}

}