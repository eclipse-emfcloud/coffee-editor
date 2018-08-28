package com.eclipsesource.workflow.ui.pages;

import java.util.Arrays;
import java.util.List;

import org.eclipse.emf.common.util.URI;
import org.eclipse.papyrus.infra.architecture.ArchitectureDomainManager;
import org.eclipse.papyrus.infra.core.architecture.RepresentationKind;
import org.eclipse.papyrus.infra.core.architecture.merged.MergedArchitectureContext;
import org.eclipse.papyrus.infra.core.architecture.merged.MergedArchitectureViewpoint;
import org.eclipse.papyrus.uml.diagram.wizards.pages.SelectRepresentationKindPage;
import org.eclipse.papyrus.uml.extensionpoints.profile.RegisteredProfile;

import com.eclipsesource.workflow.architecture.internal.utils.ArchitectureConstants;

public class MockSelectRepresentationKindPage extends SelectRepresentationKindPage {

	public MockSelectRepresentationKindPage(ContextProvider contextProvider) {
		super(contextProvider);
		// TODO Auto-generated constructor stub
	}

	@Override
	public List<String> getDiagramName() {
		RepresentationKind kind = getRepresentationKind();
		if (kind != null) {
			return Arrays.asList(kind.getName());
		} else {
			return super.getDiagramName();
		}
	}

	@Override
	public boolean isPageComplete() {
		return true;
	}

	@Override
	public String getProfileURI() {
		return URI.createURI(RegisteredProfile.getRegisteredProfile("Workflow").getPath()).toString();
	}

	@Override
	protected RepresentationKind[] getSelectedRepresentationKinds() {
		RepresentationKind kind = getRepresentationKind();
		return kind != null ? new RepresentationKind[] { kind } : super.getSelectedRepresentationKinds();
	}

	private RepresentationKind getRepresentationKind() {
		MergedArchitectureContext context = ArchitectureDomainManager.getInstance()
				.getArchitectureContextById(ArchitectureConstants.CONTEXT_ID);

		MergedArchitectureViewpoint viewpoint = context.getViewpoints().stream()
				.filter(v -> v.getId().equals(ArchitectureConstants.VIEWPOINT_ID)).findFirst().orElse(null);
		if (viewpoint != null) {
			return viewpoint.getRepresentationKinds().stream()
					.filter(r -> r.getId().equals(ArchitectureConstants.DIAGRAM_ID)).findFirst().orElse(null);
		}
		return null;
	}

}
