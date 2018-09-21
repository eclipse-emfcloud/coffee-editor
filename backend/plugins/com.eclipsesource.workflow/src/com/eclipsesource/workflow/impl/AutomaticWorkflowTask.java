package com.eclipsesource.workflow.impl;

import com.eclipsesource.workflow.IAutomaticWorkflowTask;

public class AutomaticWorkflowTask extends AbstractWorkflowTask implements IAutomaticWorkflowTask {

	private String component;

	public AutomaticWorkflowTask(String id, String name, int duration, String component) {
		super(id, name, duration);
		this.component = component;
	}

	@Override
	public String getComponent() {
		return component;
	}

	@Override
	public boolean isManual() {
		return false;
	}

}
