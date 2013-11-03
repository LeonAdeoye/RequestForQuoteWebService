package com.leon.ws.rfq.search;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebService;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;

import com.leon.ws.rfq.events.NewSearchCriterionEvent;

@WebService(serviceName="SearchController", endpointInterface="com.leon.ws.rfq.search.SearchController")
public final class SearchControllerImpl implements SearchController, ApplicationEventPublisherAware
{
	private SearchManagerDao dao;
	private ApplicationEventPublisher applicationEventPublisher;

	public void setSearchManagerDao(SearchManagerDao dao)
	{
		if(dao == null)
			throw new NullPointerException("dao");

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

		SearchCriterionImpl newlySavedCriterion = this.dao.save(owner, key, controlName, controlValue, isPrivate, isFilter);

		if(newlySavedCriterion != null)
		{
			this.applicationEventPublisher.publishEvent(new NewSearchCriterionEvent(this, newlySavedCriterion));
			return true;
		}

		return false;
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

	@Override
	public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher)
	{
		if(applicationEventPublisher == null)
			throw new NullPointerException("applicationEventPublisher");

		this.applicationEventPublisher = applicationEventPublisher;
	}
}
