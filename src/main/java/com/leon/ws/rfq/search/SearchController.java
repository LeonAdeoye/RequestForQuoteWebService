package com.leon.ws.rfq.search;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

@WebService(name="SearchController")
public interface SearchController 
{
	@WebMethod
	boolean delete(@WebParam(name="owner") String owner, @WebParam(name="key") String key);

	@WebMethod
	boolean updatePrivacy(@WebParam(name="owner") String owner, @WebParam(name="key") String key, 
			@WebParam(name="isPrivate") Boolean isPrivate);	
	
	@WebMethod
	boolean save(@WebParam(name="owner") String owner, @WebParam(name="key")String key, 
			@WebParam(name="controlName") String controlName, @WebParam(name="controlValue") String controlValue, 
			@WebParam(name="isPrivate") Boolean isPrivate, @WebParam(name="isFilter") Boolean isFilter);
	
	@WebMethod
	List<SearchCriterion> getAll();

	@WebMethod
	List<SearchCriterion> get(@WebParam(name="owner") String owner, @WebParam(name="key")String key);	
}
