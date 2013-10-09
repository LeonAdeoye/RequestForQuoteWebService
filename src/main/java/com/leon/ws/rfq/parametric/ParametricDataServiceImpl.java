package com.leon.ws.rfq.parametric;

import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;

public final class ParametricDataServiceImpl implements ParametricDataService
{
	private Map<String, Double> volatilities = new ConcurrentSkipListMap<>();
	private Map<String, Double> interestRates = new ConcurrentSkipListMap<>();
	
	public ParametricDataServiceImpl() {}
	
	/**
	 * Initializes the service.
	 *
	 */	
	public void initialize()
	{
		
	}
	
	/**
	 * Returns the volatility of an underlying product using the underlying RIC as the lookup key.
	 *
	 * @param  underlyingRIC	the RIC of the underlying product used as key to retrieve the volatility.
	 * @return					the volatility if it exists otherwise zero.
	 * @throws					IllegalArgumentException if underlyingRIC parameter is an empty string.
	 */		
	@Override
	public double getVolatility(String underlyingRIC)
	{
		if(underlyingRIC.isEmpty())
			throw new IllegalArgumentException("underlyingRIC");		
		
		if(volatilities.containsKey(underlyingRIC))
			return volatilities.get(underlyingRIC);
		else
			return 0.2; //TODO
	}
	
	/**
	 * Sets the volatility of an underlying product using the underlying RIC as the lookup key.
	 *
	 * @param  underlyingRIC	the RIC of the underlying product used as key to retrieve the volatility.
	 * @param  close			the volatility of the underlying product to be stored using the RIC as a key.
	 * @throws					IllegalArgumentException if underlyingRIC is empty || volatility <= 0
	 */	
	@Override
	public void setVolatility(String underlyingRIC, double volatility)
	{
		if(underlyingRIC.isEmpty())
			throw new IllegalArgumentException("underlyingRIC");
		
		if(volatility <= 0)
			throw new IllegalArgumentException("volatility");			
		
		volatilities.put(underlyingRIC, volatility);
	}

	/**
	 * Returns the interest rate of a currency using the underlying RIC as the lookup key.
	 *
	 * @param  currency			the symbol of the currency used as key to retrieve the interest rate.
	 * @return					the interest rate if it exists otherwise zero.
	 * @throws					IllegalArgumentException if currency symbol parameter is an empty string.
	 */		
	@Override
	public double getInterestRate(String currency)
	{
		if(currency.isEmpty())
			throw new IllegalArgumentException("currency");		
		
		if(interestRates.containsKey(currency))
			return interestRates.get(currency);
		else
			return 0.1; //TODO
	}
	
	/**
	 * Sets the interest rate of an currency using the currency symbol as the lookup key.
	 *
	 * @param  currency			the currency symbol used as key to retrieve the interest rate.
	 * @param  close			the interest rate of the currency to be stored using the symbol as a key.
	 * @throws					IllegalArgumentException if currency symbol parameter string is empty || interest rate <= 0
	 */	
	@Override
	public void setInterestRate(String currency, double interestRate)
	{
		if(currency.isEmpty())
			throw new IllegalArgumentException("currency");
		
		if(interestRate <= 0)
			throw new IllegalArgumentException("interestRate");
		
		interestRates.put(currency, interestRate);
	}
	
	/**
	 * clears the storage maps for interest rate and volatilities.
	 *
	 */		
	public void clearAll()
	{
		volatilities.clear();
		interestRates.clear();
	}
}
