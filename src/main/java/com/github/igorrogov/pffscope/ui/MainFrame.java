package com.github.igorrogov.pffscope.ui;

import com.github.igorrogov.pffscope.Pst;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.nio.file.Path;
import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.SwingWorker;
import javax.swing.UIManager;

public class MainFrame extends JFrame {

	private TreePanel treePanel;

	private PropertiesPanel propertiesPanel;

	private MainFrame(Path path)
			  throws Exception
	{
		setTitle("pff-scope");
		setJMenuBar(new MainMenu());

		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		UIManager.put("MenuBar.border", BorderFactory.createEmptyBorder());
		UIManager.put("ScrollPane.border", BorderFactory.createLineBorder(Color.decode("#CDCDCD")));

		createPanel();

		setSize(800, 600);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);

		loadPst(path);
	}

	private void createPanel() {
		treePanel = new TreePanel();
		propertiesPanel = new PropertiesPanel();

		JPanel detailsPanel = new JPanel();
		detailsPanel.setLayout(new BorderLayout());
		detailsPanel.add(new JLabel("details"), BorderLayout.CENTER);

		JSplitPane leftPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, treePanel, propertiesPanel);
		leftPane.setDividerLocation(200);

		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPane, detailsPanel);
		splitPane.setDividerLocation(200);

		add(splitPane);
	}

	private void loadPst(Path path) {
		var worker = new SwingWorker<Pst, Void>() {
			@Override
			protected Pst doInBackground()
					  throws Exception
			{
				return new Pst(path);
			}

			@Override
			protected void done() {
				try {
					Pst pst = get();
					treePanel.add(pst);
					propertiesPanel.show(pst);
				}
				catch (Exception e) {
					throw new RuntimeException(e);
				}
			}
		};
		worker.execute();
	}

	private class MainMenu extends JMenuBar {

		private MainMenu () {
			super();

			JMenu fileMenu = new JMenu("File");
			fileMenu.add(new JMenuItem("Open PST"));
			add(fileMenu);

			JMenu helpMenu = new JMenu("Help");
			helpMenu.add(new JMenuItem(new AbstractAction("About") {
				@Override
				public void actionPerformed(ActionEvent e) {
					JOptionPane.showMessageDialog(MainFrame.this, "pff-scope");
				}
			}));
			add(helpMenu);
		}

	}

	public static void main(String[] args)
			  throws Exception
	{
		new MainFrame(Path.of(args[0]));
	}

}
