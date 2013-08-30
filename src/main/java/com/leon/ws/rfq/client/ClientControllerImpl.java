package com.leon.ws.rfq.client;

import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.Oneway;


@WebService(serviceName="ClientController", endpointInterface="com.leon.ws.rfq.client.ClientController")
public class ClientControllerImpl implements ClientController
{
	private ClientManagerDao dao;
	
	public void setClientManagerDao(ClientManagerDao dao)
	{
		this.dao = dao;
	}
	
	public ClientControllerImpl() {}
	
	@WebMethod
	@Oneway
	public void delete(int identifier)
	{
		dao.delete(identifier);
	}
	
	@WebMethod
	@Oneway
	public void save(String name, int tier)
	{
		dao.save(name, tier);
	}
	
	@WebMethod
	@Oneway
	public void updateTier(int identifier, int tier)
	{
		dao.updateTier(identifier, tier);
	}
	
	@WebMethod
	@Oneway
	public void updateValidity(int identifier, boolean isValid)
	{
		dao.updateValidity(identifier, isValid);
	}
	
	@WebMethod
	public List<ClientDetail> getAll()
	{
        return dao.getAll();        
	}
}
