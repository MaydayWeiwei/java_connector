package org.openflexo.technologyadapter.java.view;

import java.awt.event.MouseEvent;

import org.openflexo.technologyadapter.java.model.JAVAFileModel;
import org.openflexo.technologyadapter.java.model.JAVAFolderModel;

import edu.uci.ics.jung.visualization.control.GraphMouseListener;

@SuppressWarnings("rawtypes")
public class GraphVertexMouseListener implements GraphMouseListener {
	
	private JAVAModuleView javaFolderView;

	public GraphVertexMouseListener(JAVAModuleView javaFolderView) {
		super();
		this.javaFolderView = javaFolderView;
	}

	public void graphClicked(Object obj, MouseEvent me) {
		
		if (obj instanceof JAVAFolderModel) {
			JAVAFolderModel folderModel = (JAVAFolderModel) obj;
			JAVAFolderViewConstructor constructor = new JAVAFolderViewConstructor(folderModel, javaFolderView);
			javaFolderView.removeAll();
			javaFolderView.add(folderModel.getName(), constructor.getJavaVisualizationViewer());
		} else if(obj instanceof JAVAFileModel) {
			JAVAFileModel fileModel = (JAVAFileModel) obj;
			javaFolderView.removeAll();
			JAVAFileViewConstructor constructor = new JAVAFileViewConstructor(fileModel);
			javaFolderView.add(fileModel.getName(), constructor.getJavaVisualizationViewer());
		}

	}

	public void graphPressed(Object v, MouseEvent me) {
		// TODO Auto-generated method stub

	}

	public void graphReleased(Object v, MouseEvent me) {
		// TODO Auto-generated method stub

	}

}
