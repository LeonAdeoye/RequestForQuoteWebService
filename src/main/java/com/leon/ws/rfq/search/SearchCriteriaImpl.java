package com.leon.ws.rfq.search;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="SearchCriteriaImpl", namespace = "com.leon.ws.rfq.search")
public final class SearchCriteriaImpl
{
	@XmlElementWrapper(name = "SearchCriteriaImpl")
	@XmlElement(name = "SearchCriterionImpl")

	private ArrayList<SearchCriterionImpl> searchCriteriaArrayList;

	public SearchCriteriaImpl(ArrayList<SearchCriterionImpl> criteria)
	{
		this.searchCriteriaArrayList = criteria;
	}

	public SearchCriteriaImpl() {}

	public void setCriteria(ArrayList<SearchCriterionImpl> criteria)
	{
		this.searchCriteriaArrayList = criteria;
	}

	public ArrayList<SearchCriterionImpl> getCriteria()
	{
		return this.searchCriteriaArrayList;
	}

	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		for(SearchCriterionImpl criterion : this.searchCriteriaArrayList)
		{
			builder.append("Criterion: { ");
			builder.append(criterion);
			builder.append(" }");
		}
		return builder.toString();
	}
}
