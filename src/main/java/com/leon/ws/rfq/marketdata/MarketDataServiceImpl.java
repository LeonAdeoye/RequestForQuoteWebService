package com.leon.ws.rfq.marketdata;

import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;

public class MarketDataServiceImpl implements MarketDataService
{
	private Map<String, Prices> priceMap = new ConcurrentSkipListMap<>();
	// Nested class
	private class Prices
	{
		double ask = 0;
		double bid = 0;
		double mid = 0;
		double close = 0;
	}
	
	public MarketDataServiceImpl() {}
	
	/**
	 * Returns the mid price of an underlying product using the underlying RIC as the lookup key.
	 *
	 * @param  underlyingRIC	the RIC of the underlying product used as key to retrieve the mid price
	 * @return					the mid price if exists otherwise zero.
	 * @throws					IllegalArgumentException if underlyingRIC is empty.
	 */
	@Override
	public double getMidPrice(String underlyingRIC)
	{
		if(underlyingRIC.isEmpty())
			throw new IllegalArgumentException("underlyingRIC");
		
		if(priceMap.containsKey(underlyingRIC))
			return priceMap.get(underlyingRIC).mid;
		else
			return 0;
	}

	/**
	 * Sets the mid price of an underlying product using the underlying RIC as the lookup key.
	 *
	 * @param  underlyingRIC	the RIC of the underlying product used as key to retrieve the mid price.
	 * @param  mid				the mid price of the underlying product to be stored using the RIC as a key.
	 * @throws					IllegalArgumentException if underlyingRIC is empty || mid <= 0
	 */	
	@Override
	public void setMidPrice(String underlyingRIC, double mid)
	{
		if(underlyingRIC.isEmpty())
			throw new IllegalArgumentException("underlyingRIC");
		
		if(mid <= 0)
			throw new IllegalArgumentException("mid");		
		
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

	/**
	 * Returns the ask price of an underlying product using the underlying RIC as the lookup key.
	 *
	 * @param  underlyingRIC	the RIC of the underlying product used as key to retrieve the ask price
	 * @return					the ask price if it exists otherwise zero.
	 * @throws					IllegalArgumentException if underlyingRIC is empty.
	 */	
	@Override
	public double getAskPrice(String underlyingRIC)
	{
		if(underlyingRIC.isEmpty())
			throw new IllegalArgumentException("underlyingRIC");
		
		if(priceMap.containsKey(underlyingRIC))
			return priceMap.get(underlyingRIC).ask;
		else
			return 0;
	}

	/**
	 * Sets the ask price of an underlying product using the underlying RIC as the lookup key.
	 *
	 * @param  underlyingRIC	the RIC of the underlying product used as key to retrieve the ask price.
	 * @param  ask				the ask price of the underlying product to be stored using the RIC as a key.
	 * @throws					IllegalArgumentException if underlyingRIC is empty || ask <= 0
	 */		
	@Override
	public void setAskPrice(String underlyingRIC, double ask)
	{
		if(underlyingRIC.isEmpty())
			throw new IllegalArgumentException("underlyingRIC");
		
		if(ask <= 0)
			throw new IllegalArgumentException("ask");	
		
		Prices prices;		
		if(priceMap.containsKey(underlyingRIC))
		{
			prices = priceMap.get(underlyingRIC);
			prices.ask = ask;
			priceMap.put(underlyingRIC, prices);
		}
		else
		{
			prices = new Prices();
			prices.ask = ask;
			priceMap.put(underlyingRIC, prices);
		}	
	}

	/**
	 * Returns the bid price of an underlying product using the underlying RIC as the lookup key.
	 *
	 * @param  underlyingRIC	the RIC of the underlying product used as key to retrieve the bid price
	 * @return					the bid price if it exists otherwise zero.
	 * @throws					IllegalArgumentException if underlyingRIC is empty.
	 */		
	@Override
	public double getBidPrice(String underlyingRIC)
	{
		if(underlyingRIC.isEmpty())
			throw new IllegalArgumentException("underlyingRIC");
		
		if(priceMap.containsKey(underlyingRIC))
			return priceMap.get(underlyingRIC).bid;
		else
			return 0;
	}

	/**
	 * Sets the bid price of an underlying product using the underlying RIC as the lookup key.
	 *
	 * @param  underlyingRIC	the RIC of the underlying product used as key to retrieve the bid price.
	 * @param  bid				the bid price of the underlying product to be stored using the RIC as a key.
	 * @throws					IllegalArgumentException if underlyingRIC is empty || bid <= 0
	 */		
	@Override
	public void setBidPrice(String underlyingRIC, double bid)
	{
		if(underlyingRIC.isEmpty())
			throw new IllegalArgumentException("underlyingRIC");
		
		if(bid <= 0)
			throw new IllegalArgumentException("bid");	
		
		Prices prices;		
		if(priceMap.containsKey(underlyingRIC))
		{
			prices = priceMap.get(underlyingRIC);
			prices.bid = bid;
			priceMap.put(underlyingRIC, prices);
		}
		else
		{
			prices = new Prices();
			prices.bid = bid;
			priceMap.put(underlyingRIC, prices);
		}		
	}

	/**
	 * Returns the close price of an underlying product using the underlying RIC as the lookup key.
	 *
	 * @param  underlyingRIC	the RIC of the underlying product used as key to retrieve the close price
	 * @return					the close price if it exists otherwise zero.
	 * @throws					IllegalArgumentException if underlyingRIC is empty.
	 */		
	@Override
	public double getClosePrice(String underlyingRIC)
	{
		if(underlyingRIC.isEmpty())
			throw new IllegalArgumentException("underlyingRIC");
		
		if(priceMap.containsKey(underlyingRIC))
			return priceMap.get(underlyingRIC).close;
		else
			return 0;
	}

	/**
	 * Sets the close price of an underlying product using the underlying RIC as the lookup key.
	 *
	 * @param  underlyingRIC	the RIC of the underlying product used as key to retrieve the close price.
	 * @param  close			the close price of the underlying product to be stored using the RIC as a key.
	 * @throws					IllegalArgumentException if underlyingRIC is empty || close <= 0
	 */		
	@Override
	public void setClosePrice(String underlyingRIC, double close)
	{
		if(underlyingRIC.isEmpty())
			throw new IllegalArgumentException("underlyingRIC");
		
		if(close <= 0)
			throw new IllegalArgumentException("close");
		
		Prices prices;		
		if(priceMap.containsKey(underlyingRIC))
		{
			prices = priceMap.get(underlyingRIC);
			prices.close = close;
			priceMap.put(underlyingRIC, prices);
		}
		else
		{
			prices = new Prices();
			prices.close = close;
			priceMap.put(underlyingRIC, prices);
		}
	}

	/**
	 * Deletes all prices for all underlying RICs.
	 *
	 */		
	@Override
	public void clearAll()
	{
		priceMap.clear();		
	}

	/**
	 * Deletes all prices for a specific RIC
	 *
	 * @param  underlyingRIC	the RIC of the underlying product that prices will be deleted for.
	 * @throws					IllegalArgumentException if underlyingRIC is empty
	 */		
	@Override
	public void clearPricesForUnderlying(String underlyingRIC)
	{
		if(underlyingRIC.isEmpty())
			throw new IllegalArgumentException("underlyingRIC");
		
		if(priceMap.containsKey(underlyingRIC))
			priceMap.remove(underlyingRIC);		
	}

	/**
	 * Returns the size of the map containing the prices for all underlying RICs.
	 * 
	 * @return	the size of the price map. 
	 */	
	@Override
	public int size()
	{
		return priceMap.size();
	}
}
