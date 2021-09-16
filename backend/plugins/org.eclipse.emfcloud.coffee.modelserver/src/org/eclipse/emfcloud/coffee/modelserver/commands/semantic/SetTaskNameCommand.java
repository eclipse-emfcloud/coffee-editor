/********************************************************************************
 * Copyright (c) 2021 EclipseSource and others.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0, or the MIT License which is
 * available at https://opensource.org/licenses/MIT.
 *
 * SPDX-License-Identifier: EPL-2.0 OR MIT
 ********************************************************************************/
package org.eclipse.emfcloud.coffee.modelserver.commands.semantic;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emfcloud.coffee.Task;
import org.eclipse.emfcloud.coffee.modelserver.commands.util.SemanticCommandUtil;

public class SetTaskNameCommand extends SemanticElementCommand {

	protected String semanticUriFragment;
	protected String newName;

	public SetTaskNameCommand(final EditingDomain domain, final URI modelUri, final String semanticUriFragment,
			final String newName) {
		super(domain, modelUri);
		this.semanticUriFragment = semanticUriFragment;
		this.newName = newName;
	}

	@Override
	protected void doExecute() {
		Task elementToRename = SemanticCommandUtil.getElement(semanticModel, semanticUriFragment, Task.class);
		elementToRename.setName(newName);
	}

}
