package com.leon.ws.rfq.parametric;

public interface ParametricDataService
{
	double getVolatility(String  underlyingRIC);
	double getInterestRate(String currency);
	
	void setVolatility(String underlyingRIC, double volatility);
	void setInterestRate(String currency, double interestRate);
}
