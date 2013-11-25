package com.leon.ws.rfq.reporting;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.leon.ws.rfq.utilities.UtilityMethods;

public class GreeksPerCategoryReportDataImpl
{
	private static final Logger logger = LoggerFactory.getLogger(GreeksPerCategoryReportDataImpl.class);
	private String categoryValue;
	private double greekTotal;
	private String greekType;

	public GreeksPerCategoryReportDataImpl(String categoryValue, String greekType, double greekTotal)
	{
		this.categoryValue = categoryValue;
		this.greekTotal = greekTotal;
		this.greekType = greekType;

		logger.debug("GreeksPerCatgeoryReportDataImpl object instantiated = > " +  this);
	}

	public GreeksPerCategoryReportDataImpl() {}

	public String getCategoryValue()
	{
		return this.categoryValue;
	}

	public void setCategoryValue(String categoryValue)
	{
		this.categoryValue = categoryValue;
	}
	
	public String getGreekType()
	{
		return this.greekType;
	}

	public void setGreekType(String greekType)
	{
		this.greekType = greekType;
	}

	public double getGreekTotal()
	{
		return this.greekTotal;
	}

	public void setGreekTotal(double greekTotal)
	{
		this.greekTotal = greekTotal;
	}

	@Override
	public String toString()
	{
		StringBuilder buf = new StringBuilder();
		buf.append("Category value: ");
		buf.append(this.categoryValue);
		buf.append(", greek type: ");
		buf.append(this.greekType);
		buf.append(", greek total: ");
		buf.append(this.greekTotal);
		
		return buf.toString();
	}

	@Override
	public boolean equals(Object o)
	{
		if(this == o)
			return true;

		if(!(o instanceof GreeksPerCategoryReportDataImpl))
			return false;

		GreeksPerCategoryReportDataImpl param = (GreeksPerCategoryReportDataImpl) o;

		return 	this.categoryValue.equals(param.greekType) &&
				this.greekType.equals(param.greekType) &&
				(this.greekTotal == param.greekTotal);
	}

	@Override
	public int hashCode()
	{
		int result = 17;
		result = (37 * result) + (this.categoryValue == null ? 0 : this.categoryValue.hashCode());
		result = (37 * result) + (this.greekType == null ? 0 : this.greekType.hashCode());
		result = (37 * result) + UtilityMethods.doubleHashCode(this.greekTotal);
		return result;
	}
}
