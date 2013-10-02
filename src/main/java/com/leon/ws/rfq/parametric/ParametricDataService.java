package com.leon.ws.rfq.parametric;

import java.math.BigDecimal;

public interface ParametricDataService
{
	BigDecimal getVolatility(String  underlyingRIC);
	BigDecimal getInterestRate(String currency);
	
	void setVolatility(String underlyingRIC, BigDecimal volatility);
	void setInterestRate(String currency, BigDecimal interestRate);
}
