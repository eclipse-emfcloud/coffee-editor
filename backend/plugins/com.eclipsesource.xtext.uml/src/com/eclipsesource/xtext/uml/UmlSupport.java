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

import org.eclipse.xtext.ISetup;
import org.eclipse.xtext.resource.generic.AbstractGenericResourceSupport;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;

public class UmlSupport extends AbstractGenericResourceSupport implements ISetup{

	@Override
	protected Module createGuiceModule() {
		return new UmlRuntimeModule();
	}

	@Override
	public Injector createInjectorAndDoEMFRegistration() {
		Injector injector = Guice.createInjector(getGuiceModule());
		injector.injectMembers(this);
		registerInRegistry(false);
		return injector;
	}

}
