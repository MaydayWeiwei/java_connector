package org.openflexo.technologyadapter.java.view.composant;

import org.openflexo.fge.Drawing.DrawingTreeNode;
import org.openflexo.fge.control.MouseClickControl;
import org.openflexo.fge.control.MouseClickControlAction;
import org.openflexo.fge.control.MouseControlContext;
import org.openflexo.technologyadapter.java.model.JAVAFileModel;
import org.openflexo.technologyadapter.java.model.JAVAFolderModel;
import org.openflexo.technologyadapter.java.view.JAVAModuleView;

public class JAVAMouseClickControl implements
		MouseClickControl<JAVADrawingController> {

	@Override
	public boolean isApplicable(DrawingTreeNode<?, ?> node,
			JAVADrawingController controller, MouseControlContext context) {
		if (context.isControlDown()) {
			Object obj = ((JAVAGraphNode) node.getDrawable()).getModel();
			JAVAModuleView javaModuleView = ((CircularDrawing) node
					.getDrawing()).getJavaModuleView();
			if (obj instanceof JAVAFolderModel) {
				JAVAFolderModel folderModel = (JAVAFolderModel) obj;
				javaModuleView.removeAll();
				JAVAFolderViewConstructor constructor = new JAVAFolderViewConstructor(
						folderModel, javaModuleView);
				javaModuleView.addTab(folderModel.getName(),
						constructor.createPanel());
			} else if (obj instanceof JAVAFileModel) {
				JAVAFileModel fileModel = (JAVAFileModel) obj;
				if (fileModel.getName().toLowerCase().endsWith(".java")) {
					javaModuleView.removeAll();
					JAVAFileViewConstructor constructor = new JAVAFileViewConstructor(
							fileModel, javaModuleView);
					javaModuleView.addTab(fileModel.getName(),
							constructor.createPanel());
				}
			}
			return true;
		}
		return false;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isShiftPressed() {
		return false;
	}

	@Override
	public boolean isCtrlPressed() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isMetaPressed() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isAltPressed() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public org.openflexo.fge.control.MouseControl.MouseButton getButton() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MouseClickControlAction<JAVADrawingController> getControlAction() {
		return null;
	}

	@Override
	public void setControlAction(
			MouseClickControlAction<JAVADrawingController> action) {
	}

	@Override
	public int getClickCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void handleClick(DrawingTreeNode<?, ?> node,
			JAVADrawingController controller, MouseControlContext context) {

	}

}
