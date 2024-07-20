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
		  BTreePageDataStruct btree,
		  List<BTreeEntry> entries
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

	public static Page read(SeekableByteChannel channel, BRef ref)
			  throws IOException
	{
		channel.position(ref.offset());
		var ps = StructFactory.parse(PageStruct.class, channel);
		PageType type = PageType.forValue(ps.type());

		BTreePageDataStruct btree = null;
		List<BTreeEntry> entries = null;
		if (type == PageType.BlockBTree || type == PageType.NodeBTree) {
			btree = StructFactory.parse(BTreePageDataStruct.class, ps.data());

			if (btree.cLevel() > 0) {
				// BTreeEntry
				int numEntries = btree.cEnt();
				if (numEntries > 0) {
					entries = new ArrayList<>(numEntries);
					channel.position(ref.offset());
					for (int i = 0; i < numEntries; i++) {
						var btes = StructFactory.parse(BTreeEntryStruct.class, channel);
						entries.add(new BTreeEntry(btes.key(), BRef.from(btes.bref())));
					}
				}
			}
			else {
				// TODO: BBTENTRY or NBTENTRY
			}
		}

		return new Page(type, BlockID.parse(ps.bid()), btree, entries);
	}

}
