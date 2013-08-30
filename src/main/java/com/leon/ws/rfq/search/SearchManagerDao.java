package com.leon.ws.rfq.search;

import java.util.List;

public interface SearchManagerDao 
{
	public boolean delete(String owner, String key);
	public boolean updatePrivacy(String owner, String key, Boolean isPrivate);
	public boolean save(String owner, String key, String controlName, String controlValue, Boolean isPrivate, Boolean isFilter);
	
	public List<SearchCriterion> getAll();
	public List<SearchCriterion> get(String owner, String key);
}