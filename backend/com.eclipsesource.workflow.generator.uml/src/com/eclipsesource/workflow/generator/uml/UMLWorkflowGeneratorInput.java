package com.eclipsesource.workflow.generator.uml;

import static com.eclipsesource.workflow.architecture.internal.utils.ArchitectureConstants.STEREOTYPE_AUTOMATIC_TASK;
import static com.eclipsesource.workflow.architecture.internal.utils.ArchitectureConstants.STEREOTYPE_MANUAL_TASK;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.uml2.uml.Action;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.Stereotype;
import org.eclipse.uml2.uml.UMLPackage;

import com.eclipsesource.workflow.architecture.internal.utils.WorkflowExtensionUtil;
import com.eclipsesource.workflow.generator.AbstractWorkflowGeneratorInput;
import com.eclipsesource.workflow.generator.IWorkflowGeneratorInput;
import com.eclipsesource.workflow.generator.IWorkflowTask;
import com.eclipsesource.workflow.generator.impl.AutomaticWorkflowTask;
import com.eclipsesource.workflow.generator.impl.ManualWorkflowTask;

public class UMLWorkflowGeneratorInput extends AbstractWorkflowGeneratorInput implements IWorkflowGeneratorInput {

	private ResourceSet resourceSet;
	private Resource modelResource;
	private String content;

	public UMLWorkflowGeneratorInput(String packageName, String sourceFileName, String content) {
		super(packageName, sourceFileName);
		this.content = content;
	}

	private Model getModel() {
		Resource resource = getModelResource();
		if(resource == null) {
			return null;
		}
		for (EObject eObject : getModelResource().getContents()) {
			if (eObject instanceof Model) {
				return (Model) eObject;
			}
		}
		return null;
	}

	private Resource getModelResource() {
		if (modelResource == null) {
			resourceSet = new ResourceSetImpl();
			modelResource = resourceSet.createResource(URI.createURI("temp://" + getSourceFileName() + ".uml"), UMLPackage.eCONTENT_TYPE);
			try {
				modelResource.load(new ByteArrayInputStream(content.getBytes()), null);
			} catch (IOException e) {
				modelResource = null;
			}
		}
		return modelResource;
	}

	@Override
	public void dispose() {
		if (modelResource != null) {
			modelResource.unload();
		}
	}

	@Override
	public List<IWorkflowTask> getTasks() {
		if (getModel() == null) {
			return Collections.emptyList();
		}
		List<IWorkflowTask> tasks = new ArrayList<>();
		for (TreeIterator<EObject> iterator = getModel().eAllContents(); iterator.hasNext();) {
			EObject eObject = iterator.next();
			if (eObject instanceof Action) {
				Action action = (Action) eObject;
				Stereotype stereotype = action.getAppliedStereotype(STEREOTYPE_AUTOMATIC_TASK);
				if(stereotype != null) {
					tasks.add(createAutomaticWorkflowTask(action, stereotype));
				}
				else {
					stereotype = action.getAppliedStereotype(STEREOTYPE_MANUAL_TASK);
					if(stereotype != null) {
						tasks.add(createManualWorkflowTask(action, stereotype));			
					}
				}
			}
		}
		return tasks;
	}

	private IWorkflowTask createAutomaticWorkflowTask(Action action, Stereotype stereotype) {
		String id = action.getActivity().getName() + "::" + action.getName();
		Integer duration = WorkflowExtensionUtil.getStereotypePropertyValue(action, "duration", stereotype, Integer.class);
		String component = WorkflowExtensionUtil.getStringProperty(action, "component", stereotype);
		return new AutomaticWorkflowTask(id, action.getName(), duration, component);
	}
	
	private IWorkflowTask createManualWorkflowTask(Action action, Stereotype stereotype) {
		String id = action.getActivity().getName() + "::" + action.getName();
		Integer duration = WorkflowExtensionUtil.getStereotypePropertyValue(action, "duration", stereotype, Integer.class);
		String actor = WorkflowExtensionUtil.getStringProperty(action, "actor", stereotype);
		return new ManualWorkflowTask(id, action.getName(), duration, actor);
	}
}
