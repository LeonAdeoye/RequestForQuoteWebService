package com.leon.ws.rfq.request;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import com.leon.ws.rfq.search.SearchCriteriaImpl;

@WebService(name="RequestController")
public interface RequestController
{
	@WebMethod
	int save(@WebParam(name="requestDetail") RequestDetailImpl requestDetail);
	
	@WebMethod
	boolean update(@WebParam(name="requestDetail") RequestDetailImpl requestDetail);	
	
	@WebMethod
	RequestDetailImpl getRequest(@WebParam(name="identifier") int identifier, @WebParam(name="rePrice") boolean rePrice);
	
	@WebMethod
	RequestDetailListImpl getRequestsForToday(@WebParam(name="rePrice") boolean rePrice);
	
	@WebMethod
	RequestDetailListImpl getRequestsMatchingAdhocCriteria(@WebParam(name="rePrice") SearchCriteriaImpl criteria, 
			@WebParam(name="rePrice") boolean rePrice);
	
	@WebMethod
	RequestDetailListImpl getRequestsMatchingExistingCriteria(@WebParam(name="criteriaOwner") String criteriaOwner, 
			@WebParam(name="criteriaKey") String criteriaKey, @WebParam(name="rePrice") boolean rePrice);	
}
