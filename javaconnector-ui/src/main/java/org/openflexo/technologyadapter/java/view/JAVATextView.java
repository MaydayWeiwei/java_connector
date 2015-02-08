package org.openflexo.technologyadapter.java.view;

import javax.swing.JFrame;
import javax.swing.JTextArea;

@SuppressWarnings("serial")
public class JAVATextView extends JFrame{

	public JAVATextView(JTextArea textView) {
		this.getContentPane().add(textView);
		this.pack();
		this.setVisible(true);
	}
}
