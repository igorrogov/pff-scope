package com.github.igorrogov.pffscope.ui;

import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;

class PropertiesPanel extends JPanel {

	private final JTable table;

	PropertiesPanel() {
		setLayout(new BorderLayout());
		table = new JTable(new Object[][] { { "key1", "value1" }, { "key2", "value2" } }, new String[] { "Name", "Value" });

		JLabel title = new JLabel("Properties");

		add(title, BorderLayout.NORTH);
		add(table, BorderLayout.CENTER);
	}

}
