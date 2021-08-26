package org.eclipse.emfcloud.coffee.modelserver.commands.compound;

import java.util.function.Supplier;

import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emfcloud.coffee.Node;
import org.eclipse.emfcloud.coffee.modelserver.commands.notation.AddNodeShapeCommand;
import org.eclipse.emfcloud.coffee.modelserver.commands.semantic.AddAutomatedTaskCommand;
import org.eclipse.glsp.graph.GPoint;

public class AddAutomatedTaskCompoundCommand extends CompoundCommand {

	public AddAutomatedTaskCompoundCommand(final EditingDomain domain, final URI modelUri, final GPoint classPosition) {
		// Chain semantic and notation command
		AddAutomatedTaskCommand command = new AddAutomatedTaskCommand(domain, modelUri);
		this.append(command);
		Supplier<Node> semanticResultSupplier = () -> command.getNode();
		this.append(new AddNodeShapeCommand(domain, modelUri, classPosition, semanticResultSupplier));
	}
}
