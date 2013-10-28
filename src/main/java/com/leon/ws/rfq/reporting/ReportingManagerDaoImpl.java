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

	private static final String REQUESTS_COUNT_BY_BOOKCODE = "BookCode";
	private static final String REQUESTS_COUNT_BY_CLIENT = "Client";
	private static final String REQUESTS_COUNT_BY_UNDERLYING = "Underlying";
	private static final String REQUESTS_COUNT_BY_INITIATOR = "Inititor";

	private static final String REQUESTS_COUNT_BY_BOOKCODE_GET = "CALL requestsCountByBookCode(?, ?)";
	private static final String REQUESTS_COUNT_BY_CLIENT_GET = "CALL requestsCountByClient(?, ?)";
	private static final String REQUESTS_COUNT_BY_UNDERLYING_GET = "CALL requestsCountByUnderlying(?, ?)";
	private static final String REQUESTS_COUNT_BY_INITIATOR_GET = "CALL requestsCountByInitiator(?, ?)";

	private class ReportDataParameterizedRowMapper implements ParameterizedRowMapper<RequestCountReportDataImpl>
	{
		@Override
		public RequestCountReportDataImpl mapRow(ResultSet rs, int rowNum) throws SQLException
		{
			RequestCountReportDataImpl reportDataRow = new RequestCountReportDataImpl(rs.getString("categoryValue"), rs.getInt("requestCount"));

			return reportDataRow;
		}
	}

	private String getPreparedStatement(String categoryType)
	{
		switch(categoryType)
		{
		case REQUESTS_COUNT_BY_BOOKCODE:
			return REQUESTS_COUNT_BY_BOOKCODE_GET;
		case REQUESTS_COUNT_BY_CLIENT:
			return REQUESTS_COUNT_BY_CLIENT_GET;
		case REQUESTS_COUNT_BY_UNDERLYING:
			return REQUESTS_COUNT_BY_UNDERLYING_GET;
		case REQUESTS_COUNT_BY_INITIATOR:
			return REQUESTS_COUNT_BY_INITIATOR_GET;
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
		return this.databaseExecutor.getResultSet(getPreparedStatement(categoryType),
				new ReportDataParameterizedRowMapper(), new Date(fromDate.getTime().getTime()), minimumCount);
	}
}
