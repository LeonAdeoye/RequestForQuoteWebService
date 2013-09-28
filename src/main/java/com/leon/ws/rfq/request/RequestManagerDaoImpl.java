package com.leon.ws.rfq.request;

import com.leon.ws.rfq.database.GenericDatabaseCommandExecutor;
import com.leon.ws.rfq.search.SearchCriteriaImpl;

public class RequestManagerDaoImpl implements RequestManagerDao
{
	private static final String SAVE = "CALL request_SAVE (?, ?, ?)";
	private static final String UPDATE = "CALL request_UPDATE (?, ?)";
	private static final String GET = "CALL request_GET";
	private static final String SELECT_ALL = "CALL requests_SELECT_ALL";
	private GenericDatabaseCommandExecutor<RequestDetailImpl> databaseExecutor;
	
	RequestManagerDaoImpl()
	{
		
	}
	
	RequestManagerDaoImpl(GenericDatabaseCommandExecutor<RequestDetailImpl> databaseExecutor)
	{
		this.databaseExecutor = databaseExecutor;
	}
	
	public void setDatabaseCommandExecutor(GenericDatabaseCommandExecutor<RequestDetailImpl> databaseExecutor)
	{
		this.databaseExecutor = databaseExecutor;
	}	

	@Override
	public int save(RequestDetailImpl request)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean update(RequestDetailImpl request)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public RequestDetailImpl getRequest(int identifier)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RequestDetailListImpl getRequestsForToday()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RequestDetailListImpl getRequestsMatchingAdhocCriteria(SearchCriteriaImpl criteria)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RequestDetailListImpl getRequestsMatchingExistingCriteria(String criteriaOwner, String criteriaKey)
	{
		// TODO Auto-generated method stub
		return null;
	}

}
