package com.leon.ws.rfq.marketdata;

public interface MarketDataService
{
	void initialize();
	
	double getMidPrice(String underlyingRIC);
	void setMidPrice(String underlyingRIC, double mid);
	
	double getAskPrice(String underlyingRIC);
	void setAskPrice(String underlyingRIC, double ask);
	
	double getBidPrice(String underlyingRIC);
	void setBidPrice(String underlyingRIC, double bid);	
	
	double getClosePrice(String underlyingRIC);
	void setClosePrice(String underlyingRIC, double close);
	
	void clearAll();	
	void clearPricesForUnderlying(String underlyingRIC);
	int size();
}
