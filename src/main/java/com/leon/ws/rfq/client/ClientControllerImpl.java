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

	@Override
	@WebMethod(exclude = true)
	public void setClientManagerDao(ClientManagerDao dao)
	{
		if(dao == null)
			throw new NullPointerException("dao");

		this.dao = dao;
	}

	public ClientControllerImpl() {}

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

	@Override
	@WebMethod
	public List<ClientDetailImpl> getAll()
	{
		if(logger.isDebugEnabled())
			logger.debug("Received request to get all previously saved underlyings.");

		return this.dao.getAll();
	}

	@Override
	public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher)
	{
		if(applicationEventPublisher == null)
			throw new NullPointerException("applicationEventPublisher");

		this.applicationEventPublisher = applicationEventPublisher;
	}
}
