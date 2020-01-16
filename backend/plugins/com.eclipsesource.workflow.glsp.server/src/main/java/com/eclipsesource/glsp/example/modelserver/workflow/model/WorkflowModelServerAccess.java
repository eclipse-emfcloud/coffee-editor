/*******************************************************************************
 * Copyright (c) 2019 EclipseSource and others.
 *  
 *   This program and the accompanying materials are made available under the
 *   terms of the Eclipse Public License v. 2.0 which is available at
 *   http://www.eclipse.org/legal/epl-2.0.
 *  
 *   This Source Code may also be made available under the following Secondary
 *   Licenses when the conditions for such availability set forth in the Eclipse
 *   Public License v. 2.0 are satisfied: GNU General Public License, version 2
 *   with the GNU Classpath Exception which is available at
 *   https://www.gnu.org/software/classpath/license.html.
 *  
 *   SPDX-License-Identifier: EPL-2.0 OR GPL-2.0 WITH Classpath-exception-2.0
 ******************************************************************************/
package com.eclipsesource.glsp.example.modelserver.workflow.model;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.apache.log4j.Logger;
import org.eclipse.emf.common.command.BasicCommandStack;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.eclipsesource.glsp.api.jsonrpc.GLSPServerException;
import com.eclipsesource.glsp.example.modelserver.workflow.wfnotation.WfnotationPackage;
import com.eclipsesource.glsp.graph.GNode;
import com.eclipsesource.modelserver.client.ModelServerClient;
import com.eclipsesource.modelserver.client.Response;
import com.eclipsesource.modelserver.client.NotificationSubscriptionListener;
import com.eclipsesource.modelserver.coffee.model.coffee.CoffeePackage;
import com.eclipsesource.modelserver.coffee.model.coffee.Flow;
import com.eclipsesource.modelserver.coffee.model.coffee.Node;
import com.eclipsesource.modelserver.command.CCommand;
import com.eclipsesource.modelserver.common.codecs.EncodingException;
import com.eclipsesource.modelserver.edit.CommandCodec;
import com.google.common.base.Preconditions;

public class WorkflowModelServerAccess {
	private static Logger LOGGER = Logger.getLogger(WorkflowModelServerAccess.class);

	private static final String FORMAT_XMI = "xmi";

	private String sourceURI;
	private ResourceSet resourceSet;

	private WorkflowFacade workflowFacade;

	private Map<String, Node> idMapping;

	private ModelServerClient modelServerClient;
	private NotificationSubscriptionListener<EObject> subscriptionListener;

	private EditingDomain editingDomain;
	private CommandCodec commandCodec;

	public WorkflowModelServerAccess(String sourceURI, ModelServerClient modelServerClient,
			AdapterFactory adapterFactory, CommandCodec commandCodec) {
		Preconditions.checkNotNull(modelServerClient);
		this.sourceURI = sourceURI;
		this.modelServerClient = modelServerClient;
		this.resourceSet = setupResourceSet();
		this.editingDomain = new AdapterFactoryEditingDomain(adapterFactory, new BasicCommandStack(), resourceSet);
		this.commandCodec = commandCodec;
	}

	public void subscribe(NotificationSubscriptionListener<EObject> subscriptionListener) {
		this.subscriptionListener = subscriptionListener;
		this.modelServerClient.subscribe(getSemanticResource(sourceURI), subscriptionListener, FORMAT_XMI);
	}

	public void unsubscribe() {
		if (subscriptionListener != null) {
			this.modelServerClient.unsubscribe(getSemanticResource(sourceURI));
		}
	}

	public void update() {
		EObject root = workflowFacade.getSemanticResource().getContents().get(0);
		modelServerClient.update(getSemanticResource(sourceURI), root, FORMAT_XMI);
	}

	public ResourceSet setupResourceSet() {
		ResourceSet resourceSet = new ResourceSetImpl();
		resourceSet.getPackageRegistry().put(CoffeePackage.eINSTANCE.getNsURI(), CoffeePackage.eINSTANCE);
		resourceSet.getPackageRegistry().put(WfnotationPackage.eINSTANCE.getNsURI(), WfnotationPackage.eINSTANCE);
		resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("*", new XMIResourceFactoryImpl());
		return resourceSet;
	}

	public WorkflowFacade getWorkflowFacade() {
		if (workflowFacade == null) {
			createWorkflowFacade();
		}
		return workflowFacade;
	}

	public void setWorkflowFacade(WorkflowFacade workflowFacade) {
		this.workflowFacade = workflowFacade;
	}

	protected WorkflowFacade createWorkflowFacade() {
		try {
			Resource notationResource = loadResource(convertToFile(sourceURI).getAbsolutePath()); // leave local for now
			EObject root = modelServerClient.get(getSemanticResource(sourceURI), FORMAT_XMI)
					.thenApply(res -> res.body()).get();

			Resource semanticResource = loadResource(convertToFile(getSemanticResource(sourceURI)).getAbsolutePath(),
					root);
			workflowFacade = new WorkflowFacade(semanticResource, notationResource);
			return workflowFacade;
		} catch (IOException | InterruptedException | ExecutionException e) {
			LOGGER.error(e);
			return null;
		}
	}

	private File convertToFile(String sourceURI) {
		if (sourceURI != null) {
			return new File(sourceURI);
		}
		return null;
	}

	private String getSemanticResource(String uri) {
		return uri.replaceFirst(".coffeenotation", ".coffee");
	}

	public static String toXMI(Resource resource) throws IOException {
		OutputStream out = new ByteArrayOutputStream();
		resource.save(out, Collections.EMPTY_MAP);
		return out.toString();
	}

	private Resource loadResource(String path, EObject root) throws IOException {
		Resource resource = createResource(path);
		resource.getContents().clear();
		resource.getContents().add(root);
		return resource;
	}

	private Resource loadResource(String path) throws IOException {
		Resource resource = createResource(path);
		resource.load(Collections.EMPTY_MAP);
		return resource;
	}

	private Resource createResource(String path) {
		return resourceSet.createResource(URI.createFileURI(path));
	}

	public void setNodeMapping(Map<Node, GNode> mapping) {
		initIdMap(mapping);
	}

	private void initIdMap(Map<Node, GNode> mapping) {
		idMapping = new HashMap<>();
		mapping.entrySet().forEach(entry -> idMapping.put(entry.getValue().getId(), entry.getKey()));
	}

	public Node getNodeById(String id) {
		return idMapping.get(id);
	}

	public Optional<Flow> getFlow(Node source, Node target) {
		return this.workflowFacade.getCurrentWorkflow().getFlows().stream()
				.filter(flow -> source.equals(flow.getSource()) && target.equals(flow.getTarget())).findFirst();
	}

	public void save() {
		try {
			workflowFacade.getNotationResource().save(Collections.emptyMap());
		} catch (IOException e) {
			throw new GLSPServerException("Could not save notation resource", e);
		}

	}

	public ModelServerClient getModelServerClient() {
		return this.modelServerClient;
	}

	public EditingDomain getEditingDomain() {
		return editingDomain;
	}

	public CommandCodec getCommandCodec() {
		return commandCodec;
	}

	public CompletableFuture<Response<Boolean>> edit(Command command) throws EncodingException {
		CCommand ccommand = getCommandCodec().encode(command);
		return this.modelServerClient.edit(getSemanticResource(sourceURI), ccommand, FORMAT_XMI);
	}
}
