package com.github.igorrogov.pffscope.ndb;

import com.github.igorrogov.pffscope.struct.Bytes;
import com.github.igorrogov.pffscope.struct.Index;
import com.github.igorrogov.pffscope.struct.SkipField;
import com.github.igorrogov.pffscope.struct.Struct;
import com.github.igorrogov.pffscope.struct.Type;

import static com.github.igorrogov.pffscope.struct.FieldType.Int64;

public record RootStruct(

		  /*
			* dwReserved		4 bytes
			*/
		  @Index(0)
		  @SkipField(4)
		  Object dwReserved,

		  @Index(1)
		  @Type(Int64)
		  long ibFileEof, // total file size

		  /*
			* ibAMapLast		8 bytes
			* cbAMapFree		8 bytes
			* cbPMapFree		8 bytes
			*/
		  @Index(2)
		  @SkipField(8 + 8 + 8)
		  Object unused2,

		  @Index(3)
		  @Bytes(16)
		  byte[] brefNBT,

		  @Index(4)
		  @Bytes(16)
		  byte[] brefBBT

		  // skip fAMapValid		1 byte
		  // skip bReserved		1 byte
		  // skip wReserved		2 bytes

) implements Struct
{

}
