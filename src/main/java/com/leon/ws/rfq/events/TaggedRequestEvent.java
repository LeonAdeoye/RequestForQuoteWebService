package com.leon.ws.rfq.events;

import org.springframework.context.ApplicationEvent;

import com.leon.ws.rfq.request.RequestDetailImpl;

@SuppressWarnings("serial")
public final class TaggedRequestEvent extends ApplicationEvent
{
	private final RequestDetailImpl request;
	
	public TaggedRequestEvent(Object source, RequestDetailImpl request)
	{
		super(source);
		this.request = request;
	}
	
	public RequestDetailImpl getRequest()
	{
		return request;
	}

}
