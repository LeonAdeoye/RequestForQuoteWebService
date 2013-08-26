package com.leon.ws.rfq.client;

import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

@WebService(name="ClientController")
public interface ClientController 
{
	@WebMethod
	public void delete(@WebParam(name="identifier") int identifier);
	
	@WebMethod
	public void save(@WebParam(name="name")String name, 
					@WebParam(name="tier")int tier);
	
	@WebMethod
	public void updateTier(@WebParam(name="identifier") int identifier,
							@WebParam(name="tier")int tier);
	
	@WebMethod
	public void updateValidity(@WebParam(name="identifier") int identifier,
							@WebParam(name="isValid")boolean isValid);	
	
	@WebMethod
	public List<ClientDetail> getAll();
}
