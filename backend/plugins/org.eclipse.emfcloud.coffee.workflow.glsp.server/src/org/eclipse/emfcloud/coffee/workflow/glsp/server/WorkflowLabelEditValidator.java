/********************************************************************************
 * Copyright (c) 2021-2022 EclipseSource and others.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0, or the MIT License which is
 * available at https://opensource.org/licenses/MIT.
 *
 * SPDX-License-Identifier: EPL-2.0 OR MIT
 ********************************************************************************/
package org.eclipse.emfcloud.coffee.workflow.glsp.server;

import java.util.List;
import java.util.regex.Pattern;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emfcloud.coffee.CoffeePackage;
import org.eclipse.emfcloud.coffee.workflow.glsp.server.model.WorkflowModelServerAccess;
import org.eclipse.emfcloud.coffee.workflow.glsp.server.model.WorkflowModelState;
import org.eclipse.emfcloud.coffee.workflow.glsp.server.util.ModelTypes;
import org.eclipse.emfcloud.coffee.workflow.glsp.server.wfgraph.impl.TaskNodeImpl;
import org.eclipse.emfcloud.modelserver.emf.common.EMFFacetConstraints;
import org.eclipse.glsp.graph.GCompartment;
import org.eclipse.glsp.graph.GLabel;
import org.eclipse.glsp.graph.GModelElement;
import org.eclipse.glsp.server.features.directediting.LabelEditValidator;
import org.eclipse.glsp.server.features.directediting.ValidationStatus;
import org.eclipse.glsp.server.model.GModelState;

import com.google.inject.Inject;

public class WorkflowLabelEditValidator implements LabelEditValidator {

   @Inject
   protected GModelState modelState;

   @Override
   public ValidationStatus validate(final String label, final GModelElement element) {
      WorkflowModelServerAccess modelAccess = WorkflowModelState.getModelAccess(modelState);
      GModelElement parent = getRoot(element);

      String featureId = getFeatureId(element.getType());

      if (featureId != null) {
         EMFFacetConstraints constraints = modelAccess.getConstraintList(getElementId(parent.getType()), featureId);
         if (constraints != null) {
            return checkConstraints(constraints, label, parent);
         }
      }
      return ValidationStatus.ok();
   }

   private String getElementId(final String type) {
      if (type.equals(ModelTypes.AUTOMATED_TASK)) {
         return EcoreUtil.getURI(CoffeePackage.Literals.AUTOMATIC_TASK).toString();
      }
      if (type.equals(ModelTypes.MANUAL_TASK)) {
         return EcoreUtil.getURI(CoffeePackage.Literals.MANUAL_TASK).toString();
      }
      return null;
   }

   private String getFeatureId(final String type) {
      if (type.equals(ModelTypes.LABEL_HEADING)) {
         return CoffeePackage.Literals.TASK__NAME.getName();
      }
      return null;
   }

   @SuppressWarnings("checkstyle:CyclomaticComplexity")
   private ValidationStatus checkConstraints(final EMFFacetConstraints constraints, final String text,
      final GModelElement node) {
      if (constraints.getMinLength() != null) {
         if (text.length() < constraints.getMinLength()) {
            return ValidationStatus
               .error("Name must be at least " + constraints.getMinLength() + " character(s) long");
         }
      }
      if (constraints.getMaxLength() != -1) {
         if (text.length() > constraints.getMaxLength()) {
            return ValidationStatus
               .error("Name must not be longer than " + constraints.getMaxLength() + " character(s)");
         }
      }
      List<String> patterns = constraints.getPattern();
      if (!(patterns.isEmpty())) {
         for (String pattern : patterns) {
            if (!Pattern.matches(pattern, text)) {
               if (node instanceof TaskNodeImpl) {
                  return ValidationStatus.error("Must consist only of letters, numbers, - and spaces");
               }
               return ValidationStatus.error("Must fit the following expression: " + pattern);
            }
         }
      }
      return ValidationStatus.ok();
   }

   private GModelElement getRoot(final GModelElement element) {
      if (element instanceof GLabel || element instanceof GCompartment) {
         return getRoot(element.getParent());
      }
      return element;
   }

}
