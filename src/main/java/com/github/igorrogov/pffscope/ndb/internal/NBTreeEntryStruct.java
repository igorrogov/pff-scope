package com.github.igorrogov.pffscope.ndb.internal;

import com.github.igorrogov.pffscope.struct.Index;
import com.github.igorrogov.pffscope.struct.SkipField;
import com.github.igorrogov.pffscope.struct.Struct;
import com.github.igorrogov.pffscope.struct.Type;

import static com.github.igorrogov.pffscope.struct.FieldType.Int32;
import static com.github.igorrogov.pffscope.struct.FieldType.Int64;

public record NBTreeEntryStruct(

		  @Index(0)
		  @Type(Int32)
		  int nid,

		  /*
			* Unused part of NID.
			*/
		  @Index(1)
		  @SkipField(4)
		  Object nidUnused,

		  /*
			* The BID of the data block for this node.
			*/
		  @Index(2)
		  @Type(Int64)
		  long bidData,

		  /*
			* The BID of the subnode block for this node.
			* If this value is zero, a subnode block does not exist for this node.
			*/
		  @Index(3)
		  @Type(Int64)
		  long bidSub,

		  /*
		   * NID of the parent Folder object's node or zero.
		   */
		  @Index(4)
		  @Type(Int32)
		  int nidParent,

		  @Index(5)
		  @SkipField(4)
		  Object dwPadding

		  ) implements Struct {

}
