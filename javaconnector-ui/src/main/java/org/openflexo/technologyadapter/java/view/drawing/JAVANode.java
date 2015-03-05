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

import japa.parser.ParseException;

import java.beans.PropertyChangeSupport;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

import org.openflexo.fge.geom.FGEPoint;
import org.openflexo.foundation.resource.RepositoryFolder;
import org.openflexo.technologyadapter.java.model.JAVAFileModel;
import org.openflexo.technologyadapter.java.rm.JAVAResource;
import org.openflexo.toolbox.HasPropertyChangeSupport;

public class JAVANode implements HasPropertyChangeSupport {

	private static final Logger LOGGER = Logger.getLogger(JAVANode.class.getPackage().getName());

	private String name;
	private Object model;
	private final JAVAGraph graph;
	private List<JAVAEdge> inputEdges;
	private List<JAVAEdge> outputEdges;
	private double x, y;
	private Double dragX = null;
	private Double dragY = null;
	private Integer depth = null;

	private static final double CENTER_X = 300;
	private static final double CENTER_Y = 300;
	private static final double RADIUS = 130;

	private PropertyChangeSupport pcSupport;

	public JAVANode(String name, JAVAGraph graph, Object model) {
		pcSupport = new PropertyChangeSupport(this);
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
		// TODO rename repository need to be fixed
		// if (model instanceof RepositoryFolder<?>) {
		// RepositoryFolder<JAVAResource> resourceRepository = (RepositoryFolder<JAVAResource>) model;
		// if (!resourceRepository.getName().equals(name)) {
		// resourceRepository.setName(name);
		// }
		// }
		if (model instanceof JAVAFileModel) {
			JAVAFileModel fileModel = (JAVAFileModel) model;
			if (!fileModel.getName().equals(name)) {
				fileModel.setName(name);
			}
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
		getPropertyChangeSupport().firePropertyChange("inputEdges", null, inputEdges);
	}

	public void addToInputEdges(JAVAEdge aEdge) {
		inputEdges.add(aEdge);
		getPropertyChangeSupport().firePropertyChange("inputEdges", null, inputEdges);
	}

	public void removeFromInputEdges(JAVAEdge aEdge) {
		inputEdges.remove(aEdge);
		getPropertyChangeSupport().firePropertyChange("inputEdges", null, inputEdges);
	}

	public List<JAVAEdge> getOutputEdges() {
		return outputEdges;
	}

	public void setOutputEdges(List<JAVAEdge> outputEdges) {
		this.outputEdges = outputEdges;
		getPropertyChangeSupport().firePropertyChange("outputEdges", null, outputEdges);
	}

	public void addToOutputEdges(JAVAEdge aEdge) {
		outputEdges.add(aEdge);
		getPropertyChangeSupport().firePropertyChange("outputEdges", null, outputEdges);
	}

	public void removeFromOutputEdges(JAVAEdge aEdge) {
		outputEdges.remove(aEdge);
		getPropertyChangeSupport().firePropertyChange("outputEdges", null, outputEdges);
	}

	public void connectTo(JAVANode toNode) {
		JAVAEdge newEdge = new JAVAEdge(this, toNode);
		addToOutputEdges(newEdge);
		toNode.addToInputEdges(newEdge);
		getPropertyChangeSupport().firePropertyChange("outputEdges", null, outputEdges);
		toNode.getPropertyChangeSupport().firePropertyChange("inputEdges", null, toNode.getInputEdges());
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
			List<JAVANode> siblings = getSiblingNodes();
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
			JAVANode parentNode = getInputEdges().get(0).getStartNode();
			List<JAVANode> parentSiblings = parentNode.getSiblingNodes();
			int parentIndex = parentSiblings.indexOf(parentNode);
			parentAngle = 2 * Math.PI / (parentSiblings.size());
			startAngle = 2 * Math.PI * parentIndex / parentSiblings.size();
		}
		isUpToDate = true;
	}

	public double getCircularX() {
		final int realDepth = depth == null ? getDepth() : depth;
		return CENTER_X + (realDepth * RADIUS) * Math.cos(getTeta());
	}

