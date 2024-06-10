package com.github.igorrogov.pffscope.struct;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Struct {

	private final List<Field> fields;

	private Struct(List<Field> fields) {
		this.fields = fields;
	}

	public Map<Field, byte[]> parse(ByteBuffer bb) {
		Map<Field, byte[]> result = new HashMap<>();
		for (Field field : fields) {
			if (field.skip) {
				bb.position(bb.position() + field.size);
			}
			else {
				byte[] bytes = new byte[field.size];
				bb.get(bytes);
				result.put(field, bytes);
			}
		}
		return result;
	}

	public static class Builder {

		private final List<Field> fields = new ArrayList<>();

		public Builder field(Field field) {
			fields.add(field);
			return this;
		}

		public Builder skip(String name, int size) {
			fields.add(new Field(name, size, true));
			return this;
		}

		public Struct build() {
			return new Struct(Collections.unmodifiableList(fields));
		}

	}

	public record Field(String name, int size, boolean skip) {

		public static Field create(String name, int size) {
			return new Field(name, size, false);
		}

	}

}
