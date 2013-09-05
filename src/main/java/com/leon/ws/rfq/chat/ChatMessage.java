package com.leon.ws.rfq.chat;

public interface ChatMessage
{
	String getOwner();		
	String getContent();		
	String getTimeStamp();		
	int getRequestForQuoteId();	
	int getSequenceId();	
	
	void setOwner(String owner);	
	void setContent(String content);
	void setTimeStamp(String timeStamp);
	void setRequestForQuoteId(int requestForQuoteId);
	void setSequenceId(int sequenceId);	
}
