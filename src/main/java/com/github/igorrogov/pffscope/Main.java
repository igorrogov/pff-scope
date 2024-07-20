package com.github.igorrogov.pffscope;

import com.github.igorrogov.pffscope.ndb.Page;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.nio.channels.SeekableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class Main {

	public static void main(String[] args)
			  throws Exception
	{
		Path pstFile = Path.of(args[0]);
		System.out.println(pstFile);
		Pst pst = new Pst(pstFile);
		System.out.println(pp(pst.header));

		try (SeekableByteChannel channel = Files.newByteChannel(pstFile, StandardOpenOption.READ)) {
			Page rootNodePage = Page.read(channel, pst.header.nodeTreeRoot());
			Page rootBlockPage = Page.read(channel, pst.header.blockTreeRoot());
			System.out.println(" rootNodePage: \n" + pp(rootNodePage) + "\n rootBlockPage: \n" + pp(rootBlockPage));
		}
	}

	private static String pp(Object object) {
		return ReflectionToStringBuilder.toString(object, ToStringStyle.MULTI_LINE_STYLE);
	}

}
