package com.leon.ws.rfq.reporting;

import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jws.WebMethod;
import javax.jws.WebService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.leon.ws.rfq.option.model.ExtrapolationPoints;
import com.leon.ws.rfq.option.model.OptionPricingModel;
import com.leon.ws.rfq.request.OptionDetailImpl;
import com.leon.ws.rfq.request.RequestDetailImpl;
import com.leon.ws.rfq.request.RequestManagerDao;

@WebService(serviceName="ReportingController", endpointInterface="com.leon.ws.rfq.reporting.ReportingController")
public class ReportingControllerImpl implements ReportingController
{
	private final static Logger logger = LoggerFactory.getLogger(ReportingControllerImpl.class);
	private ReportingManagerDao reportingManagerDao;
	private RequestManagerDao requestManagerDao;
	private OptionPricingModel model;

	/**
	 * Default constructor
	 */
	public ReportingControllerImpl() {}

	/**
	 * Sets the reporting DAO property
	 * @throws NullPointerException			if reportingManagerDao parameter reference is null.
	 */
	public void setReportingManagerDao(ReportingManagerDao reportingManagerDao)
	{
		if(reportingManagerDao == null)
			throw new NullPointerException("reportingManagerDao");

		this.reportingManagerDao = reportingManagerDao;
	}

	/**
	 * Sets the model property
	 * @throws NullPointerException			if model parameter reference is null.
	 */
	public void setOptionPricer(OptionPricingModel model)
	{
		if(model == null)
			throw new NullPointerException("model");
		
		this.model = model;
	}
	
	/**
	 * Sets the request DAO property
	 * @throws NullPointerException			if requestManagerDao parameter reference is null.
	 */
	public void setRequestManagerDao(RequestManagerDao requestManagerDao)
	{
		if(requestManagerDao == null)
			throw new NullPointerException("requestManagerDao");
		
		this.requestManagerDao = requestManagerDao;
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

		return this.reportingManagerDao.getRequestsByCategory(categoryType, fromDate, minimumCount);
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

		return this.reportingManagerDao.getGreeksByCategory(categoryType, maturityDateFrom, maturityDateTo, minimumGreek);
	}
	
	/**
	 * Returns a list of greek totals for each category type value.
	 *
	 * @param rangeVariable					the range variable used for the extrapolation.
	 * @param requestId						the requestId of the RFQ to be priced.
	 * @param rangeMinimum					the minimum range value to be used for the extrapolation.
	 * @param rangeMaximum					the maximum range value to be used for the extrapolation.
	 * @param rangeIncrement				the increment of the range variable used for the extrapolation.
	 * @throws IllegalArgumentException		if the categoryType is empty or the minimumGreek is < 0.0.
	 * @throws NullPointerException			if maturityDateTo is null or maturityDateFrom is null.
	 */
	@Override
	@WebMethod
	public ExtrapolationPoints getGreeksExtrapolation(int requestId, String rangeVariable,
			double rangeMinimum, double rangeMaximum, double rangeIncrement)
	{
		if(requestId <= 0)
			throw new IllegalArgumentException("requestId");
		
		if(rangeVariable.isEmpty())
			throw new IllegalArgumentException("rangeVariable");

		if(rangeMinimum < 0.0)
			throw new IllegalArgumentException("rangeMinimum");

		if(rangeMaximum < 0.0)
			throw new IllegalArgumentException("rangeMaximum");
			
		if(rangeIncrement <= 0.0)
			throw new IllegalArgumentException("rangeIncrement");
		
		
		if(logger.isDebugEnabled())
			logger.debug("Received request for a greek extrapolation report for request id: " + requestId +
					", using range variable: " + rangeVariable +
					", with inclusive minimum range value: " + rangeMinimum +
					", with inclusive maximum range value: " + rangeMaximum +
					", and with range increment: " + rangeIncrement);
		
		ExtrapolationPoints extrapolationPoints = new ExtrapolationPoints();
		
		try
		{
			RequestDetailImpl request = this.requestManagerDao.getRequest(requestId);
			
			if(request == null)
			{
				if(logger.isErrorEnabled())
					logger.error("Failed to retrieve request details for RFQ with identifier: " + requestId);
				
				return extrapolationPoints;
			}
							
			Map<String, Double> input = new HashMap<>();
			 
			
			for(OptionDetailImpl leg : request.getLegs())
			{
				input.put(OptionPricingModel.VOLATILITY, leg.getVolatility());
				input.put(OptionPricingModel.UNDERLYING_PRICE, leg.getUnderlyingPrice());
				input.put(OptionPricingModel.STRIKE, leg.getStrike());
				input.put(OptionPricingModel.TIME_TO_EXPIRY, leg.getYearsToExpiry());
				input.put(OptionPricingModel.INTEREST_RATE, leg.getInterestRate());
				
				extrapolationPoints.merge(this.model.calculateRange(input, rangeVariable,
						rangeMinimum, rangeMaximum, rangeIncrement));
				
				input.clear();
			}
		}
		catch(Exception e)
		{
			if(logger.isErrorEnabled())
				logger.error("Failed to complete range calculation. Exception thrown: " + e);
		}
		
		if(logger.isDebugEnabled())
			logger.debug(extrapolationPoints.toString());
		
		return extrapolationPoints;
	}
}
