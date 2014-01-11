package com.leon.ws.rfq.option.model;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = "com.leon.ws.rfq.model")
public final class OptionPriceResultSet
{
	@XmlElementWrapper(name = "OptionPriceResultSet")
	@XmlElement(name = "OptionPriceResult")
	  
	private ArrayList<OptionPriceResult> optionPriceResultSetArrayList;
	
	public OptionPriceResultSet(ArrayList<OptionPriceResult> resultSet)
	{
		this.optionPriceResultSetArrayList = resultSet;
	}
	
	public OptionPriceResultSet() {}
	  
	public void setResultSet(ArrayList<OptionPriceResult> resultSet)
	{
		this.optionPriceResultSetArrayList = resultSet;
	}
	
	public ArrayList<OptionPriceResult> getResultSet()
	{
		if(this.optionPriceResultSetArrayList == null)
			return new ArrayList<OptionPriceResult>();
		else
			return this.optionPriceResultSetArrayList;
	}
	
	public void add(OptionPriceResult resultSet)
	{
		if(this.optionPriceResultSetArrayList == null)
			this.optionPriceResultSetArrayList = new ArrayList<OptionPriceResult>();
			
		this.optionPriceResultSetArrayList.add(resultSet);
	}
}
