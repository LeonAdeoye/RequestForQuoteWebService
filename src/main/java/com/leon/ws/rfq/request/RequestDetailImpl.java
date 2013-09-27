package com.leon.ws.rfq.request;

import java.util.ArrayList;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="RequestDetailImpl", namespace = "com.leon.ws.rfq.request")
public class RequestDetailImpl 
{
	private String request;
	private int identifier;
	private String bookCode;
	private ArrayList<OptionDetailImpl> legs;
	
	public RequestDetailImpl() {}
	
	public OptionDetailListImpl getLegs()
	{
		OptionDetailListImpl optionlegs =  new OptionDetailListImpl();
		optionlegs.setOptionDetailList(legs);
		return optionlegs;
	}
	
	public void setLegs(OptionDetailListImpl optionLegs)
	{
		this.legs = optionLegs.getOptionDetailList();
	}
	
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
		
		if(legs != null && legs.size() > 0)
		{
			buf.append(", Legs: \n");
			for(OptionDetailImpl leg : this.legs)
			{
				if(leg != null)
					buf.append(leg.toString() + "\n");
			}			
		}
		
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
		
		boolean isEqual = false;
		
		if(legs != null)
		{
			for(OptionDetailImpl leg : this.legs)
			{
				// TODO - check each leg and compare against param's legs
				isEqual = leg.equals(leg);
			}					
		}		
		
		return 	isEqual && this.identifier == param.identifier &&	
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
		
		if(legs != null)
		{
			for(OptionDetailImpl leg : this.legs)
			{
				if(leg != null )
					result = 37 * result + leg.hashCode();
			}					
		}
		return result;
	}	
}
