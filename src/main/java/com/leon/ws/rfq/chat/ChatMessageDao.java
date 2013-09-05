package com.leon.ws.rfq.chat;

import java.util.List;

public interface ChatMessageDao
{
	ChatMessage save(int requestForQuoteId, String owner, String content);
	List<ChatMessageImpl> get(int requestForQuoteId, int fromThisSequenceId);
}
