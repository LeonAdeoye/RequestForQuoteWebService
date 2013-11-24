package com.leon.ws.rfq.reporting;

import java.util.GregorianCalendar;
import java.util.List;

import javax.jws.WebParam;
import javax.jws.WebService;

@WebService(name="ReportingController")
public interface ReportingController
{
	List<RequestCountReportDataImpl> getRequestsByCategory(@WebParam(name="categoryType")String categoryType,
			@WebParam(name="fromDate") GregorianCalendar fromDate,
			@WebParam(name="minimumCount") int minimumCount);

	List<GreeksPerCategoryReportDataImpl> getGreeksByCategory(@WebParam(name="categoryType")String categoryType,
			@WebParam(name="greeksToBeIncluded")List<String> greeksToBeIncluded,
			@WebParam(name="MaturityDatefrom")GregorianCalendar maturityDateFrom,
			@WebParam(name="MaturityDateTo")GregorianCalendar maturityDateTo,
			@WebParam(name="minimumGreek")double minimumGreek);
}
