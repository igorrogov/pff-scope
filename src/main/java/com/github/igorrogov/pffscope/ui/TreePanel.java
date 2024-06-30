package com.github.igorrogov.pffscope.ui;

import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTree;

class TreePanel extends JPanel {

	private final JTree tree;

	TreePanel() {
		setLayout(new BorderLayout());
		tree = new JTree(new String[] {"test.pst"});

		JLabel title = new JLabel("PST Files");

		add(title, BorderLayout.NORTH);
		add(tree, BorderLayout.CENTER);
	}

}
