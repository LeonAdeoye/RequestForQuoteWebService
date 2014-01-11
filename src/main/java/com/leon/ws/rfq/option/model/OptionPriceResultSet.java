package com.leon.ws.rfq.option.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = "com.leon.ws.rfq.model")
public final class OptionPriceResultSet
{
	@XmlElementWrapper(name = "OptionPriceResultSet")
	@XmlElement(name = "OptionPriceResult")
	  
	private ArrayList<OptionPriceResult> optionPriceResultSetArrayList;
	private final Map<Double, OptionPriceResult> optionPriceResultSetMap = new HashMap<>();
	
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
		this.setResultSet(this.optionPriceResultSetMap == null
				? new ArrayList<OptionPriceResult>()
				: new ArrayList<>(this.optionPriceResultSetMap.values()));
		
		return this.optionPriceResultSetArrayList;
	}
	
	public void merge(OptionPriceResult optionPriceResultToBeMerged)
	{
		if(this.optionPriceResultSetMap.containsKey(optionPriceResultToBeMerged.getRangeVariable()))
		{
			OptionPriceResult optionPriceResult = this.optionPriceResultSetMap.get(optionPriceResultToBeMerged.getRangeVariable());
			optionPriceResult.add(optionPriceResultToBeMerged);
		}
		else
			this.optionPriceResultSetMap.put(optionPriceResultToBeMerged.getRangeVariable(), optionPriceResultToBeMerged);
	}
}
