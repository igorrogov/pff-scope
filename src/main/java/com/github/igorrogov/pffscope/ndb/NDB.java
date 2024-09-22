package com.github.igorrogov.pffscope.ndb;

/**
 * Node Database (NDB) public API.
 */
public interface NDB {

	Node getRootNode();

	Node getNode(NID nid);

}
