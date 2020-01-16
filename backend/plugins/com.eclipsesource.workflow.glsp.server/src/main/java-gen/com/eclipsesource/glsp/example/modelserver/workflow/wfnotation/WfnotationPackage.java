/**
 * Copyright (c) 2019 EclipseSource and others.
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 * 
 * This Source Code may also be made available under the following Secondary
 * Licenses when the conditions for such availability set forth in the Eclipse
 * Public License v. 2.0 are satisfied: GNU General Public License, version 2
 * with the GNU Classpath Exception which is available at
 * https://www.gnu.org/software/classpath/license.html.
 * 
 * SPDX-License-Identifier: EPL-2.0 OR GPL-2.0 WITH Classpath-exception-2.0
 */
package com.eclipsesource.glsp.example.modelserver.workflow.wfnotation;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc --> The <b>Package</b> for the model. It contains
 * accessors for the meta objects to represent
 * <ul>
 * <li>each class,</li>
 * <li>each feature of each class,</li>
 * <li>each operation of each class,</li>
 * <li>each enum,</li>
 * <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * 
 * @see com.eclipsesource.glsp.example.modelserver.workflow.wfnotation.WfnotationFactory
 * @model kind="package"
 * @generated
 */
public interface WfnotationPackage extends EPackage {
	/**
	 * The package name. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	String eNAME = "wfnotation";

	/**
	 * The package namespace URI. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	String eNS_URI = "http://www.eclipsesource.com/glsp/examples/workflow/notation";

	/**
	 * The package namespace name. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	String eNS_PREFIX = "wfnotation";

	/**
	 * The singleton instance of the package. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 */
	WfnotationPackage eINSTANCE = com.eclipsesource.glsp.example.modelserver.workflow.wfnotation.impl.WfnotationPackageImpl
			.init();

	/**
	 * The meta object id for the
	 * '{@link com.eclipsesource.glsp.example.modelserver.workflow.wfnotation.impl.DiagramImpl
	 * <em>Diagram</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see com.eclipsesource.glsp.example.modelserver.workflow.wfnotation.impl.DiagramImpl
	 * @see com.eclipsesource.glsp.example.modelserver.workflow.wfnotation.impl.WfnotationPackageImpl#getDiagram()
	 * @generated
	 */
	int DIAGRAM = 1;

	/**
	 * The meta object id for the
	 * '{@link com.eclipsesource.glsp.example.modelserver.workflow.wfnotation.impl.DiagramElementImpl
	 * <em>Diagram Element</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @see com.eclipsesource.glsp.example.modelserver.workflow.wfnotation.impl.DiagramElementImpl
	 * @see com.eclipsesource.glsp.example.modelserver.workflow.wfnotation.impl.WfnotationPackageImpl#getDiagramElement()
	 * @generated
	 */
	int DIAGRAM_ELEMENT = 0;

	/**
	 * The feature id for the '<em><b>Semantic Element</b></em>' containment
	 * reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int DIAGRAM_ELEMENT__SEMANTIC_ELEMENT = 0;

	/**
	 * The number of structural features of the '<em>Diagram Element</em>' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int DIAGRAM_ELEMENT_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>Diagram Element</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int DIAGRAM_ELEMENT_OPERATION_COUNT = 0;

	/**
	 * The feature id for the '<em><b>Semantic Element</b></em>' containment
	 * reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int DIAGRAM__SEMANTIC_ELEMENT = DIAGRAM_ELEMENT__SEMANTIC_ELEMENT;

	/**
	 * The feature id for the '<em><b>Elements</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int DIAGRAM__ELEMENTS = DIAGRAM_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Diagram</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int DIAGRAM_FEATURE_COUNT = DIAGRAM_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Diagram</em>' class. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int DIAGRAM_OPERATION_COUNT = DIAGRAM_ELEMENT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the
	 * '{@link com.eclipsesource.glsp.example.modelserver.workflow.wfnotation.impl.SemanticProxyImpl
	 * <em>Semantic Proxy</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @see com.eclipsesource.glsp.example.modelserver.workflow.wfnotation.impl.SemanticProxyImpl
	 * @see com.eclipsesource.glsp.example.modelserver.workflow.wfnotation.impl.WfnotationPackageImpl#getSemanticProxy()
	 * @generated
	 */
	int SEMANTIC_PROXY = 2;

