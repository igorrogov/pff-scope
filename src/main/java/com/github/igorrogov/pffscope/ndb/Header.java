package com.github.igorrogov.pffscope.ndb;

import com.github.igorrogov.pffscope.struct.Struct;
import com.github.igorrogov.pffscope.struct.Struct.Field;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SeekableByteChannel;

public class Header {

	private static final Field dwMagic = Field.create("dwMagic", 32);

	private static final Struct HEADER = new Struct.Builder()
			  .field(dwMagic)
			  .skip("dwCRCPartial", 32)
			  .build();

	public static Header parse(SeekableByteChannel fc)
			  throws IOException
	{
		ByteBuffer bb = ByteBuffer.allocate(64);
		fc.read(bb);
		bb.flip();

		var result = HEADER.parse(bb);
		validateMagic(result.get(dwMagic));

		return new Header();
	}

	private static void validateMagic(byte[] magicBytes) {
		// TODO:
	}

	private Header() {

	}

}
