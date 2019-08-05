package com.eclipsesource.workflow.dsl.ide.server;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.apache.commons.io.FileUtils;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.lsp4j.DidChangeConfigurationParams;
import org.eclipse.lsp4j.DidChangeWatchedFilesParams;
import org.eclipse.lsp4j.DidChangeWorkspaceFoldersParams;
import org.eclipse.lsp4j.FileEvent;
import org.eclipse.lsp4j.InitializeParams;
import org.eclipse.lsp4j.SymbolInformation;
import org.eclipse.lsp4j.WorkspaceFolder;
import org.eclipse.lsp4j.WorkspaceSymbolParams;
import org.eclipse.lsp4j.services.WorkspaceService;

import com.eclipsesource.modelserver.client.ModelServerClient;
import com.eclipsesource.modelserver.coffee.model.coffee.Machine;
import com.eclipsesource.workflow.dsl.index.IWorkflowIndex;

public class WorkflowIndexService implements WorkspaceService {
	private static final String WORKFLOW_EXTENSION = "xmi";
	private static final String DOT_WORKFLOW_EXTENSION = "." + WORKFLOW_EXTENSION;
	
	private IWorkflowIndex index;

	public WorkflowIndexService(IWorkflowIndex index) {
		this.index = index;
	}

	public void initialize(InitializeParams params) {
		updateWorkflowIndex(params);
	}
	
	@Override
	public void didChangeWorkspaceFolders(DidChangeWorkspaceFoldersParams params) {
		for(WorkspaceFolder addedFoler : params.getEvent().getAdded()) {
			workspaceFolderAdded(addedFoler.getUri());
		}
		for(WorkspaceFolder deletedFolder : params.getEvent().getRemoved()) {
			workspaceFolderDeleted(deletedFolder.getUri());
		}
	}
	
	@Override
	public void didChangeConfiguration(final DidChangeConfigurationParams params) {
	}

	@Override
	public void didChangeWatchedFiles(final DidChangeWatchedFilesParams params) {
		updateWorkflowIndex(params);
	}

	@Override
	public CompletableFuture<List<? extends SymbolInformation>> symbol(final WorkspaceSymbolParams params) {
		return CompletableFuture.supplyAsync(ArrayList<SymbolInformation>::new);
	}
	
	private void updateWorkflowIndex(InitializeParams params) {
		workspaceFolderAdded(params.getRootUri());
	}
	
	private void updateWorkflowIndex(final DidChangeWatchedFilesParams params) {
		for(FileEvent event : params.getChanges()) {
			String uri = event.getUri();
			if(!uri.endsWith(DOT_WORKFLOW_EXTENSION)) {
				continue;
			}
			switch(event.getType()) {
			case Created:
				workflowFileCreated(uri);
				break;
			case Changed:
				workflowFileChanged(uri);
				break;
			case Deleted:
				workflowFileDeleted(uri);
				break;			
			}
		}
	}
	
	private void workspaceFolderAdded(String folderUri) {
		try {
			System.out.println("[WorkflowDSL] Folder " + folderUri + " added.");
			File rootFolder = new File(new URI(folderUri));
			FileUtils.iterateFiles(rootFolder, new String[] { WORKFLOW_EXTENSION }, true).forEachRemaining(file -> workflowFileCreated(file.toURI().toString()));
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
	}
	
	private void workspaceFolderDeleted(String folderUri) {
		try {
			System.out.println("[WorkflowDSL] Folder " + folderUri + " deleted.");
			File rootFolder = new File(new URI(folderUri));
			FileUtils.iterateFiles(rootFolder, new String[] { WORKFLOW_EXTENSION }, true).forEachRemaining(file -> workflowFileDeleted(file.toURI().toString()));
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
	}

	private void workflowFileCreated(String uri) {
		try {
			index.putGraph(uri, getContent(uri));
			System.out.println("[WorkflowDSL] File " + uri + " added to index.");
		} catch (InterruptedException | ExecutionException | IOException e) {
			e.printStackTrace();
		}
	}
	
	private void workflowFileChanged(String uri) {
		try {
			// changed: simply re-parse the whole graph
			index.putGraph(uri, getContent(uri));
			System.out.println("[WorkflowDSL] File " + uri + " changed in index.");
		} catch (InterruptedException | ExecutionException | IOException e) {
			e.printStackTrace();
		}
	}
	
	private void workflowFileDeleted(String uri) {
		index.removeGraph(uri);
		System.out.println("[WorkflowDSL] File " + uri + " deleted from index.");
	}
	
	private static Machine getContent (String uri) throws InterruptedException, ExecutionException, IOException {
		ModelServerClient msc = new ModelServerClient("http://localhost:8081/api/v1/");
		return (Machine) msc.get(Paths.get(uri).getFileName().toString(), "xmi").get().body();
	}
	
}
