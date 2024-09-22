package com.github.igorrogov.pffscope.ndb.internal;

import com.github.igorrogov.pffscope.struct.Bytes;
import com.github.igorrogov.pffscope.struct.Index;
import com.github.igorrogov.pffscope.struct.Struct;
import com.github.igorrogov.pffscope.struct.Type;

import static com.github.igorrogov.pffscope.struct.FieldType.Int64;

public record BTreeEntryStruct(

		  /*
			* The key value associated with this BTENTRY.
			* All the entries in the child BTPAGE referenced by BREF have key values greater than or equal to this key value.
			* The btkey is either an NID (zero extended to 8 bytes for Unicode PSTs) or a BID, depending on the ptype of the page.
			*/
		  @Index(0)
		  @Type(Int64)
		  long key,

		  /*
			* BREF structure (section 2.2.2.4) that points to the child BTPAGE.
			*/
		  @Index(1)
		  @Bytes(16)
		  byte[] bref

) implements Struct {

}
