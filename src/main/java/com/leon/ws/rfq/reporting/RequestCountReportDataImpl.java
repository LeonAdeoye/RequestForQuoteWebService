package com.leon.ws.rfq.reporting;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RequestCountReportDataImpl
{
	private static final Logger logger = LoggerFactory.getLogger(RequestCountReportDataImpl.class);
	private String categoryValue;
	private int requestCount;

	public RequestCountReportDataImpl(String categoryValue, int requestCount)
	{
		this.categoryValue = categoryValue;
		this.requestCount = requestCount;

		logger.debug("RequestCountReportDataImpl object instantiated = > " +  this);
	}

	public RequestCountReportDataImpl() {}

	public String getCategoryValue()
	{
		return this.categoryValue;
	}

	public void setCategoryValue(String categoryValue)
	{
		this.categoryValue = categoryValue;
	}

	public int getRequestCount()
	{
		return this.requestCount;
	}

	public void setRequestCount(int requestCount)
	{
		this.requestCount = requestCount;
	}

	@Override
	public String toString()
	{
		StringBuilder buf = new StringBuilder();
		buf.append("Category value: ");
		buf.append(this.categoryValue);
		buf.append(", Request count: ");
		buf.append(this.requestCount);
		return buf.toString();
	}

	@Override
	public boolean equals(Object o)
	{
		if(this == o)
			return true;

		if(!(o instanceof RequestCountReportDataImpl))
			return false;

		RequestCountReportDataImpl param = (RequestCountReportDataImpl) o;

		return 	this.categoryValue.equals(param.categoryValue) &&
				(this.requestCount == param.requestCount);
	}

	@Override
	public int hashCode()
	{
		int result = 17;
		result = (37 * result) + (this.categoryValue == null ? 0 : this.categoryValue.hashCode());
		result = (37 * result) + this.requestCount;
		return result;
	}
}
