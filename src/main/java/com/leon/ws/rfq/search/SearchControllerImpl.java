package com.leon.ws.rfq.search;

import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.Oneway;

@WebService(serviceName="SearchController", endpointInterface="com.leon.ws.rfq.search.SearchController")
public class SearchControllerImpl implements SearchController
{
	private SearchManagerDao dao;
	
	public void setSearchManagerDao(SearchManagerDao dao)
	{
		this.dao = dao;
	}
	
	public SearchControllerImpl() {}
	
	@WebMethod
	@Oneway
	public void delete(String owner, String key)
	{
		dao.delete(owner, key);
	}
	
	@WebMethod
	@Oneway
	public void updatePrivacy(String owner, String key, Boolean isPrivate)
	{
		dao.updatePrivacy(owner, key, isPrivate);
	}	
	
	@WebMethod
	@Oneway
	public void save(String owner, String key, String controlName, String controlValue, Boolean isPrivate, Boolean isFilter)
	{
		dao.save(owner, key, controlName, controlValue, isPrivate, isFilter);
	}
	
	@WebMethod
	public List<SearchCriterion> getAll()
	{
        return dao.getAll();        
	}
	
	@WebMethod
	public List<SearchCriterion> get(String owner, String key)
	{
		return dao.get(owner, key);
	}	
}
