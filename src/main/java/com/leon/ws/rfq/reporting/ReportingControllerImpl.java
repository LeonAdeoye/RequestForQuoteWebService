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
	 * @throws IllegalArgumentException		if the categoryType is empty or the minimumGreek is < 0.0.
	 * @throws NullPointerException			if maturityDateTo is null or maturityDateFrom is null.
	 */
	@Override
	@WebMethod
	public List<GreeksPerCategoryReportDataImpl> getGreeksByCategory(String categoryType,
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

		if(logger.isDebugEnabled())
			logger.debug("Received report request for greeks for category: " + categoryType +
					", within maturity date from: " + maturityDateFrom + " to: " + maturityDateTo +
					", and with minimum greek value to be excluded: " + minimumGreek);

		return this.dao.getGreeksByCategory(categoryType, maturityDateFrom, maturityDateTo, minimumGreek);
	}
	
	/**
	 * Returns a list of greek totals for each category type value.
	 *
	 * @param inputType						the input type for each count: UnderlyingPrice, InterestRate, Volatility, etc.
	 * @param maturityDateFrom				the maturity date from which the RFQs will be included in the report.
	 * @param maturityDateTo				the maturity date up until which the RFQs will be included in the report.
	 * @param minimumInput					the minimum input value to be used for the interpolation.
	 * @param maximumInput					the maximum input value to be used for the interpolation.
	 * @throws IllegalArgumentException		if the categoryType is empty or the minimumGreek is < 0.0.
	 * @throws NullPointerException			if maturityDateTo is null or maturityDateFrom is null.
	 */
	@Override
	@WebMethod
	public List<GreeksPerInputReportDataImpl> getGreeksByInput(String inputType, GregorianCalendar maturityDateFrom,
			GregorianCalendar maturityDateTo, double minimumInput, double maximumInput)
	{
		if(inputType.isEmpty())
			throw new IllegalArgumentException("inputType");

		if(minimumInput < 0.0)
			throw new IllegalArgumentException("minimumInput");

		if(maximumInput < 0.0)
			throw new IllegalArgumentException("maximumInput");
			
		if(maturityDateTo == null)
			throw new NullPointerException("maturityDateTo");

		if(maturityDateFrom == null)
			throw new NullPointerException("maturityDateFrom");

		if(logger.isDebugEnabled())
			logger.debug("Received report request for greeks by input type: " + inputType +
					", within maturity date from: " + maturityDateFrom + " to: " + maturityDateTo +
					", and with minimum input value to be excluded: " + minimumInput +
					", and with maximum input value to be excluded: " + maximumInput);

		return this.dao.getGreeksByInput(inputType, maturityDateFrom, maturityDateTo, minimumInput, maximumInput);
	}
}
