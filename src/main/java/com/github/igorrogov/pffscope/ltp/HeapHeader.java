package com.github.igorrogov.pffscope.ltp;

import com.github.igorrogov.pffscope.struct.StructFactory;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Arrays;

import static java.nio.ByteOrder.LITTLE_ENDIAN;

public record HeapHeader(

		  ClientSignature signature,
		  HID hidUserRoot,
		  int[] entryOffsets,
		  BTreeHeapHeaderStruct bthh,
		  HID btthUserRoor

) {

	public static HeapHeader parse(byte[] data)
			  throws IOException
	{
		HeapHeaderStruct hhs = StructFactory.parse(HeapHeaderStruct.class, data);
		if (hhs.bSig() != 0xEC) {
			throw new IOException("Invalid Heap-on-Node signature: " + hhs.bSig());
		}

		int[] entryOffsets = parsePageMap(data, hhs);
		ClientSignature signature = ClientSignature.forValue(hhs.bClientSig());

		byte[] subData = Arrays.copyOfRange(data, entryOffsets[0], entryOffsets[0] + 8);
		BTreeHeapHeaderStruct bthh = StructFactory.parse(BTreeHeapHeaderStruct.class, subData);
		HID btthUserRoor = HID.parse(bthh.hidRoot());

		return new HeapHeader(signature, HID.parse(hhs.hidUserRoot()), entryOffsets, bthh, btthUserRoor);
	}

	private static int[] parsePageMap(byte[] data, HeapHeaderStruct hhs) {
		// HNPAGEMAP
		// cAlloc (2 bytes) - Allocation count. This represents the number of items (allocations) in the HN.
		// cFree (2 bytes) - Free count. This represents the number of freed items in the HN.
		// rgibAlloc (variable) - Allocation table. This contains cAlloc + 1 entries. Each entry is 2 bytes.

		int pageMapOffset = hhs.ibHnpm();

		ByteBuffer bb = ByteBuffer.wrap(data).order(LITTLE_ENDIAN);
		bb.position(pageMapOffset);
		int entries = bb.getShort() & 0xffff;
		bb.getShort(); // cFree, free count

		int[] entryOffsets = new int[entries];

		// offsets
		for (int i = 0; i < entries; i++) {
			entryOffsets[i] = bb.getShort() & 0xffff;
		}

		return entryOffsets;
	}

	public enum ClientSignature {
		TableContext(0x7C),
		BTree(0xB5),
		PropertyContext(0xBC),
		Unknown(0x00);

		public final int value;

		ClientSignature(int value) {
			this.value = value;
		}

		public static ClientSignature forValue(int value) {
			return Arrays.stream(ClientSignature.values()).filter(c -> c.value == value).findFirst().orElse(Unknown);
		}

	}

}
