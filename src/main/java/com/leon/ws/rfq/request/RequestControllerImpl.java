package com.leon.ws.rfq.request;

import javax.jws.WebMethod;
import javax.jws.WebService;

import com.leon.ws.rfq.search.SearchCriteriaImpl;

@WebService(serviceName="RequestController", endpointInterface="com.leon.ws.rfq.request.RequestController")
public class RequestControllerImpl implements RequestController
{
	public RequestControllerImpl() {}

	@WebMethod
	public boolean save(RequestDetailImpl requestDetail)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@WebMethod
	public boolean update(RequestDetailImpl requestDetail)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@WebMethod
	public RequestDetailImpl getRequest(int identifier, boolean rePrice)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@WebMethod
	public RequestDetailListImpl getRequestsForToday(boolean rePrice)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@WebMethod
	public RequestDetailListImpl getRequestsMatchingAdhocCriteria(
			SearchCriteriaImpl criteria, boolean rePrice)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@WebMethod
	public RequestDetailListImpl getRequestsMatchingExistingCriteria(
			String criteriaOwner, String criteriaKey, boolean rePrice)
	{
		// TODO Auto-generated method stub
		return null;
	}
}