	/**
	 * The feature id for the '<em><b>Uri</b></em>' attribute. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SEMANTIC_PROXY__URI = 0;

	/**
	 * The feature id for the '<em><b>Resolved Element</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SEMANTIC_PROXY__RESOLVED_ELEMENT = 1;

	/**
	 * The number of structural features of the '<em>Semantic Proxy</em>' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SEMANTIC_PROXY_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>Semantic Proxy</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SEMANTIC_PROXY_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the
	 * '{@link com.eclipsesource.glsp.example.modelserver.workflow.wfnotation.impl.ShapeImpl
	 * <em>Shape</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see com.eclipsesource.glsp.example.modelserver.workflow.wfnotation.impl.ShapeImpl
	 * @see com.eclipsesource.glsp.example.modelserver.workflow.wfnotation.impl.WfnotationPackageImpl#getShape()
	 * @generated
	 */
	int SHAPE = 3;

	/**
	 * The feature id for the '<em><b>Semantic Element</b></em>' containment
	 * reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SHAPE__SEMANTIC_ELEMENT = DIAGRAM_ELEMENT__SEMANTIC_ELEMENT;

	/**
	 * The feature id for the '<em><b>Position</b></em>' containment reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SHAPE__POSITION = DIAGRAM_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Size</b></em>' containment reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SHAPE__SIZE = DIAGRAM_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Shape</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SHAPE_FEATURE_COUNT = DIAGRAM_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The number of operations of the '<em>Shape</em>' class. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SHAPE_OPERATION_COUNT = DIAGRAM_ELEMENT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the
	 * '{@link com.eclipsesource.glsp.example.modelserver.workflow.wfnotation.impl.EdgeImpl
	 * <em>Edge</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see com.eclipsesource.glsp.example.modelserver.workflow.wfnotation.impl.EdgeImpl
	 * @see com.eclipsesource.glsp.example.modelserver.workflow.wfnotation.impl.WfnotationPackageImpl#getEdge()
	 * @generated
	 */
	int EDGE = 4;

	/**
	 * The feature id for the '<em><b>Semantic Element</b></em>' containment
	 * reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int EDGE__SEMANTIC_ELEMENT = DIAGRAM_ELEMENT__SEMANTIC_ELEMENT;

	/**
	 * The feature id for the '<em><b>Bend Points</b></em>' containment reference
	 * list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int EDGE__BEND_POINTS = DIAGRAM_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Edge</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int EDGE_FEATURE_COUNT = DIAGRAM_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Edge</em>' class. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int EDGE_OPERATION_COUNT = DIAGRAM_ELEMENT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the
	 * '{@link com.eclipsesource.glsp.example.modelserver.workflow.wfnotation.impl.PointImpl
	 * <em>Point</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see com.eclipsesource.glsp.example.modelserver.workflow.wfnotation.impl.PointImpl
	 * @see com.eclipsesource.glsp.example.modelserver.workflow.wfnotation.impl.WfnotationPackageImpl#getPoint()
	 * @generated
	 */
	int POINT = 5;

	/**
	 * The feature id for the '<em><b>X</b></em>' attribute. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int POINT__X = 0;

	/**
	 * The feature id for the '<em><b>Y</b></em>' attribute. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int POINT__Y = 1;

	/**
	 * The number of structural features of the '<em>Point</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int POINT_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>Point</em>' class. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int POINT_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the
	 * '{@link com.eclipsesource.glsp.example.modelserver.workflow.wfnotation.impl.DimensionImpl
	 * <em>Dimension</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see com.eclipsesource.glsp.example.modelserver.workflow.wfnotation.impl.DimensionImpl
	 * @see com.eclipsesource.glsp.example.modelserver.workflow.wfnotation.impl.WfnotationPackageImpl#getDimension()
	 * @generated
	 */
	int DIMENSION = 6;

	/**
	 * The feature id for the '<em><b>Width</b></em>' attribute. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int DIMENSION__WIDTH = 0;

	/**
	 * The feature id for the '<em><b>Height</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int DIMENSION__HEIGHT = 1;

	/**
	 * The number of structural features of the '<em>Dimension</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int DIMENSION_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>Dimension</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int DIMENSION_OPERATION_COUNT = 0;

	/**
	 * Returns the meta object for class
	 * '{@link com.eclipsesource.glsp.example.modelserver.workflow.wfnotation.Diagram
	 * <em>Diagram</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Diagram</em>'.
	 * @see com.eclipsesource.glsp.example.modelserver.workflow.wfnotation.Diagram
	 * @generated
	 */
	EClass getDiagram();

