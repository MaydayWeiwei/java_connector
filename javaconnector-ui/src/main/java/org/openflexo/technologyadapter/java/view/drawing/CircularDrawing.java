/**
 * 
 * Copyright (c) 2014, Openflexo
 * 
 * This file is part of Diana-core, a component of the software infrastructure 
 * developed at Openflexo.
 * 
 * 
 * Openflexo is dual-licensed under the European Union Public License (EUPL, either 
 * version 1.1 of the License, or any later version ), which is available at 
 * https://joinup.ec.europa.eu/software/page/eupl/licence-eupl
 * and the GNU General Public License (GPL, either version 3 of the License, or any 
 * later version), which is available at http://www.gnu.org/licenses/gpl.html .
 * 
 * You can redistribute it and/or modify under the terms of either of these licenses
 * 
 * If you choose to redistribute it and/or modify under the terms of the GNU GPL, you
 * must include the following additional permission.
 *
 *          Additional permission under GNU GPL version 3 section 7
 *
 *          If you modify this Program, or any covered work, by linking or 
 *          combining it with software containing parts covered by the terms 
 *          of EPL 1.0, the licensors of this Program grant you additional permission
 *          to convey the resulting work. * 
 * 
 * This software is distributed in the hope that it will be useful, but WITHOUT ANY 
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A 
 * PARTICULAR PURPOSE. 
 *
 * See http://www.openflexo.org/license.html for details.
 * 
 * 
 * Please contact Openflexo (openflexo-contacts@openflexo.org)
 * or visit www.openflexo.org if you need additional information.
 * 
 */

package org.openflexo.technologyadapter.java.view.drawing;

import java.awt.Color;
import java.awt.Image;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

import org.openflexo.connie.DataBinding;
import org.openflexo.fge.BackgroundStyle.BackgroundStyleType;
import org.openflexo.fge.ConnectorGraphicalRepresentation;
import org.openflexo.fge.DrawingGraphicalRepresentation;
import org.openflexo.fge.FGEModelFactory;
import org.openflexo.fge.ForegroundStyle.DashStyle;
import org.openflexo.fge.GRBinding.ConnectorGRBinding;
import org.openflexo.fge.GRBinding.DrawingGRBinding;
import org.openflexo.fge.GRBinding.GeometricGRBinding;
import org.openflexo.fge.GRBinding.ShapeGRBinding;
import org.openflexo.fge.GRProvider.ConnectorGRProvider;
import org.openflexo.fge.GRProvider.DrawingGRProvider;
import org.openflexo.fge.GRProvider.GeometricGRProvider;
import org.openflexo.fge.GRProvider.ShapeGRProvider;
import org.openflexo.fge.GRStructureVisitor;
import org.openflexo.fge.GeometricGraphicalRepresentation;
import org.openflexo.fge.GraphicalRepresentation;
import org.openflexo.fge.ShapeGraphicalRepresentation;
import org.openflexo.fge.connectors.ConnectorSpecification.ConnectorType;
import org.openflexo.fge.geom.FGECircle;
import org.openflexo.fge.geom.FGEGeometricObject.Filling;
import org.openflexo.fge.geom.FGEPoint;
import org.openflexo.fge.geom.area.FGEArea;
import org.openflexo.fge.geom.area.FGEUnionArea;
import org.openflexo.fge.impl.DrawingImpl;
import org.openflexo.fge.shapes.ShapeSpecification.ShapeType;
import org.openflexo.foundation.resource.RepositoryFolder;
import org.openflexo.technologyadapter.java.model.JAVAClassOrInterfaceModel;
import org.openflexo.technologyadapter.java.model.JAVAFileModel;
import org.openflexo.technologyadapter.java.model.JAVAMethodModel;
import org.openflexo.technologyadapter.java.view.JAVARepositoryView;

public class CircularDrawing extends DrawingImpl<JAVAGraph> {

	private static final Logger LOGGER = Logger.getLogger(CircularDrawing.class.getPackage().getName());

