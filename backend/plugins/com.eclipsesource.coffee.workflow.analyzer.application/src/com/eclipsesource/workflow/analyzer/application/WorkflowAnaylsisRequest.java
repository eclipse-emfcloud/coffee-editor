package com.eclipsesource.workflow.analyzer.application;

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
