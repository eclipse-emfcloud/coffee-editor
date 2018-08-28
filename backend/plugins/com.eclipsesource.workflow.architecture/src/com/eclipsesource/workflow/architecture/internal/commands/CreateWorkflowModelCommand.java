/*****************************************************************************
 * Copyright (c) 2017 EclipseSource France SAS.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *
 *		Remi Schnekenburger - Initial API and implementation
 *
 *****************************************************************************/
package com.eclipsesource.workflow.architecture.internal.commands;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.papyrus.uml.diagram.common.commands.ModelCreationCommandBase;
import org.eclipse.uml2.uml.UMLFactory;

/**
 * Command to create a new Workflow model.
 */
public class CreateWorkflowModelCommand extends ModelCreationCommandBase {

	@Override
	protected EObject createRootElement() {
		return UMLFactory.eINSTANCE.createModel();
	}

	protected void initializeModel(EObject owner) {
		super.initializeModel(owner);
		((org.eclipse.uml2.uml.Package) owner).setName(getModelName());

	}

	/**
	 * Gets the model name.
	 *
	 * @return the model name
	 */
	protected String getModelName() {
		return "WorkflowModel"; //$NON-NLS-1$
	}
}