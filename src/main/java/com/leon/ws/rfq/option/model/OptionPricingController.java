package com.leon.ws.rfq.option.model;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;


@WebService(name="OptionPricingController")
public interface OptionPricingController
{
	@WebMethod
	void parameterize(@WebParam(name="strike") double strike,
			@WebParam(name="volatility") double volatility,
			@WebParam(name="underlyingPrice") double underlyingPrice,
			@WebParam(name="daysToExpiry") double daysToExpiry,
			@WebParam(name="interestRate") double interestRate,
			@WebParam(name="isCall") boolean isCall,
			@WebParam(name="isEuropean") boolean isEuropean,
			@WebParam(name="dayCountConvention") double dayCountConvention);
	
	
	@WebMethod
	OptionPriceResult calculate(@WebParam(name="strike") double strike,
			@WebParam(name="volatility") double volatility,
			@WebParam(name="underlyingPrice") double underlyingPrice,
			@WebParam(name="daysToExpiry") double daysToExpiry,
			@WebParam(name="interestRate") double interestRate,
			@WebParam(name="isCall") boolean isCall,
			@WebParam(name="isEuropean") boolean isEuropean,
			@WebParam(name="dayCountConvention") double dayCountConvention);
	
	@WebMethod
	String getModelDetails();
	
	@WebMethod
	ExtrapolationSet calculateRange(@WebParam(name="rangeKey") String rangeKey,
			@WebParam(name="startValue") double startValue,
			@WebParam(name="endValue") double endValue,
			@WebParam(name="increment") double increment);
}
