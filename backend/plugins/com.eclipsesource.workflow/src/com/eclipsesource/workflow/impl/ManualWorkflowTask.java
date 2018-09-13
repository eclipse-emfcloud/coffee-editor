package com.eclipsesource.workflow.impl;

import com.eclipsesource.workflow.IManualWorkflowTask;

public class ManualWorkflowTask extends AbstractWorkflowTask implements IManualWorkflowTask {

	private String actor;

	public ManualWorkflowTask(String id, String name, int duration, String actor) {
		super(id, name, duration);
		this.actor = actor;
	}

	@Override
	public String getActor() {
		return actor;
	}

	@Override
	public boolean isManual() {
		return true;
	}

}
