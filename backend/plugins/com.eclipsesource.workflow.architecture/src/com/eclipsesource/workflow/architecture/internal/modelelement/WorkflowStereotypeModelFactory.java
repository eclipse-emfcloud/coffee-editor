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

import static com.eclipsesource.workflow.architecture.internal.utils.ArchitectureConstants.STEREOTYPE_AUTOMATIC_TASK;
import static com.eclipsesource.workflow.architecture.internal.utils.ArchitectureConstants.STEREOTYPE_MANUAL_TASK;
import static com.eclipsesource.workflow.architecture.internal.utils.ArchitectureConstants.STEREOTYPE_WEIGHTED_FLOW;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.papyrus.infra.emf.utils.EMFHelper;
import org.eclipse.papyrus.infra.properties.contexts.DataContextElement;
import org.eclipse.papyrus.infra.properties.ui.modelelement.EMFModelElement;
import org.eclipse.papyrus.uml.properties.modelelement.StereotypeModelElementFactory;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Stereotype;

public class WorkflowStereotypeModelFactory extends StereotypeModelElementFactory {
	private static final Set<String> ST_QUALIFIED_NAMES = new HashSet<String>(
			Arrays.asList(STEREOTYPE_AUTOMATIC_TASK, STEREOTYPE_MANUAL_TASK, STEREOTYPE_WEIGHTED_FLOW));

	@Override
	protected EMFModelElement doCreateFromSource(Object source, DataContextElement context) {
		Element umlElement = resolveUMLElement(source);
		if (umlElement != null) {
			Optional<Stereotype> stereotype = umlElement.getAppliedStereotypes().stream()
					.filter(st -> ST_QUALIFIED_NAMES.contains(st.getQualifiedName())).findFirst();
			if (stereotype.isPresent()) {
				EObject stereotypeApplication = umlElement.getStereotypeApplication(stereotype.get());
				return new WorkflowStereotypeModelElement(stereotypeApplication, stereotype.get(),
						EMFHelper.resolveEditingDomain(source));
			}
		}
		return super.doCreateFromSource(source, context);

	}

	private Element resolveUMLElement(Object source) {
		EObject eElement = EMFHelper.getEObject(source);

		if (eElement instanceof Element) {
			return (Element) eElement;
		}

		return null;
	}

}
