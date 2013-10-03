package com.leon.ws.rfq.parametric;

import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;

public class ParametericDataServiceImpl implements ParametricDataService
{
	private Map<String, Double> volatilities = new ConcurrentSkipListMap<>();
	private Map<String, Double> interestRates = new ConcurrentSkipListMap<>();
	
	@Override
	public double getVolatility(String underlyingRIC)
	{
		if(volatilities.containsKey(underlyingRIC))
			return volatilities.get(underlyingRIC);
		else
			return 0;
	}
	
	@Override
	public void setVolatility(String underlyingRIC, double volatility)
	{
		volatilities.put(underlyingRIC, volatility);
	}

	@Override
	public double getInterestRate(String currency)
	{
		if(interestRates.containsKey(currency))
			return interestRates.get(currency);
		else
			return 0;
	}
	
	@Override
	public void setInterestRate(String currency, double interestRate)
	{
		interestRates.put(currency, interestRate);
	}	
}
