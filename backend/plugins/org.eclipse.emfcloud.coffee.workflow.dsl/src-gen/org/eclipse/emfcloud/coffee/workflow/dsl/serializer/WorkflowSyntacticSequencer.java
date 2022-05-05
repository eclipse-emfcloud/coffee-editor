/*******************************************************************************
 * Copyright (c) 2019-2022 EclipseSource and others.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0, or the MIT License which is
 * available at https://opensource.org/licenses/MIT.
 *
 * SPDX-License-Identifier: EPL-2.0 OR MIT
 ******************************************************************************/
package org.eclipse.emfcloud.coffee.workflow.dsl.serializer;

import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emfcloud.coffee.workflow.dsl.services.WorkflowGrammarAccess;
import org.eclipse.xtext.IGrammarAccess;
import org.eclipse.xtext.RuleCall;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.serializer.analysis.GrammarAlias.AbstractElementAlias;
import org.eclipse.xtext.serializer.analysis.ISyntacticSequencerPDAProvider.ISynTransition;
import org.eclipse.xtext.serializer.sequencer.AbstractSyntacticSequencer;

import com.google.inject.Inject;

@SuppressWarnings("all")
public class WorkflowSyntacticSequencer extends AbstractSyntacticSequencer {

   protected WorkflowGrammarAccess grammarAccess;

   @Inject
   protected void init(final IGrammarAccess access) {
      grammarAccess = (WorkflowGrammarAccess) access;
   }

   @Override
   protected String getUnassignedRuleCallToken(final EObject semanticObject, final RuleCall ruleCall,
      final INode node) {
      return "";
   }

   @Override
   protected void emitUnassignedTokens(final EObject semanticObject, final ISynTransition transition,
      final INode fromNode, final INode toNode) {
      if (transition.getAmbiguousSyntaxes().isEmpty()) {
         return;
      }
      List<INode> transitionNodes = collectNodes(fromNode, toNode);
      for (AbstractElementAlias syntax : transition.getAmbiguousSyntaxes()) {
         List<INode> syntaxNodes = getNodesFor(transitionNodes, syntax);
         acceptNodes(getLastNavigableState(), syntaxNodes);
      }
   }

}
