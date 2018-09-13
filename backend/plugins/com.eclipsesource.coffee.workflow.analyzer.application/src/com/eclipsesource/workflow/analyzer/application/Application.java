package com.eclipsesource.workflow.analyzer.application;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;

import com.eclipsesource.workflow.analyzer.json.AnalyzeWorkflow;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * This class controls all aspects of the application's execution
 */
public class Application implements IApplication {

	@Override
	public Object start(IApplicationContext context) throws Exception {

		String[] args = (String[]) context.getArguments().get(IApplicationContext.APPLICATION_ARGS);
		Integer port = null;
		String host = null;
		for (int i = 0; i < args.length; i++) {
			String arg = args[i];
			switch (arg) {
			case "-port":
				i++;
				port = Integer.valueOf(args[i]);
				break;
			case "-host":
				i++;
				host = args[i];
				break;
			}
		}
		try(
				Socket socket = new Socket(host, port);
				BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				OutputStreamWriter osw = new OutputStreamWriter(socket.getOutputStream());
			){
			String requestMessage = reader.readLine();
			JsonObject request = new JsonParser().parse(requestMessage).getAsJsonObject();
			String wfUri = request.get("WF-URI").getAsString();
			String wfConfigUri = request.get("WFConfig-URI").getAsString();
			String graph = new String(Files.readAllBytes(Paths.get(URI.create(wfUri))));
			String config = new String(Files.readAllBytes(Paths.get(URI.create(wfConfigUri))));
			String analyzeResult = new AnalyzeWorkflow(graph, config).generate();
			osw.write(analyzeResult);
			osw.flush();
		}
		return IApplication.EXIT_OK;
	}

	@Override
	public void stop() {
		// nothing to do
	}
}
