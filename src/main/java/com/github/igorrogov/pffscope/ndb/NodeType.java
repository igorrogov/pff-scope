package com.github.igorrogov.pffscope.ndb;

import java.util.Arrays;

public enum NodeType {
	Heap(0x00),
	Internal(0x01),
	NormalFolder(0x02),
	SearchFolder(0x03),
	NormalMessage(0x04),
	Attachment(0x05),
	SearchUpdateQueue(0x06),
	SearchCriteriaObject(0x07),
	FolderAssociatedInformation(0x08),
	ContentsTableIndex(0x0A),
	ReceiveFolderTable(0x0B),
	OutgoingQueueTable(0x0C),
	HierarchyTable(0x0D),
	ContentsTable(0x0E),
	AssocContentsTable(0x0F),
	SearchContentsTable(0x10),
	AttachmentTable(0x11),
	RecipientTable(0x12),
	SearchTableIndex(0x13),
	LTP(0x14),
	;

	public final int value;

	NodeType(int value) {
		this.value = value;
	}

	public static NodeType from(int value) {
		return Arrays.stream(values()).filter(v -> v.value == value).findFirst().orElse(null);
	}

}
