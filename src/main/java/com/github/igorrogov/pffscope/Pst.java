package com.github.igorrogov.pffscope;

import com.github.igorrogov.pffscope.ndb.Header;

import java.io.IOException;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class Pst {

	public final Path path;

	public final Header header;

	public Pst(Path path)
			  throws IOException
	{
		this.path = path;
		this.header = parseHeader();
	}

	private Header parseHeader()
			  throws IOException
	{
		try (SeekableByteChannel channel = Files.newByteChannel(path, StandardOpenOption.READ)) {
			return Header.parse(channel);
		}
	}

}