	/**
	 * Returns the meta object for the containment reference list
	 * '{@link com.eclipsesource.glsp.example.modelserver.workflow.wfnotation.Diagram#getElements
	 * <em>Elements</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list
	 *         '<em>Elements</em>'.
	 * @see com.eclipsesource.glsp.example.modelserver.workflow.wfnotation.Diagram#getElements()
	 * @see #getDiagram()
	 * @generated
	 */
	EReference getDiagram_Elements();

	/**
	 * Returns the meta object for class
	 * '{@link com.eclipsesource.glsp.example.modelserver.workflow.wfnotation.DiagramElement
	 * <em>Diagram Element</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Diagram Element</em>'.
	 * @see com.eclipsesource.glsp.example.modelserver.workflow.wfnotation.DiagramElement
	 * @generated
	 */
	EClass getDiagramElement();

	/**
	 * Returns the meta object for the containment reference
	 * '{@link com.eclipsesource.glsp.example.modelserver.workflow.wfnotation.DiagramElement#getSemanticElement
	 * <em>Semantic Element</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>Semantic
	 *         Element</em>'.
	 * @see com.eclipsesource.glsp.example.modelserver.workflow.wfnotation.DiagramElement#getSemanticElement()
	 * @see #getDiagramElement()
	 * @generated
	 */
	EReference getDiagramElement_SemanticElement();

	/**
	 * Returns the meta object for class
	 * '{@link com.eclipsesource.glsp.example.modelserver.workflow.wfnotation.SemanticProxy
	 * <em>Semantic Proxy</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Semantic Proxy</em>'.
	 * @see com.eclipsesource.glsp.example.modelserver.workflow.wfnotation.SemanticProxy
	 * @generated
	 */
	EClass getSemanticProxy();

	/**
	 * Returns the meta object for the attribute
	 * '{@link com.eclipsesource.glsp.example.modelserver.workflow.wfnotation.SemanticProxy#getUri
	 * <em>Uri</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Uri</em>'.
	 * @see com.eclipsesource.glsp.example.modelserver.workflow.wfnotation.SemanticProxy#getUri()
	 * @see #getSemanticProxy()
	 * @generated
	 */
	EAttribute getSemanticProxy_Uri();

	/**
	 * Returns the meta object for the reference
	 * '{@link com.eclipsesource.glsp.example.modelserver.workflow.wfnotation.SemanticProxy#getResolvedElement
	 * <em>Resolved Element</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the reference '<em>Resolved Element</em>'.
	 * @see com.eclipsesource.glsp.example.modelserver.workflow.wfnotation.SemanticProxy#getResolvedElement()
	 * @see #getSemanticProxy()
	 * @generated
	 */
	EReference getSemanticProxy_ResolvedElement();

	/**
	 * Returns the meta object for class
	 * '{@link com.eclipsesource.glsp.example.modelserver.workflow.wfnotation.Shape
	 * <em>Shape</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Shape</em>'.
	 * @see com.eclipsesource.glsp.example.modelserver.workflow.wfnotation.Shape
	 * @generated
	 */
	EClass getShape();

	/**
	 * Returns the meta object for the containment reference
	 * '{@link com.eclipsesource.glsp.example.modelserver.workflow.wfnotation.Shape#getPosition
	 * <em>Position</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>Position</em>'.
	 * @see com.eclipsesource.glsp.example.modelserver.workflow.wfnotation.Shape#getPosition()
	 * @see #getShape()
	 * @generated
	 */
	EReference getShape_Position();

	/**
	 * Returns the meta object for the containment reference
	 * '{@link com.eclipsesource.glsp.example.modelserver.workflow.wfnotation.Shape#getSize
	 * <em>Size</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>Size</em>'.
	 * @see com.eclipsesource.glsp.example.modelserver.workflow.wfnotation.Shape#getSize()
	 * @see #getShape()
	 * @generated
	 */
	EReference getShape_Size();

	/**
	 * Returns the meta object for class
	 * '{@link com.eclipsesource.glsp.example.modelserver.workflow.wfnotation.Edge
	 * <em>Edge</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Edge</em>'.
	 * @see com.eclipsesource.glsp.example.modelserver.workflow.wfnotation.Edge
	 * @generated
	 */
	EClass getEdge();

