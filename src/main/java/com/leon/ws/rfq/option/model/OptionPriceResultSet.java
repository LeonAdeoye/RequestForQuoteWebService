package com.leon.ws.rfq.option.model;

import java.util.ArrayList;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = "com.leon.ws.rfq.option.model")
public class OptionPriceResultSet
{
	@XmlElementWrapper(name = "optionPriceResults")
	@XmlElement(name = "OptionPriceResult")
	  
	private ArrayList<OptionPriceResult> optionPriceResults;
	  
	public void setOptionPriceResult(ArrayList<OptionPriceResult> optionPriceResults) 
	{
		this.optionPriceResults = optionPriceResults;
	}
	
	public ArrayList<OptionPriceResult> getOptionPriceResultSet() 
	{
		return this.optionPriceResults;
	}	  
}
