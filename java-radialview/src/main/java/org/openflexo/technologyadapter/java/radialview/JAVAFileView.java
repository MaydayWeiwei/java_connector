package org.openflexo.technologyadapter.java.radialview;

import java.awt.Dimension;

import javax.swing.JDialog;

import org.openflexo.technologyadapter.java.model.JAVAFileModel;
import org.openflexo.technologyadapter.java.radialview.library.JAVAFileViewConstructor;

public class JAVAFileView extends JDialog {

	public JAVAFileView(JAVAFileModel fileModel) {
		JAVAFileViewConstructor constructor = new JAVAFileViewConstructor(fileModel);
		getContentPane().add(constructor.createPanel());

		setPreferredSize(new Dimension(550, 600));
		validate();
		pack();

		setVisible(true);
	}

}
