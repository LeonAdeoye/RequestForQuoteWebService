package com.leon.ws.rfq.request;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

@WebService(name="RequestController")
public interface RequestController
{
	@WebMethod
	boolean save(@WebParam(name="identifier") int identifier,
			@WebParam(name="request") String request,
				@WebParam(name="bookCode")String bookCode);
	
	@WebMethod
	boolean update(@WebParam(name="identifier") int identifier,	@WebParam(name="bookCode") String bookCode);	
	
	@WebMethod
	RequestDetailImpl getRequest(@WebParam(name="identifier") int identifier);
	
	@WebMethod
	RequestDetailListImpl getTodaysRequest();
	
	@WebMethod
	RequestDetailListImpl getRequestsMatchingCriteria();	
}
