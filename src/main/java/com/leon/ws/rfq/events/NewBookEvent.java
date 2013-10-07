package com.leon.ws.rfq.events;

import org.springframework.context.ApplicationEvent;
import com.leon.ws.rfq.book.BookDetailImpl;

@SuppressWarnings("serial")
public final class NewBookEvent extends ApplicationEvent
{
	private BookDetailImpl newBook;
	
	public NewBookEvent(Object source, BookDetailImpl newBook)
	{
		super(source);
		this.newBook = newBook;
	}
	
	@Override
	public String toString()
	{
		return "New book event: " + newBook;
	}
	
	public BookDetailImpl getNewBook()
	{
		return this.newBook;
	}
}
