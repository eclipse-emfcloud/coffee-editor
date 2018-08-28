package com.eclipsesource.demo.rest;

import com.eclipsesource.workflow.dsl.ide.extension.commands.AnalyzeWorkflowCommand;
import com.eclipsesource.workflow.dsl.ide.extension.commands.BuildCommand;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.osgi.service.component.annotations.Component;

@Path("/backend")
@Component(service = StaticContentService.class)
public class StaticContentService {

	public StaticContentService() {
		System.out.println("STARTED");
	}

//	@Path("/wfanalysis")
//	@POST
//	@Consumes(MediaType.APPLICATION_JSON)
//	@Produces(MediaType.APPLICATION_JSON)
//	public String workflowAnalysis(String json) {
//		JsonElement jsonElement = new JsonParser().parse(json);
//		
//		//call something to parse the json?
//		
//		return new WorkflowAnalysis(jsonElement).generateAnalysisDataAsJson(new NullProgressMonitor());
//	}
	
	
	@Path("/wfanalysis")
	@POST
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON)
	public String workflowAnalysis(String path) {
		return (String) new AnalyzeWorkflowCommand().execute(new String[] {path});
	}
	
	@Path("/generate")
	@POST
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON)
	public String codeGeneration(String path) {
		return (String) new BuildCommand().execute(new String[] {path});
	}
}
