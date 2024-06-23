package com.github.igorrogov.pffscope;

import com.github.igorrogov.pffscope.ndb.BRef;
import com.github.igorrogov.pffscope.ndb.Header;
import com.github.igorrogov.pffscope.ndb.Page;
import com.github.igorrogov.pffscope.ndb.Root;
import com.github.igorrogov.pffscope.struct.StructFactory;

import java.nio.channels.SeekableByteChannel;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class Main {

	public static void main(String[] args)
			  throws Exception
	{
		String pstFile = args[0];
		System.out.println(pstFile);
		try (SeekableByteChannel channel = Files.newByteChannel(Paths.get(pstFile), StandardOpenOption.READ)) {
			Header header = StructFactory.parse(Header.class, channel);
			System.out.println(header);

			Root root = StructFactory.parse(Root.class, header.root());
			System.out.println(root);

			BRef brefNBT = StructFactory.parse(BRef.class, root.brefNBT());
			BRef brefBBT = StructFactory.parse(BRef.class, root.brefBBT());
			System.out.println("nbt: " + brefNBT + ", bbt: " + brefBBT);

			Page rootNodePage = Page.read(channel, brefNBT);
			Page rootBlockPage = Page.read(channel, brefBBT);
			System.out.println("rootNodePage: " + rootNodePage + ", rootBlockPage: " + rootBlockPage);
		}
	}

}
