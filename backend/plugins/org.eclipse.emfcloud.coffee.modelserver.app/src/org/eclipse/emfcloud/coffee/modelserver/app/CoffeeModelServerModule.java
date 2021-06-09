package org.eclipse.emfcloud.coffee.modelserver.app;

import org.eclipse.emfcloud.modelserver.common.utils.MultiBinding;
import org.eclipse.emfcloud.modelserver.emf.common.ModelResourceManager;
import org.eclipse.emfcloud.modelserver.emf.configuration.EPackageConfiguration;
import org.eclipse.emfcloud.modelserver.emf.di.DefaultModelServerModule;

public class CoffeeModelServerModule extends DefaultModelServerModule {

	@Override
	protected Class<? extends ModelResourceManager> bindModelResourceManager() {
		return CoffeeModelResourceManager.class;
	}

	@Override
	protected void configureEPackages(final MultiBinding<EPackageConfiguration> binding) {
		super.configureEPackages(binding);
		binding.add(CoffeePackageConfiguration.class);
		binding.add(CoffeeNotationPackageConfiguration.class);
	}
}
