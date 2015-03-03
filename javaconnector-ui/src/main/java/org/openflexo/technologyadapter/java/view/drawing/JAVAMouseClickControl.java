package org.openflexo.technologyadapter.java.view.drawing;

import java.util.logging.Logger;

import org.openflexo.fge.Drawing.DrawingTreeNode;
import org.openflexo.fge.control.MouseClickControl;
import org.openflexo.fge.control.MouseClickControlAction;
import org.openflexo.fge.control.MouseControlContext;
import org.openflexo.foundation.resource.RepositoryFolder;
import org.openflexo.technologyadapter.java.model.JAVAFileModel;
import org.openflexo.technologyadapter.java.rm.JAVAResource;
import org.openflexo.technologyadapter.java.view.JAVARepositoryView;

public class JAVAMouseClickControl implements MouseClickControl<JAVADrawingController> {

	private static final Logger LOGGER = Logger.getLogger(JAVAMouseClickControl.class.getPackage().getName());

	private JAVARepositoryView repositoryView;

	public JAVAMouseClickControl(JAVARepositoryView repositoryView) {
		this.repositoryView = repositoryView;
	}

	@Override
	public boolean isApplicable(DrawingTreeNode<?, ?> node, JAVADrawingController controller, MouseControlContext context) {
		if (context.isControlDown()) {
			Object obj = ((JAVANode) node.getDrawable()).getModel();
			if (obj instanceof RepositoryFolder<?>) {
				RepositoryFolder<JAVAResource> repository = (RepositoryFolder<JAVAResource>) obj;
				JAVARepositoryConstructor constructor = new JAVARepositoryConstructor(repository, repositoryView);
				repositoryView.addTab(repository.getName(), constructor.createPanel());
			}
			else if (obj instanceof JAVAFileModel) {
				JAVAFileModel fileModel = (JAVAFileModel) obj;
				if (fileModel.getName().toLowerCase().endsWith(".java")) {
					JAVAFileViewConstructor constructor = new JAVAFileViewConstructor(fileModel, repositoryView);
					repositoryView.addTab(fileModel.getName(), constructor.createPanel());
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
	public void setControlAction(MouseClickControlAction<JAVADrawingController> action) {
	}

	@Override
	public int getClickCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void handleClick(DrawingTreeNode<?, ?> node, JAVADrawingController controller, MouseControlContext context) {

	}

}
