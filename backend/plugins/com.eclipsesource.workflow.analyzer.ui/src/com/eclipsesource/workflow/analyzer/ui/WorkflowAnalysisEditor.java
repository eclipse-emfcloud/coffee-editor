package com.eclipsesource.workflow.analyzer.ui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.stream.Collectors;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Platform;
import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.browser.ProgressEvent;
import org.eclipse.swt.browser.ProgressListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.EditorPart;
import org.eclipse.ui.part.FileEditorInput;
import org.osgi.framework.Bundle;

public class WorkflowAnalysisEditor extends EditorPart {

	public static final String EXTENSION = "wfanalysis"; //$NON-NLS-1$

	private static final String PLUGIN_ID = "com.eclipsesource.workflow.analyzer.ui"; //$NON-NLS-1$
	private static final String WEB_INDEX_PATH = "wf-analyzer-web-app/index.html"; //$NON-NLS-1$
	private static final String NL = "\n";

	private Browser browser;
	private String appRootPath;

	@Override
	public void init(IEditorSite site, IEditorInput input) throws PartInitException {
		if (!(input instanceof IFileEditorInput))
			throw new PartInitException("Invalid Input: Must be IFileEditorInput");
		setSite(site);
		setInput(input);
		setPartName(getInputFile().getName());
		try {
			appRootPath = getAppRootPath();
		} catch (IOException e) {
			throw new PartInitException("Cannot load root web app", e);
		}
	}

	private String getAppRootPath() throws IOException {
		Bundle bundle = Platform.getBundle(PLUGIN_ID);
		URL entry = bundle.getEntry(WEB_INDEX_PATH);
		return FileLocator.toFileURL(entry).toString();
	}

	@Override
	public void createPartControl(Composite parent) {
		browser = new Browser(parent, SWT.NONE);
		browser.setUrl(getBrowserInputUrl());
		browser.addProgressListener(new ProgressListener() {
			public void changed(ProgressEvent event) {
			}

			public void completed(ProgressEvent event) {
				loadGraph();
			}
		});
	}

	private void loadGraph() {
		String script = "var json = " + this.getInputFileContents() + ";" + NL + "createVisualization(json);";
		browser.execute(script);
	}

	private String getBrowserInputUrl() {
		return appRootPath;
	}

	private String getInputFileContents() {
		try {
			IFile file = getInputFile();
			String result = new BufferedReader(new InputStreamReader(file.getContents())).lines()
					.collect(Collectors.joining(NL));
			return result;
		} catch (CoreException e) {
			e.printStackTrace();
		}
		return ""; //$NON-NLS-1$
	}

	private IFile getInputFile() {
		FileEditorInput fileInput = (FileEditorInput) getEditorInput();
		return fileInput.getFile();
	}

	@Override
	public void setFocus() {
		browser.setFocus();
	}

	@Override
	public void doSave(IProgressMonitor monitor) {
		// read-only
	}

	@Override
	public void doSaveAs() {
		// read-only
	}

	@Override
	public boolean isDirty() {
		// read-only
		return false;
	}

	@Override
	public boolean isSaveAsAllowed() {
		// read-only
		return false;
	}

}
