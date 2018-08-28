package com.eclipsesource.workflow.architecture.internal.utils;

import static com.eclipsesource.workflow.architecture.internal.utils.ArchitectureConstants.WORKFLOW_PROFILE;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.Stereotype;

public interface WorkflowExtensionUtil {
	
	public static boolean hasWorkflowExtension(EObject eobject) {
		if (!(eobject instanceof NamedElement))
			return false;
		NamedElement namedElement = (NamedElement) eobject;
		return namedElement.getAppliedStereotypes().stream()
				.anyMatch(st -> st.getProfile().getName().equals(WORKFLOW_PROFILE));
	}

	public static Object getStereotypePropertyValue(final NamedElement namedElement, final String name,
			Stereotype stereotype) {

		if (stereotype != null) {
			return namedElement.getValue(stereotype, name);
		}
		return null;
	}
	
	public static <T> T getStereotypePropertyValue(final NamedElement namedElement, final String name,
			Stereotype stereotype, Class<T> expectedClazz) {
		Object value = getStereotypePropertyValue(namedElement, name, stereotype);
		return expectedClazz.isInstance(value) ? expectedClazz.cast(value) : null;
	}

	public static void setStereotypePropertyValue(final NamedElement namedElement, final String name,
			Stereotype stereotype, Object value) {
		if (stereotype != null) {
			namedElement.setValue(stereotype, name, value);
		}
	}
	
	public static String getStringProperty(final NamedElement namedElement, String name, Stereotype stereotype) {
		Object value = getStereotypePropertyValue(namedElement, name, stereotype);
		if(value instanceof NamedElement) {
			return ((NamedElement) value).getName();
		}
		return null;
	}
	
}
