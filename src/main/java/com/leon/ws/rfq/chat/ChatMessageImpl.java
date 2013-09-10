package com.leon.ws.rfq.chat;

import javax.xml.bind.annotation.XmlRootElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.google.gson.Gson;

@XmlRootElement(name="ChatMessageImpl")
public class ChatMessageImpl implements ChatMessage
{
	private static Logger logger = LoggerFactory.getLogger(ChatMessageImpl.class);	
	private String owner;
	private String content;
	private String timeStamp;
	private int sequenceId;
	private int requestForQuoteId;
	
	public ChatMessageImpl()
	{
		this.owner = "";
		this.content = "";
		this.timeStamp = "";
		this.requestForQuoteId = -1;
		this.sequenceId = -1;
		
		logger.debug("MessageImpl object instantiated = > " +  this.toString());
	}
		
	public ChatMessageImpl(String owner, String content, String timeStamp, 
			int requestForQuoteId, int sequenceId)
	{
		this.owner = owner;
		this.content = content;
		this.timeStamp = timeStamp;
		this.requestForQuoteId = requestForQuoteId;
		this.sequenceId = sequenceId;
		
		logger.debug("MessageImpl object instantiated = > " +  this.toString());
	}
	
	public String getOwner()
	{
		return this.owner;
	}
	
	public void setOwner(String owner)
	{
		this.owner = owner;
	}	
	
	public String getContent()
	{
		return this.content;
	}
	
	public void setContent(String content)
	{
		this.content = content;
	}
	
	public String getTimeStamp()
	{
		return timeStamp;
	}
	
	public void setTimeStamp(String timeStamp)
	{
		this.timeStamp = timeStamp;
	}
	
	public int getRequestForQuoteId()
	{
		return this.requestForQuoteId;
	}	
	
	public void setRequestForQuoteId(int requestForQuoteId)
	{
		this.requestForQuoteId = requestForQuoteId;
	}
	
	public int getSequenceId()
	{
		return this.sequenceId;
	}	
	
	public void setSequenceId(int sequenceId)
	{
		this.sequenceId = sequenceId;
	}	

	@Override
	public String toString()
	{	
		return new Gson().toJson(this);
	}
	
	@Override
	public boolean equals(Object o)
	{
		if(this == o)
			return true;
		
		if(!(o instanceof ChatMessageImpl))
			return false;
		
		ChatMessageImpl param = (ChatMessageImpl) o;
		
		return 	this.requestForQuoteId == param.requestForQuoteId &&
				this.sequenceId == param.sequenceId &&
				this.owner.equals(param.owner) &&
				this.content.equals(param.content) &&
				this.timeStamp.equals(param.timeStamp);				
	}
	
	@Override
	public int hashCode()
	{
		int result = 17;
		result = 37 * result + (int) requestForQuoteId;
		result = 37 * result + (int) sequenceId;
		result = 37 * result + (timeStamp == null ? 0 : timeStamp.hashCode());
		result = 37 * result + (owner == null ? 0 : owner.hashCode());
		result = 37 * result + (content == null ? 0 : content.hashCode());
		return result;
	}
}