package com.leon.ws.rfq.events;

import org.springframework.context.ApplicationEvent;

import com.google.gson.Gson;
import com.leon.ws.rfq.search.SearchCriterionImpl;

@SuppressWarnings("serial")
public final class NewSearchCriterionEvent extends ApplicationEvent implements JsonSerializableEvent
{
	private final SearchCriterionImpl newCriterion;
	private static final String NEW_CRITERION_UPDATE = "NewCriterionUpdate";

	public NewSearchCriterionEvent(Object source, SearchCriterionImpl newCriterion)
	{
		super(source);
		this.newCriterion = newCriterion;
	}

	@Override
	public String toString()
	{
		return "New criterion event: " + this.newCriterion;
	}

	public SearchCriterionImpl getNewCriterion()
	{
		return this.newCriterion;
	}

	@Override
	public String getJson()
	{
		return new Gson().toJson(this.newCriterion);
	}

	@Override
	public String getMessageType()
	{
		return NEW_CRITERION_UPDATE;
	}
}
