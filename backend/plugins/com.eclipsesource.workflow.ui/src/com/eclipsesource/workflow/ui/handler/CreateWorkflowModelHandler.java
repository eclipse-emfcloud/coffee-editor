package com.eclipsesource.workflow.ui.handler;

import static com.eclipsesource.workflow.architecture.internal.utils.UIUtil.extractSelection;
import static com.eclipsesource.workflow.architecture.internal.utils.UIUtil.getCurrentSelection;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IResource;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.emfforms.coffee.model.coffee.CoffeePackage;
import org.eclipse.emfforms.coffee.model.coffee.Component;
import org.eclipse.emfforms.coffee.model.coffee.Machine;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.ui.PlatformUI;

import com.eclipsesource.workflow.ui.wizard.CreateWorkflowModelWizard;

public class CreateWorkflowModelHandler extends AbstractHandler {
	private static String COFFEE_EXTENSION = "coffee";

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		// TODO Auto-generated method stub
		CreateWorkflowModelWizard wizard = new CreateWorkflowModelWizard();
		// init wizard
		wizard.init(PlatformUI.getWorkbench(), new StructuredSelection());
		WizardDialog dialog = new WizardDialog(wizard.getShell(), wizard);
		dialog.create();
		IResource selectedResource = extractSelection(getCurrentSelection());
		if (selectedResource != null && selectedResource.getFileExtension().equals(COFFEE_EXTENSION)) {
			List<String> componentList = new ArrayList<>();
			EObject model = loadCoffeeModel(selectedResource);
			for (TreeIterator<EObject> iterator = model.eAllContents(); iterator.hasNext();) {
				EObject eObject = iterator.next();
				if (eObject instanceof Component) {
					componentList.add(extractName((Component) eObject));
				}
			}
			wizard.getMainPage().setSelectedComponents(componentList);
			if (model instanceof Machine) {
				wizard.getMainPage().setModelName(((Machine) model).getName());
			}
			wizard.performFinish();
		} else {
			dialog.open();
		}

		return null;
	}

	private String extractName(Component component) {
		if (component instanceof Machine) {
			return ((Machine) component).getName();
		}
		return component.eClass().getName();
	}

	private EObject loadCoffeeModel(IResource resource) {
		CoffeePackage.eINSTANCE.eClass();
		Resource.Factory.Registry reg = Resource.Factory.Registry.INSTANCE;
		Map<String, Object> m = reg.getExtensionToFactoryMap();
		m.put("coffee", new XMIResourceFactoryImpl());

		ResourceSet resourceSet = new ResourceSetImpl();
		URI uri = URI.createFileURI(resource.getLocation().toFile().getPath());
		Resource modelResource = resourceSet.getResource(uri, true);
		return modelResource.getContents().get(0);
	}
}
