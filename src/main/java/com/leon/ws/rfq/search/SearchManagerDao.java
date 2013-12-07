package com.leon.ws.rfq.search;

import java.util.List;

public interface SearchManagerDao
{
	boolean delete(String owner, String key);
	boolean updatePrivacy(String owner, String key, Boolean isPrivate);

	SearchCriterionImpl save(String owner, String key, String controlName, String controlValue,
			Boolean isPrivate, Boolean isFilter);

	List<SearchCriterionImpl> getAll();
	List<SearchCriterionImpl> get(String owner, String key);
}