package com.leon.ws.rfq.option.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = "com.leon.ws.rfq.option.model")
public final class ExtrapolationSet
{
	@XmlElementWrapper(name = "extrapolationPoints")
	@XmlElement(name = "ExtrapolationPoint")
	
	private final Map<Double, ExtrapolationPoint> extrapolationMap = new HashMap<Double, ExtrapolationPoint>();
	
	public void addExtrapolationPoint(double extrapolationKeyValue, OptionPriceResult optionPriceResult)
	{
		if(optionPriceResult == null)
			throw new NullPointerException("optionPriceResult");
		
		if(this.extrapolationMap.containsKey(extrapolationKeyValue))
		{
			ExtrapolationPoint existingPoint = this.extrapolationMap.get(extrapolationKeyValue);
			existingPoint.add(optionPriceResult);
		}
		else
			this.extrapolationMap.put(extrapolationKeyValue,
					new ExtrapolationPoint(extrapolationKeyValue, optionPriceResult));
	}
	
	public void merge(ExtrapolationSet input)
	{
		if(input == null)
			throw new NullPointerException("input");
		
		ArrayList<ExtrapolationPoint> points = input.getExtrapolationPoints();
		
		for(int i = 0; i < points.size(); ++i)
			addExtrapolationPoint(points.get(i).getRangeValue(), points.get(i).getOptionPriceResult());
	}
	
	public ArrayList<ExtrapolationPoint> getExtrapolationPoints()
	{
		return new ArrayList<ExtrapolationPoint>(this.extrapolationMap.values());
	}
}
