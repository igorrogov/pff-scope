package com.github.igorrogov.pffscope.memory;

import com.github.igorrogov.pffscope.memory.SegmentParser.Field;
import com.github.igorrogov.pffscope.ndb.internal.BRef;
import com.github.igorrogov.pffscope.ndb.internal.RootStruct;
import com.github.igorrogov.pffscope.struct.StructFactory;

import java.io.IOException;
import java.lang.foreign.MemorySegment;
import java.util.Arrays;

import static java.nio.ByteOrder.LITTLE_ENDIAN;

public record Header(
		  ClientSignature clientSignature,
		  int clientSignatureRaw,
		  Format format,
		  int formatRaw,
		  int clientVersion,
		  CryptMethod cryptMethod,
		  int cryptMethodRaw,
		  long fileSize,
		  BRef nodeTreeRoot,
		  BRef blockTreeRoot
) {

	private static final Field<Integer, Long> DW_MAGIC = SegmentParser.newUInt32();

	private static final SegmentParser HEADER_PARSER = SegmentParser.build(LITTLE_ENDIAN, DW_MAGIC);

	public static Header parse(MemorySegment ms)
			  throws IOException
	{
		System.out.println("dwMagic: " + HEADER_PARSER.get(ms, DW_MAGIC));

//		ClientSignature cs = ClientSignature.forValue(hs.wMagicClient());
//		Format format = Format.forValue(hs.wVer());
//		CryptMethod cryptMethod = CryptMethod.forValue(hs.bCryptMethod());
//
//		// TODO: parse rgnid[] 128 bytes
//		// it could be useful to provide some stats about the PST file in general (e.g. number of items per type)
//
//		RootStruct rs = StructFactory.parse(RootStruct.class, hs.root());
//
//		return new Header(cs, hs.wMagicClient(), format, hs.wVer(), hs.wVerClient(), cryptMethod,
//				  hs.bCryptMethod(), rs.ibFileEof(), BRef.from(rs.brefNBT()), BRef.from(rs.brefBBT()));
		return null;
	}

	public enum ClientSignature {
		PAB(0x4241),
		PST(0x4d53),
		OST(0x4f53);

		public final int value;

		ClientSignature(int value) {
			this.value = value;
		}

		public static ClientSignature forValue(int value) {
			return Arrays.stream(ClientSignature.values()).filter(c -> c.value == value).findFirst().orElse(null);
		}

	}

	public enum Format {
		Ansi(0x0F),	// 15
		Unicode(0x17), // 23
		Unicode4k(0x24); // 36

		public final int minValue;

		Format(int minValue) {
			this.minValue = minValue;
		}

		public static Format forValue(int value) {
			if (value <= Ansi.minValue) {
				return Ansi;
			}
			if (value >= Unicode4k.minValue) {
				return Unicode4k;
			}
			return Unicode;
		}

	}

	public enum CryptMethod {
		None(0x00),
		Permute(0x01),
		Cyclic(0x02),
		EdpCrypted(0x10);

		public final int value;

		CryptMethod(int value) {
			this.value = value;
		}

		public static CryptMethod forValue(int value) {
			return Arrays.stream(CryptMethod.values()).filter(c -> c.value == value).findFirst().orElse(null);
		}

	}

}
