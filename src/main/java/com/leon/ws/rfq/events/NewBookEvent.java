package com.leon.ws.rfq.events;

import org.springframework.context.ApplicationEvent;

import com.google.gson.Gson;
import com.leon.ws.rfq.book.BookDetailImpl;

@SuppressWarnings("serial")
public final class NewBookEvent extends ApplicationEvent implements JsonSerializableEvent
{
	private final BookDetailImpl newBook;
	private final String messageType;

	public NewBookEvent(Object source, BookDetailImpl newBook, String messageType)
	{
		super(source);
		this.newBook = newBook;
		this.messageType = messageType;
	}

	@Override
	public String toString()
	{
		return "New book event: " + this.newBook;
	}

	public BookDetailImpl getNewBook()
	{
		return this.newBook;
	}

	@Override
	public String getJson()
	{
		return new Gson().toJson(this.newBook);
	}

	@Override
	public String getMessageType()
	{
		return this.messageType;
	}
}
