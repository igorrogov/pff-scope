package com.github.igorrogov.pffscope.memory;

import com.github.igorrogov.pffscope.ndb.internal.Header;

import java.lang.foreign.MemoryLayout;
import java.lang.foreign.MemoryLayout.PathElement;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.ValueLayout;
import java.lang.invoke.VarHandle;
import java.nio.ByteOrder;

public class HeaderSegment {

	private final static MemoryLayout HEADER = MemoryLayout.structLayout(

			  ValueLayout.JAVA_INT
						 .withOrder(ByteOrder.LITTLE_ENDIAN)
						 .withName("dwMagic")

	);

	private final static VarHandle DW_MAGIC = HEADER.varHandle(PathElement.groupElement("dwMagic"));

	public static void parse(MemorySegment ms) {
		int magic = (int) DW_MAGIC.get(ms, 0);
		System.out.println("magic: " + magic);
		System.out.println("var handle: " + DW_MAGIC.coordinateTypes());
	}

}
