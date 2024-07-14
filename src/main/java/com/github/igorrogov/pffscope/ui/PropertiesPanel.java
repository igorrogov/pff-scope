package com.github.igorrogov.pffscope.ui;

import com.github.igorrogov.pffscope.Pst;
import com.github.igorrogov.pffscope.ndb.Header;
import org.apache.commons.lang3.StringUtils;

import java.awt.BorderLayout;
import java.text.NumberFormat;
import java.util.HexFormat;
import java.util.List;
import java.util.Vector;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;

class PropertiesPanel extends JPanel {

	private static final HexFormat HEX = HexFormat.of().withLowerCase();

	private static final NumberFormat NF = NumberFormat.getInstance();

	private static final Vector<String> COLUMNS = new Vector<>(List.of("Name", "Value"));

	private final JTable table;

	private final Vector<Vector<String>> rows = new Vector<>();

	PropertiesPanel() {
		setLayout(new BorderLayout());
		table = new JTable(rows, COLUMNS);

		JLabel title = new JLabel("Properties");

		add(title, BorderLayout.NORTH);
		add(table, BorderLayout.CENTER);
	}

	public void show(Pst pst) {
		rows.clear();

		Header h = pst.header;
		addProperty("Client Signature", withHex(h.clientSignature(), h.clientSignatureRaw()));
		addProperty("Format", withHex(h.format(), h.formatRaw()));
		addProperty("Client Version", withHex(null, h.clientVersion()));
		addProperty("Crypt Method", withHex(h.cryptMethod(), h.cryptMethodRaw()));
		addProperty("File Size", NF.format(h.fileSize()));

		table.tableChanged(null);
	}

	private void addProperty(String name, String value) {
		rows.add(new Vector<>(List.of(name, value)));
	}

	private String withHex(Object object, int value) {
		String hex = "0x" + StringUtils.stripStart(HEX.toHexDigits(value), "0");
		if (object == null) {
			return hex;
		}

		return object + " (" + hex + ")";
	}

}
