package org.eclipse.emfcloud.coffee.modelserver.app;

import org.eclipse.emfcloud.coffee.modelserver.commands.contributions.AddAutomatedTaskCommandContribution;
import org.eclipse.emfcloud.coffee.modelserver.commands.contributions.AddDecisionNodeCommandContribution;
import org.eclipse.emfcloud.coffee.modelserver.commands.contributions.AddManualTaskCommandContribution;
import org.eclipse.emfcloud.coffee.modelserver.commands.contributions.AddMergeNodeCommandContribution;
import org.eclipse.emfcloud.modelserver.common.utils.MapBinding;
import org.eclipse.emfcloud.modelserver.common.utils.MultiBinding;
import org.eclipse.emfcloud.modelserver.edit.CommandContribution;
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

	@Override
	protected void configureCommandCodecs(MapBinding<String, CommandContribution> binding) {
		super.configureCommandCodecs(binding);

		// Nodes
		binding.put(AddManualTaskCommandContribution.TYPE, AddManualTaskCommandContribution.class);
		binding.put(AddAutomatedTaskCommandContribution.TYPE, AddAutomatedTaskCommandContribution.class);
		binding.put(AddDecisionNodeCommandContribution.TYPE, AddDecisionNodeCommandContribution.class);
		binding.put(AddMergeNodeCommandContribution.TYPE, AddMergeNodeCommandContribution.class);
	}

}
