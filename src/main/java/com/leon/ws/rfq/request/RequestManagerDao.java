package com.leon.ws.rfq.request;

import com.leon.ws.rfq.search.SearchCriteriaImpl;

public interface RequestManagerDao
{
	RequestDetailImpl save(RequestDetailImpl request, String savedByUser);
	boolean update(RequestDetailImpl request, String updatedByUser);
	RequestDetailImpl getRequest(int identifier);
	RequestDetailListImpl getRequestsForToday();
	RequestDetailListImpl getRequestsMatchingAdhocCriteria(SearchCriteriaImpl criteria);
	RequestDetailListImpl getRequestsMatchingExistingCriteria(String criteriaOwner, String criteriaKey);
}
