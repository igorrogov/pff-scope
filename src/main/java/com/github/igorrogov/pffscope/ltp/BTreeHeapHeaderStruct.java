package com.github.igorrogov.pffscope.ltp;

import com.github.igorrogov.pffscope.struct.Index;
import com.github.igorrogov.pffscope.struct.Struct;
import com.github.igorrogov.pffscope.struct.Type;

import static com.github.igorrogov.pffscope.struct.FieldType.Int32;
import static com.github.igorrogov.pffscope.struct.FieldType.UInt8;

public record BTreeHeapHeaderStruct(

		  /*
			* MUST be bTypeBTH (5).
			*/
		  @Index(0)
		  @Type(UInt8)
		  int bType,

		  /*
			* Size of the BTree Key value, in bytes. This value MUST be set to 2, 4, 8, or 16.
			*/
		  @Index(1)
		  @Type(UInt8)
		  int cbKey,

		  /*
			* Size of the data value, in bytes. This MUST be greater than zero and less than or equal to 32.
			*/
		  @Index(2)
		  @Type(UInt8)
		  int cbEnt,

		  /*
			* Index depth (zero based).
			*/
		  @Index(3)
		  @Type(UInt8)
		  int bIdxLevels,

		  /*
			* This is the HID that points to the BTH entries for this BTHHEADER.
			* The data consists of an array of BTH records. This value is set to zero if the BTH is empty.
			*/
		  @Index(4)
		  @Type(Int32)
		  int hidRoot

		  ) implements Struct  {

}
