package com.leon.ws.rfq.events;

import org.springframework.context.ApplicationEvent;

import com.google.gson.Gson;
import com.leon.ws.rfq.request.RequestDetailImpl;

@SuppressWarnings("serial")
public final class TaggedRequestEvent extends ApplicationEvent implements JsonSerializableEvent
{
	private final RequestDetailImpl request;
	private static final String NEW_REQUEST_UPDATE = "NewRequestUpdate";

	public TaggedRequestEvent(Object source, RequestDetailImpl request)
	{
		super(source);
		this.request = request;
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
		return NEW_REQUEST_UPDATE;
	}
}
