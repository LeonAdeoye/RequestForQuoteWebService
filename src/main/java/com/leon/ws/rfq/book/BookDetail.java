package com.leon.ws.rfq.book;

import javax.xml.bind.annotation.XmlRootElement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@XmlRootElement(name="BookDetail")
public class BookDetail 
{
	private static Logger logger = LoggerFactory.getLogger(BookDetail.class);
	private String bookCode;
	private boolean isValid;
	private String entity;

	public BookDetail(String bookCode, String entity, boolean isValid)
	{
		this.bookCode = bookCode;
		this.entity = entity;
		this.isValid = isValid;
		
		logger.debug("BookDetail object instantiated = > " +  this);
	}
	
	public BookDetail() {} 
	
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
	
	public String toString()
	{
		StringBuffer buf = new StringBuffer();
		buf.append("Book Code: ");
		buf.append(this.bookCode);
		buf.append(", Entity: ");
		buf.append(this.entity);
		buf.append(", Is Valid: ");
		buf.append(this.isValid ? "TRUE" : "FALSE");
		return buf.toString();
	}
}