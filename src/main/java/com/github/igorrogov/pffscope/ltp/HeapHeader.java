package com.github.igorrogov.pffscope.ltp;

import com.github.igorrogov.pffscope.struct.StructFactory;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Arrays;

import static java.nio.ByteOrder.LITTLE_ENDIAN;

public record HeapHeader(

		  ClientSignature signature,
		  int hidUserRoot,
		  int[] entryOffsets

) {

	public static HeapHeader parse(byte[] data)
			  throws IOException
	{
		HeapHeaderStruct hhs = StructFactory.parse(HeapHeaderStruct.class, data);
		if (hhs.bSig() != 0xEC) {
			throw new IOException("Invalid Heap-on-Node signature: " + hhs.bSig());
		}

		ByteBuffer bb = ByteBuffer.wrap(data).order(LITTLE_ENDIAN);
		bb.position(hhs.ibHnpm());
		int entries = bb.getShort() & 0xffff;
		bb.getShort(); // cFree, free count

		int[] entryOffsets = new int[entries];

		// offsets
		for (int i = 0; i < entries; i++) {
			entryOffsets[i] = bb.getShort() & 0xffff;
		}

		ClientSignature signature = ClientSignature.forValue(hhs.bClientSig());
		return new HeapHeader(signature, hhs.hidUserRoot(), entryOffsets);
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
