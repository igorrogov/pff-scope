package com.github.igorrogov.pffscope.ndb;

import com.github.igorrogov.pffscope.struct.Bytes;
import com.github.igorrogov.pffscope.struct.Index;
import com.github.igorrogov.pffscope.struct.SkipField;
import com.github.igorrogov.pffscope.struct.Struct;

public record Root(

	/*
	 * dwReserved		4 bytes
	 * ibFileEof		8 bytes	(file size)
	 * ibAMapLast		8 bytes
	 * cbAMapFree		8 bytes
	 * cbPMapFree		8 bytes
	 */
	@Index(0)
	@SkipField(4 + 8 + 8 + 8 + 8)
	Object unused1,

	@Index(1)
	@Bytes(16)
	byte[] brefNBT,

	@Index(2)
	@Bytes(16)
	byte[] brefBBT

) implements Struct {
}
