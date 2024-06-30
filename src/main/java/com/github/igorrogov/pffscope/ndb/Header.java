package com.github.igorrogov.pffscope.ndb;

import com.github.igorrogov.pffscope.struct.StructFactory;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.nio.channels.ReadableByteChannel;
import java.util.Arrays;
import java.util.HexFormat;

public record Header(
		  ClientSignature clientSignature,
		  String clientSignatureText,
		  Format format,
		  String formatText
) {

	public static Header parse(ReadableByteChannel channel)
			  throws IOException
	{
		HeaderStruct hs = StructFactory.parse(HeaderStruct.class, channel);

		ClientSignature cs = ClientSignature.forValue(hs.wMagicClient());
		Format format = Format.forValue(hs.wVer());

		return new Header(cs, getClientSignatureText(cs, hs.wMagicClient()), format, getFormatText(format, hs.wVer()));
	}

	private static final HexFormat HEX = HexFormat.of().withLowerCase();

	private static String getClientSignatureText(ClientSignature cs, int value) {
		String hex = StringUtils.stripStart(HEX.toHexDigits(value), "0");
		return (cs != null ? cs.name() : "Unknown") + " (" + hex + ")";
	}

	private static String getFormatText(Format format, int value) {
		String hex = StringUtils.stripStart(HEX.toHexDigits(value), "0");
		return (format != null ? format.name() : "Unknown") + " (" + hex + ")";
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

}
