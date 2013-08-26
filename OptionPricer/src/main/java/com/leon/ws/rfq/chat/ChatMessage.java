package com.leon.ws.rfq.chat;

public interface ChatMessage
{
	public String getOwner();		
	public String getContent();		
	public String getTimeStamp();		
	public int getRequestForQuoteId();	
	public int getSequenceId();	
	
	public void setOwner(String owner);	
	public void setContent(String content);
	public void setTimeStamp(String timeStamp);
	public void setRequestForQuoteId(int requestForQuoteId);
	public void setSequenceId(int sequenceId);	
}
