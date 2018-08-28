package com.eclipsesource.workflow.architecture.internal.commands;

import static org.eclipse.papyrus.infra.gmfdiag.common.utils.DiagramUtils.getPapyrusDiagramStyle;

import java.util.Optional;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.infra.gmfdiag.style.PapyrusDiagramStyle;

import com.eclipsesource.workflow.architecture.internal.utils.EMFHacks;

/**
 * A helper to compose additional behaviour into headless diagram creation
 * commands.
 */
public class HeadlessDiagramCreationHelper {

	/**
	 * Initializes me.
	 */
	HeadlessDiagramCreationHelper() {
		super();
	}

	/**
	 * Ensures that the setting of a new {@code diagram}'s {@code owner} and context
	 * {@code element} are properly recorded for undo/redo, such that undoing the
	 * creation command will result in the owner and element being forgotten as
	 * expected (so that they no longer turn up, for example, in cross-reference
	 * searches).
	 * 
	 * @param diagram
	 *            a new diagram
	 * @param owner
	 *            its owner
	 * @param element
	 *            its element
	 */
	void recordOwnerAndElement(Diagram diagram, EObject owner, EObject element) {
		// The basic diagram creation sets the element before the diagram
		// is attached to the resource set, so the transaction doesn't record
		// the setting of the element and consequently undo doesn't unset it.
		// This results in the inverse cross-reference map still retaining the
		// reference so that (e.g.) the Model Explorer still thinks the diagram
		// exists for this element after creation of the diagram is undone
		EMFHacks.silently(diagram, View::unsetElement).setElement(element); // This is now recorded

		// Likewise the diagram owner
		Optional<PapyrusDiagramStyle> diagramStyle = Optional.ofNullable(getPapyrusDiagramStyle(diagram));
		EMFHacks.silently(diagramStyle, ds -> ds.setOwner(null)).ifPresent(ds -> ds.setOwner(owner)); // This is now
																										// recorded
	}
}