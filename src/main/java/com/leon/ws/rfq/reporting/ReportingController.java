package com.leon.ws.rfq.reporting;

import java.util.GregorianCalendar;

import javax.jws.WebParam;
import javax.jws.WebService;

@WebService(name="ReportingController")
public interface ReportingController
{
	RequestCountReportDataListImpl getRequestsByCategory(@WebParam(name="categoryType")String categoryType,
			@WebParam(name="fromDate") GregorianCalendar fromDate, @WebParam(name="minimumCount") int minimumCount);
}
