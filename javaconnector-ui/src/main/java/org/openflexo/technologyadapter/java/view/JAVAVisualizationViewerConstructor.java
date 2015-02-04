package org.openflexo.technologyadapter.java.view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections15.Transformer;
import org.openflexo.technologyadapter.java.model.JAVAFileModel;
import org.openflexo.technologyadapter.java.model.JAVAFolderModel;

import edu.uci.ics.jung.algorithms.layout.PolarPoint;
import edu.uci.ics.jung.algorithms.layout.RadialTreeLayout;
import edu.uci.ics.jung.graph.DelegateForest;
import edu.uci.ics.jung.graph.util.EdgeType;
import edu.uci.ics.jung.visualization.DefaultVisualizationModel;
import edu.uci.ics.jung.visualization.VisualizationModel;
import edu.uci.ics.jung.visualization.VisualizationServer;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.DefaultModalGraphMouse;
import edu.uci.ics.jung.visualization.control.GraphMouseListener;
import edu.uci.ics.jung.visualization.control.ModalGraphMouse.Mode;

public class JAVAVisualizationViewerConstructor {

	private JAVAFolderModel rootFolderModel;

	private VisualizationViewer<Object, Integer> javaVisualizationViewer;

	private Transformer<Object, Paint> vertexcolor;

	private Transformer<Object, String> vertexlabel;
	
	private JAVAFolderView javaFolderView;

	public JAVAVisualizationViewerConstructor(JAVAFolderModel rootFolderModel, JAVAFolderView javaFolderView) {
		this.javaFolderView = javaFolderView;
		this.rootFolderModel = rootFolderModel;
		this.vertexcolor = createColorTransformer();
		this.vertexlabel = createVertexLabelTransformer();
		this.javaVisualizationViewer = createJAVAVisualizationView();
	}

	public VisualizationViewer<Object, Integer> getJavaVisualizationViewer() {
		return javaVisualizationViewer;
	}

	public void setJavaVisualizationViewer(
			VisualizationViewer<Object, Integer> javaVisualizationViewer) {
		this.javaVisualizationViewer = javaVisualizationViewer;
	}

	private VisualizationViewer<Object, Integer> createJAVAVisualizationView() {
		List<JAVAFolderModel> folderModelList = new ArrayList<JAVAFolderModel>();
		DelegateForest<Object, Integer> graph = new DelegateForest<Object, Integer>();
		graph.addVertex(rootFolderModel);
		graph.setRoot(rootFolderModel);
		folderModelList.add(rootFolderModel);
		return completeGraph(graph, folderModelList, 1);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private VisualizationViewer<Object, Integer> completeGraph(
			DelegateForest<Object, Integer> graph,
			List<JAVAFolderModel> folderModels, int number) {

		List<JAVAFolderModel> newFolderModels = new ArrayList<JAVAFolderModel>();

		if (folderModels.size() > 0 && graph.getHeight() < 15) {
			for (JAVAFolderModel father : folderModels) {
				for (JAVAFileModel file : father.getChildrenFiles()) {
					graph.addEdge(number, father, file, EdgeType.DIRECTED);
					number++;
				}
				for (JAVAFolderModel folder : father.getChildrenFolders()) {
					graph.addEdge(number, father, folder, EdgeType.DIRECTED);
					number++;
					newFolderModels.add(folder);
				}
			}
			completeGraph(graph, newFolderModels, number);
		}
		RadialTreeLayout<Object, Integer> radialTreeLayout = new RadialTreeLayout<Object, Integer>(
				graph);
		final VisualizationModel<Object, Integer> visualizationModel = new DefaultVisualizationModel<Object, Integer>(
				radialTreeLayout);
		VisualizationViewer<Object, Integer> vv = new VisualizationViewer<Object, Integer>(
				visualizationModel);
		VisualizationServer.Paintable rings = new Rings(radialTreeLayout,
				graph, vv);
		vv.addPreRenderPaintable(rings);
		vv.getRenderContext().setVertexFillPaintTransformer(vertexcolor);
		vv.setVertexToolTipTransformer(vertexlabel);
		
		DefaultModalGraphMouse<Object, Integer> gm = new DefaultModalGraphMouse<Object, Integer>(); 
		GraphMouseListener gel = new GraphVertexMouseListener(javaFolderView);
		
		gm.setMode(Mode.PICKING);
		vv.addKeyListener(gm.getModeKeyListener());
		vv.addGraphMouseListener(gel);
		
		
	    vv.setGraphMouse(gm);
	    
		return vv;
	}

	private Transformer<Object, Paint> createColorTransformer() {
		Transformer<Object, Paint> vertexColor = new Transformer<Object, Paint>() {
			public Paint transform(Object obj) {
				if (obj instanceof JAVAFileModel) {
					String fileName = ((JAVAFileModel) obj).getName()
							.toLowerCase();
					if (fileName.endsWith("xml")) {
						return Color.GREEN;
					} else if (fileName.endsWith("java")) {
						return Color.BLUE;
					} else if (fileName.endsWith("jsp")) {
						return Color.PINK;
					}
				}
				return Color.YELLOW;
			}
		};
		return vertexColor;
	}

	private Transformer<Object, String> createVertexLabelTransformer() {
		Transformer<Object, String> vertexLabel = new Transformer<Object, String>() {
			public String transform(Object obj) {
				if (obj instanceof JAVAFileModel)
					return ((JAVAFileModel) obj).getName();
				return ((JAVAFolderModel) obj).getName();
			}
		};
		return vertexLabel;
	}

	class Rings implements VisualizationServer.Paintable {

		Collection<Double> depths;
		RadialTreeLayout<Object, Integer> radialLayout;
		DelegateForest<Object, Integer> graphe;
		VisualizationViewer<Object, Integer> vv;

		public Rings(RadialTreeLayout<Object, Integer> radialLayout,
				DelegateForest<Object, Integer> graph,
				VisualizationViewer<Object, Integer> vv) {
			this.radialLayout = radialLayout;
			this.graphe = graph;
			this.vv = vv;
			depths = getDepths();
		}

		private Collection<Double> getDepths() {
			Set<Double> depths = new HashSet<Double>();
			Map<Object, PolarPoint> polarLocations = radialLayout
					.getPolarLocations();
			for (Object v : graphe.getVertices()) {
				if (!graphe.isRoot(v)) {
					PolarPoint pp = polarLocations.get(v);
					depths.add(pp.getRadius());
				}
			}
			return depths;
		}

		public void paint(Graphics g) {
			g.setColor(Color.gray);

			g.setFont(new Font("Lucida Grande", Font.TRUETYPE_FONT, 11));
			Graphics2D g2d = (Graphics2D) g;
			final float dash1[] = { 10.0f };
			g2d.setStroke(new BasicStroke(1.0f, BasicStroke.CAP_BUTT,
					BasicStroke.JOIN_MITER, 10.0f, dash1, 0.0f));
			Point2D center = radialLayout.getCenter();

			Ellipse2D ellipse = new Ellipse2D.Double();
			for (double d : depths) {
				ellipse.setFrameFromDiagonal(center.getX() - d, center.getY()
						- d, center.getX() + d, center.getY() + d);
				Shape shape = vv.getRenderContext().getMultiLayerTransformer()
						.transform(ellipse);
				g2d.draw(shape);
			}
		}

		public boolean useTransform() {
			return true;
		}
	}

}
