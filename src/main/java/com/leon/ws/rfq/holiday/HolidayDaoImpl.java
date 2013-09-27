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
	private GenericDatabaseCommandExecutor<HolidayImpl> databaseExecutor;
	
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
	public List<HolidayImpl> getAll()
	{	
		ParameterizedRowMapper<HolidayImpl> holidaysRowMapper = new ParameterizedRowMapper<HolidayImpl>() 
		{
			public HolidayImpl mapRow(ResultSet rs, int rowNum) throws SQLException
			{
				return new HolidayImpl(rs.getString("location"), rs.getDate("holidayDate"));					
			}				
		};
		
		return databaseExecutor.getResultSet(SELECT_ALL, holidaysRowMapper);		
	}

	@Override
	public List<HolidayImpl> get(String location)
	{
		ParameterizedRowMapper<HolidayImpl> holidaysRowMapper = new ParameterizedRowMapper<HolidayImpl>() 
		{
			public HolidayImpl mapRow(ResultSet rs, int rowNum) throws SQLException
			{
				return new HolidayImpl(rs.getString("location"), rs.getDate("holidayDate"));					
			}				
		};
		
		return databaseExecutor.getResultSet(GET, holidaysRowMapper, location);	
	}
}
