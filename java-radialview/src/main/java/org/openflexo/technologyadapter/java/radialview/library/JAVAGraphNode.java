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

package org.openflexo.technologyadapter.java.radialview.library;

import japa.parser.ParseException;
import japa.parser.ast.body.ClassOrInterfaceDeclaration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Observable;

import org.openflexo.fge.geom.FGEPoint;
import org.openflexo.technologyadapter.java.model.JAVAClassOrInterfaceModel;
import org.openflexo.technologyadapter.java.model.JAVAFileModel;
import org.openflexo.technologyadapter.java.model.JAVAMethodModel;
import org.openflexo.technologyadapter.java.rm.JAVAResourceRepository;

public class JAVAGraphNode extends Observable {

	private String name;
	private Object model;
	private final JAVAGraph graph;
	private List<JAVAEdge> inputEdges;
	private List<JAVAEdge> outputEdges;
	private double x, y;

	private static final double CENTER_X = 300;
	private static final double CENTER_Y = 300;
	private static final double RADIUS = 100;

	public JAVAGraphNode(String name, JAVAGraph graph, Object model) {
		inputEdges = new ArrayList<JAVAEdge>();
		outputEdges = new ArrayList<JAVAEdge>();
		this.name = name;
		this.graph = graph;
		this.model = model;
		x = Math.random() * 500;
		y = Math.random() * 500;
		graph.addToNodes(this);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) throws ParseException, IOException {
		System.out.println("Set node name with " + name);
		this.name = name;
		if (model instanceof JAVAResourceRepository) {
			JAVAResourceRepository resourceRepository = (JAVAResourceRepository) model;
			if (!resourceRepository.getRootFolder().getName().equals(name)) {
				File folder = resourceRepository.getRootFolder().getFile();
				File newFolder = new File(folder.getParent() + "/" + name);
				folder.renameTo(newFolder);
			}
		}
		else if (model instanceof JAVAFileModel) {
			JAVAFileModel fileModel = (JAVAFileModel) model;
			if (!fileModel.getName().equals(name)) {
				File file = fileModel.getFileModel();
				File newFile = new File(file.getParent() + "/" + name);
				file.renameTo(newFile);
				fileModel.setFileModel(newFile);
			}
		}
		else if (model instanceof JAVAClassOrInterfaceModel) {
			JAVAClassOrInterfaceModel javaClassModel = (JAVAClassOrInterfaceModel) model;
			if (!javaClassModel.getName().equals(name)) {
				ClassOrInterfaceDeclaration classDeclaration = javaClassModel.getClassOrInterfaceModel();
				classDeclaration.setName(name);
				javaClassModel.setClassOrInterfaceModel(classDeclaration);
			}
		}
		else if (model instanceof JAVAMethodModel) {
			// JAVAMethodModel javaMethodModel = (JAVAMethodModel) model;
			// JAVAClassOrInterfaceModel father = javaMethodModel
			// .getJavaFatherItem();
			// FileInputStream in = new FileInputStream(father.getJavaFile()
			// .getFileModel());
			// CompilationUnit cu;
			// try {
			// cu = JavaParser.parse(in);
			// if (!javaMethodModel.getName().equals(name)) {
			// MethodDeclaration methodDeclaration = javaMethodModel
			// .getMethodModel();
			// for (Node node : cu.getChildrenNodes().get(0)
			// .getChildrenNodes()) {
			// if (node.equals(methodDeclaration)) {
			// ((MethodDeclaration) node).setName(name);
			// }
			// }
			// methodDeclaration.setName(name);
			// javaMethodModel.setMethodModel(methodDeclaration);
			// }
			//
			// } finally {
			// in.close();
			// }
			//
		}
	}

	public JAVAGraph getGraph() {
		return graph;
	}

	public Object getModel() {
		return model;
	}

	public void setModel(Object model) {
		this.model = model;
	}

	public List<JAVAEdge> getInputEdges() {
		return inputEdges;
	}

	public void setInputEdges(List<JAVAEdge> inputEdges) {
		this.inputEdges = inputEdges;
		setChanged();
		notifyObservers();
	}

	public void addToInputEdges(JAVAEdge aNode) {
		inputEdges.add(aNode);
		setChanged();
		notifyObservers();
	}

