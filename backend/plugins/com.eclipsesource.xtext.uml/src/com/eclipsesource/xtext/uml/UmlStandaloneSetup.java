/*****************************************************************************
 * Copyright (c) 2018 EclipseSource Services GmbH. 
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *
 *		Tobias Ortmayr - Initial API and implementation
 *
 *****************************************************************************/
package com.eclipsesource.xtext.uml;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.resource.UMLResource;

public class UmlStandaloneSetup {
	
	public static void doSetup() {
		new UmlStandaloneSetup();
	}

	private UmlStandaloneSetup() {
		UMLPackage.eINSTANCE.eClass();
		Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("uml", UMLResource.Factory.INSTANCE);
		new UmlSupport().registerServices(false);
	}
}
