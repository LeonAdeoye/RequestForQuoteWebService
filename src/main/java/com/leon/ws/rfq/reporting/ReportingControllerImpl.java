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
}
