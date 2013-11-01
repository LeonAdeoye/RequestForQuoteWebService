package com.leon.ws.rfq.events;

import org.springframework.context.ApplicationEvent;

import com.google.gson.Gson;
import com.leon.ws.rfq.underlying.UnderlyingDetailImpl;

@SuppressWarnings("serial")
public class NewUnderlyingEvent extends ApplicationEvent implements JsonSerializableEvent
{
	private static final String NEW_UNDERLYING_UPDATE = "NewUnderlyingUpdate";
	private final UnderlyingDetailImpl newUnderlying;

	public NewUnderlyingEvent(Object source, UnderlyingDetailImpl newUnderlying)
	{
		super(source);
		this.newUnderlying = newUnderlying;
	}

	@Override
	public String getJson()
	{
		return new Gson().toJson(this.newUnderlying);
	}

	@Override
	public String getMessageType()
	{
		return NEW_UNDERLYING_UPDATE;
	}

	@Override
	public String toString()
	{
		return "New underlying event: " + this.newUnderlying;
	}

	public UnderlyingDetailImpl getNewUnderlying()
	{
		return this.newUnderlying;
	}
}
