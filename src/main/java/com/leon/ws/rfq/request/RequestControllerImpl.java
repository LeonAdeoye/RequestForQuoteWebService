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

	@Override
	@WebMethod
	public int save(RequestDetailImpl request, String savedBy)
	{
		if(request == null)
			throw new IllegalArgumentException("request");

		if(savedBy.isEmpty())
			throw new IllegalArgumentException("savedBy");

		if(logger.isDebugEnabled())
			logger.debug("Received request from user " + savedBy + " to SAVE RFQ [" + request + "].");

		RequestDetailImpl newRequest = this.dao.save(request, savedBy);

		if(newRequest != null)
		{
			this.applicationEventPublisher.publishEvent(new TaggedRequestEvent(this, newRequest));
			return newRequest.getIdentifier();
		}

		return -1;
	}

	@Override
	@WebMethod
	public boolean update(RequestDetailImpl request, String updatedBy)
	{
		if(request == null)
			throw new IllegalArgumentException("request");

		if(updatedBy.isEmpty())
			throw new IllegalArgumentException("updatedBy");

		if(logger.isDebugEnabled())
			logger.debug("Received request from user " + updatedBy + " to UPDATE RFQ [" + request + "].");

		boolean success = this.dao.update(request, updatedBy);

		if(success)
			this.applicationEventPublisher.publishEvent(new TaggedRequestEvent(this, request));

		return success;
	}

	@Override
	@WebMethod
	public RequestDetailImpl getRequest(int identifier, boolean rePrice)
	{
		if(logger.isDebugEnabled())
			logger.debug("Received request to retrieve" + (rePrice ? " (and reprice)" : "")  + " RFQ with identifier [" + identifier + "].");

		RequestDetailImpl request = this.dao.getRequest(identifier);

		if(rePrice)
			this.applicationEventPublisher.publishEvent(new TaggedRequestEvent(this, request));

		return request;
	}

	@Override
	@WebMethod
	public RequestDetailListImpl getRequestsForToday(boolean rePrice)
	{
		if(logger.isDebugEnabled())
			logger.debug("Received request to retrieve" + (rePrice ? " (and reprice)" : "")  + " RFQs created today.");


		RequestDetailListImpl requests = this.dao.getRequestsForToday();

		if(rePrice)
		{
			for(RequestDetailImpl request : requests.getRequestDetailList())
				this.applicationEventPublisher.publishEvent(new TaggedRequestEvent(this, request));
		}

		return requests;
	}

	@Override
	@WebMethod
	public RequestDetailListImpl getRequestsMatchingAdhocCriteria(SearchCriteriaImpl criteria, boolean rePrice)
	{
		if(logger.isDebugEnabled())
			logger.debug("Received request to retrieve" + (rePrice ? " (and reprice)" : "")  +
					" RFQs matching the adhoc criteria [" + criteria + "].");

		RequestDetailListImpl requests = this.dao.getRequestsMatchingAdhocCriteria(criteria);

		if(rePrice)
		{
			for(RequestDetailImpl request : requests.getRequestDetailList())
				this.applicationEventPublisher.publishEvent(new TaggedRequestEvent(this, request));
		}

		return requests;
	}

	@Override
	@WebMethod
	public RequestDetailListImpl getRequestsMatchingExistingCriteria(String criteriaOwner, String criteriaKey, boolean rePrice)
	{
		if(logger.isDebugEnabled())
			logger.debug("Received request to retrieve" + (rePrice ? " (and reprice)" : "")  +
					" RFQs matching the existing criteria with owner ["	+ criteriaOwner +
					"] and description key [" + criteriaKey + "].");

		RequestDetailListImpl requests = this.dao.getRequestsMatchingExistingCriteria(criteriaOwner, criteriaKey);

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
