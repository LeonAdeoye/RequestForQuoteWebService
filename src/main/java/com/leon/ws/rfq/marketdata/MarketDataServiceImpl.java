package com.leon.ws.rfq.marketdata;

import java.math.BigDecimal;
import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;

public class MarketDataServiceImpl implements MarketDataService
{
	private class Prices
	{
		BigDecimal ask = new BigDecimal(0);
		BigDecimal bid = new BigDecimal(0);
		BigDecimal mid = new BigDecimal(0);
		BigDecimal close = new BigDecimal(0);
	}
	
	Map<String, Prices> priceMap = new ConcurrentSkipListMap<>();
	
	@Override
	public BigDecimal getMidPrice(String underlyingRIC)
	{
		if(priceMap.containsKey(underlyingRIC))
			return priceMap.get(underlyingRIC).mid;
		else
			return new BigDecimal(0);
	}

	@Override
	public void setMidPrice(String underlyingRIC, BigDecimal mid)
	{
		Prices prices;		
		if(priceMap.containsKey(underlyingRIC))
		{
			prices = priceMap.get(underlyingRIC);
			prices.mid = mid;
			priceMap.put(underlyingRIC, prices);
		}
		else
		{
			prices = new Prices();
			prices.mid = mid;
			priceMap.put(underlyingRIC, prices);
		}	
	}

	@Override
	public BigDecimal getAskPrice(String underlyingRIC)
	{
		if(priceMap.containsKey(underlyingRIC))
			return priceMap.get(underlyingRIC).ask;
		else
			return new BigDecimal(0);
	}

	@Override
	public void setAskPrice(String underlyingRIC, BigDecimal ask)
	{
		Prices prices;		
		if(priceMap.containsKey(underlyingRIC))
		{
			prices = priceMap.get(underlyingRIC);
			prices.mid = ask;
			priceMap.put(underlyingRIC, prices);
		}
		else
		{
			prices = new Prices();
			prices.mid = ask;
			priceMap.put(underlyingRIC, prices);
		}	
	}

	@Override
	public BigDecimal getBidPrice(String underlyingRIC)
	{
		if(priceMap.containsKey(underlyingRIC))
			return priceMap.get(underlyingRIC).bid;
		else
			return new BigDecimal(0);
	}

	@Override
	public void setBidPrice(String underlyingRIC, BigDecimal bid)
	{
		Prices prices;		
		if(priceMap.containsKey(underlyingRIC))
		{
			prices = priceMap.get(underlyingRIC);
			prices.mid = bid;
			priceMap.put(underlyingRIC, prices);
		}
		else
		{
			prices = new Prices();
			prices.mid = bid;
			priceMap.put(underlyingRIC, prices);
		}		
	}

	@Override
	public BigDecimal getClosePrice(String underlyingRIC)
	{
		if(priceMap.containsKey(underlyingRIC))
			return priceMap.get(underlyingRIC).close;
		else
			return new BigDecimal(0);
	}

	@Override
	public void setClosePrice(String underlyingRIC, BigDecimal close)
	{
		Prices prices;		
		if(priceMap.containsKey(underlyingRIC))
		{
			prices = priceMap.get(underlyingRIC);
			prices.mid = close;
			priceMap.put(underlyingRIC, prices);
		}
		else
		{
			prices = new Prices();
			prices.mid = close;
			priceMap.put(underlyingRIC, prices);
		}
	}

}
