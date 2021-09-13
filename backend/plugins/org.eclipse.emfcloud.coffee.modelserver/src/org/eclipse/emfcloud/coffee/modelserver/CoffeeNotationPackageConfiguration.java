/*******************************************************************************
 * Copyright (c) 2019-2021 EclipseSource and others.
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0, or the MIT License which is
 * available at https://opensource.org/licenses/MIT.
 * 
 * SPDX-License-Identifier: EPL-2.0 OR MIT
 ******************************************************************************/
package org.eclipse.emfcloud.coffee.modelserver;

import java.util.Collection;
import org.eclipse.emfcloud.coffee.modelserver.wfnotation.WfnotationPackage;
import org.eclipse.emfcloud.modelserver.emf.configuration.EPackageConfiguration;

import com.google.common.collect.Lists;

public class CoffeeNotationPackageConfiguration implements EPackageConfiguration {

	@Override
	public String getId() {
		return WfnotationPackage.eINSTANCE.getNsURI();
	}

	@Override
	public Collection<String> getFileExtensions() {
		return Lists.newArrayList("coffeenotation");
	}

	@Override
	public void registerEPackage() {
		WfnotationPackage.eINSTANCE.eClass();
	}

}
