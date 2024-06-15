package com.github.igorrogov.pffscope.struct;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.nio.channels.SeekableByteChannel;

public class Struct {

	@SuppressWarnings("unchecked")
	public static <T extends Record> T parse(Class<T> cls, SeekableByteChannel channel)
			  throws Exception
	{
		var c = (Constructor<T>) cls.getDeclaredConstructors()[0];

		Field[] fields = cls.getDeclaredFields();
		Object[] args = new Object[fields.length];
		for (int i = 0; i < fields.length; i++) {
			args[i] = createField(fields[i], channel);
		}

		return c.newInstance(args);
	}

	private static Object createField(Field field, SeekableByteChannel channel)
			  throws IOException
	{
		StructField ft = field.getAnnotation(StructField.class);
		return ft.type().read.read(channel);
	}

}
