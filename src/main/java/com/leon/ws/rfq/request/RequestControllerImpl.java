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

	/**
	 * Sets the DAO reference property.
	 * 
	 * @param dao						the RequestManagerDao.
	 * @throws NullPointerException		if the DAO reference is null.
	 */
	public void setRequestManagerDao(RequestManagerDao dao)
	{
		if(dao == null)
			throw new NullPointerException("dao");

		this.dao = dao;
	}

	/**
	 * Saves the incoming request to the database using the DAO interface reference.
	 * For each request a TaggedRequestEvent is published.
	 * 
	 * @param request						the request to be saved.
	 * @param savedBy						the identifier of the user performing the save.
	 * @throws IllegalArgumentException		if the user identifier string is empty.
	 * @throws NullPointerException			if the request reference is null.
	 * @return 								the identifier of the newly saved request if the save is successful, -1 otherwise.
	 */
	@Override
	@WebMethod
	public int save(RequestDetailImpl request, String savedBy)
	{
		if(request == null)
			throw new NullPointerException("request");

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

	/**
	 * Updates the incoming request to the database using the DAO interface reference.
	 * 
	 * For each request a TaggedRequestEvent is published.
	 * @param request						the request to be updated.
	 * @param savedBy						the identifier of the user performing the update.
	 * @throws NullPointerException			if the request reference is null.
	 * @throws IllegalArgumentException		if the user identifier string is empty.
	 * @return								true if the update is successful, false otherwise.
	 */
	@Override
	@WebMethod
	public boolean update(RequestDetailImpl request, String updatedBy)
	{
		if(request == null)
			throw new NullPointerException("request");

		if(updatedBy.isEmpty())
			throw new IllegalArgumentException("updatedBy");

		if(logger.isDebugEnabled())
			logger.debug("Received request from user " + updatedBy + " to UPDATE RFQ [" + request + "].");

		boolean success = this.dao.update(request, updatedBy);

		if(success)
			this.applicationEventPublisher.publishEvent(new TaggedRequestEvent(this, request));

		return success;
	}

	/**
	 * Returns a reference to the request which matches the identifier parameter.
	 * 
	 * @param identifier					the unique identifier of request to be retrieved by the DAO reference.
	 * @param rePrice						the boolean flag indicator if the RFQ is to be re-priced.
	 * @throws IllegalArgumentException		if the identifier of the request is less than or equal to zero.
	 * @return								the request reference if the retrieval is successful, null otherwise.
	 */
	@Override
	@WebMethod
	public RequestDetailImpl getRequest(int identifier, boolean rePrice)
	{
		if(identifier <= 0)
			throw new IllegalArgumentException("identifier");
		
		if(logger.isDebugEnabled())
			logger.debug("Received request to retrieve" + (rePrice ? " (and reprice)" : "")  + " RFQ with identifier [" + identifier + "].");

		RequestDetailImpl request = this.dao.getRequest(identifier);

		if(rePrice)
			this.applicationEventPublisher.publishEvent(new TaggedRequestEvent(this, request));

		return request;
	}

	/**
	 * Returns a reference to all requests with a trade date of today.
	 * 
	 * @param rePrice						the boolean flag indicator if the RFQ is to be re-priced.
	 * @return								the request references if retrieval is successful, null otherwise.
	 */
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

	/**
	 * Returns a reference to all requests matching adhoc search criteria.
	 * 
	 * @param criteria						the adhoc search criteria.
	 * @param rePrice						the boolean flag indicator if the RFQ is to be re-priced.
	 * @throws NullPointerException			if the criteria reference is null.
	 * @return								the request references if retrieval is successful, null otherwise.
	 */
	@Override
	@WebMethod
	public RequestDetailListImpl getRequestsMatchingAdhocCriteria(SearchCriteriaImpl criteria, boolean rePrice)
	{
		if(criteria == null)
			throw new NullPointerException("criteria");
		
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

	/**
	 * Returns a reference to all requests matching existing search criteria.
	 * 
	 * @param criteriaOwner					the existing search criteria owner.
	 * @param criteriaKey					the existing search criteria key.
	 * @param rePrice						the boolean flag indicator if the RFQ is to be re-priced.
	 * @throws IllegalArgumentException		if the criteriaOwner or the criteriaKey string is empty.
	 * @return								the request references if retrieval is successful, null otherwise.
	 */
	@Override
	@WebMethod
	public RequestDetailListImpl getRequestsMatchingExistingCriteria(String criteriaOwner, String criteriaKey, boolean rePrice)
	{
		if(criteriaOwner.isEmpty())
			throw new IllegalArgumentException("criteriaOwner");
		
		if(criteriaKey.isEmpty())
			throw new IllegalArgumentException("criteriaKey");
		
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

	/**
	 * Sets the application publisher reference property.
	 * 
	 * @param applicationEventPublisher		the applicationEventPublisher.
	 * @throws NullPointerException			if the applicationEventPublisher reference is null.
	 */
	@Override
	public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher)
	{
		if(applicationEventPublisher == null)
			throw new NullPointerException("applicationEventPublisher");

		this.applicationEventPublisher = applicationEventPublisher;
	}
}
