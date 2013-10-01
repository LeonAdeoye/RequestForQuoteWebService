package com.leon.ws.rfq.option.model;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.Oneway;
import java.math.BigDecimal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebService(serviceName="OptionPricingController", 
endpointInterface="com.leon.ws.rfq.option.model.OptionPricingController")
public class OptionPricingControllerImpl implements OptionPricingController
{
	static final Logger logger = LoggerFactory.getLogger(OptionPricingControllerImpl.class);
	OptionPricingModelContext context = null;
	
	public OptionPricingControllerImpl()
	{
		if(logger.isDebugEnabled())
			logger.debug("constructor => default model:{}", context.getModel().toString());
		
		context = new OptionPricingModelContext(new BlackScholesModelImpl());		
	}
	
	public OptionPricingControllerImpl(OptionPricingModel model)
	{
		if(logger.isDebugEnabled())		
			logger.debug("constructor => setting model:{}", context.getModel().toString());
		
		context = new OptionPricingModelContext(model);
	}
	
	@WebMethod(exclude=true)
	public void setModel(OptionPricingModel model)
	{		
		if(context != null)
		{
			if(logger.isDebugEnabled())		
				logger.debug("setting to model:{}", model.toString());
			
			context.setModel(model);
		}
	}
	
	@WebMethod
	public OptionPricingModel getModel()
	{
		if(context != null)
			return context.getModel();
		else
			return null;
	}
	
	@WebMethod
	public String getModelDetails()
	{
		return "Black Scholes Model - European options only.";
	}
	
	@WebMethod
	@Oneway
	public void parameterize(BigDecimal strike,	BigDecimal volatility, BigDecimal underlyingPrice, BigDecimal daysToExpiry,
			BigDecimal interestRate, boolean isCall, boolean isEuropean, BigDecimal dayCountConvention)
	{ 		
		if(context != null)
		{
			if(logger.isDebugEnabled())			
				logger.debug("option pricer parameterizing with values => strike:{}, interestRate:{}, underlyingPrice:{}, " +
					"volatility:{}, daysToExpiry:{}, dayCountConvention:{}, isCall:{}, isEuropean:{}", strike.toPlainString(), interestRate.toPlainString(), 
					underlyingPrice.toPlainString(), volatility.toPlainString(), daysToExpiry.toPlainString(), dayCountConvention.toPlainString(), 
					(isCall ? "TRUE" : "FALSE"), (isEuropean ? "TRUE" : "FALSE"));
			
	        context.setSrike(strike.doubleValue());
	        context.setInterestRate(interestRate.doubleValue());
	        context.setUnderlyingPrice(underlyingPrice.doubleValue());
	        context.setVolatility(volatility.doubleValue());
	        context.setDaysToExpiry(daysToExpiry.doubleValue());
	        context.setDayCountConvention(dayCountConvention.doubleValue());       
	        context.setToCall(isCall);
	        context.setToEuropean(isEuropean);
		}
		else if(logger.isErrorEnabled())
			logger.error("Pricing context is null! Cannot price.");
	}
	
	@WebMethod
	public OptionPriceResult calculate(BigDecimal strike,	BigDecimal volatility, BigDecimal underlyingPrice, BigDecimal daysToExpiry,
			BigDecimal interestRate, boolean isCall, boolean isEuropean, BigDecimal dayCountConvention)
	{		
		if(context != null)
		{
			if(logger.isDebugEnabled())	
				logger.debug("Option price calculation with parameters => strike:{}, interestRate:{}, underlyingPrice:{}, " +
					"volatility:{}, daysToExpiry:{}, dayCountConvention:{}, isCall:{}, isEuropean:{}", strike.toPlainString(), interestRate.toPlainString(), 
					underlyingPrice.toPlainString(), volatility.toPlainString(), daysToExpiry.toPlainString(), dayCountConvention.toPlainString(), 
					(isCall ? "TRUE" : "FALSE"), (isEuropean ? "TRUE" : "FALSE"));			
			
	        context.setSrike(strike.doubleValue());
	        context.setInterestRate(interestRate.doubleValue());
	        context.setUnderlyingPrice(underlyingPrice.doubleValue());
	        context.setVolatility(volatility.doubleValue());
	        context.setDaysToExpiry(daysToExpiry.doubleValue());
	        context.setDayCountConvention(dayCountConvention.doubleValue());       
	        context.setToCall(isCall);
	        context.setToEuropean(isEuropean);
	        
			try
			{
				return context.calculate();
			}
			catch(Exception e)
			{
				if(logger.isErrorEnabled())
					logger.error("Failed to calculate option price => Exception thrown.", e);
			}	        
		}
		else if(logger.isErrorEnabled())
			logger.error("Pricing context is null! Cannot price");
		
		return null;
	}
	
	@WebMethod
	public OptionPriceResultSet calculateRange(String rangeKey,	BigDecimal startValue, BigDecimal endValue,	BigDecimal increment)
	{
		try
		{
			if(logger.isDebugEnabled())	
				logger.debug("Calculating range => rangeKey:{}, startValue:{}, endValue:{}, increment:{}",
						rangeKey, startValue.toPlainString(), endValue.toPlainString(), increment.toPlainString());			
			
			return context.calculateRange(rangeKey, startValue.doubleValue(), endValue.doubleValue(), increment.doubleValue());
		}
		catch(Exception e)
		{
			if(logger.isErrorEnabled())
				logger.error("Failed to calculate range => Exception thrown", e);
		}
		return null; 
	}
}
