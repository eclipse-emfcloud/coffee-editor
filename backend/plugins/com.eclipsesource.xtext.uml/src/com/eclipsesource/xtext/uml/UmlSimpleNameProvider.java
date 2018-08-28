/*****************************************************************************
 * Copyright (c) 2016 EclipseSource Services GmbH. 
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

import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.naming.IQualifiedNameConverter;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.naming.SimpleNameProvider;
import org.eclipse.xtext.util.SimpleAttributeResolver;

import com.google.inject.Inject;

public class UmlSimpleNameProvider extends SimpleNameProvider {
	@Inject
	private IQualifiedNameConverter qualifiedNameConverter;

	@Override
	public QualifiedName getFullyQualifiedName(EObject obj) {
		String name = SimpleAttributeResolver.NAME_RESOLVER.apply(obj);
	
		if (name == null || name.length() == 0)
			return null;
		name = name.replaceAll("\\s+", "_");
		return qualifiedNameConverter.toQualifiedName(name);
	}

}
