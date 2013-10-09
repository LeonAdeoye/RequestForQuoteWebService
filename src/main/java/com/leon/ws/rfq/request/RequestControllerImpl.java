package com.leon.ws.rfq.request;

import javax.jws.WebMethod;
import javax.jws.WebService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;

import com.leon.ws.rfq.events.TaggedRequestEvent;
import com.leon.ws.rfq.search.SearchCriteriaImpl;

@WebService(serviceName="RequestController", endpointInterface="com.leon.ws.rfq.request.RequestController")
public final class RequestControllerImpl implements RequestController, ApplicationEventPublisherAware
{
	private final static Logger logger = LoggerFactory.getLogger(RequestControllerImpl.class);	
	private RequestManagerDao dao;
	private ApplicationEventPublisher applicationEventPublisher; 
	
	public RequestControllerImpl() {}
	
	public void setRequestManagerDao(RequestManagerDao dao)
	{
		this.dao = dao;
	}
	
	@WebMethod
	public int save(RequestDetailImpl request, String savedByUser)
	{
		if(logger.isDebugEnabled())
			logger.debug("Received request from user " + savedByUser + " to SAVE RFQ [" + request + "].");
		
		int identifier = dao.save(request, savedByUser);
		
		if(identifier != -1)
			this.applicationEventPublisher.publishEvent(new TaggedRequestEvent(this, request));	
		
		return identifier;
	}

	@WebMethod
	public boolean update(RequestDetailImpl request, String updatedByUser)
	{
		if(logger.isDebugEnabled())
			logger.debug("Received request from user " + updatedByUser + " to UPDATE RFQ [" + request + "].");
		
		boolean success = dao.update(request, updatedByUser);
		
		if(success)
			this.applicationEventPublisher.publishEvent(new TaggedRequestEvent(this, request));				
		
		return success;
	}

	@WebMethod
	public RequestDetailImpl getRequest(int identifier, boolean rePrice)
	{
		if(logger.isDebugEnabled())
			logger.debug("Received request to retrieve" + (rePrice ? " (and reprice)" : "")  + " RFQ with identifier [" + identifier + "].");		
		
		RequestDetailImpl request = dao.getRequest(identifier);
				
		if(rePrice)
			this.applicationEventPublisher.publishEvent(new TaggedRequestEvent(this, request));				
		
		return request;
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
				this.applicationEventPublisher.publishEvent(new TaggedRequestEvent(this, request));			
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
				this.applicationEventPublisher.publishEvent(new TaggedRequestEvent(this, request));			
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
				this.applicationEventPublisher.publishEvent(new TaggedRequestEvent(this, request));			
		}

		return requests;
	}

	@Override
	public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher)
	{
		this.applicationEventPublisher = applicationEventPublisher;
	}
}
