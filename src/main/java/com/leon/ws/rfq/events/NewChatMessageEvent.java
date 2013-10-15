package com.leon.ws.rfq.events;

import org.springframework.context.ApplicationEvent;

import com.google.gson.Gson;
import com.leon.ws.rfq.chat.ChatMessage;

@SuppressWarnings("serial")
public final class NewChatMessageEvent extends ApplicationEvent implements JsonSerializableEvent
{
	private final ChatMessage chatMessage;
	private static final String NEW_CHAT_MESSAGE = "NewChatMessage";

	public NewChatMessageEvent(Object source, ChatMessage chatMessage)
	{
		super(source);
		this.chatMessage = chatMessage;
	}

	@Override
	public String toString()
	{
		return "New chat message event: " + this.chatMessage;
	}

	public ChatMessage getNewChatMessage()
	{
		return this.chatMessage;
	}

	@Override
	public String getJson()
	{
		return new Gson().toJson(this.chatMessage);
	}

	@Override
	public String getMessageType()
	{
		return NEW_CHAT_MESSAGE;
	}
}
