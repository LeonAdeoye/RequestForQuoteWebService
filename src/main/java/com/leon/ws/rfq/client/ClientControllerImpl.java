package com.leon.ws.rfq.client;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;

import com.leon.ws.rfq.events.NewClientEvent;

@WebService(serviceName="ClientController", endpointInterface="com.leon.ws.rfq.client.ClientController")
public final class ClientControllerImpl implements ClientController, ApplicationEventPublisherAware
{
	private static Logger logger = LoggerFactory.getLogger(ClientControllerImpl.class);
	private ApplicationEventPublisher applicationEventPublisher;
	private ClientManagerDao dao;

	/**
	 * Sets the DAO property.
	 * 
	 * @param dao		the ClientManagerDao object.
	 * @throws NullPointerException if the dao reference parameter is null.
	 */
	@Override
	@WebMethod(exclude = true)
	public void setClientManagerDao(ClientManagerDao dao)
	{
		if(dao == null)
			throw new NullPointerException("dao");

		this.dao = dao;
	}

	/**
	 * Default constructor
	 */
	public ClientControllerImpl() {}

	/**
	 * Saves the details of a new client with the specified name and tier, assumes the validity is true.
	 * 
	 * @param name							the name of the client to be saved.
	 * @param tier							the new tier setting of the client.
	 * @param savedBy						the user initiating the save.
	 * @throws IllegalArgumentException 	if the name, tier, or savedBy parameter is empty.
	 * @returns true if update was successful; otherwise false.
	 */
	@Override
	@WebMethod
	public boolean save(String name, String tier, String savedBy)
	{
		if(name.isEmpty())
			throw new IllegalArgumentException("name");

		if(savedBy.isEmpty())
			throw new IllegalArgumentException("savedBy");

		if(tier.isEmpty())
			throw new IllegalArgumentException("tier");

		if(logger.isDebugEnabled())
			logger.debug("Received request from user [" + savedBy + "] to save client with name [" + name + "].");

		ClientDetailImpl newClient = this.dao.save(name, tier, savedBy);

		if(newClient != null)
			this.applicationEventPublisher.publishEvent(new NewClientEvent(this, newClient));

		return newClient != null;
	}

	/**
	 * Updates the tier of client with the specified identifier.
	 * 
	 * @param identifier					the identifier of the client whose validity will be updated.
	 * @param tier							the new tier setting of the client.
	 * @param updatedBy						the user initiating the update.
	 * @throws IllegalArgumentException 	if the identifier is less than or equal to zero of the updatedBy parameter is empty.
	 * @returns true if update was successful; otherwise false.
	 */
	@Override
	@WebMethod
	public boolean updateTier(int identifier, String tier, String updatedBy)
	{
		if(identifier <= 0)
			throw new IllegalArgumentException("identifier");

		if(updatedBy.isEmpty())
			throw new IllegalArgumentException("updatedBy");

		if(tier.isEmpty())
			throw new IllegalArgumentException("tier");

		if(logger.isDebugEnabled())
			logger.debug("Received request from user [" + updatedBy + "] to update the tier of the client with identifier [" + identifier + "].");

		return this.dao.updateTier(identifier, tier, updatedBy);
	}

	/**
	 * Updates the validity of client with the specified identifier.
	 * 
	 * @param identifier					the identifier of the client whose validity will be updated.
	 * @param isValid						the new validity setting of the client.
	 * @param updatedBy						the user initiating the update.
	 * @throws IllegalArgumentException 	if the identifier is less than or equal to zero of the updatedBy parameter is empty.
	 * @returns true if update was successful; otherwise false.
	 */
	@Override
	@WebMethod
	public boolean updateValidity(int identifier, boolean isValid, String updatedBy)
	{
		if(identifier <= 0)
			throw new IllegalArgumentException("identifier");

		if(updatedBy.isEmpty())
			throw new IllegalArgumentException("updatedBy");

		if(logger.isDebugEnabled())
			logger.debug("Received request from user [" + updatedBy + "] to update the validity of the client with identifier [" + identifier + "].");

		return this.dao.updateValidity(identifier, isValid, updatedBy);
	}

	/**
	 * Retrieves the complete list of all clients.
	 * 
	 * @returns a list of client detail items.
	 */
	@Override
	@WebMethod
	public List<ClientDetailImpl> getAll()
	{
		if(logger.isDebugEnabled())
			logger.debug("Received request to get all previously saved underlyings.");

		return this.dao.getAll();
	}
	/**
	 * Sets the event publisher property.
	 * 
	 * @param applicationEventPublisher		the event publisher property.
	 * @throws NullPointerException			if the applicationEventPublisher reference parameter is null.
	 */
	@Override
	public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher)
	{
		if(applicationEventPublisher == null)
			throw new NullPointerException("applicationEventPublisher");

		this.applicationEventPublisher = applicationEventPublisher;
	}
}
