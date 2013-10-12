package com.leon.ws.rfq.events;

import org.springframework.context.ApplicationEvent;

import com.google.gson.Gson;
import com.leon.ws.rfq.request.RequestDetailImpl;

@SuppressWarnings("serial")
public final class TaggedRequestEvent extends ApplicationEvent implements JsonSerializableEvent
{
	private final RequestDetailImpl request;
	private final String messageType;

	public TaggedRequestEvent(Object source, RequestDetailImpl request, String messageType)
	{
		super(source);
		this.request = request;
		this.messageType = messageType;
	}

	public RequestDetailImpl getRequest()
	{
		return this.request;
	}

	@Override
	public String getJson()
	{
		return new Gson().toJson(this.request);
	}

	@Override
	public String getMessageType()
	{
		return this.messageType;
	}
}
