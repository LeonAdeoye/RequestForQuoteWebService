package com.leon.ws.rfq.search;

import javax.xml.bind.annotation.XmlRootElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@XmlRootElement(name="SearchCriterionImpl")
public final class SearchCriterionImpl 
{
	private static final Logger logger = LoggerFactory.getLogger(SearchCriterionImpl.class);
	private String controlName;
	private String controlValue;
	private String owner;
	private Boolean isPrivate;
	private Boolean isFilter;
	private String key;
	
	public SearchCriterionImpl(String owner, String key, String controlName, String controlValue, Boolean isPrivate, Boolean isFilter)
	{
		this.owner = owner;
		this.key = key;
		this.controlName = controlName;
		this.controlValue = controlValue;
		this.isPrivate = isPrivate;
		this.isFilter = isFilter;
		
		logger.debug("Criterion instantiated = > " +  this.toString());
	}
	
	public SearchCriterionImpl() {}	
	
	public String getOwner()
	{
		return this.owner;
	}
	
	public void setOwner(String owner)
	{
		this.owner = owner;
	}
	
	public String getKey()
	{
		return this.key;
	}
	
	public void setKey(String key)
	{
		this.key = key;
	}	
	
	public String getControlName()
	{
		return this.controlName;
	}
	
	public void setControlName(String controlName)
	{
		this.controlName = controlName;
	}	
	
	public String getControlValue()
	{
		return this.controlValue;
	}
	
	public void setControlValue(String controlValue)
	{
		this.controlValue = controlValue;
	}		
	
	public Boolean getIsPrivate()
	{
		return this.isPrivate;
	}
	
	public void setIsPrivate(Boolean isPrivate)
	{
		this.isPrivate = isPrivate;
	}
	
	public Boolean getIsFilter()
	{
		return this.isFilter;
	}
	
	public void setIsFilter(Boolean isFilter)
	{
		this.isFilter = isFilter;
	}	
	
	public String toString()
	{
		StringBuffer buf = new StringBuffer();
		buf.append("Owner: ");
		buf.append(this.owner);
		buf.append(", Key: ");
		buf.append(this.key);
		buf.append(", Criterion Key: ");
		buf.append(this.controlName);
		buf.append(", Criterion Value: ");
		buf.append(this.controlValue);
		buf.append(", IsPrivate: ");
		buf.append(this.isPrivate ? "TRUE" : "FALSE");
		buf.append(", IsFilter: ");
		buf.append(this.isFilter ? "TRUE" : "FALSE");		
		return buf.toString();
	}
	
	public boolean equals(Object o)
	{
		if(this == o)
			return true;
		
		if(!(o instanceof SearchCriterionImpl))
			return false;
		
		SearchCriterionImpl criterion = (SearchCriterionImpl) o;
		
		return this.owner.equals(criterion.owner) && this.key.equals(criterion.key) && this.controlName.equals(criterion.controlName) 
				&& this.controlValue.equals(criterion.controlValue) && this.isFilter == criterion.isFilter && this.isPrivate == criterion.isPrivate;
	}
}
