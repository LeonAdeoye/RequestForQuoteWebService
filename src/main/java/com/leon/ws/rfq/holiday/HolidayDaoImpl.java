package com.leon.ws.rfq.holiday;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.List;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import com.leon.ws.rfq.database.GenericDatabaseCommandExecutor;

public class HolidayDaoImpl implements HolidayDao
{
	private static final String SAVE = "CALL holidays_SAVE (?, ?, ?)";
	private static final String SELECT_ALL = "CALL holidays_SELECT_ALL";
	private static final String GET = "CALL holidays_GET (?)";
	private static final String DELETE = "CALL holidays_DELETE (?, ?)";
	private GenericDatabaseCommandExecutor<Holiday> databaseExecutor;
	
	HolidayDaoImpl()
	{
		
	}
	
	HolidayDaoImpl(GenericDatabaseCommandExecutor<Holiday> databaseExecutor)
	{
		this.databaseExecutor = databaseExecutor;
	}
	
	public void setDatabaseCommandExecutor(GenericDatabaseCommandExecutor<Holiday> databaseExecutor)
	{
		this.databaseExecutor = databaseExecutor;
	}	
	
	@Override
	public boolean delete(String location, Calendar holidayDate)
	{						
		return databaseExecutor.executePreparedStatement(DELETE, location, holidayDate);				
	}

	@Override
	public boolean save(String location, Calendar holidayDate, String addedByUser)
	{
		return databaseExecutor.executePreparedStatement(SAVE, location, holidayDate, addedByUser);
	}

	@Override
	public List<Holiday> getAll()
	{	
		ParameterizedRowMapper<Holiday> holidaysRowMapper = new ParameterizedRowMapper<Holiday>() 
		{
			public Holiday mapRow(ResultSet rs, int rowNum) throws SQLException
			{
				return new Holiday(rs.getString("location"), rs.getDate("holidayDate"));					
			}				
		};
		
		return databaseExecutor.getResultSet(SELECT_ALL, holidaysRowMapper);		
	}

	@Override
	public List<Holiday> get(String location)
	{
		ParameterizedRowMapper<Holiday> holidaysRowMapper = new ParameterizedRowMapper<Holiday>() 
		{
			public Holiday mapRow(ResultSet rs, int rowNum) throws SQLException
			{
				return new Holiday(rs.getString("location"), rs.getDate("holidayDate"));					
			}				
		};
		
		return databaseExecutor.getResultSet(GET, holidaysRowMapper, location);	
	}
}
