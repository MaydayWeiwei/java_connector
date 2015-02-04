package org.openflexo.technologyadapter.java.view;

import java.awt.event.MouseEvent;

import org.openflexo.technologyadapter.java.model.JAVAFolderModel;

import edu.uci.ics.jung.visualization.control.GraphMouseListener;

@SuppressWarnings("rawtypes")
public class GraphVertexMouseListener implements GraphMouseListener {
	
	private JAVAFolderView javaFolderView;

	public GraphVertexMouseListener(JAVAFolderView javaFolderView) {
		super();
		this.javaFolderView = javaFolderView;
	}

	public void graphClicked(Object obj, MouseEvent me) {

		if (obj instanceof JAVAFolderModel) {
			JAVAFolderModel folderModel = (JAVAFolderModel) obj;
			JAVAVisualizationViewerConstructor constructor = new JAVAVisualizationViewerConstructor(folderModel, javaFolderView);
			javaFolderView.removeAll();
			javaFolderView.add(folderModel.getName(), constructor.getJavaVisualizationViewer());
		}

	}

	public void graphPressed(Object v, MouseEvent me) {
		// TODO Auto-generated method stub

	}

	public void graphReleased(Object v, MouseEvent me) {
		// TODO Auto-generated method stub

	}

}
