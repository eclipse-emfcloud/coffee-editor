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
