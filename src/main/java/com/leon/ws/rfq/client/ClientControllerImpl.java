package com.leon.ws.rfq.client;

import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService(serviceName="ClientController", endpointInterface="com.leon.ws.rfq.client.ClientController")
public final class ClientControllerImpl implements ClientController
{
	private ClientManagerDao dao;
	
	public void setClientManagerDao(ClientManagerDao dao)
	{
		this.dao = dao;
	}
	
	public ClientControllerImpl() {}
	
	@WebMethod
	public boolean delete(int identifier)
	{
		return dao.delete(identifier);
	}
	
	@WebMethod
	public boolean save(String name, int tier)
	{
		return dao.save(name, tier);
	}
	
	@WebMethod
	public boolean updateTier(int identifier, int tier)
	{
		return dao.updateTier(identifier, tier);
	}
	
	@WebMethod
	public boolean updateValidity(int identifier, boolean isValid)
	{
		return dao.updateValidity(identifier, isValid);
	}
	
	@WebMethod
	public List<ClientDetailImpl> getAll()
	{
        return dao.getAll();        
	}
}
