package com.leon.ws.rfq.underlying;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;

import com.leon.ws.rfq.events.NewUnderlyingEvent;

@WebService(serviceName="UnderlyingController", endpointInterface="com.leon.ws.rfq.underlying.UnderlyingController")
public class UnderlyingControllerImpl implements UnderlyingController, ApplicationEventPublisherAware
{
	private static Logger logger = LoggerFactory.getLogger(UnderlyingControllerImpl.class);
	private ApplicationEventPublisher applicationEventPublisher;
	private UnderlyingManagerDao dao;

	public void setUnderlyingManagerDao(UnderlyingManagerDao dao)
	{
		this.dao = dao;
	}

	@Override
	@WebMethod
	public boolean save(String ric, String description, String savedBy)
	{
		if(ric.isEmpty())
			throw new IllegalArgumentException("ric");

		if(description.isEmpty())
			throw new IllegalArgumentException("description");

		if(savedBy.isEmpty())
			throw new IllegalArgumentException("savedBy");

		if(logger.isDebugEnabled())
			logger.debug("Received request from user [" + savedBy + "] to save underlying with RIC [" + ric + "].");

		UnderlyingDetailImpl newUnderlying = this.dao.save(ric, description, savedBy);

		if(newUnderlying != null)
			this.applicationEventPublisher.publishEvent(new NewUnderlyingEvent(this, newUnderlying));

		return newUnderlying != null;
	}

	@Override
	@WebMethod
	public boolean updateValidity(String ric, boolean isValid, String updatedBy)
	{
		if(ric.isEmpty())
			throw new IllegalArgumentException("ric");

		if(updatedBy.isEmpty())
			throw new IllegalArgumentException("updatedBy");

		if(logger.isDebugEnabled())
			logger.debug("Received request from user [" + updatedBy + "] to update the validity of the underlying with RIC [" + ric + "].");

		return this.dao.updateValidity(ric, isValid, updatedBy);
	}

	@Override
	@WebMethod
	public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher)
	{
		this.applicationEventPublisher = applicationEventPublisher;
	}

	@Override
	@WebMethod
	public List<UnderlyingDetailImpl> getAll()
	{
		if(logger.isDebugEnabled())
			logger.debug("Received request to get all previously saved underlyings.");

		return this.dao.getAll();
	}
}
