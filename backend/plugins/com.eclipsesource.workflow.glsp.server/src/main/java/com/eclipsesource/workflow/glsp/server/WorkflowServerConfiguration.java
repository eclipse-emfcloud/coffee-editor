package com.eclipsesource.workflow.glsp.server;

import org.eclipse.glsp.api.configuration.ServerConfiguration;
import org.eclipse.glsp.api.layout.ServerLayoutKind;

public class WorkflowServerConfiguration implements ServerConfiguration{

	@Override
	public ServerLayoutKind getLayoutKind() {
		return ServerLayoutKind.MANUAL;
	}

}
