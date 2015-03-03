package org.openflexo.technologyadapter.java.view.drawing;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import org.openflexo.fge.swing.JDianaInteractiveEditor;
import org.openflexo.fge.swing.SwingViewFactory;
import org.openflexo.fge.swing.control.SwingToolFactory;
import org.openflexo.fge.swing.control.tools.JDianaScaleSelector;

public class JAVADrawingController extends JDianaInteractiveEditor<JAVAGraph> {

	private final JPopupMenu contextualMenu;
	private final JDianaScaleSelector scaleSelector;

	public JAVADrawingController(CircularDrawing aDrawing) {
		super(aDrawing, aDrawing.getFactory(), SwingViewFactory.INSTANCE, SwingToolFactory.DEFAULT);
		scaleSelector = (JDianaScaleSelector) getToolFactory().makeDianaScaleSelector(this);
		contextualMenu = new JPopupMenu();
		contextualMenu.add(new JMenuItem("Item"));
	}

	public JPopupMenu getContextualMenu() {
		return contextualMenu;
	}

	public JDianaScaleSelector getScaleSelector() {
		return scaleSelector;
	}

}
