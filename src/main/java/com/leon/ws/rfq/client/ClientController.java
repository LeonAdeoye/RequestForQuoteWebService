package com.leon.ws.rfq.client;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

@WebService(name="ClientController")
public interface ClientController
{
	@WebMethod
	boolean save(@WebParam(name="name")String name,
			@WebParam(name="tier")String tier,
			@WebParam(name="savedBy")String savedBy);

	@WebMethod
	boolean updateTier(@WebParam(name="identifier") int identifier,
			@WebParam(name="tier")String tier,
			@WebParam(name="updatedBy")String updatedBy);

	@WebMethod
	boolean updateValidity(@WebParam(name="identifier") int identifier,
			@WebParam(name="isValid")boolean isValid,
			@WebParam(name="updatedBy")String updatedBy);

	@WebMethod
	List<ClientDetailImpl> getAll();
}
