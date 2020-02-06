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
