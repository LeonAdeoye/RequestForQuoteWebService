package com.leon.ws.rfq.request;

import javax.jws.WebMethod;
import javax.jws.WebService;
import com.leon.ws.rfq.search.SearchCriteriaImpl;

@WebService(serviceName="RequestController", endpointInterface="com.leon.ws.rfq.request.RequestController")
public class RequestControllerImpl implements RequestController
{
	public RequestControllerImpl() {}
	
	private RequestManagerDao dao;
	
	public void setRequestManagerDao(RequestManagerDao dao)
	{
		this.dao = dao;
	}	

	@WebMethod
	public int save(RequestDetailImpl requestDetail)
	{
		return dao.save(requestDetail);
	}

	@WebMethod
	public boolean update(RequestDetailImpl requestDetail)
	{
		return dao.update(requestDetail);
	}

	@WebMethod
	public RequestDetailImpl getRequest(int identifier, boolean rePrice)
	{
		RequestDetailImpl request = dao.getRequest(identifier);
		
		if(rePrice)
		{
			
		}
		
		return request;
	}

	@WebMethod
	public RequestDetailListImpl getRequestsForToday(boolean rePrice)
	{
		RequestDetailListImpl requests = dao.getRequestsForToday();
		
		if(rePrice)
		{
			
		}
		
		return requests;
	}

	@WebMethod
	public RequestDetailListImpl getRequestsMatchingAdhocCriteria(SearchCriteriaImpl criteria, boolean rePrice)
	{
		RequestDetailListImpl requests = dao.getRequestsMatchingAdhocCriteria(criteria);
		
		if(rePrice)
		{
			
		}
		
		return requests;
	}

	@WebMethod
	public RequestDetailListImpl getRequestsMatchingExistingCriteria(String criteriaOwner, String criteriaKey, boolean rePrice)
	{
		RequestDetailListImpl requests = dao.getRequestsMatchingExistingCriteria(criteriaOwner, criteriaKey);
		
		if(rePrice)
		{
			
		}
		
		return requests;
	}
}
