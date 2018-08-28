package com.eclipsesource.workflow.generator;

public interface IWorkflowTask {
	String getId();
	String getName();
	int getDuration();
	boolean isManual();
}