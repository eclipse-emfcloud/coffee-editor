/*******************************************************************************
 * Copyright (c) 2019-2020 EclipseSource and others.
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0, or the MIT License which is
 * available at https://opensource.org/licenses/MIT.
 * 
 * SPDX-License-Identifier: EPL-2.0 OR MIT
 ******************************************************************************/
package com.eclipsesource.workflow.glsp.server;

import org.eclipse.emf.ecore.EFactory;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.glsp.graph.GraphExtension;

import com.eclipsesource.workflow.glsp.server.wfgraph.WfgraphFactory;
import com.eclipsesource.workflow.glsp.server.wfgraph.WfgraphPackage;

public class WorkflowGraphExtension implements GraphExtension {
	@Override
	public EPackage getEPackage() {
		return WfgraphPackage.eINSTANCE;
	}

	@Override
	public EFactory getEFactory() {
		return WfgraphFactory.eINSTANCE;
	}

}
