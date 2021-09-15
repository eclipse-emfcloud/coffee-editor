package org.eclipse.emfcloud.coffee.modelserver;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceImpl;

public class CoffeeResourceImpl extends XMIResourceImpl implements CoffeeResource {

	public CoffeeResourceImpl(final URI uri) {
		super(uri);
	}

	@Override
	protected boolean useIDAttributes() {
		return false;
	}

	@Override
	protected boolean useUUIDs() {
		return true;
	}

	@Override
	protected boolean assignIDsWhileLoading() {
		return false;
	}

}