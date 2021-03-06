package com.leon.ws.rfq.reporting;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.List;

import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import com.leon.ws.rfq.database.GenericDatabaseCommandExecutor;

public class ReportingManagerDaoImpl implements ReportingManagerDao
{
	private GenericDatabaseCommandExecutor databaseExecutor;

	private static final String BOOKCODE_CATEGORY = "BookCode";
	private static final String CLIENT_CATEGORY = "Client";
	private static final String UNDERLYING_CATEGORY = "Underlying";
	private static final String INITIATOR_CATEGORY = "Initiator";
	private static final String TRADEDATE_CATEGORY = "TradeDate";
	private static final String MATURITYDATE_CATEGORY = "MaturityDate";
	private static final String PICKER_CATEGORY = "Picker";
	private static final String STATUS_CATEGORY = "Status";
	
	private static final String REQUESTS_COUNT_BY_BOOKCODE_GET = "CALL requestsCountByBookCode(?, ?)";
	private static final String REQUESTS_COUNT_BY_CLIENT_GET = "CALL requestsCountByClient(?, ?)";
	private static final String REQUESTS_COUNT_BY_UNDERLYING_GET = "CALL requestsCountByUnderlying(?, ?)";
	private static final String REQUESTS_COUNT_BY_INITIATOR_GET = "CALL requestsCountByInitiator(?, ?)";
	private static final String REQUESTS_COUNT_BY_TRADEDATE_GET = "CALL requestsCountByTradeDate(?, ?)";
	private static final String REQUESTS_COUNT_BY_MATURITYDATE_GET = "CALL requestsCountByMaturityDate(?, ?)";
	private static final String REQUESTS_COUNT_BY_PICKER_GET = "CALL requestsCountByPicker(?, ?)";
	private static final String REQUESTS_COUNT_BY_STATUS_GET = "CALL requestsCountByStatus(?, ?)";

	private static final String GREEKS_BY_BOOKCODE_GET = "CALL greeksByBookCode(?, ?, ?)";
	private static final String GREEKS_BY_CLIENT_GET = "CALL greeksByClient(?, ?, ?)";
	private static final String GREEKS_BY_UNDERLYING_GET = "CALL greeksByUnderlying(?, ?, ?)";
	private static final String GREEKS_BY_INITIATOR_GET = "CALL greeksByInitiator(?, ?, ?)";
	private static final String GREEKS_BY_TRADEDATE_GET = "CALL greeksByTradeDate(?, ?, ?)";
	private static final String GREEKS_BY_MATURITYDATE_GET = "CALL greeksByMaturityDate(?, ?, ?)";
	private static final String GREEKS_BY_PICKER_GET = "CALL greeksByPicker(?, ?, ?)";
	private static final String GREEKS_BY_STATUS_GET = "CALL greeksByStatus(?, ?, ?)";

	private class RequestCountPerCatgeoryReportParameterizedRowMapper implements ParameterizedRowMapper<RequestCountReportDataImpl>
	{
		@Override
		public RequestCountReportDataImpl mapRow(ResultSet rs, int rowNum) throws SQLException
		{
			return new RequestCountReportDataImpl(rs.getString("categoryValue"), rs.getInt("requestCount"));
		}
	}
	
	private class GreeksPerCatgeoryReportParameterizedRowMapper implements ParameterizedRowMapper<GreeksPerCategoryReportDataImpl>
	{
		@Override
		public GreeksPerCategoryReportDataImpl mapRow(ResultSet rs, int rowNum) throws SQLException
		{
			return new GreeksPerCategoryReportDataImpl(rs.getString("categoryValue"),
					rs.getDouble("delta"), rs.getDouble("gamma"), rs.getDouble("vega"),
					rs.getDouble("theta"), rs.getDouble("rho"));
		}
	}

	private String getRequestCountPerCategoryPreparedStatement(String categoryType)
	{
		switch(categoryType)
		{
		case BOOKCODE_CATEGORY:
			return REQUESTS_COUNT_BY_BOOKCODE_GET;
		case CLIENT_CATEGORY:
			return REQUESTS_COUNT_BY_CLIENT_GET;
		case UNDERLYING_CATEGORY:
			return REQUESTS_COUNT_BY_UNDERLYING_GET;
		case INITIATOR_CATEGORY:
			return REQUESTS_COUNT_BY_INITIATOR_GET;
		case TRADEDATE_CATEGORY:
			return REQUESTS_COUNT_BY_TRADEDATE_GET;
		case MATURITYDATE_CATEGORY:
			return REQUESTS_COUNT_BY_MATURITYDATE_GET;
		case PICKER_CATEGORY:
			return REQUESTS_COUNT_BY_PICKER_GET;
		case STATUS_CATEGORY:
			return REQUESTS_COUNT_BY_STATUS_GET;

		}
		return null;
	}
	
	private String getGreeksPerCategoryPreparedStatement(String categoryType)
	{
		switch(categoryType)
		{
		case BOOKCODE_CATEGORY:
			return GREEKS_BY_BOOKCODE_GET;
		case CLIENT_CATEGORY:
			return GREEKS_BY_CLIENT_GET;
		case UNDERLYING_CATEGORY:
			return GREEKS_BY_UNDERLYING_GET;
		case INITIATOR_CATEGORY:
			return GREEKS_BY_INITIATOR_GET;
		case TRADEDATE_CATEGORY:
			return GREEKS_BY_TRADEDATE_GET;
		case MATURITYDATE_CATEGORY:
			return GREEKS_BY_MATURITYDATE_GET;
		case PICKER_CATEGORY:
			return GREEKS_BY_PICKER_GET;
		case STATUS_CATEGORY:
			return GREEKS_BY_STATUS_GET;

		}
		return null;
	}

	public ReportingManagerDaoImpl() {}

	ReportingManagerDaoImpl(GenericDatabaseCommandExecutor databaseExecutor)
	{
		this.databaseExecutor = databaseExecutor;
	}

	public void setDatabaseCommandExecutor(GenericDatabaseCommandExecutor databaseExecutor)
	{
		this.databaseExecutor = databaseExecutor;
	}

	@Override
	public List<RequestCountReportDataImpl> getRequestsByCategory(String categoryType,Calendar fromDate, int minimumCount)
	{
		return this.databaseExecutor.getResultSet(getRequestCountPerCategoryPreparedStatement(categoryType),
				new RequestCountPerCatgeoryReportParameterizedRowMapper(), new Date(fromDate.getTime().getTime()), minimumCount);
	}
	
	@Override
	public List<GreeksPerCategoryReportDataImpl> getGreeksByCategory(String categoryType,
			Calendar maturityDateFrom, Calendar maturityDateTo, double minimumGreek)
	{
		return this.databaseExecutor.getResultSet(getGreeksPerCategoryPreparedStatement(categoryType),
				new GreeksPerCatgeoryReportParameterizedRowMapper(), new Date(maturityDateFrom.getTime().getTime()),
				new Date(maturityDateTo.getTime().getTime()), minimumGreek);
	}
}
