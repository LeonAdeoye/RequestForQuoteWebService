package com.leon.ws.rfq.parametric;

import java.math.BigDecimal;
import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;

public class ParametericDataServiceImpl implements ParametricDataService
{
	Map<String, BigDecimal> volatilities = new ConcurrentSkipListMap<>();
	Map<String, BigDecimal> interestRates = new ConcurrentSkipListMap<>();
	
	@Override
	public BigDecimal getVolatility(String underlyingRIC)
	{
		if(volatilities.containsKey(underlyingRIC))
			return new BigDecimal(volatilities.get(underlyingRIC).toPlainString());
		else
			return new BigDecimal(0);
	}
	
	@Override
	public void setVolatility(String underlyingRIC, BigDecimal volatility)
	{
		volatilities.put(underlyingRIC, volatility);
	}

	@Override
	public BigDecimal getInterestRate(String currency)
	{
		if(interestRates.containsKey(currency))
			return new BigDecimal(interestRates.get(currency).toPlainString());
		else
			return new BigDecimal(0);
	}
	
	@Override
	public void setInterestRate(String currency, BigDecimal interestRate)
	{
		interestRates.put(currency, interestRate);
	}	
}
