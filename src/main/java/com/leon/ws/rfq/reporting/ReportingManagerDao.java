package com.leon.ws.rfq.reporting;

import java.util.Calendar;
import java.util.List;

public interface ReportingManagerDao
{
	List<RequestCountReportDataImpl> getRequestsByCategory(String categoryType, Calendar fromDate, int minimumCount);

	List<GreeksPerCategoryReportDataImpl> getGreeksByCategory(String categoryType,
			Calendar maturityDateFrom, Calendar maturityDateTo, double minimumGreek);
}
