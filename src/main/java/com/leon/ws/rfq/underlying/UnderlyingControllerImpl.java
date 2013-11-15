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

	/**
	 * Sets the Underlying Manager DAO object reference property.
	 * 
	 * @param dao 						the underlying manager dao for saving to the database.
	 * @throws NullPointerException 	if the dao parameter is null.
	 */
	@Override
	@WebMethod(exclude = true)
	public void setUnderlyingManagerDao(UnderlyingManagerDao dao)
	{
		if(dao == null)
			throw new NullPointerException("dao");

		this.dao = dao;
	}

	/**
	 * Saves the underlying to the database and publishes an event for the listening client communicator.
	 * 
	 * @param ric 							the RIC of the underlying to be saved.
	 * @param description					the description of the underlying to be saved.
	 * @param savedBy						the user who is saving the underlying.
	 * @returns	true if the save was successful; false otherwise.
	 * @throws IllegalArgumentException 	if the RIC or description or savedBy parameter is an empty string.
	 */
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

	/**
	 * Updates the validity of the underlying in the database.
	 * 
	 * @param ric 							the RIC of the underlying to be updated.
	 * @param isValid						the validity of the underlying to be updated.
	 * @returns	true if the update was successful; false otherwise.
	 * @throws IllegalArgumentException 	if the underlying's RIC parameter is an empty string.
	 */
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

	/**
	 * Sets the application event publisher.
	 * 
	 * @param applicationEventPublisher 	the applicationEventPublisher for publishing events.
	 * @throws NullPointerException 		if the applicationEventPublisher parameter is null.
	 */
	@Override
	@WebMethod
	public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher)
	{
		if(applicationEventPublisher == null)
			throw new NullPointerException("applicationEventPublisher");

		this.applicationEventPublisher = applicationEventPublisher;
	}

	/**
	 * Gets all underlyings previously saved to the database.
	 * @returns a list of underlyings that were previously saved in the database.
	 */
	@Override
	@WebMethod
	public List<UnderlyingDetailImpl> getAll()
	{
		if(logger.isDebugEnabled())
			logger.debug("Received request to get all previously saved underlyings.");

		return this.dao.getAll();
	}
}
