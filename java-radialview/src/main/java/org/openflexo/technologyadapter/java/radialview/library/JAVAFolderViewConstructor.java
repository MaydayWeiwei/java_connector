package org.openflexo.technologyadapter.java.radialview.library;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.openflexo.fge.FGEModelFactory;
import org.openflexo.fge.FGEModelFactoryImpl;
import org.openflexo.foundation.resource.RepositoryFolder;
import org.openflexo.model.exceptions.ModelDefinitionException;
import org.openflexo.technologyadapter.java.rm.JAVAResource;

public class JAVAFolderViewConstructor {

	private RepositoryFolder<JAVAResource> resourceRepository;

	public JAVAFolderViewConstructor(RepositoryFolder<JAVAResource> resourceRepository) {
		this.resourceRepository = resourceRepository;
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
		List<RepositoryFolder<JAVAResource>> repositoryList = new ArrayList<RepositoryFolder<JAVAResource>>();
		List<JAVAGraphNode> graphNodeList = new ArrayList<JAVAGraphNode>();
		JAVAGraph graph = new JAVAGraph();
		JAVAGraphNode node = new JAVAGraphNode(resourceRepository.getName(), graph, resourceRepository);
		repositoryList.add(resourceRepository);
		graphNodeList.add(node);
		completeDrawing(graph, graphNodeList, repositoryList, 1, 0);
		CircularDrawing circularDrawing = new CircularDrawing(graph, factory);
		circularDrawing.printGraphicalObjectHierarchy();
		return circularDrawing;
	}

	private void completeDrawing(JAVAGraph graph, List<JAVAGraphNode> graphNodeList, List<RepositoryFolder<JAVAResource>> repositoryList,
			int size, int height) {

		List<RepositoryFolder<JAVAResource>> newRepositoryList = new ArrayList<RepositoryFolder<JAVAResource>>();
		List<JAVAGraphNode> newGraphNodeList = new ArrayList<JAVAGraphNode>();

		if (size > 0 && height < 2) {
			for (int i = 0; i < size; i++) {
				JAVAGraphNode parent = graphNodeList.get(i);
				RepositoryFolder<JAVAResource> repository = repositoryList.get(i);
				for (JAVAResource resource : repository.getResources()) {
					JAVAGraphNode node = new JAVAGraphNode(resource.getName(), graph, resource);
					parent.connectTo(node);
				}
				for (RepositoryFolder<JAVAResource> folder : repository.getChildren()) {
					JAVAGraphNode node = new JAVAGraphNode(folder.getName(), graph, folder);
					parent.connectTo(node);
					newGraphNodeList.add(node);
					newRepositoryList.add(folder);
				}
			}
			completeDrawing(graph, newGraphNodeList, newRepositoryList, newRepositoryList.size(), ++height);
		}

	}

}
