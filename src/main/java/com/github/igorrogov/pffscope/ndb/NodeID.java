package com.github.igorrogov.pffscope.ndb;

public record NodeID(
		  NodeType type,
		  int index
) {

	public static NodeID parse(int rawValue) {
		// in PST spec, the type is stored in the first 5 bits (Little Endian)
		// in Java, the byte order is reversed (LE -> BE), therefore we read the last 5 bits instead

		int type = rawValue & 0x1F; // last 5 bits
		int index = rawValue >>> 5; // first 27 bits
		return new NodeID(NodeType.from(type), index);
	}

}
