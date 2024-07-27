package com.github.igorrogov.pffscope.struct;

import java.io.IOException;
import java.nio.channels.ReadableByteChannel;

public enum FieldType {

	UInt8(1, c -> Buffers.readLE(c, 1).get() & 0xff),
	UInt16(2, c -> Buffers.readLE(c, 2).getShort() & 0xffff),
	UInt32(4, c -> Buffers.readLE(c, 4).getInt() & 0xffffffffL),
	Int32(4, c -> Buffers.readLE(c, 4).getInt()),
	Int64(8, c -> Buffers.readLE(c, 8).getLong());

	public final int length;

	public final ReadFunction read;

	FieldType(int length, ReadFunction read) {
		this.length = length;
		this.read = read;
	}

	public interface ReadFunction {

		Object read(ReadableByteChannel channel)
				  throws IOException;

	}

}
