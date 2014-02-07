package com.leon.ws.rfq.events;

import org.springframework.context.ApplicationEvent;

import com.google.gson.Gson;
import com.leon.ws.rfq.group.GroupDetailImpl;

@SuppressWarnings("serial")
public final class NewGroupEvent extends ApplicationEvent implements JsonSerializableEvent
{
	private final GroupDetailImpl newGroup;
	private static final String NEW_GROUP_UPDATE = "NewGroupUpdate";

	public NewGroupEvent(Object source, GroupDetailImpl newGroup)
	{
		super(source);
		this.newGroup = newGroup;
	}

	@Override
	public String toString()
	{
		return "New group event: " + this.newGroup;
	}

	public GroupDetailImpl getNewGroup()
	{
		return this.newGroup;
	}

	@Override
	public String getJson()
	{
		return new Gson().toJson(this.newGroup);
	}

	@Override
	public String getMessageType()
	{
		return NEW_GROUP_UPDATE;
	}
}
