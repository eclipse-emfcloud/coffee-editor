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

import java.util.UUID;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emfcloud.coffee.modelserver.wfnotation.Diagram;
import org.eclipse.glsp.graph.GPoint;
import org.eclipse.glsp.graph.util.GraphUtil;

public final class NotationCommandUtil {

	// Hide constructor for utility class
	private NotationCommandUtil() {
	}

	public static GPoint getGPoint(final String propertyX, final String propertyY) {
		GPoint gPoint = GraphUtil.point(propertyX.isEmpty() ? 0.0d : Double.parseDouble(propertyX),
				propertyY.isEmpty() ? 0.0d : Double.parseDouble(propertyY));
		return gPoint;
	}

	public static Diagram getDiagram(final URI modelUri, final EditingDomain domain) {
		Resource notationResource = domain.getResourceSet()
				.getResource(modelUri.trimFileExtension().appendFileExtension("coffeenotation"), false);
		EObject notationRoot = notationResource.getContents().get(0);
		if (!(notationRoot instanceof Diagram)) {
			return null;
		}
		return (Diagram) notationRoot;
	}

	public static String getSemanticProxyUri(final EObject element) {
		return EcoreUtil.getURI(element).fragment();
	}

	public static String generateId() {
		return UUID.randomUUID().toString();
	}
}
