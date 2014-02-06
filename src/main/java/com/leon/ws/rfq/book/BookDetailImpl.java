package com.leon.ws.rfq.book;

import javax.xml.bind.annotation.XmlRootElement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;

@XmlRootElement(name="BookDetailImpl")
public final class BookDetailImpl
{
	private static final Logger logger = LoggerFactory.getLogger(BookDetailImpl.class);
	private String bookCode;
	private boolean isValid;
	private String entity;

	public BookDetailImpl() {}

	public BookDetailImpl(String bookCode, String entity, boolean isValid)
	{
		this.bookCode = bookCode;
		this.entity = entity;
		this.isValid = isValid;

		logger.debug("BookDetailImpl object instantiated => " +  this);
	}

	public String getBookCode()
	{
		return this.bookCode;
	}

	public void setBookCode(String bookCode)
	{
		this.bookCode = bookCode;
	}

	public String getEntity()
	{
		return this.entity;
	}

	public void setEntity(String entity)
	{
		this.entity = entity;
	}

	public boolean getIsValid()
	{
		return this.isValid;
	}

	public void setIsValid(boolean isValid)
	{
		this.isValid = isValid;
	}

	@Override
	public String toString()
	{
		StringBuilder buf = new StringBuilder("Book Code: ");
		buf.append(this.bookCode);
		buf.append(", Entity: ");
		buf.append(this.entity);
		buf.append(", Is Valid: ");
		buf.append(this.isValid ? "TRUE" : "FALSE");
		return buf.toString();
	}

	public String toJson()
	{
		return new Gson().toJson(this);
	}
}