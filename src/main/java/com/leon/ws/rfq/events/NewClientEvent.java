package com.leon.ws.rfq.events;

import org.springframework.context.ApplicationEvent;

import com.google.gson.Gson;
import com.leon.ws.rfq.client.ClientDetailImpl;

@SuppressWarnings("serial")
public class NewClientEvent extends ApplicationEvent implements JsonSerializableEvent
{
	private final ClientDetailImpl newClient;
	private static final String NEW_CLIENT_UPDATE = "NewClientUpdate";

	public NewClientEvent(Object source, ClientDetailImpl newClient)
	{
		super(source);
		this.newClient = newClient;
	}

	@Override
	public String toString()
	{
		return "New client event: " + this.newClient;
	}

	public ClientDetailImpl getNewClient()
	{
		return this.newClient;
	}

	@Override
	public String getJson()
	{
		return new Gson().toJson(this.newClient);
	}

	@Override
	public String getMessageType()
	{
		return NEW_CLIENT_UPDATE;
	}
}
