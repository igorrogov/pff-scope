package com.github.igorrogov.pffscope;

import com.github.igorrogov.pffscope.ndb.BBTreeEntry;
import com.github.igorrogov.pffscope.ndb.BRef;
import com.github.igorrogov.pffscope.ndb.BlockID;
import com.github.igorrogov.pffscope.ndb.Header;
import com.github.igorrogov.pffscope.ndb.NBTreeEntry;
import com.github.igorrogov.pffscope.ndb.Page;
import com.github.igorrogov.pffscope.ndb.PermuteDecryptor;

import java.io.IOException;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

public class Pst implements AutoCloseable {

	public final Path path;

	public final Header header;

	private final SeekableByteChannel channel;

	// TODO: implement more efficiently

	public final List<NBTreeEntry> nodes;

	public final List<BBTreeEntry> blocks;

	public Pst(Path path)
			  throws IOException
	{
		this.path = path;
		this.channel = Files.newByteChannel(path, StandardOpenOption.READ);
		this.header = Header.parse(channel);
		this.nodes = getAllNodes();
		this.blocks = getAllBlocks();
	}

	public Page getRootNodePage()
			  throws IOException
	{
		return Page.read(channel, header.nodeTreeRoot());
	}

	public Page getRootBlockPage()
			  throws IOException
	{
		return Page.read(channel, header.blockTreeRoot());
	}

	private List<NBTreeEntry> getAllNodes()
			  throws IOException
	{
		List<NBTreeEntry> nodes = new ArrayList<>();
		loadNodesFromPageRecursively(nodes, getRootNodePage());
		return nodes;
	}

	private void loadNodesFromPageRecursively(List<NBTreeEntry> nodes, Page page)
			  throws IOException
	{
		nodes.addAll(page.nodes());
		for (Page subPage : page.readSubPages(channel)) {
			loadNodesFromPageRecursively(nodes, subPage);
		}
	}

	private List<BBTreeEntry> getAllBlocks()
			  throws IOException
	{
		List<BBTreeEntry> blocks = new ArrayList<>();
		loadBlocksFromPageRecursively(blocks, getRootBlockPage());
		return blocks;
	}

	private void loadBlocksFromPageRecursively(List<BBTreeEntry> blocks, Page page)
			  throws IOException
	{
		blocks.addAll(page.blocks());
		for (Page subPage : page.readSubPages(channel)) {
			loadBlocksFromPageRecursively(blocks, subPage);
		}
	}

	public byte[] getNodeData(NBTreeEntry node)
			  throws IOException
	{
		BlockID data = node.data();
		BBTreeEntry entry = getBlockEntry(data);
		byte[] bytes = BRef.read(channel, entry.bRef(), entry.size());
		// TODO: implement proper decryption based on header info
		PermuteDecryptor.decrypt(bytes);
		return bytes;
	}

	public BBTreeEntry getBlockEntry(BlockID bid)
			  throws IOException
	{
		return blocks.stream().filter(b -> b.bRef().bid().equals(bid)).findFirst().orElse(null);
	}

	@Override
	public void close() {
		try {
			channel.close();
		}
		catch (IOException e) {
			System.err.println("Error closing " + path + ": " + e);
		}
	}
}
