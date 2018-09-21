package com.eclipsesource.workflow;

public interface IWorkflowTask {
	String getId();
	String getName();
	int getDuration();
	boolean isManual();
}