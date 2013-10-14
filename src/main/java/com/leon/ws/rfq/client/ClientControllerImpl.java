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

	public void setClientManagerDao(ClientManagerDao dao)
	{
		this.dao = dao;
	}

	public ClientControllerImpl() {}

	@Override
	@WebMethod
	public boolean save(String name, int tier, String savedBy)
	{
		if(name.isEmpty())
			throw new IllegalArgumentException("name");

		if(savedBy.isEmpty())
			throw new IllegalArgumentException("savedBy");

		if(logger.isDebugEnabled())
			logger.debug("Received request from user [" + savedBy + "] to save client with name [" + name + "].");

		ClientDetailImpl newClient = this.dao.save(name, tier, savedBy);

		if(newClient != null)
			this.applicationEventPublisher.publishEvent(new NewClientEvent(this, new ClientDetailImpl(name, 1, 1, true)));

		return newClient != null;
	}

	@Override
	@WebMethod
	public boolean updateTier(int identifier, int tier, String updatedBy)
	{
		if(updatedBy.isEmpty())
			throw new IllegalArgumentException("updatedBy");

		return this.dao.updateTier(identifier, tier, updatedBy);
	}

	@Override
	@WebMethod
	public boolean updateValidity(int identifier, boolean isValid, String updatedBy)
	{
		if(updatedBy.isEmpty())
			throw new IllegalArgumentException("updatedBy");

		return this.dao.updateValidity(identifier, isValid, updatedBy);
	}

	@Override
	@WebMethod
	public List<ClientDetailImpl> getAll()
	{
		return this.dao.getAll();
	}

	@Override
	public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher)
	{
		this.applicationEventPublisher = applicationEventPublisher;
	}
}
