package com.github.igorrogov.pffscope.ndb;

/**
 * The BREF is a record that maps a BID to its absolute file offset location.
 * @param bid		block ID
 * @param offset	absolute file offset location
 */
public record BRef(BlockID bid, long offset) {

}
