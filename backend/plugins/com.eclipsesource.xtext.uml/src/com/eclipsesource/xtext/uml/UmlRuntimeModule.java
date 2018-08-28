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

import org.eclipse.xtext.naming.IQualifiedNameProvider;
import org.eclipse.xtext.resource.IDefaultResourceDescriptionStrategy;
import org.eclipse.xtext.resource.generic.AbstractGenericResourceRuntimeModule;


public class UmlRuntimeModule extends AbstractGenericResourceRuntimeModule {

	@Override
	protected String getLanguageName() {
		return "org.eclipse.uml2.uml.editor.presentation.UMLEditorID";
	}

	@Override
	protected String getFileExtensions() {	
		return "uml";
	}

	public Class<? extends IDefaultResourceDescriptionStrategy> bindIDefaultResourceDescriptionStrategy() {
		return UmlResourceDescriptionStrategy.class;
	}
	
	@Override
	public Class<? extends IQualifiedNameProvider> bindIQualifiedNameProvider() {
		return UmlSimpleNameProvider.class;
	}

}
