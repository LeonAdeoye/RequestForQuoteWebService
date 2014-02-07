package com.leon.ws.rfq.events;

import org.springframework.context.ApplicationEvent;

import com.google.gson.Gson;
import com.leon.ws.rfq.user.UserDetailImpl;

@SuppressWarnings("serial")
public final class NewUserEvent extends ApplicationEvent implements JsonSerializableEvent
{
	private final UserDetailImpl newUser;
	private static final String NEW_USER_UPDATE = "NewUserUpdate";

	public NewUserEvent(Object source, UserDetailImpl newUser)
	{
		super(source);
		this.newUser = newUser;
	}

	@Override
	public String toString()
	{
		return "New user event: " + this.newUser;
	}

	public UserDetailImpl getNewUser()
	{
		return this.newUser;
	}

	@Override
	public String getJson()
	{
		return new Gson().toJson(this.newUser);
	}

	@Override
	public String getMessageType()
	{
		return NEW_USER_UPDATE;
	}
}
