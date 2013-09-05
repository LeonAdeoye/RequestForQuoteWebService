package com.leon.ws.rfq.option.model;

import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import java.math.BigDecimal;


@WebService(name="OptionPricingController")
public interface OptionPricingController
{
	@WebMethod
	void parameterize(@WebParam(name="strike") BigDecimal strike,
			@WebParam(name="volatility") BigDecimal volatility,
			@WebParam(name="underlyingPrice") BigDecimal underlyingPrice,
			@WebParam(name="daysToExpiry") BigDecimal daysToExpiry,
			@WebParam(name="interestRate") BigDecimal interestRate,
			@WebParam(name="isCall") boolean isCall,
			@WebParam(name="isEuropean") boolean isEuropean,
			@WebParam(name="dayCountConvention") BigDecimal dayCountConvention);
	
	
	@WebMethod
	OptionPriceResult calculate(@WebParam(name="strike") BigDecimal strike,
			@WebParam(name="volatility") BigDecimal volatility,
			@WebParam(name="underlyingPrice") BigDecimal underlyingPrice,
			@WebParam(name="daysToExpiry") BigDecimal daysToExpiry,
			@WebParam(name="interestRate") BigDecimal interestRate,
			@WebParam(name="isCall") boolean isCall,
			@WebParam(name="isEuropean") boolean isEuropean,
			@WebParam(name="dayCountConvention") BigDecimal dayCountConvention);
	
	@WebMethod
	String getModelDetails();	
	
	@WebMethod
	OptionPriceResultSet calculateRange(@WebParam(name="rangeKey") String rangeKey,
			@WebParam(name="startValue") BigDecimal startValue,
			@WebParam(name="endValue") BigDecimal endValue,
			@WebParam(name="increment") BigDecimal increment); 
	
}
