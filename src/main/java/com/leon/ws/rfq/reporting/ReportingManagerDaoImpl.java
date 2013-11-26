package com.leon.ws.rfq.reporting;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.List;

import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import com.leon.ws.rfq.database.GenericDatabaseCommandExecutor;
import com.leon.ws.rfq.utilities.UtilityMethods;

public class ReportingManagerDaoImpl implements ReportingManagerDao
{
	private GenericDatabaseCommandExecutor databaseExecutor;

	private static final String BOOKCODE_CATEGORY = "BookCode";
	private static final String CLIENT_CATEGORY = "Client";
	private static final String UNDERLYING_CATEGORY = "Underlying";
	private static final String INITIATOR_CATEGORY = "Initiator";
	private static final String TRADEDATE_CATEGORY = "TradeDate";
	private static final String PICKER_CATEGORY = "Picker";
	private static final String STATUS_CATEGORY = "Status";
	
	private static final String REQUESTS_COUNT_BY_BOOKCODE_GET = "CALL requestsCountByBookCode(?, ?)";
	private static final String REQUESTS_COUNT_BY_CLIENT_GET = "CALL requestsCountByClient(?, ?)";
	private static final String REQUESTS_COUNT_BY_UNDERLYING_GET = "CALL requestsCountByUnderlying(?, ?)";
	private static final String REQUESTS_COUNT_BY_INITIATOR_GET = "CALL requestsCountByInitiator(?, ?)";
	private static final String REQUESTS_COUNT_BY_TRADEDATE_GET = "CALL requestsCountByTradeDate(?, ?)";
	private static final String REQUESTS_COUNT_BY_PICKER_GET = "CALL requestsCountByPicker(?, ?)";
	private static final String REQUESTS_COUNT_BY_STATUS_GET = "CALL requestsCountByStatus(?, ?)";

	private static final String GREEKS_BY_BOOKCODE_GET = "CALL GreeksByBookCode(?, ?, ?, ?, ?)";
	private static final String GREEKS_BY_CLIENT_GET = "CALL GreeksByClient(?, ?, ?, ?, ?)";
	private static final String GREEKS_BY_UNDERLYING_GET = "CALL GreeksByUnderlying(?, ?, ?, ?, ?)";
	private static final String GREEKS_BY_INITIATOR_GET = "CALL GreeksByUnderlying(?, ?, ?, ?, ?)";
	private static final String GREEKS_BY_TRADEDATE_GET = "CALL GreeksByTradeDate(?, ?, ?, ?, ?)";
	private static final String GREEKS_BY_PICKER_GET = "CALL GreeksByPicker(?, ?, ?, ?, ?)";
	private static final String GREEKS_BY_STATUS_GET = "CALL GreeksByStatus(?, ?, ?, ?, ?)";

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
					rs.getString("greekType"), rs.getDouble("greekTotal"));
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
	public List<GreeksPerCategoryReportDataImpl> getGreeksByCategory(String categoryType, List<String> greeksToBeIncluded,
			Calendar maturityDateFrom, Calendar maturityDateTo, double minimumGreek)
	{
		String greeks = UtilityMethods.join(greeksToBeIncluded, ",");
		
		return this.databaseExecutor.getResultSet(getGreeksPerCategoryPreparedStatement(categoryType),
				new GreeksPerCatgeoryReportParameterizedRowMapper(), new Date(maturityDateFrom.getTime().getTime()),
				new Date(maturityDateTo.getTime().getTime()), greeks, minimumGreek);
	}
}
