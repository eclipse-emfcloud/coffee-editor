package com.eclipsesource.workflow.generator;

public abstract class AbstractWorkflowGeneratorInput implements IWorkflowGeneratorInput {

	private String packageName;
	private String sourceFileName;

	public AbstractWorkflowGeneratorInput(String packageName, String sourceFileName) {
		this.packageName = packageName;
		this.sourceFileName = sourceFileName;
	}

	@Override
	public String getPackageName() {
		return packageName;
	}

	@Override
	public String getSourceFileName() {
		return sourceFileName;
	}

	@Override
	public void dispose() {
		// do nothing
	}
}
