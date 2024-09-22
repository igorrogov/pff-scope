package com.github.igorrogov.pffscope.ndb.internal;

/**
 * BID (Block ID) - Every block allocated in the PST file is identified using the BID structure.
 */
public record BlockID(long index, boolean internal) {

	public static BlockID parse(long rawValue) {
		// 0 bit - reserved
		// 1 bit - 1 for internal, 0 for external
		// 62 bits - index

		boolean internal = (rawValue & 0x02) != 0x00;
		return new BlockID(rawValue >> 2, internal);
	}

}