	/**
	 * Returns the meta object for the containment reference list
	 * '{@link com.eclipsesource.glsp.example.modelserver.workflow.wfnotation.Edge#getBendPoints
	 * <em>Bend Points</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list '<em>Bend
	 *         Points</em>'.
	 * @see com.eclipsesource.glsp.example.modelserver.workflow.wfnotation.Edge#getBendPoints()
	 * @see #getEdge()
	 * @generated
	 */
	EReference getEdge_BendPoints();

	/**
	 * Returns the meta object for class
	 * '{@link com.eclipsesource.glsp.example.modelserver.workflow.wfnotation.Point
	 * <em>Point</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Point</em>'.
	 * @see com.eclipsesource.glsp.example.modelserver.workflow.wfnotation.Point
	 * @generated
	 */
	EClass getPoint();

	/**
	 * Returns the meta object for the attribute
	 * '{@link com.eclipsesource.glsp.example.modelserver.workflow.wfnotation.Point#getX
	 * <em>X</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>X</em>'.
	 * @see com.eclipsesource.glsp.example.modelserver.workflow.wfnotation.Point#getX()
	 * @see #getPoint()
	 * @generated
	 */
	EAttribute getPoint_X();

	/**
	 * Returns the meta object for the attribute
	 * '{@link com.eclipsesource.glsp.example.modelserver.workflow.wfnotation.Point#getY
	 * <em>Y</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Y</em>'.
	 * @see com.eclipsesource.glsp.example.modelserver.workflow.wfnotation.Point#getY()
	 * @see #getPoint()
	 * @generated
	 */
	EAttribute getPoint_Y();

	/**
	 * Returns the meta object for class
	 * '{@link com.eclipsesource.glsp.example.modelserver.workflow.wfnotation.Dimension
	 * <em>Dimension</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Dimension</em>'.
	 * @see com.eclipsesource.glsp.example.modelserver.workflow.wfnotation.Dimension
	 * @generated
	 */
	EClass getDimension();

	/**
	 * Returns the meta object for the attribute
	 * '{@link com.eclipsesource.glsp.example.modelserver.workflow.wfnotation.Dimension#getWidth
	 * <em>Width</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Width</em>'.
	 * @see com.eclipsesource.glsp.example.modelserver.workflow.wfnotation.Dimension#getWidth()
	 * @see #getDimension()
	 * @generated
	 */
	EAttribute getDimension_Width();

	/**
	 * Returns the meta object for the attribute
	 * '{@link com.eclipsesource.glsp.example.modelserver.workflow.wfnotation.Dimension#getHeight
	 * <em>Height</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Height</em>'.
	 * @see com.eclipsesource.glsp.example.modelserver.workflow.wfnotation.Dimension#getHeight()
	 * @see #getDimension()
	 * @generated
	 */
	EAttribute getDimension_Height();

	/**
	 * Returns the factory that creates the instances of the model. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	WfnotationFactory getWfnotationFactory();

	/**
	 * <!-- begin-user-doc --> Defines literals for the meta objects that represent
	 * <ul>
	 * <li>each class,</li>
	 * <li>each feature of each class,</li>
	 * <li>each operation of each class,</li>
	 * <li>each enum,</li>
	 * <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the
		 * '{@link com.eclipsesource.glsp.example.modelserver.workflow.wfnotation.impl.DiagramImpl
		 * <em>Diagram</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see com.eclipsesource.glsp.example.modelserver.workflow.wfnotation.impl.DiagramImpl
		 * @see com.eclipsesource.glsp.example.modelserver.workflow.wfnotation.impl.WfnotationPackageImpl#getDiagram()
		 * @generated
		 */
		EClass DIAGRAM = eINSTANCE.getDiagram();

		/**
		 * The meta object literal for the '<em><b>Elements</b></em>' containment
		 * reference list feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference DIAGRAM__ELEMENTS = eINSTANCE.getDiagram_Elements();

		/**
		 * The meta object literal for the
		 * '{@link com.eclipsesource.glsp.example.modelserver.workflow.wfnotation.impl.DiagramElementImpl
		 * <em>Diagram Element</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc
		 * -->
		 * 
		 * @see com.eclipsesource.glsp.example.modelserver.workflow.wfnotation.impl.DiagramElementImpl
		 * @see com.eclipsesource.glsp.example.modelserver.workflow.wfnotation.impl.WfnotationPackageImpl#getDiagramElement()
		 * @generated
		 */
		EClass DIAGRAM_ELEMENT = eINSTANCE.getDiagramElement();

