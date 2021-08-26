/*******************************************************************************
 * Copyright (c) 2021 EclipseSource and others.
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0, or the MIT License which is
 * available at https://opensource.org/licenses/MIT.
 * 
 * SPDX-License-Identifier: EPL-2.0 OR MIT
 ******************************************************************************/
package org.eclipse.emfcloud.coffee.modelserver.commands.util;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emfcloud.coffee.Machine;
import org.eclipse.emfcloud.coffee.Workflow;

public final class SemanticCommandUtil {

	// Hide constructor for utility class
	private SemanticCommandUtil() {
	}

	public static Workflow getModel(final URI modelUri, final EditingDomain domain) {
		Resource semanticResource = domain.getResourceSet()
				.getResource(modelUri.trimFileExtension().appendFileExtension("coffee"), false);
		EObject semanticRoot = semanticResource.getContents().get(0);
		if (!(semanticRoot instanceof Machine)) {
			return null;
		}
		Machine machine = (Machine) semanticRoot;
		if (machine.getWorkflows().size() < 1) {
			return null;
		}
		// TODO We might want to hand in the index of the used workflow
		return machine.getWorkflows().get(0);
	}
}
