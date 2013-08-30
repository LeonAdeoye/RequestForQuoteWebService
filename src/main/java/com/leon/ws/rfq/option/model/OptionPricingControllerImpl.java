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
	static final Logger LOG = LoggerFactory.getLogger(OptionPricingControllerImpl.class);
	OptionPricingModelContext context = null;
	
	public OptionPricingControllerImpl()
	{
		context = new OptionPricingModelContext(new BlackScholesModelImpl());
		LOG.debug("OptionPricingControllerImpl::constructor => model:{}", context.getModel().toString());
	}
	
	public OptionPricingControllerImpl(OptionPricingModel model)
	{
		context = new OptionPricingModelContext(model);
		LOG.debug("OptionPricingControllerImpl::constructor => model:{}", context.getModel().toString());
	}
	
	@WebMethod(exclude=true)
	public void setModel(OptionPricingModel model)
	{		
		if(context != null)
		{
			LOG.debug("OptionPricingControllerImpl::setModel => model:{}", model.toString());
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
		LOG.debug("OptionPricingControllerImpl::parameterize => strike{}, interestRate:{}, underlyingPrice:{}, " +
				"volatility:{}, daysToExpiry:{}, dayCountConvention:{}, isCall:{}, isEuropean:{}", strike.toString(), interestRate.toString(), 
				underlyingPrice.toPlainString(), volatility.toPlainString(), daysToExpiry.toPlainString(), dayCountConvention.toPlainString(), (isCall ? "TRUE" : "FALSE"), (isEuropean ? "TRUE" : "FALSE"));
		
		if(context != null)
		{
	        context.setSrike(strike.doubleValue());
	        context.setInterestRate(interestRate.doubleValue());
	        context.setUnderlyingPrice(underlyingPrice.doubleValue());
	        context.setVolatility(volatility.doubleValue());
	        context.setDaysToExpiry(daysToExpiry.doubleValue());
	        context.setDayCountConvention(dayCountConvention.doubleValue());       
	        context.setToCall(isCall);
	        context.setToEuropean(isEuropean);
		}
		else
			LOG.error("OptionPricingControllerImpl::parameterize => context is null!");
	}
	
	@WebMethod
	public OptionPriceResult calculate(BigDecimal strike,	BigDecimal volatility, BigDecimal underlyingPrice, BigDecimal daysToExpiry,
			BigDecimal interestRate, boolean isCall, boolean isEuropean, BigDecimal dayCountConvention)
	{
		LOG.debug("OptionPricingControllerImpl::parameterize => strike{}, interestRate:{}, underlyingPrice:{}, " +
				"volatility:{}, daysToExpiry:{}, dayCountConvention:{}, isCall:{}, isEuropean:{}", strike.toString(), interestRate.toString(), 
				underlyingPrice.toPlainString(), volatility.toPlainString(), daysToExpiry.toPlainString(), dayCountConvention.toPlainString(), (isCall ? "TRUE" : "FALSE"), (isEuropean ? "TRUE" : "FALSE"));
		
		if(context != null)
		{
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
				LOG.error("OptionPricingControllerImpl::calculate => Exception:{}, Message:{}", e.toString(), e.getMessage());
			}	        
		}
		else
			LOG.error("OptionPricingControllerImpl::parameterize => context is null!");
		
		return null;
	}
	
	@WebMethod
	public OptionPriceResultSet calculateRange(String rangeKey,	BigDecimal startValue, BigDecimal endValue,	BigDecimal increment)
	{
		LOG.debug("OptionPricingControllerImpl::calculateRange => rangeKey:{}, startValue:{}, endValue:{}, increment:{}",
					rangeKey, startValue.toPlainString(), endValue.toPlainString(), increment.toPlainString());
		try
		{
			return context.calculateRange(rangeKey, startValue.doubleValue(), endValue.doubleValue(), increment.doubleValue());
		}
		catch(Exception e)
		{
			LOG.error("OptionPricingControllerImpl::calculateRange => Exception:{}, Message:{}", e.toString(), e.getMessage());
		}
		return null; 
	}
}
