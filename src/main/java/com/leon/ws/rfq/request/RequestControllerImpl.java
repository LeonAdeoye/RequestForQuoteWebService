package com.leon.ws.rfq.request;

import javax.jws.WebMethod;
import javax.jws.WebService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.leon.ws.rfq.marketdata.MarketDataService;
import com.leon.ws.rfq.option.model.OptionPriceResult;
import com.leon.ws.rfq.option.model.OptionPricingController;
import com.leon.ws.rfq.parametric.ParametricDataService;
import com.leon.ws.rfq.search.SearchCriteriaImpl;

@WebService(serviceName="RequestController", endpointInterface="com.leon.ws.rfq.request.RequestController")
public class RequestControllerImpl implements RequestController
{
	private static Logger logger = LoggerFactory.getLogger(RequestControllerImpl.class);
	
	public RequestControllerImpl() {}
	
	private RequestManagerDao dao;
	private OptionPricingController optionPricer;
	private MarketDataService marketDataService;
	private ParametricDataService parametricDataService;
	
	public void setRequestManagerDao(RequestManagerDao dao)
	{
		this.dao = dao;
	}
	
	public void setMarketDataService(MarketDataService marketDataService)
	{
		this.marketDataService = marketDataService;
	}
	
	public void setParametricDataService(ParametricDataService parametricDataService)
	{
		this.parametricDataService = parametricDataService;
	}	

	@WebMethod
	public int save(RequestDetailImpl requestDetail, String savedByUser)
	{
		if(logger.isDebugEnabled())
			logger.debug("Received request from user " + savedByUser + " to SAVE RFQ [" + requestDetail + "].");
		
		return dao.save(requestDetail, savedByUser);
	}

	@WebMethod
	public boolean update(RequestDetailImpl requestDetail, String updatedByUser)
	{
		if(logger.isDebugEnabled())
			logger.debug("Received request from user " + updatedByUser + " to UPDATE RFQ [" + requestDetail + "].");
		
		return dao.update(requestDetail, updatedByUser);
	}

	@WebMethod
	public RequestDetailImpl getRequest(int identifier, boolean rePrice)
	{
		if(logger.isDebugEnabled())
			logger.debug("Received request to retrieve" + (rePrice ? " (and reprice)" : "")  + " RFQ with identifier [" + identifier + "].");		
		
		RequestDetailImpl request = dao.getRequest(identifier);
		
		if(rePrice)
		{
			for(OptionDetailImpl optionLeg : request.getLegs().getOptionDetailList())
			{
				rePriceWithLatestData(optionLeg);		       
			}	
		}
		
		return request;
	}
	
	private OptionDetailImpl rePriceWithLatestData(OptionDetailImpl optionLeg)
	{
       
        OptionPriceResult result = optionPricer.calculate(
        		optionLeg.getStrike(), 
        		parametricDataService.getVolatility(optionLeg.getUnderlyingRIC()), 
        		marketDataService.getMidPrice(optionLeg.getUnderlyingRIC()), 
        		optionLeg.getDaysToExpiry(), 
        		parametricDataService.getInterestRate(optionLeg.getCurrency()), 
        		optionLeg.getIsCall(), 
        		optionLeg.getIsEuropean(), 
        		optionLeg.getDayCountConvention());

        	optionLeg.setDelta(result.getDelta());
        	return optionLeg;
	}

	@WebMethod
	public RequestDetailListImpl getRequestsForToday(boolean rePrice)
	{
		if(logger.isDebugEnabled())
			logger.debug("Received request to retrieve" + (rePrice ? " (and reprice)" : "")  + " RFQs created today.");		
		
		
		RequestDetailListImpl requests = dao.getRequestsForToday();
		
		if(rePrice)
		{
			for(RequestDetailImpl request : requests.getRequestDetailList())
			{
				for(OptionDetailImpl optionLeg : request.getLegs().getOptionDetailList())
				{
					rePriceWithLatestData(optionLeg);		       
				}			
			}
		}
		
		return requests;
	}

	@WebMethod
	public RequestDetailListImpl getRequestsMatchingAdhocCriteria(SearchCriteriaImpl criteria, boolean rePrice)
	{
		if(logger.isDebugEnabled())
			logger.debug("Received request to retrieve" + (rePrice ? " (and reprice)" : "")  + 
					" RFQs matching the adhoc criteria [" + criteria + "].");		
		
		
		RequestDetailListImpl requests = dao.getRequestsMatchingAdhocCriteria(criteria);
		
		if(rePrice)
		{
			for(RequestDetailImpl request : requests.getRequestDetailList())
			{
				for(OptionDetailImpl optionLeg : request.getLegs().getOptionDetailList())
				{
					rePriceWithLatestData(optionLeg);		       
				}			
			}
		}
		
		return requests;
	}

	@WebMethod
	public RequestDetailListImpl getRequestsMatchingExistingCriteria(String criteriaOwner, String criteriaKey, boolean rePrice)
	{
		if(logger.isDebugEnabled())
			logger.debug("Received request to retrieve" + (rePrice ? " (and reprice)" : "")  + 
					" RFQs matching the existing criteria with owner ["	+ criteriaOwner + 
					"] and description key [" + criteriaKey + "].");		
		
		
		RequestDetailListImpl requests = dao.getRequestsMatchingExistingCriteria(criteriaOwner, criteriaKey);
		
		if(rePrice)
		{
			for(RequestDetailImpl request : requests.getRequestDetailList())
			{
				for(OptionDetailImpl optionLeg : request.getLegs().getOptionDetailList())
				{
					rePriceWithLatestData(optionLeg);		       
				}			
			}
		}
		
		return requests;
	}
}
