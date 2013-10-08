package com.leon.ws.rfq.client;

import javax.xml.bind.annotation.XmlRootElement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@XmlRootElement(name="ClientDetailImpl")
public class ClientDetailImpl 
{
	private static final Logger logger = LoggerFactory.getLogger(ClientDetailImpl.class);
	private String name;
	private int identifier;
	private boolean isValid;
	private int tier;

	public ClientDetailImpl(String name, int identifier, int tier, boolean isValid)
	{
		this.name = name;
		this.identifier = identifier;
		this.tier = tier;
		this.isValid = isValid;
		
		logger.debug("ClientDetailImpl object instantiated = > " +  this);
	}
	
	public ClientDetailImpl() {} 
	
	public String getName()
	{
		return this.name;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	public int getIdentifier()
	{
		return this.identifier;
	}
	
	public void setIdentifier(int identifier)
	{
		this.identifier = identifier;
	}		
	
	public int getTier()
	{
		return this.tier;
	}
	
	public void setTier(int tier)
	{
		this.tier = tier;
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
		buf.append("Name: ");
		buf.append(this.name);
		buf.append(", Identifier: ");
		buf.append(this.identifier);
		buf.append(", Tier: ");
		buf.append(this.tier);
		buf.append(", Is Valid: ");
		buf.append(this.isValid ? "TRUE" : "FALSE");
		return buf.toString();
	}
}