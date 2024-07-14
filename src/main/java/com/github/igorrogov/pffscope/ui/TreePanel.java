package com.github.igorrogov.pffscope.ui;

import com.github.igorrogov.pffscope.Pst;

import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

class TreePanel extends JPanel {

	private final JTree tree;

	private final DefaultMutableTreeNode root = new DefaultMutableTreeNode();

	TreePanel() {
		setLayout(new BorderLayout());
		tree = new JTree(root);
		tree.setRootVisible(false);

		JLabel title = new JLabel("PST Files");

		add(title, BorderLayout.NORTH);
		add(tree, BorderLayout.CENTER);
	}

	public void add(Pst pst) {
		String fileName = pst.path.getFileName().toString();
		root.add(new DefaultMutableTreeNode(fileName));
		tree.expandPath(new TreePath(root));
	}

}
