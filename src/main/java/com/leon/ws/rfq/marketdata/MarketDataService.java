package com.leon.ws.rfq.marketdata;

import java.math.BigDecimal;

public interface MarketDataService
{
	BigDecimal getMidPrice(String underlyingRIC);
	void setMidPrice(String underlyingRIC, BigDecimal mid);
	
	BigDecimal getAskPrice(String underlyingRIC);
	void setAskPrice(String underlyingRIC, BigDecimal ask);
	
	BigDecimal getBidPrice(String underlyingRIC);
	void setBidPrice(String underlyingRIC, BigDecimal bid);	
	
	BigDecimal getClosePrice(String underlyingRIC);
	void setClosePrice(String underlyingRIC, BigDecimal close);	
}
