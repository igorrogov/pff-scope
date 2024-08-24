package com.github.igorrogov.pffscope.ltp;

// Heap item ID
public record HID(

		  /*
			* HID index.
			* This is the 1-based index value that identifies an item allocated from the heap node.
			*/
		  int index,

		  /*
			* This is the zero-based data block index.
			* This number indicates the zero-based index of the data block in which this heap item resides.
			*/
		  int blockIndex

) {

	public static HID parse(int value) {
		// 5 bits - hidType, MUST be set to 0 (NID_TYPE_HID) to indicate a valid HID.
		// 11 bits - hidIndex
		// 16 bits - hidBlockIndex

		// TODO: OST2013 would be different here

		// ignore first 5 bits (shift right by 5), then ignore last 5 bits (mask by 0x7FF)
		int index = (value >> 5) & 0x7FF;

		// ignore first 16 bits (shift right by 16), then convert to uint16
		int blockIndex = (value >> 16) & 0xFFFF;

		return new HID(index, blockIndex);
	}

}
