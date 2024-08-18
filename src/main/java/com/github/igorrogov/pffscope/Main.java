package com.github.igorrogov.pffscope;

import com.github.igorrogov.pffscope.ltp.HeapHeader;
import com.github.igorrogov.pffscope.ndb.NBTreeEntry;
import com.github.igorrogov.pffscope.ndb.NodeType;
import com.github.igorrogov.pffscope.ndb.Page;
import org.apache.commons.io.HexDump;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.nio.file.Path;

public class Main {

	public static void main(String[] args)
			  throws Exception
	{
		Path pstFile = Path.of(args[0]);
		System.out.println(pstFile);
		try (Pst pst = new Pst(pstFile)) {
			System.out.println(pp(pst.header));

			Page rootNodePage = pst.getRootNodePage();
			Page rootBlockPage = pst.getRootBlockPage();
			System.out.println(" rootNodePage: \n" + pp(rootNodePage) + "\n rootBlockPage: \n" + pp(rootBlockPage));

			System.out.println("nodes: " + pst.nodes.size());
			System.out.println("blocks: " + pst.blocks.size());

			// find first folder that is not root (parent NID != own NID)
			NBTreeEntry folder = pst.nodes.stream()
					  .filter(n -> n.nid().type() == NodeType.NormalFolder && !n.nid().equals(n.parent()))
					  .findFirst()
					  .orElse(null);
			if (folder == null) {
				System.out.println("no folder found");
				return;
			}

			System.out.println("\n\n folder: " + pp(folder));
			System.out.println("\n\n folder data entry: " + pp(pst.getBlockEntry(folder.data())));
			System.out.println("");
			byte[] nodeData = pst.getNodeData(folder);
			HexDump.dump(nodeData, System.out);

			System.out.println("\n\n heap header: " + pp(HeapHeader.parse(nodeData)));
		}
	}

	private static String pp(Object object) {
		return ReflectionToStringBuilder.toString(object, ToStringStyle.MULTI_LINE_STYLE);
	}

}
