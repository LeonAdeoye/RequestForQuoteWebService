package com.leon.ws.rfq.reporting;

import java.util.Calendar;

public interface ReportingManagerDao
{
	RequestCountReportDataListImpl getRequestsByCategory(String categoryType, Calendar fromDate, int minimumCount);
}
