package com.leon.ws.rfq.request;

import javax.xml.bind.annotation.XmlRootElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@XmlRootElement(name="RequestDetailImpl")
public class RequestDetailImpl 
{
	private static Logger logger = LoggerFactory.getLogger(RequestDetailImpl.class);
	private String request;
	private int identifier;
	private String bookCode;


	public RequestDetailImpl(String request, int identifier, String bookCode)
	{
		this.request = request;
		this.identifier = identifier;
		this.bookCode = bookCode;
		
		logger.debug("RequestDetailImpl object instantiated = > " +  this);
	}
	
	public RequestDetailImpl() {} 
	
	public String getRequest()
	{
		return this.request;
	}
	
	public void setRequest(String request)
	{
		this.request = request;
	}
	
	public String getBookCode()
	{
		return this.bookCode;
	}
	
	public void setBookCode(String bookCode)
	{
		this.bookCode = bookCode;
	}	
	
	public int getIdentifier()
	{
		return this.identifier;
	}
	
	public void setIdentifier(int identifier)
	{
		this.identifier = identifier;
	}		
	
	@Override
	public String toString()
	{
		StringBuffer buf = new StringBuffer();
		buf.append("Request: ");
		buf.append(this.request);
		buf.append(", Identifier: ");
		buf.append(this.identifier);
		buf.append(", Book code: ");
		buf.append(this.bookCode);		
		return buf.toString();
	}
	
	@Override
	public boolean equals(Object o)
	{
		if(this == o)
			return true;
		
		if(!(o instanceof RequestDetailImpl))
			return false;
		
		RequestDetailImpl param = (RequestDetailImpl) o;
		
		return 	this.identifier == param.identifier &&	
				this.request.equals(param.request) &&
				this.bookCode.equals(param.bookCode);
	}
	
	@Override
	public int hashCode()
	{
		int result = 17;
		result = 37 * result + (int) identifier;
		result = 37 * result + (request == null ? 0 : request.hashCode());
		result = 37 * result + (bookCode == null ? 0 : bookCode.hashCode());
		return result;
	}	
}
