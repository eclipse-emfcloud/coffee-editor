/*****************************************************************************
 * Copyright (c) 2017 EclipseSource and others.
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

package com.eclipsesource.workflow.architecture.internal;

import org.eclipse.papyrus.infra.core.log.LogHelper;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * Activator for this plugin.
 */
public class Activator extends AbstractUIPlugin {

	public final static String PLUGIN_ID = "com.eclipsesource.workflow.architecture"; //$NON-NLS-1$

	public static LogHelper log;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void start(BundleContext bundleContext) throws Exception {
		super.start(bundleContext);
		log = new LogHelper(this);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void stop(BundleContext bundleContext) throws Exception {
		log = null;
		super.stop(bundleContext);
	}
}