	public void removeFromInputEdges(JAVAEdge aNode) {
		inputEdges.remove(aNode);
		setChanged();
		notifyObservers();
	}

	public List<JAVAEdge> getOutputEdges() {
		return outputEdges;
	}

	public void setOutputEdges(List<JAVAEdge> outputEdges) {
		this.outputEdges = outputEdges;
		setChanged();
		notifyObservers();
	}

	public void addToOutputEdges(JAVAEdge aNode) {
		outputEdges.add(aNode);
		setChanged();
		notifyObservers();
	}

	public void removeFromOutputEdges(JAVAEdge aNode) {
		outputEdges.remove(aNode);
		setChanged();
		notifyObservers();
	}

	public void connectTo(JAVAGraphNode toNode) {
		JAVAEdge newEdge = new JAVAEdge(this, toNode);
		addToOutputEdges(newEdge);
		toNode.addToInputEdges(newEdge);
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	private Double teta = null;
	private double parentAngle;
	private double startAngle;

	private boolean isUpToDate = false;

	public double getParentAngle() {
		if (!isUpToDate) {
			updateParentAngle();
		}
		return parentAngle;
	}

	public double getStartAngle() {
		if (!isUpToDate) {
			updateParentAngle();
		}
		return startAngle;
	}

	public double getTeta() {
		if (teta == null) {
			List<JAVAGraphNode> siblings = getSiblingNodes();
			int index = siblings.indexOf(this);
			teta = getStartAngle() + getParentAngle() / siblings.size() * index;
		}
		return teta;
	}

	private void updateParentAngle() {
		if (getInputEdges().size() == 0) {
			parentAngle = 2 * Math.PI;
			startAngle = 0;
		}
		else {
			JAVAGraphNode parentNode = getInputEdges().get(0).getStartNode();
			List<JAVAGraphNode> parentSiblings = parentNode.getSiblingNodes();
			int parentIndex = parentSiblings.indexOf(parentNode);
			parentAngle = 2 * Math.PI / (parentSiblings.size());
			startAngle = 2 * Math.PI * parentIndex / parentSiblings.size();
		}
		isUpToDate = true;
	}

	public double getCircularX() {
		return CENTER_X + (getDepth() * RADIUS) * Math.cos(getTeta());
	}

	public double getCircularY() {
		return CENTER_Y + (getDepth() * RADIUS) * Math.sin(getTeta());
	}

	public void setCircularX(double circX) {
		if (teta != null) {
			updateTetaWithNewPosition(new FGEPoint(circX, getCircularY()));
		}
	}

	public void setCircularY(double circY) {
		if (teta != null) {
			updateTetaWithNewPosition(new FGEPoint(getCircularX(), circY));
		}
	}

	private void updateTetaWithNewPosition(FGEPoint newPosition) {
		// System.out.println("teta was = " + getTeta());
		teta = Math.atan2(newPosition.y - CENTER_Y, newPosition.x - CENTER_X);
		// System.out.println("teta is now = " + getTeta());
	}

	public int getDepth() {
		if (getInputEdges().size() == 0) {
			return 0;
		}
		else {
			JAVAGraphNode parentNode = getInputEdges().get(0).getStartNode();
			return parentNode.getDepth() + 1;
		}
	}

	public List<JAVAGraphNode> getSiblingNodes() {
		if (getInputEdges().size() == 0) {
			return Collections.singletonList(this);
		}
		else {
			JAVAGraphNode parentNode = getInputEdges().get(0).getStartNode();
			List<JAVAGraphNode> returned = new ArrayList<JAVAGraphNode>();
			for (JAVAEdge e : getInputEdges().get(0).getStartNode().getOutputEdges()) {
				returned.add(e.getEndNode());
			}
			return returned;
		}

	}

	@Override
	public String toString() {
		return "GraphNode[" + name + "](" + x + "," + y + ")";
	}

	private double labelX = 30;
	private double labelY = 0;

	public double getLabelX() {
		return labelX;
	}

	public void setLabelX(double labelX) {
		this.labelX = labelX;
	}

	public double getLabelY() {
		return labelY;
	}

	public void setLabelY(double labelY) {
		this.labelY = labelY;
	}

}
