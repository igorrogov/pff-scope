package com.github.igorrogov.pffscope.ndb;

import com.github.igorrogov.pffscope.struct.Bytes;
import com.github.igorrogov.pffscope.struct.Index;
import com.github.igorrogov.pffscope.struct.SkipField;
import com.github.igorrogov.pffscope.struct.Struct;
import com.github.igorrogov.pffscope.struct.Type;

import static com.github.igorrogov.pffscope.struct.FieldType.*;

public record HeaderStruct(

	@Index(0)
	@Type(UInt32)
	long dwMagic,

	@Index(1)
	@Type(UInt32)
	long dwCRCPartial,

	@Index(2)
	@Type(UInt16)
	int wMagicClient,

	@Index(3)
	@Type(UInt16)
	int wVer,

	@Index(4)
	@Type(UInt16)
	int wVerClient,

	@Index(5)
	@Type(UInt8)
	int bPlatformCreate,

	@Index(6)
	@Type(UInt8)
	int bPlatformAccess,

	/*
	 * dwReserved1		4 bytes
	 * dwReserved2		4 bytes
	 * bidUnused		8 bytes
	 * bidNextP			8 bytes
	 * dwUnique			4 bytes
	 * rgnid[]			128 bytes
	 * qwUnused			8 bytes
	 */
	@Index(7)
	@SkipField(4 + 4 + 8 + 8 + 4 + 128 + 8)
	Object unusedFieldsBeforeRoot,

	@Index(8)
	@Bytes(72)
	byte[] root,

  /*
	* dwAlign		4 bytes
	* rgbFM			128 bytes
	* rgbFP			128 bytes
	* bSentinel		1 byte
	*/
	@Index(9)
	@SkipField(4 + 128 + 128 + 1)
	Object unusedFieldsAfterRoot,

	@Index(10)
	@Type(UInt8)
	int bCryptMethod,

  /*
	* rgbReserved	2 bytes
	* bidNextB		8 bytes
	* dwCRCFull		4 bytes
	* rgbReserved2	3 bytes
	* bReserved		1 byte
	* rgbReserved3	32 bytes
	*/
	@Index(11)
	@SkipField(2 + 8 + 4 + 3 + 1 + 32)
	Object unusedFieldsAfterCrypt

	) implements Struct {
}
