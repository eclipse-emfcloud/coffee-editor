/**
 * Copyright (c) 2019-2020 EclipseSource and others.
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0, or the MIT License which is
 * available at https://opensource.org/licenses/MIT.
 * 
 * SPDX-License-Identifier: EPL-2.0 OR MIT
 */
package org.eclipse.emfcloud.coffee.workflow.glsp.server.wfnotation;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Edge</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.emfcloud.coffee.workflow.glsp.server.wfnotation.Edge#getBendPoints <em>Bend Points</em>}</li>
 * </ul>
 *
 * @see org.eclipse.emfcloud.coffee.workflow.glsp.server.wfnotation.WfnotationPackage#getEdge()
 * @model
 * @generated
 */
public interface Edge extends DiagramElement {
	/**
	 * Returns the value of the '<em><b>Bend Points</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.emfcloud.coffee.workflow.glsp.server.wfnotation.Point}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Bend Points</em>' containment reference list.
	 * @see org.eclipse.emfcloud.coffee.workflow.glsp.server.wfnotation.WfnotationPackage#getEdge_BendPoints()
	 * @model containment="true"
	 * @generated
	 */
	EList<Point> getBendPoints();

} // Edge