		/**
		 * The meta object literal for the '<em><b>Semantic Element</b></em>'
		 * containment reference feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference DIAGRAM_ELEMENT__SEMANTIC_ELEMENT = eINSTANCE.getDiagramElement_SemanticElement();

		/**
		 * The meta object literal for the
		 * '{@link com.eclipsesource.glsp.example.modelserver.workflow.wfnotation.impl.SemanticProxyImpl
		 * <em>Semantic Proxy</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc
		 * -->
		 * 
		 * @see com.eclipsesource.glsp.example.modelserver.workflow.wfnotation.impl.SemanticProxyImpl
		 * @see com.eclipsesource.glsp.example.modelserver.workflow.wfnotation.impl.WfnotationPackageImpl#getSemanticProxy()
		 * @generated
		 */
		EClass SEMANTIC_PROXY = eINSTANCE.getSemanticProxy();

		/**
		 * The meta object literal for the '<em><b>Uri</b></em>' attribute feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute SEMANTIC_PROXY__URI = eINSTANCE.getSemanticProxy_Uri();

		/**
		 * The meta object literal for the '<em><b>Resolved Element</b></em>' reference
		 * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference SEMANTIC_PROXY__RESOLVED_ELEMENT = eINSTANCE.getSemanticProxy_ResolvedElement();

		/**
		 * The meta object literal for the
		 * '{@link com.eclipsesource.glsp.example.modelserver.workflow.wfnotation.impl.ShapeImpl
		 * <em>Shape</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see com.eclipsesource.glsp.example.modelserver.workflow.wfnotation.impl.ShapeImpl
		 * @see com.eclipsesource.glsp.example.modelserver.workflow.wfnotation.impl.WfnotationPackageImpl#getShape()
		 * @generated
		 */
		EClass SHAPE = eINSTANCE.getShape();

		/**
		 * The meta object literal for the '<em><b>Position</b></em>' containment
		 * reference feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference SHAPE__POSITION = eINSTANCE.getShape_Position();

		/**
		 * The meta object literal for the '<em><b>Size</b></em>' containment reference
		 * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference SHAPE__SIZE = eINSTANCE.getShape_Size();

		/**
		 * The meta object literal for the
		 * '{@link com.eclipsesource.glsp.example.modelserver.workflow.wfnotation.impl.EdgeImpl
		 * <em>Edge</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see com.eclipsesource.glsp.example.modelserver.workflow.wfnotation.impl.EdgeImpl
		 * @see com.eclipsesource.glsp.example.modelserver.workflow.wfnotation.impl.WfnotationPackageImpl#getEdge()
		 * @generated
		 */
		EClass EDGE = eINSTANCE.getEdge();

		/**
		 * The meta object literal for the '<em><b>Bend Points</b></em>' containment
		 * reference list feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference EDGE__BEND_POINTS = eINSTANCE.getEdge_BendPoints();

		/**
		 * The meta object literal for the
		 * '{@link com.eclipsesource.glsp.example.modelserver.workflow.wfnotation.impl.PointImpl
		 * <em>Point</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see com.eclipsesource.glsp.example.modelserver.workflow.wfnotation.impl.PointImpl
		 * @see com.eclipsesource.glsp.example.modelserver.workflow.wfnotation.impl.WfnotationPackageImpl#getPoint()
		 * @generated
		 */
		EClass POINT = eINSTANCE.getPoint();

		/**
		 * The meta object literal for the '<em><b>X</b></em>' attribute feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute POINT__X = eINSTANCE.getPoint_X();

		/**
		 * The meta object literal for the '<em><b>Y</b></em>' attribute feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute POINT__Y = eINSTANCE.getPoint_Y();

		/**
		 * The meta object literal for the
		 * '{@link com.eclipsesource.glsp.example.modelserver.workflow.wfnotation.impl.DimensionImpl
		 * <em>Dimension</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see com.eclipsesource.glsp.example.modelserver.workflow.wfnotation.impl.DimensionImpl
		 * @see com.eclipsesource.glsp.example.modelserver.workflow.wfnotation.impl.WfnotationPackageImpl#getDimension()
		 * @generated
		 */
		EClass DIMENSION = eINSTANCE.getDimension();

		/**
		 * The meta object literal for the '<em><b>Width</b></em>' attribute feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute DIMENSION__WIDTH = eINSTANCE.getDimension_Width();

		/**
		 * The meta object literal for the '<em><b>Height</b></em>' attribute feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute DIMENSION__HEIGHT = eINSTANCE.getDimension_Height();

	}

} // WfnotationPackage
