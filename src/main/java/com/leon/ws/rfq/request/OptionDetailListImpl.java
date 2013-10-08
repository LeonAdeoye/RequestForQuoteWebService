package com.leon.ws.rfq.request;

import java.util.ArrayList;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="OptionDetailListImpl", namespace = "com.leon.ws.rfq.request")
public final class OptionDetailListImpl
{
	@XmlElementWrapper(name = "OptionDetailListImpl")
	@XmlElement(name = "OptionDetailImpl")
	  
	private ArrayList<OptionDetailImpl> optionDetailArrayList;
	
	public OptionDetailListImpl() {}
	
	public OptionDetailListImpl(ArrayList<OptionDetailImpl> legs)
	{
		this.optionDetailArrayList = legs;
	}
			 
	public void setOptionDetailList(ArrayList<OptionDetailImpl> legs) 
	{
		this.optionDetailArrayList = legs;
	}
	
	public ArrayList<OptionDetailImpl> getOptionDetailList() 
	{
		return this.optionDetailArrayList;
	}
}
