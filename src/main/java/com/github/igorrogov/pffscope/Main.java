package com.github.igorrogov.pffscope;

import java.nio.file.Paths;

public class Main {

	public static void main(String[] args)
			  throws Exception
	{
		String pstFile = args[0];
		System.out.println(pstFile);
		Pst pst = new Pst(Paths.get(pstFile));
		System.out.println(pst.header);

//			Root root = StructFactory.parse(Root.class, headerStruct.root());
//			System.out.println(root);

//			BRef brefNBT = StructFactory.parse(BRef.class, root.brefNBT());
//			BRef brefBBT = StructFactory.parse(BRef.class, root.brefBBT());
//			System.out.println("nbt: " + brefNBT + ", bbt: " + brefBBT);
//
//			Page rootNodePage = Page.read(channel, brefNBT);
//			Page rootBlockPage = Page.read(channel, brefBBT);
//			System.out.println("rootNodePage: " + rootNodePage + ", rootBlockPage: " + rootBlockPage);
	}

}
