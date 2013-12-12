package com.leon.ws.rfq.reporting;

import java.util.GregorianCalendar;
import java.util.List;

import javax.jws.WebParam;
import javax.jws.WebService;

@WebService(name="ReportingController")
public interface ReportingController
{
	List<RequestCountReportDataImpl> getRequestsByCategory(
			@WebParam(name="categoryType")String categoryType,
			@WebParam(name="fromDate") GregorianCalendar fromDate,
			@WebParam(name="minimumCount") int minimumCount);

	List<GreeksPerCategoryReportDataImpl> getGreeksByCategory(
			@WebParam(name="categoryType")String categoryType,
			@WebParam(name="maturityDateFrom")GregorianCalendar maturityDateFrom,
			@WebParam(name="maturityDateTo")GregorianCalendar maturityDateTo,
			@WebParam(name="minimumGreek")double minimumGreek);
	
	List<GreeksPerInputReportDataImpl> getGreeksByInput(
			@WebParam(name="inputType")String inputType,
			@WebParam(name="requestId") int requestId,
			@WebParam(name="minimumInput") double minimumInput,
			@WebParam(name="maximumInput") double maximumInput,
			@WebParam(name="increment") double increment);
}
