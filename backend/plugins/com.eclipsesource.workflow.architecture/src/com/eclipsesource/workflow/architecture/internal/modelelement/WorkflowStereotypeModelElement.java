/*****************************************************************************
 * Copyright (c) 2018 EclipseSource Services GmbH
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   EclipseSource - Initial API and implementation
 *   
 *****************************************************************************/
package com.eclipsesource.workflow.architecture.internal.modelelement;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.papyrus.uml.properties.modelelement.StereotypeModelElement;
import org.eclipse.uml2.uml.Stereotype;

public class WorkflowStereotypeModelElement extends StereotypeModelElement {

	public WorkflowStereotypeModelElement(EObject stereotypeApplication, Stereotype stereotype, EditingDomain domain) {
		super(stereotypeApplication, stereotype, domain);
	}

}
