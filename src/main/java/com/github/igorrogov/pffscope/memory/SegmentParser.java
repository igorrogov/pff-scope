package com.github.igorrogov.pffscope.memory;

import java.lang.foreign.MemoryLayout;
import java.lang.foreign.MemoryLayout.PathElement;
import java.lang.foreign.MemorySegment;
import java.lang.invoke.VarHandle;
import java.nio.ByteOrder;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import static java.lang.foreign.ValueLayout.JAVA_INT;

public class SegmentParser {

	public static class Field<M, R> {

		private final Function<ByteOrder, MemoryLayout> memory;

		private final Function<M, R> function;

		private Field(Function<ByteOrder, MemoryLayout> memory, Function<M, R> function) {
			this.memory = memory;
			this.function = function;
		}
	}

	public static Field<Integer, Long> newUInt32() {
		return new Field<>(JAVA_INT::withOrder, value -> value & 0xffffffffL);
	}

	public static SegmentParser build(ByteOrder order, Field<?, ?>... fields) {
		MemoryLayout[] fieldLayouts = Arrays.stream(fields)
				  .map(field -> field.memory.apply(order))
				  .toArray(MemoryLayout[]::new);

		Map<Field<?, ?>, VarHandle> handles = new HashMap<>();

		MemoryLayout structure = MemoryLayout.structLayout(fieldLayouts);
		for (int i = 0; i < fields.length; i++) {
			handles.put(fields[i], structure.varHandle(PathElement.groupElement(i)));
		}

		return new SegmentParser(Collections.unmodifiableMap(handles));
	}

	private final Map<Field<?, ?>, VarHandle> handles;

	private SegmentParser(Map<Field<?, ?>, VarHandle> handles) {
		this.handles = handles;
	}

	@SuppressWarnings("unchecked")
	public <M, R> R get(MemorySegment ms, Field<M, R> field) {
		M intermediate = (M) handles.get(field).get(ms, 0);
		return field.function.apply(intermediate);
	}

}
