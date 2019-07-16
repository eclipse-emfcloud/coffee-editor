package com.eclipsesource.workflow.generator;

public class GeneratedFile {
	private String fileName;
	private String content;
	private boolean overwrite;

	public GeneratedFile(String fileName, String content) {
		this(fileName, content, true);
	}
	
	public GeneratedFile(String fileName, String content, boolean overwrite) {
		this.fileName = fileName;
		this.content = content;
		this.overwrite = overwrite;
	}

	public String getFileName() {
		return fileName;
	}

	public String getContent() {
		return content;
	}

	public boolean shouldOverwrite() {
		return overwrite;
	}

	@Override
	public String toString() {
		return getFileName() + "\n" + getContent();
	}
}
