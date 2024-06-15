package com.github.igorrogov.pffscope.ndb;

import com.github.igorrogov.pffscope.struct.StructField;
import com.github.igorrogov.pffscope.struct.FieldType;

public record Header(

	@StructField(type = FieldType.UInt32)
	long dwMagic,

	@StructField(type = FieldType.UInt32)
	long dwCRCPartial,

	@StructField(type = FieldType.UInt16)
	int wMagicClient,

	@StructField(type = FieldType.UInt16)
	int wVer,

	@StructField(type = FieldType.UInt16)
	int wVerClient,

	@StructField(type = FieldType.UInt8)
	int bPlatformCreate,

	@StructField(type = FieldType.UInt8)
	int bPlatformAccess

	)
{
}
