package com.github.igorrogov.pffscope.ndb.internal;

/**
 * Intermediate BTree entry (cLevel > 0).
 */
public record BBTreeEntry(

		 BRef bRef,
		 int size

) {

}
