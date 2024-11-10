package com.github.igorrogov.pffscope.memory;

import com.github.igorrogov.pffscope.memory.SegmentParser.Field;

import java.lang.foreign.MemorySegment;

import static java.nio.ByteOrder.LITTLE_ENDIAN;

public class HeaderSegment {

	private static final Field<Integer, Long> DW_MAGIC = SegmentParser.newUInt32();

	private static final SegmentParser HEADER_PARSER = SegmentParser.build(LITTLE_ENDIAN, DW_MAGIC);

	public static void parse(MemorySegment ms) {
		long magic = HEADER_PARSER.get(ms, DW_MAGIC);
		System.out.println("magic: " + magic);
	}

}
