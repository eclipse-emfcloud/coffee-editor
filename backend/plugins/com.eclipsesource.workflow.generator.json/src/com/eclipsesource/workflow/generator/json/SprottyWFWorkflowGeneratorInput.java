package com.eclipsesource.workflow.generator.json;

import java.io.FileNotFoundException;
import java.net.URI;

import com.eclipsesource.workflow.IWorkflowGraph;
import com.eclipsesource.workflow.generator.AbstractWorkflowGeneratorInput;
import com.eclipsesource.workflow.generator.IWorkflowGeneratorInput;
import com.eclipsesource.workflow.util.SprottyWFParser;

public class SprottyWFWorkflowGeneratorInput extends AbstractWorkflowGeneratorInput implements IWorkflowGeneratorInput {
	
	private String content;
	private URI sourceFile;

	public SprottyWFWorkflowGeneratorInput(String packageName, String sourceFileName, String content) {
		super(packageName, sourceFileName);
		this.content = content;
	}
	
	public SprottyWFWorkflowGeneratorInput(String packageName, URI sourceFile) {
		super(packageName, sourceFile.getPath());
		this.sourceFile = sourceFile;
	}

	@Override
	public IWorkflowGraph getGraph() {
		try {
			return content != null ? SprottyWFParser.parseGraph(content) : SprottyWFParser.parseGraph(sourceFile);
		} catch (FileNotFoundException e) {
		}
		return IWorkflowGraph.NULL_GRAPH;
	}

}
