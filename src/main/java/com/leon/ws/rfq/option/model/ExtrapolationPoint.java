package com.leon.ws.rfq.option.model;

import com.leon.ws.rfq.utilities.UtilityMethods;

public final class ExtrapolationPoint
{
	private final double rangeValue;
	private final OptionPriceResult optionPriceResult;
	
	public ExtrapolationPoint(double rangeValue, OptionPriceResult optionPriceResult)
	{
		this.rangeValue = rangeValue;
		this.optionPriceResult = optionPriceResult;
	}
	
	double getRangeValue()
	{
		return this.rangeValue;
	}
	
	public OptionPriceResult getOptionPriceResult()
	{
		return this.optionPriceResult;
	}
	
	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder("Range value: ");
		sb.append(this.rangeValue);
		sb.append(", option price result: ");
		sb.append(this.optionPriceResult);
		return sb.toString();
	}
	
	@Override
	public boolean equals(Object o)
	{
		if(this == o)
			return true;

		if(!(o instanceof ExtrapolationPoint))
			return false;

		ExtrapolationPoint param = (ExtrapolationPoint) o;

		return 	(this.rangeValue == param.rangeValue) &&
				(this.optionPriceResult.equals(param.optionPriceResult));
	}

	@Override
	public int hashCode()
	{
		int result = 17;
		result = (37 * result) + UtilityMethods.doubleHashCode(this.rangeValue);
		result = (37 * result) + this.optionPriceResult.hashCode();
		return result;
	}

	public void add(OptionPriceResult optionPriceresult)
	{
		this.optionPriceResult.setDelta(this.optionPriceResult.getDelta() + optionPriceresult.getDelta());
		this.optionPriceResult.setGamma(this.optionPriceResult.getGamma() + optionPriceresult.getGamma());
		this.optionPriceResult.setTheta(this.optionPriceResult.getTheta() + optionPriceresult.getTheta());
		this.optionPriceResult.setVega(this.optionPriceResult.getVega() + optionPriceresult.getVega());
		this.optionPriceResult.setRho(this.optionPriceResult.getRho() + optionPriceresult.getRho());
		this.optionPriceResult.setPrice(this.optionPriceResult.getPrice() + optionPriceresult.getPrice());
	}
}
