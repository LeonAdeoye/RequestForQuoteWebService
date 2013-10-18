package com.leon.ws.rfq.chat;

import javax.xml.bind.annotation.XmlRootElement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;

@XmlRootElement(name="ChatMessageImpl")
public final class ChatMessageImpl implements ChatMessage
{
	private static final Logger logger = LoggerFactory.getLogger(ChatMessageImpl.class);
	private String owner;
	private String content;
	private String timeStamp;
	private int sequenceId;
	private int requestForQuoteId;

	public ChatMessageImpl() {}

	public ChatMessageImpl(String owner, String content, String timeStamp,
			int requestForQuoteId, int sequenceId)
	{
		this.owner = owner;
		this.content = content;
		this.timeStamp = timeStamp;
		this.requestForQuoteId = requestForQuoteId;
		this.sequenceId = sequenceId;

		if(logger.isDebugEnabled())
			logger.debug("MessageImpl object instantiated = > " +  this.toString());
	}

	@Override
	public String getOwner()
	{
		return this.owner;
	}

	@Override
	public void setOwner(String owner)
	{
		this.owner = owner;
	}

	@Override
	public String getContent()
	{
		return this.content;
	}

	@Override
	public void setContent(String content)
	{
		this.content = content;
	}

	@Override
	public String getTimeStamp()
	{
		return this.timeStamp;
	}

	@Override
	public void setTimeStamp(String timeStamp)
	{
		this.timeStamp = timeStamp;
	}

	@Override
	public int getRequestForQuoteId()
	{
		return this.requestForQuoteId;
	}

	@Override
	public void setRequestForQuoteId(int requestForQuoteId)
	{
		this.requestForQuoteId = requestForQuoteId;
	}

	@Override
	public int getSequenceId()
	{
		return this.sequenceId;
	}

	@Override
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

		return 	(this.requestForQuoteId == param.requestForQuoteId) &&
				(this.sequenceId == param.sequenceId) &&
				this.owner.equals(param.owner) &&
				this.content.equals(param.content) &&
				this.timeStamp.equals(param.timeStamp);
	}

	@Override
	public int hashCode()
	{
		int result = 17;
		result = (37 * result) + this.requestForQuoteId;
		result = (37 * result) + this.sequenceId;
		result = (37 * result) + (this.timeStamp == null ? 0 : this.timeStamp.hashCode());
		result = (37 * result) + (this.owner == null ? 0 : this.owner.hashCode());
		result = (37 * result) + (this.content == null ? 0 : this.content.hashCode());
		return result;
	}
}