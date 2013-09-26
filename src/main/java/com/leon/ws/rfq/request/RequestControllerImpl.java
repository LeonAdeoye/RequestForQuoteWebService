package com.leon.ws.rfq.request;

import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService(serviceName="RequestController", endpointInterface="com.leon.ws.rfq.request.RequestController")
public class RequestControllerImpl implements RequestController
{
	public RequestControllerImpl() {}
	
	@WebMethod
	public boolean save(int identifier, String request, String bookCode)
	{
		return true;
	}
	
	@WebMethod
	public boolean update(int identifier, String bookCode)
	{
		return true;
	}
	
	@WebMethod
	public RequestDetailImpl getRequest(int identifier)
	{
		return null;
	}
	
	@WebMethod
	public RequestDetailListImpl getTodaysRequest()
	{
		return null;
	}
	
	@WebMethod
	public RequestDetailListImpl getRequestsWithCriteria()
	{
		return null;
	}
}
