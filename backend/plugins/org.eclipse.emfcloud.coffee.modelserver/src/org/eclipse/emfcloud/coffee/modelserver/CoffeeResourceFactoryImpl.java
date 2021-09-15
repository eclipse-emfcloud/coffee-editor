package org.eclipse.emfcloud.coffee.modelserver;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceFactoryImpl;

public class CoffeeResourceFactoryImpl extends ResourceFactoryImpl implements CoffeeResource.Factory {

	public CoffeeResourceFactoryImpl() {
		super();
	}

	@Override
	public Resource createResource(final URI uri) {
		CoffeeResource resource = new CoffeeResourceImpl(uri);
		return resource;
	}

}
