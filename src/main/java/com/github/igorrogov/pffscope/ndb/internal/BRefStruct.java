package com.github.igorrogov.pffscope.ndb.internal;

import com.github.igorrogov.pffscope.struct.Index;
import com.github.igorrogov.pffscope.struct.Struct;
import com.github.igorrogov.pffscope.struct.Type;

import static com.github.igorrogov.pffscope.struct.FieldType.Int64;

public record BRefStruct(

	/*
  	 * Block Index (BID).
	 */
	@Index(0)
	@Type(Int64)
	long blockID,

   /*
	 * Absolute offset.
	 * Though it is supposed to be an unsigned int 64-bit,
	 * realistically we read it as signed because it can't be more than Long.MAX_VALUE (9,223 PB).
	 */
	@Index(1)
	@Type(Int64)
	long offset

	) implements Struct {
}
