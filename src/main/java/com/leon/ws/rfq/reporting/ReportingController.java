package com.leon.ws.rfq.reporting;

import java.util.GregorianCalendar;
import java.util.List;

import javax.jws.WebParam;
import javax.jws.WebService;

import com.leon.ws.rfq.option.model.ExtrapolationPoints;

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
	
	ExtrapolationPoints getGreeksExtrapolation(
			@WebParam(name="requestId") int requestId,
			@WebParam(name="rangeVariable") String rangeVariable,
			@WebParam(name="rangeMinimum") double rangeMinimum,
			@WebParam(name="rangeMaximum") double rangeMaximum,
			@WebParam(name="rangeIncrement") double rangeIncrement);
}
