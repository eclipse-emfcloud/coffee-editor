package com.eclipsesource.workflow.generator;

import java.util.List;

public interface IWorkflowGeneratorOutput {
	IWorkflowGeneratorInput getInput();
	List<IGeneratedFile> getGeneratedFiles();
	
	interface IGeneratedFile {
		String getFileName();
		String getContent();
		boolean shouldOverwrite();
	}
}
