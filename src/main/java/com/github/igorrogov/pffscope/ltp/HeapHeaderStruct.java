package com.github.igorrogov.pffscope.ltp;

import com.github.igorrogov.pffscope.struct.Index;
import com.github.igorrogov.pffscope.struct.Struct;
import com.github.igorrogov.pffscope.struct.Type;

import static com.github.igorrogov.pffscope.struct.FieldType.Int32;
import static com.github.igorrogov.pffscope.struct.FieldType.UInt16;
import static com.github.igorrogov.pffscope.struct.FieldType.UInt8;

public record HeapHeaderStruct(

		  /*
			* The byte offset to the HN page Map record (section 2.3.1.5), with respect to the beginning of the HNHDR structure.
			*/
		  @Index(0)
		  @Type(UInt16)
		  int ibHnpm,

		  /*
			* Block signature; MUST be set to 0xEC to indicate an HN.
			*/
		  @Index(1)
		  @Type(UInt8)
		  int bSig,

		  /*
			* Client signature.
			*/
		  @Index(2)
		  @Type(UInt8)
		  int bClientSig,

		  /*
			* HID that points to the User Root record.
			*/
		  @Index(3)
		  @Type(Int32)
		  int hidUserRoot,

		  /*
			* Per-block Fill Level Map.
			*/
		  @Index(4)
		  @Type(Int32)
		  int rgbFillLevel

) implements Struct
{

}
