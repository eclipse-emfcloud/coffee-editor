/**
 * Copyright (c) 2022 EclipseSource and others.
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0, or the MIT License which is
 * available at https://opensource.org/licenses/MIT.
 * 
 * SPDX-License-Identifier: EPL-2.0 OR MIT
 */
package org.eclipse.emfcloud.coffee.util;

import org.eclipse.emf.common.util.URI;

import org.eclipse.emf.ecore.resource.Resource;

import org.eclipse.emf.ecore.resource.impl.ResourceFactoryImpl;

/**
 * <!-- begin-user-doc -->
 * The <b>Resource Factory</b> associated with the package.
 * <!-- end-user-doc -->
 * @see org.eclipse.emfcloud.coffee.util.CoffeeResourceImpl
 * @generated
 */
public class CoffeeResourceFactoryImpl extends ResourceFactoryImpl {
   /**
    * Creates an instance of the resource factory.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   public CoffeeResourceFactoryImpl() {
      super();
   }

   /**
    * Creates an instance of the resource.
    * <!-- begin-user-doc -->
    * <!-- end-user-doc -->
    * @generated
    */
   @Override
   public Resource createResource(URI uri) {
      Resource result = new CoffeeResourceImpl(uri);
      return result;
   }

} //CoffeeResourceFactoryImpl
