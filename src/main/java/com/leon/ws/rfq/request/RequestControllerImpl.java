package com.leon.ws.rfq.request;

import javax.jws.WebMethod;
import javax.jws.WebService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.leon.ws.rfq.search.SearchCriteriaImpl;

@WebService(serviceName="RequestController", endpointInterface="com.leon.ws.rfq.request.RequestController")
public class RequestControllerImpl implements RequestController
{
	private static Logger logger = LoggerFactory.getLogger(RequestControllerImpl.class);
	
	public RequestControllerImpl() {}
	
	private RequestManagerDao dao;
	//OptionPricingModelContext context = null;
	
	public void setRequestManagerDao(RequestManagerDao dao)
	{
		this.dao = dao;
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
			/*foreach(OptionDetailImpl optionLeg : request.getLegs().getOptionDetailList())
			{
				rePrice(optionLeg);		       
			}*/
		}
		
		return request;
	}
	
	private OptionDetailImpl rePrice(OptionDetailImpl optionLeg)
	{
        /*context.setSrike(optionLeg.getStrike().doubleValue());
        context.setInterestRate(interestRate.doubleValue());
        context.setUnderlyingPrice(underlyingPrice.doubleValue());
        context.setVolatility(volatility.doubleValue());
        context.setDaysToExpiry(daysToExpiry.doubleValue());
        context.setDayCountConvention(dayCountConvention.doubleValue());       
        context.setToCall(isCall);
        context.setToEuropean(isEuropean);
        
        OptionPriceResult result = context.calculate();*/
        return null;
	}

	@WebMethod
	public RequestDetailListImpl getRequestsForToday(boolean rePrice)
	{
		if(logger.isDebugEnabled())
			logger.debug("Received request to retrieve" + (rePrice ? " (and reprice)" : "")  + " RFQs created today.");		
		
		
		RequestDetailListImpl requests = dao.getRequestsForToday();
		
		if(rePrice)
		{
			/*foreach(OptionDetailImpl optionLeg : request.getLegs())
			{
				rePrice(optionLeg);		       
			}*/
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
			/*foreach(OptionDetailImpl optionLeg : request.getLegs())
			{
				rePrice(optionLeg);		       
			}*/
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
			/*foreach(OptionDetailImpl optionLeg : request.getLegs())
			{
				rePrice(optionLeg);		       
			}*/
		}
		
		return requests;
	}
}
