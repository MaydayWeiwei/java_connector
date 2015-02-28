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

package org.openflexo.technologyadapter.java.view.library;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class JAVAGraph extends Observable {

	private List<JAVAGraphNode> nodes;
	private JAVAGraphNode rootNode;

	// private final static int thredhold = 10000000;

	public JAVAGraph() {
		nodes = new ArrayList<JAVAGraphNode>();
	}

	public List<JAVAGraphNode> getNodes() {
		return nodes;
	}

	public void setNodes(List<JAVAGraphNode> nodes) {
		this.nodes = nodes;
		setChanged();
		notifyObservers();
	}

	public void addToNodes(JAVAGraphNode aNode) {
		nodes.add(aNode);
		setChanged();
		notifyObservers();
	}

	public void removeFromNodes(JAVAGraphNode aNode) {
		nodes.remove(aNode);
		setChanged();
		notifyObservers();
	}

	public JAVAGraphNode getRootNode() {
		return rootNode;
	}

	public void setRootNode(JAVAGraphNode rootNode) {
		this.rootNode = rootNode;
		setChanged();
		notifyObservers();
	}

	// public JAVAGraphNode getClosestNode(FGEPoint p) {
	// // TODO refactor code
	// JAVAGraphNode closestNode = nodes.get(0);
	// double minDistance = (closestNode.getX() - p.x) * (closestNode.getY() - p.y) + (closestNode.getY() - p.y)
	// * (closestNode.getY() - p.y);
	// double currentDistance;
	// for (JAVAGraphNode node : nodes) {
	// currentDistance = (node.getX() - p.x) * (node.getX() - p.x) + (node.getY() - p.y) * (node.getY() - p.y);
	// if (currentDistance < minDistance) {
	// minDistance = currentDistance;
	// closestNode = node;
	// }
	// }
	// if (minDistance > thredhold) {
	// closestNode = null;
	// }
	// return closestNode;
	// }
}
