package com.leon.ws.rfq.holiday;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import com.leon.ws.rfq.database.GenericDatabaseCommandExecutor;

public final class HolidayDaoImpl implements HolidayDao
{
	private static final String SAVE = "CALL holidays_SAVE (?, ?, ?)";
	private static final String SELECT_ALL = "CALL holidays_SELECT_ALL";
	private static final String GET = "CALL holidays_GET (?)";
	private static final String DELETE = "CALL holidays_DELETE (?, ?)";
	private GenericDatabaseCommandExecutor<HolidayImpl> databaseExecutor;

	private class HolidayParameterizedRowMapper implements ParameterizedRowMapper<HolidayImpl>
	{
		@Override
		public HolidayImpl mapRow(ResultSet rs, int rowNum) throws SQLException {
			HolidayImpl holiday = new HolidayImpl();
			holiday.setLocation(rs.getString("location"));
			holiday.setHolidayDate(rs.getString("holidayDate"));
			return holiday;
		}
	}

	HolidayDaoImpl()
	{

	}

	HolidayDaoImpl(GenericDatabaseCommandExecutor<HolidayImpl> databaseExecutor)
	{
		this.databaseExecutor = databaseExecutor;
	}

	public void setDatabaseCommandExecutor(GenericDatabaseCommandExecutor<HolidayImpl> databaseExecutor)
	{
		this.databaseExecutor = databaseExecutor;
	}

	@Override
	public boolean delete(String location, String holidayDate)
	{
		return this.databaseExecutor.executePreparedStatement(DELETE, location, holidayDate);
	}

	@Override
	public boolean save(String location, String holidayDate, String addedByUser)
	{
		return this.databaseExecutor.executePreparedStatement(SAVE, location, holidayDate, addedByUser);
	}

	@Override
	public List<HolidayImpl> getAll()
	{
		return this.databaseExecutor.getResultSet(SELECT_ALL, new HolidayParameterizedRowMapper());
	}

	@Override
	public List<HolidayImpl> get(String location)
	{

		return this.databaseExecutor.getResultSet(GET, new HolidayParameterizedRowMapper(), location);
	}
}