	private DrawingGraphicalRepresentation graphRepresentation;
	private ShapeGraphicalRepresentation javaRepresentation;
	private ShapeGraphicalRepresentation folderRepresentation;
	private ShapeGraphicalRepresentation xmlRepresentation;
	private ShapeGraphicalRepresentation classRepresentation;
	private ShapeGraphicalRepresentation methodRepresentation;
	private ShapeGraphicalRepresentation fieldRepresentation;
	private ConnectorGraphicalRepresentation edgeRepresentation;
	private GeometricGraphicalRepresentation circle1GR;
	private JAVAMouseClickControl mouseClickControl;

	public CircularDrawing(JAVAGraph graph, FGEModelFactory factory, JAVARepositoryView repositoryView) {
		super(graph, factory, PersistenceMode.SharedGraphicalRepresentations);
		mouseClickControl = new JAVAMouseClickControl(repositoryView);
		javaRepresentation.addToMouseClickControls(mouseClickControl);
		folderRepresentation.addToMouseClickControls(mouseClickControl);

	}

	@Override
	public void init() {
		Image javaImage = null;
		Image folderImage = null;
		Image xmlImage = null;
		try {
			// TODO need to replace file path by something more stable
			javaImage = ImageIO.read(new File("../java/javaconnector-ui/src/main/resources/Images/java.jpg"));
			folderImage = ImageIO.read(new File("../java/javaconnector-ui/src/main/resources/Images/folder.jpg"));
			xmlImage = ImageIO.read(new File("../java/javaconnector-ui/src/main/resources/Images/xml.jpg"));
		} catch (Exception e) {
			final String msg = "Error during create image";
			LOGGER.log(Level.SEVERE, msg, e);
		}

		graphRepresentation = getFactory().makeDrawingGraphicalRepresentation();

		folderRepresentation = getFactory().makeShapeGraphicalRepresentation(ShapeType.SQUARE);
		folderRepresentation.setWidth(55);
		folderRepresentation.setHeight(48);
		folderRepresentation.setAbsoluteTextX(30);
		folderRepresentation.setAbsoluteTextY(0);
		folderRepresentation.getForeground().setColor(Color.WHITE);
		folderRepresentation.setBackground(getFactory().makeImageBackground(folderImage));

		javaRepresentation = getFactory().makeShapeGraphicalRepresentation(ShapeType.SQUARE);
		javaRepresentation.setWidth(50);
		javaRepresentation.setHeight(55);
		javaRepresentation.setAbsoluteTextX(30);
		javaRepresentation.setAbsoluteTextY(0);
		javaRepresentation.getForeground().setColor(Color.WHITE);
		javaRepresentation.setBackgroundType(BackgroundStyleType.IMAGE);
		javaRepresentation.setBackground(getFactory().makeImageBackground(javaImage));

		xmlRepresentation = getFactory().makeShapeGraphicalRepresentation(ShapeType.SQUARE);
		xmlRepresentation.setWidth(50);
		xmlRepresentation.setHeight(57);
		xmlRepresentation.setAbsoluteTextX(30);
		xmlRepresentation.setAbsoluteTextY(0);
		xmlRepresentation.getForeground().setColor(Color.WHITE);
		xmlRepresentation.setBackground(getFactory().makeImageBackground(xmlImage));

		classRepresentation = getFactory().makeShapeGraphicalRepresentation(ShapeType.CIRCLE);
		classRepresentation.setWidth(50);
		classRepresentation.setHeight(50);
		classRepresentation.setAbsoluteTextX(30);
		classRepresentation.setAbsoluteTextY(0);
		classRepresentation.setBackground(getFactory().makeColoredBackground(Color.RED));

		methodRepresentation = getFactory().makeShapeGraphicalRepresentation(ShapeType.CIRCLE);
		methodRepresentation.setWidth(50);
		methodRepresentation.setHeight(50);
		methodRepresentation.setAbsoluteTextX(30);
		methodRepresentation.setAbsoluteTextY(0);
		methodRepresentation.setBackground(getFactory().makeColoredBackground(Color.YELLOW));

		fieldRepresentation = getFactory().makeShapeGraphicalRepresentation(ShapeType.CIRCLE);
		fieldRepresentation.setWidth(50);
		fieldRepresentation.setHeight(50);
		fieldRepresentation.setAbsoluteTextX(30);
		fieldRepresentation.setAbsoluteTextY(0);
		fieldRepresentation.setBackground(getFactory().makeColoredBackground(Color.BLUE));

		edgeRepresentation = getFactory().makeConnectorGraphicalRepresentation(ConnectorType.CURVE);

		FGECircle circle1 = new FGECircle(new FGEPoint(330, 330), 130, Filling.NOT_FILLED);
		FGECircle circle2 = new FGECircle(new FGEPoint(330, 330), 260, Filling.NOT_FILLED);
		FGEArea union = FGEUnionArea.makeUnion(circle1, circle2);
		circle1GR = getFactory().makeGeometricGraphicalRepresentation(union);
		circle1GR.setForeground(getFactory().makeForegroundStyle(Color.GRAY, 0.5f, DashStyle.MEDIUM_DASHES));

		final DrawingGRBinding<JAVAGraph> graphBinding = bindDrawing(JAVAGraph.class, "graph", new DrawingGRProvider<JAVAGraph>() {
			@Override
			public DrawingGraphicalRepresentation provideGR(JAVAGraph drawable, FGEModelFactory factory) {
				return graphRepresentation;
			}
		});
		final ShapeGRBinding<JAVANode> nodeBinding = bindShape(JAVANode.class, "node", new ShapeGRProvider<JAVANode>() {
			@Override
			public ShapeGraphicalRepresentation provideGR(JAVANode drawable, FGEModelFactory factory) {
				final Object model = drawable.getModel();
				if (model instanceof RepositoryFolder<?>)
					return folderRepresentation;
				else if (model instanceof JAVAFileModel) {
					final JAVAFileModel fileModel = (JAVAFileModel) model;
					if (fileModel.getFileModel().getName().toLowerCase().endsWith(".java")) {
						return javaRepresentation;
					}
					else if (fileModel.getFileModel().getName().toLowerCase().endsWith(".xml")) {
						return xmlRepresentation;
					}
				}
				else if (model instanceof JAVAClassOrInterfaceModel)
					return classRepresentation;
				else if (model instanceof JAVAMethodModel)
					return methodRepresentation;
				return fieldRepresentation;
			}
		});
		final ConnectorGRBinding<JAVAEdge> edgeBinding = bindConnector(JAVAEdge.class, "edge", nodeBinding, nodeBinding, graphBinding,
				new ConnectorGRProvider<JAVAEdge>() {
					@Override
					public ConnectorGraphicalRepresentation provideGR(JAVAEdge drawable, FGEModelFactory factory) {
						return edgeRepresentation;
					}
				});

		final GeometricGRBinding<JAVAGraph> circle1Binding = bindGeometric(JAVAGraph.class, "circle1",
				new GeometricGRProvider<JAVAGraph>() {
					@Override
					public GeometricGraphicalRepresentation provideGR(JAVAGraph drawable, FGEModelFactory factory) {
						return circle1GR;
					}
				});

		graphBinding.addToWalkers(new GRStructureVisitor<JAVAGraph>() {

			@Override
			public void visit(JAVAGraph graph) {
				drawGeometricObject(circle1Binding, graph);
				for (JAVANode node : graph.getNodes()) {
					drawShape(nodeBinding, node);
				}
			}
		});

		nodeBinding.addToWalkers(new GRStructureVisitor<JAVANode>() {
			@Override
			public void visit(JAVANode node) {
				System.out.println("Visiting node " + node);
				for (JAVAEdge edge : node.getInputEdges()) {
					drawConnector(edgeBinding, edge, edge.getStartNode(), edge.getEndNode(), node.getGraph());
				}
				for (JAVAEdge edge : node.getOutputEdges()) {
					drawConnector(edgeBinding, edge, edge.getStartNode(), edge.getEndNode(), node.getGraph());
				}
			}
		});

		nodeBinding.setDynamicPropertyValue(GraphicalRepresentation.TEXT, new DataBinding<String>("drawable.name"), true);
		nodeBinding.setDynamicPropertyValue(ShapeGraphicalRepresentation.X, new DataBinding<Double>("drawable.circularX"), true);
		nodeBinding.setDynamicPropertyValue(ShapeGraphicalRepresentation.Y, new DataBinding<Double>("drawable.circularY"), true);

	}
}
