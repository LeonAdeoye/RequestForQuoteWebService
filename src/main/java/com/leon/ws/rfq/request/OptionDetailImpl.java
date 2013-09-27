package com.leon.ws.rfq.request;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="OptionDetailImpl", namespace = "com.leon.ws.rfq.request")
public class OptionDetailImpl
{
	private int legId;
	private boolean isCall;
	private boolean isEuropean;
	
	public OptionDetailImpl() {}
	
	public int getLegId()
	{
		return this.legId;
	}
	
	public void setLegId(int legId)
	{
		this.legId = legId;
	}
	
	public boolean getIsCall()
	{
		return this.isCall;
	}
	
	public void setIsCall(boolean isCall)
	{
		this.isCall = isCall;
	}
	
	public boolean getIsEuropean()
	{
		return this.isEuropean;
	}
	
	public void setIsEuropean(boolean isEuropean)
	{
		this.isEuropean = isEuropean;
	}	
	
	@Override
	public String toString()
	{
		StringBuffer buf = new StringBuffer();
		buf.append("Leg ID: ");
		buf.append(this.legId);
		buf.append(", Is Call: ");
		buf.append(this.isCall);
		buf.append(", Is European: ");
		buf.append(this.isEuropean);		
		return buf.toString();
	}
	
	@Override
	public boolean equals(Object o)
	{
		if(this == o)
			return true;
		
		if(!(o instanceof OptionDetailImpl))
			return false;
		
		OptionDetailImpl param = (OptionDetailImpl) o;
		
		return 	this.legId == param.legId &&	
				this.isCall == param.isCall &&
				this.isEuropean == param.isEuropean;
	}
	
	@Override
	public int hashCode()
	{
		int result = 17;
		result = 37 * result + (int) legId;
		result = 37 * result + (isCall ? 0 : 1);
		result = 37 * result + (isEuropean ? 0 : 1);
		return result;
	}	
}
