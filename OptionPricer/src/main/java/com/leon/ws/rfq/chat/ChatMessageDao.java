package com.leon.ws.rfq.chat;

import java.util.List;

public interface ChatMessageDao
{
	public ChatMessage save(int requestForQuoteId, String owner, String content);
	public List<ChatMessageImpl> get(int requestForQuoteId, int fromThisSequenceId);
}
