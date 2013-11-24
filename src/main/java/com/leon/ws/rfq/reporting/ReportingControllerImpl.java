package com.leon.ws.rfq.reporting;

import java.util.GregorianCalendar;
import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebService(serviceName="ReportingController", endpointInterface="com.leon.ws.rfq.reporting.ReportingController")
public class ReportingControllerImpl implements ReportingController
{
	private final static Logger logger = LoggerFactory.getLogger(ReportingControllerImpl.class);
	private ReportingManagerDao dao;

	/**
	 * Default constructor
	 */
	public ReportingControllerImpl() {}

	/**
	 * Default constructor.
	 * @throws NullPointerException			if ReportingManagerDao is null.
	 */
	public void setReportingManagerDao(ReportingManagerDao dao)
	{
		if(dao == null)
			throw new NullPointerException("dao");

		this.dao = dao;
	}

	/**
	 * Returns a list of RFQ counts for each category type value.
	 *
	 * @param categoryType					the category type for each count: BookCode, TradeDate, Client, Status, etc.
	 * @param fromDate						the trade date of the RFQs to be included in the report.
	 * @throws IllegalArgumentException		if the categoryType is empty or the minimumCount is < 0.
	 * @throws NullPointerException			if GregorianCalendar fromDate is null.
	 */
	@Override
	@WebMethod
	public List<RequestCountReportDataImpl> getRequestsByCategory(String categoryType, GregorianCalendar fromDate, int minimumCount)
	{
		if(categoryType.isEmpty())
			throw new IllegalArgumentException("categoryType");

		if(minimumCount < 0)
			throw new IllegalArgumentException("minimumCount");

		if(fromDate == null)
			throw new NullPointerException("fromDate");

		if(logger.isDebugEnabled())
			logger.debug("Received reporting data request for category: " + categoryType +
					", from date: " + fromDate + " , and with minimum count: " + minimumCount);

		return this.dao.getRequestsByCategory(categoryType, fromDate, minimumCount);
	}

	/**
	 * Returns a list of greek totals for each category type value.
	 *
	 * @param categoryType					the category type for each count: BookCode, TradeDate, Client, Status, etc.
	 * @param maturityDateFrom				the maturity date from which the RFQs will be included in the report.
	 * @param maturityDateTo				the maturity date up until which the RFQs will be included in the report.
	 * @throws IllegalArgumentException		if the categoryType is empty or the minimumGreek is < 0.0 or greeksToBeIncluded is empty.
	 * @throws NullPointerException			if maturityDateTo is null or maturityDateFrom is null or if greeksToBeIncluded is null.
	 */
	@Override
	@WebMethod
	public List<GreeksPerCategoryReportDataImpl> getGreeksByCategory(String categoryType, List<String> greeksToBeIncluded,
			GregorianCalendar maturityDateFrom, GregorianCalendar maturityDateTo, double minimumGreek)
	{
		if(categoryType.isEmpty())
			throw new IllegalArgumentException("categoryType");

		if(minimumGreek < 0.0)
			throw new IllegalArgumentException("minimumGreek");

		if(maturityDateTo == null)
			throw new NullPointerException("maturityDateTo");

		if(maturityDateFrom == null)
			throw new NullPointerException("maturityDateFrom");

		if(greeksToBeIncluded == null)
			throw new NullPointerException("greeksToBeIncluded");

		if(greeksToBeIncluded.size() == 0)
			throw new IllegalArgumentException("greeksToBeIncluded");

		if(logger.isDebugEnabled())
			logger.debug("Received report request for greeks for category: " + categoryType +
					", within maturity date from: " + maturityDateFrom + " to: " + maturityDateTo +
					", and with minimum greek value to be excluded: " + minimumGreek);

		return this.dao.getGreeksByCategory(categoryType, greeksToBeIncluded, maturityDateFrom, maturityDateTo, minimumGreek);
	}
}
