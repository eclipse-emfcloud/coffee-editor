package com.eclipsesource.workflow.architecture.internal.commands;

import static com.eclipsesource.workflow.architecture.internal.utils.UIUtil.getDiagramEditor;
import static com.eclipsesource.workflow.architecture.internal.utils.UIUtil.getSelectedObject;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.diagram.ui.parts.DiagramEditor;
import org.eclipse.papyrus.commands.wrappers.GMFtoGEFCommandWrapper;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.uml2.uml.Action;

public class NavigationCommandHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		EObject model = getSelectedObject(HandlerUtil.getCurrentSelection(event));
		DiagramEditor editor = getDiagramEditor(HandlerUtil.getActiveEditor(event));
		if (editor != null) {
			if (model instanceof Action) {
				editor.getDiagramEditDomain().getDiagramCommandStack()
						.execute(GMFtoGEFCommandWrapper.wrap(new ActionToCodeNavigationCommand((Action) model)));
			}
		}
		return null;
	}

}
