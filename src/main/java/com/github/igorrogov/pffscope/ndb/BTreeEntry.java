package com.github.igorrogov.pffscope.ndb;

/**
 * Intermediate BTree entry (cLevel > 0).
 */
public record BTreeEntry(

		  long key,

		  BRef childRef

) {

}
