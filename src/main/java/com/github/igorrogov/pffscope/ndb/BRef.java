package com.github.igorrogov.pffscope.ndb;

import com.github.igorrogov.pffscope.struct.StructFactory;

import java.io.IOException;

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

}
