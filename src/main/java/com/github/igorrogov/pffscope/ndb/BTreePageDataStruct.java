package com.github.igorrogov.pffscope.ndb;

import com.github.igorrogov.pffscope.struct.Bytes;
import com.github.igorrogov.pffscope.struct.Index;
import com.github.igorrogov.pffscope.struct.Struct;
import com.github.igorrogov.pffscope.struct.Type;

import static com.github.igorrogov.pffscope.struct.FieldType.UInt8;

public record BTreePageDataStruct(

		  /*
			* Entries of the BTree array.
			*/
		  @Index(0)
		  @Bytes(488)
		  byte[] rgentries,

		  /*
			* The number of BTree entries stored in the page data.
			*/
		  @Index(1)
		  @Type(UInt8)
		  int cEnt,

		  /*
			* The maximum number of entries that can fit inside the page data.
			*/
		  @Index(2)
		  @Type(UInt8)
		  int cEntMax,

		  /*
			* The size of each BTree entry, in bytes.
			*/
		  @Index(3)
		  @Type(UInt8)
		  int cbEnt,

		  /*
			* The depth level of this page. Leaf pages have a level of zero, whereas intermediate pages have a level greater than 0.
			* This value determines the type of the entries in rgentries, and is interpreted as unsigned.
			*/
		  @Index(4)
		  @Type(UInt8)
		  int cLevel

		  ) implements Struct
{

}
