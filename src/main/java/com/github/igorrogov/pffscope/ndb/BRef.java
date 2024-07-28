package com.github.igorrogov.pffscope.ndb;

import com.github.igorrogov.pffscope.struct.StructFactory;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SeekableByteChannel;

/**
 * The BREF is a record that maps a BID to its absolute file offset location.
 * @param bid		block ID
 * @param offset	absolute file offset location
 */
public record BRef(BlockID bid, long offset) {

	public static BRef from(byte[] rawData)
			  throws IOException
	{
		BRefStruct struct = StructFactory.parse(BRefStruct.class, rawData);
		return new BRef(BlockID.parse(struct.blockID()), struct.offset());
	}

	public static byte[] read(SeekableByteChannel channel, BRef ref, int size)
			  throws IOException
	{
		channel.position(ref.offset());
		ByteBuffer bb = ByteBuffer.allocate(size);
		IOUtils.readFully(channel, bb);
		return bb.array();
	}

}
