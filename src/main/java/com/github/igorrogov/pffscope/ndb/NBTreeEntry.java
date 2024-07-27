package com.github.igorrogov.pffscope.ndb;

/**
 * Intermediate BTree entry (cLevel > 0).
 */
public record NBTreeEntry(

		  NodeID nid,
		  BlockID data,
		  BlockID sub,
		  NodeID parent

) {

}
