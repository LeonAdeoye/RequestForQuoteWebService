package com.leon.ws.rfq.option.model;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.Oneway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebService(serviceName="OptionPricingController", 
endpointInterface="com.leon.ws.rfq.option.model.OptionPricingController")
public final class OptionPricingControllerImpl implements OptionPricingController
{
	static final Logger logger = LoggerFactory.getLogger(OptionPricingControllerImpl.class);
	OptionPricingModelContext context = null;
	
	public OptionPricingControllerImpl()
	{		
		context = new OptionPricingModelContext(new BlackScholesModelImpl());
		
		if(logger.isDebugEnabled())
			logger.debug("constructor => default model:{}", context.getModel().toString());		
	}
	
	public OptionPricingControllerImpl(OptionPricingModel model)
	{		
		context = new OptionPricingModelContext(model);
		
		if(logger.isDebugEnabled())		
			logger.debug("constructor => setting model:{}", context.getModel().toString());		
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
	public void parameterize(double strike,	double volatility, double underlyingPrice, double daysToExpiry,
			double interestRate, boolean isCall, boolean isEuropean, double dayCountConvention)
	{ 		
		if(context != null)
		{
			if(logger.isDebugEnabled())			
				logger.debug("option pricer parameterizing with values => strike:{}, interestRate:{}, underlyingPrice:{}, " +
					"volatility:{}, daysToExpiry:{}, dayCountConvention:{}, isCall:{}, isEuropean:{}", strike, interestRate, 
					underlyingPrice, volatility, daysToExpiry, dayCountConvention, 
					(isCall ? "TRUE" : "FALSE"), (isEuropean ? "TRUE" : "FALSE"));
			
	        context.setSrike(strike);
	        context.setInterestRate(interestRate);
	        context.setUnderlyingPrice(underlyingPrice);
	        context.setVolatility(volatility);
	        context.setDaysToExpiry(daysToExpiry);
	        context.setDayCountConvention(dayCountConvention);       
	        context.setToCall(isCall);
	        context.setToEuropean(isEuropean);
		}
		else if(logger.isErrorEnabled())
			logger.error("Pricing context is null! Cannot price.");
	}
	
	@WebMethod
	public OptionPriceResult calculate(double strike,	double volatility, double underlyingPrice, double daysToExpiry,
			double interestRate, boolean isCall, boolean isEuropean, double dayCountConvention)
	{		
		if(context != null)
		{
			if(logger.isDebugEnabled())	
				logger.debug("Option price calculation with parameters => strike:{}, interestRate:{}, underlyingPrice:{}, " +
					"volatility:{}, daysToExpiry:{}, dayCountConvention:{}, isCall:{}, isEuropean:{}", strike, interestRate, 
					underlyingPrice, volatility, daysToExpiry, dayCountConvention, 
					(isCall ? "TRUE" : "FALSE"), (isEuropean ? "TRUE" : "FALSE"));			
			
	        context.setSrike(strike);
	        context.setInterestRate(interestRate);
	        context.setUnderlyingPrice(underlyingPrice);
	        context.setVolatility(volatility);
	        context.setDaysToExpiry(daysToExpiry);
	        context.setDayCountConvention(dayCountConvention);       
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
	public OptionPriceResultSet calculateRange(String rangeKey,	double startValue, double endValue,	double increment)
	{
		try
		{
			if(logger.isDebugEnabled())	
				logger.debug("Calculating range => rangeKey:{}, startValue:{}, endValue:{}, increment:{}",
						rangeKey, startValue, endValue, increment);			
			
			return context.calculateRange(rangeKey, startValue, endValue, increment);
		}
		catch(Exception e)
		{
			if(logger.isErrorEnabled())
				logger.error("Failed to calculate range => Exception thrown", e);
		}
		return null; 
	}
}
