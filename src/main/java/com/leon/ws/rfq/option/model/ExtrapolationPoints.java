package com.leon.ws.rfq.option.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="ExtrapolationPoints", namespace = "com.leon.ws.rfq.reporting")
public final class ExtrapolationPoints
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
	
	public void merge(ExtrapolationPoints input)
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
	
	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		for(Entry<Double, ExtrapolationPoint> entry : this.extrapolationMap.entrySet())
		{
			builder.append("\n{ Range value: ");
			builder.append(entry.getKey());
			builder.append(" Option result set:");
			builder.append(entry.getValue());
			builder.append(" }");
		}
		return builder.toString();
	}
}
