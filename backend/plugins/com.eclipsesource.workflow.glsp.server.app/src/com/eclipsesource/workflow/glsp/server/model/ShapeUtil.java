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
package com.eclipsesource.workflow.glsp.server.model;

import org.eclipse.glsp.graph.GPoint;

import com.eclipsesource.workflow.glsp.server.wfnotation.Dimension;
import com.eclipsesource.workflow.glsp.server.wfnotation.Point;
import com.eclipsesource.workflow.glsp.server.wfnotation.WfnotationFactory;

public class ShapeUtil {

	public static Point point(GPoint location) {
		Point point = WfnotationFactory.eINSTANCE.createPoint();
		point.setX(location.getX());
		point.setY(location.getY());
		return point;
	}

	public static Point point(double x, double y) {
		Point point = WfnotationFactory.eINSTANCE.createPoint();
		point.setX(x);
		point.setY(y);
		return point;
	}

	public static Dimension dimension(double width, double height) {
		Dimension dimension = WfnotationFactory.eINSTANCE.createDimension();
		dimension.setWidth(width);
		dimension.setHeight(height);
		return dimension;
	}

}
