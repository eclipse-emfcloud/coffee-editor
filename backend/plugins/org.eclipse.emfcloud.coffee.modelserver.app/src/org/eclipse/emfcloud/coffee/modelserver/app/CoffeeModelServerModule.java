package org.eclipse.emfcloud.coffee.modelserver.app;

import org.eclipse.emfcloud.modelserver.common.Routing;
import org.eclipse.emfcloud.modelserver.common.codecs.Codec;
import org.eclipse.emfcloud.modelserver.common.utils.MapBinding;
import org.eclipse.emfcloud.modelserver.common.utils.MultiBinding;
import org.eclipse.emfcloud.modelserver.edit.CommandContribution;
import org.eclipse.emfcloud.modelserver.emf.configuration.EPackageConfiguration;
import org.eclipse.emfcloud.modelserver.emf.di.DefaultModelServerModule;

public class CoffeeModelServerModule extends DefaultModelServerModule {

	@Override
	protected void configureEPackages(final MultiBinding<EPackageConfiguration> binding) {
		super.configureEPackages(binding);
		binding.add(CoffeePackageConfiguration.class);
	}
}
