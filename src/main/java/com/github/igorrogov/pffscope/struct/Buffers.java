package com.github.igorrogov.pffscope.struct;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.ReadableByteChannel;

import static java.nio.ByteOrder.LITTLE_ENDIAN;

class Buffers {

	private static final ThreadLocal<ByteBuffer> LITTLE_ENDIAN_BUFFERS = ThreadLocal.withInitial(() -> ByteBuffer.allocate(8).order(LITTLE_ENDIAN));

	static ByteBuffer readLE(ReadableByteChannel channel, int size)
			  throws IOException
	{
		ByteBuffer bb = Buffers.LITTLE_ENDIAN_BUFFERS.get().clear().limit(size);
		channel.read(bb);
		bb.flip();
		return bb;
	}

}
