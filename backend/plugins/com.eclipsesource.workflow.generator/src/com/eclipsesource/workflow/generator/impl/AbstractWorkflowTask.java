package com.eclipsesource.workflow.generator.impl;

import com.eclipsesource.workflow.generator.IWorkflowTask;

public abstract class AbstractWorkflowTask implements IWorkflowTask {

	private String id;
	private String name;
	private int duration;

	public AbstractWorkflowTask(String id, String name, int duration) {
		this.id = id;
		this.name = name;
		this.duration = duration;		
	}
	
	@Override
	public String getId() {
		return id;
	}
	
	@Override
	public String getName() {
		return name;
	}
	
	@Override
	public int getDuration() {
		return duration;
	}
}
