/*******************************************************************************
 * Copyright (c) 2021 EclipseSource and others.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0, or the MIT License which is
 * available at https://opensource.org/licenses/MIT.
 *
 * SPDX-License-Identifier: EPL-2.0 OR MIT
 ******************************************************************************/
package org.eclipse.emfcloud.coffee.util;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceImpl;

public class CoffeeResourceImpl extends XMIResourceImpl implements CoffeeResource {

   public CoffeeResourceImpl(final URI uri) {
      super(uri);
   }

   @Override
   protected boolean useIDAttributes() {
      return false;
   }

   @Override
   protected boolean useUUIDs() {
      return true;
   }

   @Override
   protected boolean assignIDsWhileLoading() {
      return false;
   }

}
