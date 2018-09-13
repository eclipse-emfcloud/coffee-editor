package com.eclipsesource.workflow.generator.impl;

import java.util.ArrayList;
import java.util.List;

import com.eclipsesource.workflow.generator.IWorkflowGeneratorInput;
import com.eclipsesource.workflow.generator.IWorkflowGeneratorOutput;

public class WorkflowGeneratorOutput implements IWorkflowGeneratorOutput {

	private IWorkflowGeneratorInput input;
	private List<IGeneratedFile> generatedFiles = new ArrayList<>();

	public WorkflowGeneratorOutput(IWorkflowGeneratorInput input) {
		this.input = input;		
	}
	
	@Override
	public IWorkflowGeneratorInput getInput() {
		return input;
	}
	
	@Override
	public List<IGeneratedFile> getGeneratedFiles() {
		return generatedFiles;
	}
	
	public void addGeneratedFile(String fileName, String content, boolean overwrite) {
		generatedFiles.add(new GeneratedFile(fileName, content, overwrite));
	}
	
	public void addGeneratedFile(String fileName, String content) {
		addGeneratedFile(fileName, content, true);
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("Output for '" + input.getPackageName() + "/" + input.getSourceFileName() + "':\n");
		getGeneratedFiles().forEach(file -> sb.append(file.getFileName() + "\n"));
		return sb.toString();
	}

	public static class GeneratedFile implements IGeneratedFile {
		private String fileName;
		private String content;
		private boolean overwrite;

		public GeneratedFile(String fileName, String content, boolean overwrite) {
			this.fileName = fileName;
			this.content = content;
			this.overwrite = overwrite;
		}
		
		@Override
		public String getFileName() {
			return fileName;
		}

		@Override
		public String getContent() {
			return content;
		}

		@Override
		public boolean shouldOverwrite() {
			return overwrite;
		}
		
		@Override
		public String toString() {
			return getFileName() + "\n" + getContent();
		}
	}

}
