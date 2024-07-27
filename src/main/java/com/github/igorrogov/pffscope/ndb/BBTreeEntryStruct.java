package com.github.igorrogov.pffscope.ndb;

import com.github.igorrogov.pffscope.struct.Bytes;
import com.github.igorrogov.pffscope.struct.Index;
import com.github.igorrogov.pffscope.struct.SkipField;
import com.github.igorrogov.pffscope.struct.Struct;
import com.github.igorrogov.pffscope.struct.Type;

import static com.github.igorrogov.pffscope.struct.FieldType.Int32;
import static com.github.igorrogov.pffscope.struct.FieldType.Int64;
import static com.github.igorrogov.pffscope.struct.FieldType.UInt16;

public record BBTreeEntryStruct(

		  @Index(0)
		  @Bytes(16)
		  byte[] bref,

		  /*
			* The count of bytes of the raw data contained in the block referenced by BREF
			* excluding the block trailer and alignment padding, if any.
			*/
		  @Index(1)
		  @Type(UInt16)
		  int cb,

		  /*
			* Reference count indicating the count of references to this block.
			*/
		  @Index(2)
		  @Type(UInt16)
		  int cRef,

		  /*
			* Reference count indicating the count of references to this block.
			*/
		  @Index(3)
		  @SkipField(4)
		  Object dwPadding


		  ) implements Struct {

}
