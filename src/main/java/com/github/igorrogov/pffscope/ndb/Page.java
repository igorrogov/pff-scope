package com.github.igorrogov.pffscope.ndb;

import com.github.igorrogov.pffscope.struct.StructFactory;

import java.io.IOException;
import java.nio.channels.SeekableByteChannel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public record Page(
		  PageType type,
		  BlockID bid,
		  List<BTreeEntry> subPages,
		  List<NBTreeEntry> nodes,
		  List<BBTreeEntry> blocks
) {

	public enum PageType {
		BlockBTree(0x80),
		NodeBTree(0x81),
		FreeMap(0x82),
		AllocPageMap(0x83),
		AllocMap(0x84),
		FreePageMap(0x85),
		DList(0x86),
		Unknown(0x00);

		public final int value;

		PageType(int value) {
			this.value = value;
		}

		public static PageType forValue(int value) {
			return Arrays.stream(PageType.values()).filter(c -> c.value == value).findFirst().orElse(Unknown);
		}
	}

	public List<Page> readSubPages(SeekableByteChannel channel)
			  throws IOException
	{
		List<Page> pages = new ArrayList<>();
		for (BTreeEntry child : subPages) {
			pages.add(read(channel, child.childRef()));
		}
		return pages;
	}

	public static Page read(SeekableByteChannel channel, BRef ref)
			  throws IOException
	{
		channel.position(ref.offset());
		var ps = StructFactory.parse(PageStruct.class, channel);
		PageType type = PageType.forValue(ps.type());

		if (type != PageType.BlockBTree && type != PageType.NodeBTree) {
			return new Page(type, BlockID.parse(ps.bid()), List.of(), List.of(), List.of());
		}

		BTreePageDataStruct btree = StructFactory.parse(BTreePageDataStruct.class, ps.data());

		List<BTreeEntry> subPages = new ArrayList<>();
		List<NBTreeEntry> nodes = new ArrayList<>();
		List<BBTreeEntry> blocks = new ArrayList<>();

		int numEntries = btree.cEnt();
		if (numEntries > 0) {
			subPages = new ArrayList<>(numEntries);
			channel.position(ref.offset());
			for (int i = 0; i < numEntries; i++) {
				if (btree.cLevel() > 0) {
					// intermediate node
					var btes = StructFactory.parse(BTreeEntryStruct.class, channel);
					subPages.add(new BTreeEntry(btes.key(), BRef.from(btes.bref())));
				}
				else if (type == PageType.NodeBTree) {
					var es = StructFactory.parse(NBTreeEntryStruct.class, channel);
					nodes.add(new NBTreeEntry(
							  NodeID.parse(es.nid()),
							  BlockID.parse(es.bidData()),
							  BlockID.parse(es.bidSub()),
							  NodeID.parse(es.nidParent())));
				}
				else if (type == PageType.BlockBTree) {
					var es = StructFactory.parse(BBTreeEntryStruct.class, channel);
					blocks.add(new BBTreeEntry(BRef.from(es.bref()), es.cb()));
				}
			}
		}

		return new Page(type, BlockID.parse(ps.bid()), subPages, nodes, blocks);
	}

}
