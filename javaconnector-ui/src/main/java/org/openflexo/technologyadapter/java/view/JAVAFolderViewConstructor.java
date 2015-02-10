package org.openflexo.technologyadapter.java.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;

import org.openflexo.fge.FGEModelFactory;
import org.openflexo.fge.FGEModelFactoryImpl;
import org.openflexo.fge.swing.JDianaInteractiveEditor;
import org.openflexo.fge.swing.SwingViewFactory;
import org.openflexo.fge.swing.control.SwingToolFactory;
import org.openflexo.fge.swing.control.tools.JDianaScaleSelector;
import org.openflexo.model.exceptions.ModelDefinitionException;
import org.openflexo.technologyadapter.java.model.JAVAFileModel;
import org.openflexo.technologyadapter.java.model.JAVAFolderModel;
import org.openflexo.technologyadapter.java.view.composant.CircularDrawing;
import org.openflexo.technologyadapter.java.view.composant.JAVAGraph;
import org.openflexo.technologyadapter.java.view.composant.JAVAGraphNode;

import edu.uci.ics.jung.visualization.VisualizationServer;

public class JAVAFolderViewConstructor {

	private JAVAFolderModel rootFolderModel;

	public JAVAFolderViewConstructor(JAVAFolderModel rootFolderModel) {
		this.rootFolderModel = rootFolderModel;
	}

	public JPanel showPanel() {

		JPanel panel = new JPanel(new BorderLayout());

		final CircularDrawing d = makeDrawing();
		final TestDrawingController dc = new TestDrawingController(d);
		dc.getDrawingView().setName("[NO_CACHE]");
		panel.add(new JScrollPane(dc.getDrawingView()), BorderLayout.CENTER);
		panel.add(dc.scaleSelector.getComponent(), BorderLayout.NORTH);

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
		List<JAVAFolderModel> folderModelList = new ArrayList<JAVAFolderModel>();
		List<JAVAGraphNode> graphNodeList = new ArrayList<JAVAGraphNode>();
		JAVAGraph graph = new JAVAGraph();
		JAVAGraphNode node = new JAVAGraphNode(rootFolderModel.getName(), graph);
		folderModelList.add(rootFolderModel);
		graphNodeList.add(node);
		completeDrawing(graph, graphNodeList, folderModelList, 1, 0);
		CircularDrawing circularDrawing = new CircularDrawing(graph, factory);
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
							graph);
					parent.connectTo(node);
				}
				for (JAVAFolderModel folder : folderModel.getChildrenFolders()) {
					JAVAGraphNode node = new JAVAGraphNode(folder.getName(),
							graph);
					parent.connectTo(node);
					newGraphNodeList.add(node);
					newFolderModelList.add(folder);
				}
			}
			completeDrawing(graph, newGraphNodeList, newFolderModelList,
					newFolderModelList.size(), ++height);
		}

	}

	public class TestDrawingController extends
			JDianaInteractiveEditor<JAVAGraph> {
		private final JPopupMenu contextualMenu;
		private final JDianaScaleSelector scaleSelector;

		public TestDrawingController(CircularDrawing aDrawing) {
			super(aDrawing, aDrawing.getFactory(), SwingViewFactory.INSTANCE,
					SwingToolFactory.DEFAULT);
			scaleSelector = (JDianaScaleSelector) getToolFactory()
					.makeDianaScaleSelector(this);
			contextualMenu = new JPopupMenu();
			contextualMenu.add(new JMenuItem("Item"));
		}

	}

	class Labels implements VisualizationServer.Paintable {

		@Override
		public void paint(Graphics g) {
			g.setColor(Color.RED);
			g.fillArc(600, 10, 25, 25, 0, 360);
			g.setFont(new Font("Lucida Grande", Font.TRUETYPE_FONT, 14));
			g.drawString("Folder", 640, 25);
			g.setColor(Color.BLUE);
			g.fillArc(600, 50, 25, 25, 0, 360);
			g.drawString(".java file", 640, 65);
			g.setColor(Color.GREEN);
			g.fillArc(600, 90, 25, 25, 0, 360);
			g.drawString("Other file", 640, 105);
		}

		@Override
		public boolean useTransform() {
			return true;
		}

	}

}
