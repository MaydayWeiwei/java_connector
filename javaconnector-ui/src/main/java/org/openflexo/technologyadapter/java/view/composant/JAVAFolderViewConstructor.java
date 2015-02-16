package org.openflexo.technologyadapter.java.view.composant;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.openflexo.fge.FGEModelFactory;
import org.openflexo.fge.FGEModelFactoryImpl;
import org.openflexo.model.exceptions.ModelDefinitionException;
import org.openflexo.technologyadapter.java.model.JAVAFileModel;
import org.openflexo.technologyadapter.java.model.JAVAFolderModel;
import org.openflexo.technologyadapter.java.view.JAVAModuleView;

public class JAVAFolderViewConstructor {

	private JAVAFolderModel rootFolderModel;
	private JAVAModuleView javaModuleView;

	// private JPanel panel = new JPanel(new BorderLayout());

	public JAVAFolderViewConstructor(JAVAFolderModel rootFolderModel,
			JAVAModuleView javaModuleView) {
		this.rootFolderModel = rootFolderModel;
		this.javaModuleView = javaModuleView;
	}

	public JPanel createPanel() {

		JPanel panel = new JPanel(new BorderLayout());

		final CircularDrawing d = makeDrawing();
		final JAVADrawingController dc = new JAVADrawingController(d);
		dc.getDrawingView().setName("[NO_CACHE]");
		panel.add(new JScrollPane(dc.getDrawingView()), BorderLayout.CENTER);
		panel.add(dc.getScaleSelector().getComponent(), BorderLayout.NORTH);

		panel.validate();
		return panel;
	}

	// public JPanel getPanel() {
	// return panel;
	// }
	//
	// public void setPanel(JPanel panel) {
	// this.panel = panel;
	// }

	public CircularDrawing makeDrawing() {
		FGEModelFactory factory = null;
		try {
			factory = new FGEModelFactoryImpl();
		} catch (ModelDefinitionException e) {
			e.printStackTrace();
		}
		List<JAVAFolderModel> folderModelList = new ArrayList<JAVAFolderModel>();
		List<JAVAGraphNode> graphNodeList = new ArrayList<JAVAGraphNode>();
		JAVAGraph graph = new JAVAGraph();
		JAVAGraphNode node = new JAVAGraphNode(rootFolderModel.getName(),
				graph, rootFolderModel);
		folderModelList.add(rootFolderModel);
		graphNodeList.add(node);
		completeDrawing(graph, graphNodeList, folderModelList, 1, 0);
		CircularDrawing circularDrawing = new CircularDrawing(graph, factory,
				javaModuleView);
		circularDrawing.printGraphicalObjectHierarchy();
		return circularDrawing;
	}

	private void completeDrawing(JAVAGraph graph,
			List<JAVAGraphNode> graphNodeList,
			List<JAVAFolderModel> folderModelList, int size, int height) {

		List<JAVAFolderModel> newFolderModelList = new ArrayList<JAVAFolderModel>();
		List<JAVAGraphNode> newGraphNodeList = new ArrayList<JAVAGraphNode>();

		if (size > 0 && height < 2) {
			for (int i = 0; i < size; i++) {
				JAVAGraphNode parent = graphNodeList.get(i);
				JAVAFolderModel folderModel = folderModelList.get(i);
				for (JAVAFileModel file : folderModel.getChildrenFiles()) {
					JAVAGraphNode node = new JAVAGraphNode(file.getName(),
							graph, file);
					parent.connectTo(node);
				}
				for (JAVAFolderModel folder : folderModel.getChildrenFolders()) {
					JAVAGraphNode node = new JAVAGraphNode(folder.getName(),
							graph, folder);
					parent.connectTo(node);
					newGraphNodeList.add(node);
					newFolderModelList.add(folder);
				}
			}
			completeDrawing(graph, newGraphNodeList, newFolderModelList,
					newFolderModelList.size(), ++height);
		}

	}

}
