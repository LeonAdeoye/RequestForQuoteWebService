package com.leon.ws.rfq.client;

import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

@WebService(name="ClientController")
public interface ClientController 
{
	@WebMethod
	boolean delete(@WebParam(name="identifier") int identifier);
	
	@WebMethod
	boolean save(@WebParam(name="name")String name, 
					@WebParam(name="tier")int tier);
	
	@WebMethod
	boolean updateTier(@WebParam(name="identifier") int identifier,
							@WebParam(name="tier")int tier);
	
	@WebMethod
	boolean updateValidity(@WebParam(name="identifier") int identifier,
							@WebParam(name="isValid")boolean isValid);	
	
	@WebMethod
	List<ClientDetailImpl> getAll();
}
