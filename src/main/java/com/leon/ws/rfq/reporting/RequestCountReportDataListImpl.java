package com.leon.ws.rfq.reporting;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="RequestCountReportDataListImpl", namespace = "com.leon.ws.rfq.reporting")
public final class RequestCountReportDataListImpl
{
	@XmlElementWrapper(name = "RequestCountReportDataListImpl")
	@XmlElement(name = "RequestCountReportDataImpl")

	private ArrayList<RequestCountReportDataImpl> requestCountReportDataArrayList;

	public RequestCountReportDataListImpl(ArrayList<RequestCountReportDataImpl> requestCountList)
	{
		this.requestCountReportDataArrayList = requestCountList;
	}

	public RequestCountReportDataListImpl() {}

	public void setRequestCountList(ArrayList<RequestCountReportDataImpl> requestCountList)
	{
		this.requestCountReportDataArrayList = requestCountList;
	}

	public ArrayList<RequestCountReportDataImpl> getRequestCountList()
	{
		return this.requestCountReportDataArrayList;
	}
}