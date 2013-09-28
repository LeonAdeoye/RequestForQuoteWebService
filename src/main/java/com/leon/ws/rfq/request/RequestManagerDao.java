package com.leon.ws.rfq.request;

import com.leon.ws.rfq.search.SearchCriteriaImpl;

public interface RequestManagerDao
{
	int save(RequestDetailImpl request);
	boolean update(RequestDetailImpl request);
	RequestDetailImpl getRequest(int identifier);
	RequestDetailListImpl getRequestsForToday();
	RequestDetailListImpl getRequestsMatchingAdhocCriteria(SearchCriteriaImpl criteria);
	RequestDetailListImpl getRequestsMatchingExistingCriteria(String criteriaOwner, String criteriaKey);
}
