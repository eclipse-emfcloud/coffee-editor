package org.eclipse.emfcloud.coffee.modelserver.commands.semantic;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emfcloud.coffee.Flow;
import org.eclipse.emfcloud.coffee.Node;
import org.eclipse.emfcloud.coffee.modelserver.commands.util.SemanticCommandUtil;

public class SetFlowSourceCommand extends SemanticElementCommand {

	protected String semanticUriFragment;
	protected String newSourceUriFragment;

	public SetFlowSourceCommand(final EditingDomain domain, final URI modelUri, final String semanticUriFragment,
			final String newSourceUriFragment) {
		super(domain, modelUri);
		this.semanticUriFragment = semanticUriFragment;
		this.newSourceUriFragment = newSourceUriFragment;
	}

	@Override
	protected void doExecute() {
		Flow flow = SemanticCommandUtil.getElement(semanticModel, semanticUriFragment, Flow.class);
		Node newSource = SemanticCommandUtil.getElement(semanticModel, newSourceUriFragment, Node.class);
		flow.setSource(newSource);
	}

}
