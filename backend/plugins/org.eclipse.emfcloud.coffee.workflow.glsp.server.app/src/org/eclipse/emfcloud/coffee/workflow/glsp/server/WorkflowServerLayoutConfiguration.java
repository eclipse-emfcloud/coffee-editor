/*******************************************************************************
 * Copyright (c) 2019-2020 EclipseSource and others.
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0, or the MIT License which is
 * available at https://opensource.org/licenses/MIT.
 * 
 * SPDX-License-Identifier: EPL-2.0 OR MIT
 ******************************************************************************/
package org.eclipse.emfcloud.coffee.workflow.glsp.server;

import org.eclipse.glsp.server.layout.ServerLayoutConfiguration;
import org.eclipse.glsp.server.layout.ServerLayoutKind;

public class WorkflowServerLayoutConfiguration implements ServerLayoutConfiguration {

	@Override
	public ServerLayoutKind getLayoutKind() {
		return ServerLayoutKind.MANUAL;
	}

}
