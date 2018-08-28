package com.eclipsesource.workflow.architecture.internal.commands;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.papyrus.infra.gmfdiag.common.helper.DiagramPrototype;
import org.eclipse.papyrus.uml.diagram.activity.CreateActivityDiagramCommand;

/**
 * Specific implementation for the creation of the diagram, to avoid setting a
 * name using a popup
 */
public class CreateHeadlessWorkflowDiagramCommand extends CreateActivityDiagramCommand {

	/** Implementation ID for the viewpoint's diagram prototype definition. */
	private static final String IMPLEMENTATION_ID = "WorkflowDiagram"; //$NON-NLS-1$

	/**
	 * Initializes me.
	 */
	public CreateHeadlessWorkflowDiagramCommand() {
		super();
	}

	@Override
	public String getCreatedDiagramType() {
		return IMPLEMENTATION_ID;
	}

	@Override
	protected Diagram doCreateDiagram(Resource diagramResource, EObject owner, EObject element,
			DiagramPrototype prototype, String name) {
		HeadlessDiagramCreationHelper helper = new HeadlessDiagramCreationHelper();

		Diagram result = super.doCreateDiagram(diagramResource, owner, element, prototype, name);

		if (result != null) {
			helper.recordOwnerAndElement(result, owner, element);
		}

		return result;
	}

}