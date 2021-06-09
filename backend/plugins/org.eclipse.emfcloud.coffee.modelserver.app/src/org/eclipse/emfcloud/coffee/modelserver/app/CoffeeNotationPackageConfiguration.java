package org.eclipse.emfcloud.coffee.modelserver.app;

import java.util.Collection;
import org.eclipse.emfcloud.coffee.workflow.glsp.server.wfnotation.WfnotationPackage;
import org.eclipse.emfcloud.modelserver.emf.configuration.EPackageConfiguration;

import com.google.common.collect.Lists;

public class CoffeeNotationPackageConfiguration implements EPackageConfiguration {

	@Override
	public String getId() {
		return WfnotationPackage.eINSTANCE.getNsURI();
	}

	@Override
	public Collection<String> getFileExtensions() {
		return Lists.newArrayList("coffeenotation");
	}

	@Override
	public void registerEPackage() {
		WfnotationPackage.eINSTANCE.eClass();
	}

}
