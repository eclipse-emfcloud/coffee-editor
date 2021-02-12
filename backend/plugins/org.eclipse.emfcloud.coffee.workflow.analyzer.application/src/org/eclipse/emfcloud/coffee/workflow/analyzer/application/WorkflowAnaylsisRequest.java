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
package org.eclipse.emfcloud.coffee.workflow.analyzer.application;

public class WorkflowAnaylsisRequest {

	private String workflowUri;
	private String workflowConfigUri;
	
	public void setWorkflowUri(String workflowUri) {
		this.workflowUri = workflowUri;
	}

	public String getWorkflowUri() {
		return workflowUri;
	}
	
	public void setWorkflowConfigUri(String workflowConfigUri) {
		this.workflowConfigUri = workflowConfigUri;
	}

	public String getWorkflowConfigUri() {
		return workflowConfigUri;
	}

}
