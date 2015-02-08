package org.openflexo.technologyadapter.java.view;

import java.awt.event.MouseEvent;

import javax.swing.JTextArea;

import org.openflexo.technologyadapter.java.model.JAVAClassOrInterfaceModel;
import org.openflexo.technologyadapter.java.model.JAVAFieldModel;
import org.openflexo.technologyadapter.java.model.JAVAFileModel;
import org.openflexo.technologyadapter.java.model.JAVAFolderModel;
import org.openflexo.technologyadapter.java.model.JAVAMethodModel;

import edu.uci.ics.jung.visualization.control.GraphMouseListener;

@SuppressWarnings("rawtypes")
public class GraphVertexMouseListener implements GraphMouseListener {

	private JAVAModuleView javaModuleView;

	public GraphVertexMouseListener(JAVAModuleView javaModuleView) {
		super();
		this.javaModuleView = javaModuleView;
	}

	public void graphClicked(Object obj, MouseEvent me) {

		if (obj instanceof JAVAFolderModel) {
			JAVAFolderModel folderModel = (JAVAFolderModel) obj;
			JAVAFolderViewConstructor constructor = new JAVAFolderViewConstructor(
					folderModel, javaModuleView);
			javaModuleView.removeAll();
			javaModuleView.add(folderModel.getName(),
					constructor.getJavaVisualizationViewer());
		} else if (obj instanceof JAVAFileModel) {
			JAVAFileModel fileModel = (JAVAFileModel) obj;
			javaModuleView.removeAll();
			JAVAFileViewConstructor constructor = new JAVAFileViewConstructor(
					fileModel, javaModuleView);
			javaModuleView.add(fileModel.getName(),
					constructor.getJavaVisualizationViewer());
		} else if (obj instanceof JAVAClassOrInterfaceModel) {
			JAVAClassOrInterfaceModel coi = (JAVAClassOrInterfaceModel) obj;
			new JAVATextView(new JTextArea(coi.getClassOrInterfaceModel().toString()));
		} else if (obj instanceof JAVAMethodModel) {
			JAVAMethodModel mm = (JAVAMethodModel) obj;
			new JAVATextView(new JTextArea(mm.getMethodModel().toString()));
		} else if (obj instanceof JAVAFieldModel) {
			JAVAFieldModel fm = (JAVAFieldModel) obj;
			new JAVATextView(new JTextArea(fm.getFieldModel().toString()));
		}

	}

	public void graphPressed(Object v, MouseEvent me) {
		// TODO Auto-generated method stub

	}

	public void graphReleased(Object v, MouseEvent me) {
		// TODO Auto-generated method stub

	}

}
