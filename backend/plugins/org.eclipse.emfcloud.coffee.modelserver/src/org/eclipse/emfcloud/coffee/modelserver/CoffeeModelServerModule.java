package org.eclipse.emfcloud.coffee.modelserver;

import org.eclipse.emfcloud.coffee.modelserver.commands.contributions.AddAutomatedTaskCommandContribution;
import org.eclipse.emfcloud.coffee.modelserver.commands.contributions.AddDecisionNodeCommandContribution;
import org.eclipse.emfcloud.coffee.modelserver.commands.contributions.AddFlowCommandContribution;
import org.eclipse.emfcloud.coffee.modelserver.commands.contributions.AddManualTaskCommandContribution;
import org.eclipse.emfcloud.coffee.modelserver.commands.contributions.AddMergeNodeCommandContribution;
import org.eclipse.emfcloud.coffee.modelserver.commands.contributions.AddWeightedFlowCommandContribution;
import org.eclipse.emfcloud.coffee.modelserver.commands.contributions.RemoveFlowCommandContribution;
import org.eclipse.emfcloud.coffee.modelserver.commands.contributions.RemoveNodeCommandContribution;
import org.eclipse.emfcloud.coffee.modelserver.commands.contributions.SetTaskNameCommandContribution;
import org.eclipse.emfcloud.modelserver.common.codecs.Codec;
import org.eclipse.emfcloud.modelserver.common.utils.MapBinding;
import org.eclipse.emfcloud.modelserver.common.utils.MultiBinding;
import org.eclipse.emfcloud.modelserver.edit.CommandContribution;
import org.eclipse.emfcloud.modelserver.emf.common.ModelResourceManager;
import org.eclipse.emfcloud.modelserver.emf.configuration.EPackageConfiguration;
import org.eclipse.emfcloud.modelserver.emf.di.DefaultModelServerModule;
import org.eclipse.emfcloud.modelserver.glsp.notation.commands.contribution.ChangeBoundsCommandContribution;
import org.eclipse.emfcloud.modelserver.glsp.notation.commands.contribution.ChangeRoutingPointsCommandContribution;
import org.eclipse.emfcloud.modelserver.glsp.notation.model.NotationPackageConfiguration;

public class CoffeeModelServerModule extends DefaultModelServerModule {

	@Override
	protected Class<? extends ModelResourceManager> bindModelResourceManager() {
		return CoffeeModelResourceManager.class;
	}

	@Override
	protected void configureEPackages(final MultiBinding<EPackageConfiguration> binding) {
		super.configureEPackages(binding);
		binding.add(CoffeePackageConfiguration.class);
		binding.add(NotationPackageConfiguration.class);
	}

	@Override
	protected void configureCodecs(final MapBinding<String, Codec> binding) {
		super.configureCodecs(binding);
		binding.put(CoffeeResource.FILE_EXTENSION, CoffeeCodec.class);
	}

	@Override
	protected void configureCommandCodecs(MapBinding<String, CommandContribution> binding) {
		super.configureCommandCodecs(binding);

		// Nodes
		binding.put(AddManualTaskCommandContribution.TYPE, AddManualTaskCommandContribution.class);
		binding.put(AddAutomatedTaskCommandContribution.TYPE, AddAutomatedTaskCommandContribution.class);
		binding.put(AddDecisionNodeCommandContribution.TYPE, AddDecisionNodeCommandContribution.class);
		binding.put(AddMergeNodeCommandContribution.TYPE, AddMergeNodeCommandContribution.class);
		binding.put(RemoveNodeCommandContribution.TYPE, RemoveNodeCommandContribution.class);
		binding.put(SetTaskNameCommandContribution.TYPE, SetTaskNameCommandContribution.class);

		// Flows (Edges)
		binding.put(AddFlowCommandContribution.TYPE, AddFlowCommandContribution.class);
		binding.put(AddWeightedFlowCommandContribution.TYPE, AddWeightedFlowCommandContribution.class);
		binding.put(RemoveFlowCommandContribution.TYPE, RemoveFlowCommandContribution.class);

		// ChangeBounds
		binding.put(ChangeBoundsCommandContribution.TYPE, ChangeBoundsCommandContribution.class);
		// ChangeRoutingPoints
		binding.put(ChangeRoutingPointsCommandContribution.TYPE, ChangeRoutingPointsCommandContribution.class);
	}

}
