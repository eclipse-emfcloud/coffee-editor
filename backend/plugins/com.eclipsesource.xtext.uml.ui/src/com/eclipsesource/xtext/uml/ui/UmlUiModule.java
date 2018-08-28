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
package com.eclipsesource.xtext.uml.ui;

import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.eclipse.xtext.ui.LanguageSpecific;
import org.eclipse.xtext.ui.editor.IURIEditorOpener;
import org.eclipse.xtext.ui.resource.generic.EmfUiModule;

public class UmlUiModule extends EmfUiModule {
	 public UmlUiModule(AbstractUIPlugin plugin) {
	        super(plugin);
	    }
	 
	    @Override
	    public void configureLanguageSpecificURIEditorOpener(com.google.inject.Binder binder) {
	        binder.bind(IURIEditorOpener.class).annotatedWith(LanguageSpecific.class).to(UmlEditorOpener.class);
	    }
	 
}
