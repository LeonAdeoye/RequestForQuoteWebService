package com.leon.ws.rfq.search;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService(serviceName="SearchController", endpointInterface="com.leon.ws.rfq.search.SearchController")
public final class SearchControllerImpl implements SearchController
{
	private SearchManagerDao dao;

	public void setSearchManagerDao(SearchManagerDao dao)
	{
		this.dao = dao;
	}

	public SearchControllerImpl() {}

	@Override
	@WebMethod
	public boolean delete(String owner, String key)
	{
		if(owner.isEmpty())
			throw new IllegalArgumentException("owner");

		if(key.isEmpty())
			throw new IllegalArgumentException("key");

		return this.dao.delete(owner, key);
	}

	@Override
	@WebMethod
	public boolean updatePrivacy(String owner, String key, Boolean isPrivate)
	{
		if(owner.isEmpty())
			throw new IllegalArgumentException("owner");

		if(key.isEmpty())
			throw new IllegalArgumentException("key");

		return this.dao.updatePrivacy(owner, key, isPrivate);
	}

	@Override
	@WebMethod
	public boolean save(String owner, String key, String controlName, String controlValue, Boolean isPrivate, Boolean isFilter)
	{
		if(owner.isEmpty())
			throw new IllegalArgumentException("owner");

		if(key.isEmpty())
			throw new IllegalArgumentException("key");

		if(controlName.isEmpty())
			throw new IllegalArgumentException("controlName");

		if(controlValue.isEmpty())
			throw new IllegalArgumentException("controlValue");

		return this.dao.save(owner, key, controlName, controlValue, isPrivate, isFilter);
	}

	@Override
	@WebMethod
	public List<SearchCriterionImpl> getAll()
	{
		return this.dao.getAll();
	}

	@Override
	@WebMethod
	public List<SearchCriterionImpl> get(String owner, String key)
	{
		return this.dao.get(owner, key);
	}
}
