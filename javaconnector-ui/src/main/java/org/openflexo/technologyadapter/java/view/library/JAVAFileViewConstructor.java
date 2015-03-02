package org.openflexo.technologyadapter.java.view.library;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.openflexo.fge.FGEModelFactory;
import org.openflexo.fge.FGEModelFactoryImpl;
import org.openflexo.model.exceptions.ModelDefinitionException;
import org.openflexo.technologyadapter.java.model.JAVAClassOrInterfaceModel;
import org.openflexo.technologyadapter.java.model.JAVAFieldModel;
import org.openflexo.technologyadapter.java.model.JAVAFileModel;
import org.openflexo.technologyadapter.java.model.JAVAMethodModel;
import org.openflexo.technologyadapter.java.view.JAVARepositoryView;

public class JAVAFileViewConstructor {

	private JAVAFileModel fileModel;

	private JAVARepositoryView repositoryView;

	public JAVAFileViewConstructor(JAVAFileModel fileModel, JAVARepositoryView repositoryView) {
		this.fileModel = fileModel;
		this.repositoryView = repositoryView;
	}

	public JAVAFileViewConstructor(JAVAFileModel fileModel) {
		this.fileModel = fileModel;
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

	public CircularDrawing makeDrawing() {
		FGEModelFactory factory = null;
		try {
			factory = new FGEModelFactoryImpl();
		} catch (ModelDefinitionException e) {
			e.printStackTrace();
		}
		JAVAClassOrInterfaceModel rootClass = fileModel.getJavaClass();
		List<JAVAClassOrInterfaceModel> classList = new ArrayList<JAVAClassOrInterfaceModel>();
		List<JAVAGraphNode> graphNodeList = new ArrayList<JAVAGraphNode>();
		JAVAGraph graph = new JAVAGraph();
		JAVAGraphNode node = new JAVAGraphNode(rootClass.getName(), graph, rootClass);
		classList.add(rootClass);
		graphNodeList.add(node);
		completeDrawing(graph, graphNodeList, classList, 1, 0);
		CircularDrawing circularDrawing = new CircularDrawing(graph, factory, repositoryView);
		circularDrawing.printGraphicalObjectHierarchy();
		return circularDrawing;
	}

	private void completeDrawing(JAVAGraph graph, List<JAVAGraphNode> graphNodeList, List<JAVAClassOrInterfaceModel> classList, int size,
			int height) {

		List<JAVAClassOrInterfaceModel> newClassList = new ArrayList<JAVAClassOrInterfaceModel>();
		List<JAVAGraphNode> newGraphNodeList = new ArrayList<JAVAGraphNode>();

		if (size > 0 && height < 1) {
			for (int i = 0; i < size; i++) {
				JAVAGraphNode parent = graphNodeList.get(i);
				JAVAClassOrInterfaceModel classModel = classList.get(i);
				for (JAVAClassOrInterfaceModel innerClass : classModel.getInnerClasses()) {
					JAVAGraphNode node = new JAVAGraphNode(innerClass.getName(), graph, innerClass);
					parent.connectTo(node);
					newClassList.add(innerClass);
				}
				for (JAVAFieldModel fieldModel : classModel.getJavaFields()) {
					JAVAGraphNode node = new JAVAGraphNode(fieldModel.getName(), graph, fieldModel);
					parent.connectTo(node);
					newGraphNodeList.add(node);
				}
				for (JAVAMethodModel methodModel : classModel.getJavaMethods()) {
					JAVAGraphNode node = new JAVAGraphNode(methodModel.getName(), graph, methodModel);
					parent.connectTo(node);
					newGraphNodeList.add(node);
				}
			}
			completeDrawing(graph, newGraphNodeList, newClassList, newClassList.size(), ++height);
		}

	}

}
