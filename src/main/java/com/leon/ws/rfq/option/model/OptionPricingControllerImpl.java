package com.leon.ws.rfq.option.model;

import java.util.HashMap;
import java.util.Map;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.Oneway;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

import com.leon.ws.rfq.events.PriceUpdateEvent;
import com.leon.ws.rfq.events.TaggedRequestEvent;
import com.leon.ws.rfq.marketdata.MarketDataService;
import com.leon.ws.rfq.parametric.ParametricDataService;
import com.leon.ws.rfq.request.OptionDetailImpl;
import com.leon.ws.rfq.request.RequestDetailImpl;

@WebService(serviceName="OptionPricingController", 
endpointInterface="com.leon.ws.rfq.option.model.OptionPricingController")
public final class OptionPricingControllerImpl implements OptionPricingController, ApplicationListener
{
	static final Logger logger = LoggerFactory.getLogger(OptionPricingControllerImpl.class);
	private Map<String, Map<Integer, RequestDetailImpl>> ContinuouslyPricedRequestsKeyedByUnderlying = new HashMap<>();
	private final MarketDataService marketDataService;
	private final ParametricDataService parametricDataService;
	OptionPricingModelContext context = null;
		
	public OptionPricingControllerImpl(OptionPricingModel model, MarketDataService marketDataService, ParametricDataService parametricDataService)
	{		
		context = new OptionPricingModelContext(model);
		this.marketDataService = marketDataService;
		this.parametricDataService = parametricDataService;
		
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
	
	private void priceWithLatestMarketData(RequestDetailImpl request)
	{
		for(OptionDetailImpl optionLeg : request.getLegs().getOptionDetailList())
		{
			optionLeg.setVolatility(parametricDataService.getVolatility(optionLeg.getUnderlyingRIC()));
			optionLeg.setUnderlyingPrice(marketDataService.getMidPrice(optionLeg.getUnderlyingRIC()));
			optionLeg.setInterestRate(parametricDataService.getInterestRate(request.getPremiumSettlementCurrency()));
			
	        OptionPriceResult result = this.calculate(
	        		optionLeg.getStrike(), 
	        		optionLeg.getVolatility(), 
	        		optionLeg.getUnderlyingPrice(), 
	        		optionLeg.getDaysToExpiry(), 
	        		optionLeg.getInterestRate(), 
	        		optionLeg.getIsCall(), 
	        		optionLeg.getIsEuropean(), 
	        		optionLeg.getDayCountConvention());

	        	optionLeg.setDelta(result.getDelta());
	        	optionLeg.setGamma(result.getGamma());
	        	optionLeg.setVega(result.getVega());
	        	optionLeg.setTheta(result.getTheta());
	        	optionLeg.setRho(result.getRho());
	        	optionLeg.setPremium(result.getPrice());
	        	
	        	if(logger.isDebugEnabled())
	        		logger.debug("Repriced option: " + optionLeg.toString());
		}			
	}	

	@Override
	public void onApplicationEvent(ApplicationEvent event)
	{
		if(event instanceof PriceUpdateEvent)
		{
			PriceUpdateEvent priceUpdateEvent = (PriceUpdateEvent) event;
			
			for(Map.Entry<Integer, RequestDetailImpl> request : getRequestsToPrice(priceUpdateEvent.getUnderlyingRIC()).entrySet())
				priceWithLatestMarketData(request.getValue());			
			
			if(logger.isTraceEnabled())
				logger.trace("Price update: " + priceUpdateEvent.getPriceUpdate() + " captured by the option pricing controller for underlying: " + priceUpdateEvent.getUnderlyingRIC());				

		}
		else if(event instanceof TaggedRequestEvent)
		{
			TaggedRequestEvent taggedRequestEvent = (TaggedRequestEvent) event;
			
			registerForContinuousPricing(taggedRequestEvent.getRequest());
			
			if(logger.isDebugEnabled())
				logger.debug("Tagged request event received for request: " + taggedRequestEvent.getRequest());				
		}
	}
	
	private Map<Integer, RequestDetailImpl> getRequestsToPrice(String underlyingRIC)
	{		
		if(ContinuouslyPricedRequestsKeyedByUnderlying.containsKey(underlyingRIC))
			return ContinuouslyPricedRequestsKeyedByUnderlying.get(underlyingRIC);
		
		return new HashMap<>();
	}
	
	private void registerForContinuousPricing(RequestDetailImpl request)
	{
		for(OptionDetailImpl optionLeg : request.getLegs().getOptionDetailList())
		{
			if(ContinuouslyPricedRequestsKeyedByUnderlying.containsKey(optionLeg.getUnderlyingRIC()))
			{
				Map<Integer, RequestDetailImpl> continuouslyPricedRequestKeyedByIdentifier = 
						ContinuouslyPricedRequestsKeyedByUnderlying.get(optionLeg.getUnderlyingRIC());
				
				if(!continuouslyPricedRequestKeyedByIdentifier.containsKey(request.getIdentifier()))
					continuouslyPricedRequestKeyedByIdentifier.put(request.getIdentifier(), request);
			}
			else
			{
				Map<Integer, RequestDetailImpl> continuouslyPricedRequestKeyedByIdentifier = new HashMap<>();
				continuouslyPricedRequestKeyedByIdentifier.put(request.getIdentifier(), request);				
				ContinuouslyPricedRequestsKeyedByUnderlying.put(optionLeg.getUnderlyingRIC(), continuouslyPricedRequestKeyedByIdentifier);
			}
		}		
	}
	
	private void unregisterForContinuousPricing(RequestDetailImpl request)
	{
		for(OptionDetailImpl optionLeg : request.getLegs().getOptionDetailList())
		{
			if(ContinuouslyPricedRequestsKeyedByUnderlying.containsKey(optionLeg.getUnderlyingRIC()))
			{
				Map<Integer, RequestDetailImpl> continuouslyPricedRequestKeyedByIdentifier = 
						ContinuouslyPricedRequestsKeyedByUnderlying.get(optionLeg.getUnderlyingRIC());
				
				if(continuouslyPricedRequestKeyedByIdentifier.containsKey(request.getIdentifier()))
					continuouslyPricedRequestKeyedByIdentifier.remove(request.getIdentifier());
			}
		}		
	}	
}
