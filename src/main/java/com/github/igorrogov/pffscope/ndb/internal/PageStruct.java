package com.github.igorrogov.pffscope.ndb.internal;

import com.github.igorrogov.pffscope.struct.Bytes;
import com.github.igorrogov.pffscope.struct.Index;
import com.github.igorrogov.pffscope.struct.SkipField;
import com.github.igorrogov.pffscope.struct.Struct;
import com.github.igorrogov.pffscope.struct.Type;

import static com.github.igorrogov.pffscope.struct.FieldType.Int64;
import static com.github.igorrogov.pffscope.struct.FieldType.UInt8;

/**
 * Unicode (64-bit) page.
 */
public record PageStruct(

		  /*
			* Page data.
			*/
		  @Index(0)
		  @Bytes(496)
		  byte[] data,

		  /*
			* Page type.
			*/
		  @Index(1)
		  @Type(UInt8)
		  int type,

		  /*
		   * ptypeRepeat		1 byte
		   * wSig				2 bytes
		   * dwCRC				4 bytes
		   */
		  @Index(2)
		  @SkipField(1 + 2 + 4)
		  Object unused,

		  /*
			* Block ID of the page.
			*/
		  @Index(3)
		  @Type(Int64)
		  long bid

) implements Struct {

}
