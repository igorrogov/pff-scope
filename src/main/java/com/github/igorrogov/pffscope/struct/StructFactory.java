package com.github.igorrogov.pffscope.struct;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class StructFactory {

	public static <T extends Struct> T parse(Class<T> cls, byte[] bytes)
			  throws Exception
	{
		return parse(cls, Channels.newChannel(new ByteArrayInputStream(bytes)));
	}

	@SuppressWarnings("unchecked")
	public static <T extends Struct> T parse(Class<T> cls, ReadableByteChannel channel)
			  throws IOException
	{
		var c = (Constructor<T>) cls.getDeclaredConstructors()[0];

		List<Field> fields = Arrays.stream(cls.getDeclaredFields())
				  .sorted(Comparator.comparing(StructFactory::getSortIndex))
				  .toList();

		Object[] args = new Object[fields.size()];
		for (int i = 0; i < fields.size(); i++) {
			args[i] = createFieldArgument(fields.get(i), channel);
		}

		try {
			return c.newInstance(args);
		}
		catch (Exception e) {
			throw new IOException(e);
		}
	}

	private static int getSortIndex(Field field) {
		return field.getAnnotation(Index.class).value();
	}

	private static Object createFieldArgument(Field field, ReadableByteChannel channel)
			  throws IOException
	{
		SkipField skip = field.getAnnotation(SkipField.class);
		if (skip != null) {
			channel.read(ByteBuffer.allocate(skip.value()));
			return null;
		}

		Bytes bytes = field.getAnnotation(Bytes.class);
		if (bytes != null) {
			ByteBuffer bb = ByteBuffer.allocate(bytes.value());
			channel.read(bb);
			return bb.array();
		}

		Type type = field.getAnnotation(Type.class);
		return type.value().read.read(channel);
	}

}
