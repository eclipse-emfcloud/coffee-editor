package com.eclipsesource.workflow.generator.java;

import static com.eclipsesource.workflow.architecture.internal.utils.ArchitectureConstants.STEREOTYPE_AUTOMATIC_TASK;
import static com.eclipsesource.workflow.architecture.internal.utils.ArchitectureConstants.STEREOTYPE_MANUAL_TASK;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.uml2.uml.Action;
import org.eclipse.uml2.uml.Stereotype;

import com.eclipsesource.workflow.generator.AbstractWorkflowGenerator;
import com.eclipsesource.workflow.generator.IWorkflowGeneratorInput;

public class JavaWorkflowGenerator extends AbstractWorkflowGenerator {

	IPackageFragment pack;

	@Override
	protected void validateInputModel(IWorkflowGeneratorInput input, IProgressMonitor monitor) throws CoreException {

	}

	@Override
	protected void generateTasksClasses(IWorkflowGeneratorInput input, IProgressMonitor monitor) throws CoreException {
		SubMonitor subMonitor = SubMonitor.convert(monitor, 4);
		DynamicCodeGenerator dynamicGen = new DynamicCodeGenerator();
		StaticCodeGenerator staticGen = new StaticCodeGenerator();
		dynamicGen.init(input.getProject(), subMonitor.split(1));
		staticGen.init(input.getProject(), subMonitor.split(1));

		if (input.getModel() != null) {

			for (TreeIterator<EObject> iterator = input.getModel().eAllContents(); iterator.hasNext();) {
				EObject eObject = iterator.next();
				if (eObject instanceof Action) {
					Action action = (Action) eObject;
					Stereotype stereotype = action.getAppliedStereotype(STEREOTYPE_AUTOMATIC_TASK);
					if (stereotype == null) {
						stereotype = action.getAppliedStereotype(STEREOTYPE_MANUAL_TASK);
					}
					if (stereotype != null) {
						dynamicGen.generate(action, stereotype, input.getProject(), subMonitor.split(1));
						staticGen.generate(action, stereotype, input.getProject(), subMonitor.split(1));
					}
				}

			}
		}
	}

}
