package com.github.igorrogov.pffscope.ndb.internal;

import com.github.igorrogov.pffscope.ndb.NID;

/**
 * Intermediate BTree entry (cLevel > 0).
 */
public record NBTreeEntry(

		  NID nid,
		  BlockID data,
		  BlockID sub,
		  NID parent

) {

}
