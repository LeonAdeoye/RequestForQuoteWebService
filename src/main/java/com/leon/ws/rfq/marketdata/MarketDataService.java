package com.leon.ws.rfq.marketdata;

import java.math.BigDecimal;

public interface MarketDataService
{

	BigDecimal getUnderlyingPrice(String underlyingRIC);

}
