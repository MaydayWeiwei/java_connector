package org.openflexo.technologyadapter.java.view.drawing;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.openflexo.fge.FGEModelFactory;
import org.openflexo.fge.FGEModelFactoryImpl;
import org.openflexo.foundation.resource.RepositoryFolder;
import org.openflexo.model.exceptions.ModelDefinitionException;
import org.openflexo.technologyadapter.java.model.JAVAFileModel;
import org.openflexo.technologyadapter.java.rm.JAVAResource;
import org.openflexo.technologyadapter.java.view.JAVARepositoryView;

public class JAVARepositoryConstructor {

	private static final Logger LOGGER = Logger.getLogger(JAVARepositoryConstructor.class.getPackage().getName());

	private RepositoryFolder<JAVAResource> resourceRepository;

	private JAVARepositoryView repositoryView;

	public JAVARepositoryConstructor(RepositoryFolder<JAVAResource> resourceRepository, JAVARepositoryView repositoryView) {
		this.resourceRepository = resourceRepository;
		this.repositoryView = repositoryView;
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
		List<JAVANode> graphNodeList = new ArrayList<JAVANode>();
		JAVAGraph graph = new JAVAGraph();
		JAVANode node = new JAVANode(resourceRepository.getName(), graph, resourceRepository);
		repositoryList.add(resourceRepository);
		graphNodeList.add(node);
		completeDrawing(graph, graphNodeList, repositoryList, 1, 0);
		CircularDrawing circularDrawing = new CircularDrawing(graph, factory, repositoryView);
		circularDrawing.printGraphicalObjectHierarchy();
		return circularDrawing;
	}

	private void completeDrawing(JAVAGraph graph, List<JAVANode> graphNodeList, List<RepositoryFolder<JAVAResource>> repositoryList,
			int size, int height) {

		List<RepositoryFolder<JAVAResource>> newRepositoryList = new ArrayList<RepositoryFolder<JAVAResource>>();
		List<JAVANode> newGraphNodeList = new ArrayList<JAVANode>();

		if (size > 0 && height < 2) {
			for (int i = 0; i < size; i++) {
				JAVANode parent = graphNodeList.get(i);
				RepositoryFolder<JAVAResource> repository = repositoryList.get(i);
				for (JAVAResource resource : repository.getResources()) {
					try {
						JAVAFileModel fileModel = (JAVAFileModel) resource.getResourceData(null);
						JAVANode node = new JAVANode(resource.getName(), graph, fileModel);
						parent.connectTo(node);
					} catch (Exception e) {
						final String msg = "Error during get JAVA resource";
						LOGGER.log(Level.SEVERE, msg, e);
					}

				}
				for (RepositoryFolder<JAVAResource> folder : repository.getChildren()) {
					JAVANode node = new JAVANode(folder.getName(), graph, folder);
					parent.connectTo(node);
					newGraphNodeList.add(node);
					newRepositoryList.add(folder);
				}
			}
			completeDrawing(graph, newGraphNodeList, newRepositoryList, newRepositoryList.size(), ++height);
		}

	}

}
