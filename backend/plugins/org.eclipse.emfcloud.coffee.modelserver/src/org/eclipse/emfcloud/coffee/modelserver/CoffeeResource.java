package org.eclipse.emfcloud.coffee.modelserver;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.xmi.XMIResource;

public interface CoffeeResource extends XMIResource {

	public interface Factory extends Resource.Factory {
		Factory INSTANCE = new CoffeeResourceFactoryImpl();
	}

	String FILE_EXTENSION = "coffee";

}
