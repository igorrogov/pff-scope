package com.github.igorrogov.pffscope;

import com.github.igorrogov.pffscope.memory.HeaderSegment;
import com.github.igorrogov.pffscope.memory.SegmentParser;

import java.lang.foreign.Arena;
import java.nio.channels.FileChannel;
import java.nio.file.Path;

public class MainNew {

	public static void main(String[] args)
			  throws Exception
	{
		Path pstFile = Path.of(args[0]);
		System.out.println(pstFile);
		try (var ch = FileChannel.open(pstFile)) {
			var fileSegment = ch.map(FileChannel.MapMode.READ_ONLY, 0, 512, Arena.ofAuto());
			HeaderSegment.parse(fileSegment);
		}
	}

}
