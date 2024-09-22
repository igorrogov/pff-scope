package com.github.igorrogov.pffscope.ndb;

/**
 * A node object on the NDB b-tree which is addressed by its NID.
 */
public interface Node {

	NID nid();

	NID parent();

}