	public double getCircularY() {
		final int realDepth = depth == null ? getDepth() : depth;
		return CENTER_Y + (realDepth * RADIUS) * Math.sin(getTeta());
	}

	public void setCircularX(double circX) {
		if (teta != null) {
			this.dragX = circX;
			updateTetaWithNewPosition(new FGEPoint(circX, getCircularY()));
		}
	}

	public void setCircularY(double circY) {
		if (teta != null) {
			this.dragY = circY;
			updateTetaWithNewPosition(new FGEPoint(getCircularX(), circY));
		}
	}

	private void updateTetaWithNewPosition(FGEPoint newPosition) {
		updateDepth(newPosition);
		teta = Math.atan2(newPosition.y - CENTER_Y, newPosition.x - CENTER_X); // if (graph.getRootNode() != null) {
	}

	private void updateDepth(FGEPoint newPosition) {
		if (dragX != null && dragY != null && model instanceof JAVAFileModel) {
			depth = (int) Math.round(Math.sqrt((dragX - CENTER_X) * (dragX - CENTER_X) + (dragY - CENTER_Y) * (dragY - CENTER_Y)) / RADIUS);
			moveNode();
		}

	}

	private void moveNode() {
		if (depth != null && depth.intValue() == 1 && getDepth() == 2) {
			JAVANode oldParent = inputEdges.get(0).getStartNode();
			JAVANode newParent = oldParent.getInputEdges().get(0).getStartNode();
			moveObject(oldParent, newParent);
		}
	}

	private void moveObject(JAVANode from, JAVANode to) {
		Object newParentObj = to.getModel();
		RepositoryFolder<JAVAResource> parentRepository = (RepositoryFolder<JAVAResource>) newParentObj;
		JAVAFileModel fileModel = (JAVAFileModel) model;
		File parentRepo = parentRepository.getFile();
		File file = fileModel.getFileModel();
		fileModel.setFileModel(new File(parentRepo.getAbsolutePath() + "/" + file.getName()));
		JAVAEdge edge = this.getInputEdges().get(0);
		from.removeFromOutputEdges(edge);
		this.removeFromInputEdges(edge);
		to.connectTo(this);
		from.getPropertyChangeSupport().firePropertyChange("outputEdges", null, from.getInputEdges());
		to.getPropertyChangeSupport().firePropertyChange("outputEdges", null, to.getOutputEdges());
		this.getPropertyChangeSupport().firePropertyChange("inputEdges", null, this.getInputEdges());
		graph.getPropertyChangeSupport().firePropertyChange("nodes", null, graph.getNodes());
	}

	// private JAVANode getParentNode() {
	// List<JAVANode> siblings = getSiblingNodes();
	// JAVANode parentNode = null;
	// double minDistance = 1000;
	// if (siblings != null) {
	// for (JAVANode node : siblings) {
	// if (node.getModel() instanceof RepositoryFolder<?>) {
	// double currentDiatance = Math.sqrt((dragX - node.getX()) * (dragX - node.getX()) + (dragY - node.getY())
	// * (dragY - node.getY()));
	// if (currentDiatance < minDistance) {
	// parentNode = node;
	// minDistance = currentDiatance;
	// }
	// }
	// }
	// }
	// return parentNode;
	// }

	public int getDepth() {
		if (getInputEdges().size() == 0) {
			return 0;
		}
		else {
			JAVANode parentNode = getInputEdges().get(0).getStartNode();
			return parentNode.getDepth() + 1;
		}
	}

	public List<JAVANode> getSiblingNodes() {
		if (getInputEdges().size() == 0) {
			return Collections.singletonList(this);
		}
		else {
			JAVANode parentNode = getInputEdges().get(0).getStartNode();
			List<JAVANode> returned = new ArrayList<JAVANode>();
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

	@Override
	public String getDeletedProperty() {
		return null;
	}

	@Override
	public PropertyChangeSupport getPropertyChangeSupport() {
		return pcSupport;
	}

}
