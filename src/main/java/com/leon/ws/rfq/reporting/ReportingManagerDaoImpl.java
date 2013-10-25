package com.leon.ws.rfq.reporting;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import com.leon.ws.rfq.database.GenericDatabaseCommandExecutor;

public class ReportingManagerDaoImpl implements ReportingManagerDao
{
	private GenericDatabaseCommandExecutor databaseExecutor;

	private static final String REQUESTS_COUNT_BY_BOOKCODE = "BookCode";

	private static final String COUNT_BY_BOOKCODE = "SELECT IFNULL(bookCode,'NONE') categoryValue, COUNT(*) requestCount"
			+ " FROM requestforquotemain"
			+ " WHERE tradeDate >= ?"
			+ " GROUP By bookCode"
			+ " HAVING COUNT(*) > ?";

	//private static final String COUNT_BY_UNDERLYIER = "TODO";

	private static final String COUNT_BY_CLIENT = "SELECT IFNULL(client,'NONE') categoryValue, COUNT(*) requestCount"
			+ " FROM requestforquotemain"
			+ " WHERE tradeDate >= ?"
			+ " GROUP By clientId"
			+ " HAVING COUNT(*) > ?";

	//private static final String COUNT_BY_INITIATOR = "TODO";

	private class ReportDataParameterizedRowMapper implements ParameterizedRowMapper<RequestCountReportDataImpl>
	{
		@Override
		public RequestCountReportDataImpl mapRow(ResultSet rs, int rowNum) throws SQLException
		{
			RequestCountReportDataImpl reportDataRow = new RequestCountReportDataImpl(rs.getString("categoryValue"), rs.getInt("requestCount"));

			return reportDataRow;
		}
	}

	private final DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");

	private String getPreparedStatement(String categoryType)
	{
		switch(categoryType)
		{
		case REQUESTS_COUNT_BY_BOOKCODE:
			return COUNT_BY_BOOKCODE;
		case "Client":
			return COUNT_BY_CLIENT;
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
	public RequestCountReportDataListImpl getRequestsByCategory(String categoryType,Calendar fromDate, int minimumCount)
	{
		RequestCountReportDataListImpl requestsByCategory = new RequestCountReportDataListImpl();

		ArrayList<RequestCountReportDataImpl> resultSet = (ArrayList<RequestCountReportDataImpl>) this.databaseExecutor.getResultSet(getPreparedStatement(categoryType),
				new ReportDataParameterizedRowMapper(), this.dateFormat.format(fromDate), minimumCount);

		requestsByCategory.setRequestCountList(resultSet);

		return requestsByCategory;
	}
}
