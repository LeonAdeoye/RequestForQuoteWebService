package com.leon.ws.rfq.holiday;

import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

public class HolidayDaoJdbcImpl extends SimpleJdbcDaoSupport implements HolidayDao
{
	private static final String SAVE = "CALL holidays_SAVE (?, ?, ?)";
	private static final String SELECT_ALL = "CALL holidays_SELECT_ALL";
	private static final String GET = "CALL holidays_GET (?)";
	private static final String DELETE = "CALL holidays_DELETE (?, ?)";
	private static Logger logger = LoggerFactory.getLogger("DaoLogger");
	
	@Override
	public boolean delete(String location, Calendar holidayDate)
	{
		try
		{
			if(logger.isDebugEnabled())
				logger.debug(String.format("Executing stored procedure [%s] with parameters location [%s], and holidayDate [%s]", DELETE, location, holidayDate));
				 
			getSimpleJdbcTemplate().update(DELETE, location, holidayDate);						
			return true;				
		}
		
		catch(Exception e)
		{
			logger.error("{} -> {}", e.getMessage(), e.getStackTrace());		
		}
		return false;
	}

	@Override
	public boolean save(String location, Calendar holidayDate, String addedByUser)
	{
		try
		{
			if(logger.isDebugEnabled())
				logger.debug(String.format("Executing stored procedure [%s] with parameters location [%s], holidayDate [%s], and updatedByUser [%s]", SAVE, location, holidayDate, addedByUser));
			
			getSimpleJdbcTemplate().update(SAVE, location, holidayDate, addedByUser);	
			return true;				
		}
		
		catch(Exception e)
		{
			logger.error("{} -> {}", e.getMessage(), e.getStackTrace());		
		}
		return false;
	}

	@Override
	public List<Holiday> getAll()
	{
		try
		{
			if(logger.isDebugEnabled())
				logger.debug(String.format("Executing stored procedure [%s]", SELECT_ALL));
					
			List<Holiday> allHolidays = getSimpleJdbcTemplate().query(SELECT_ALL,
			ParameterizedBeanPropertyRowMapper.newInstance(Holiday.class));
			
			return allHolidays;
		}
		
		catch(Exception e)
		{
			logger.error("{} -> {}", e.getMessage(), e.getStackTrace());
			return new LinkedList<Holiday>();			
		}	
	}

	@Override
	public List<Holiday> get(String location)
	{
		try
		{
			if(logger.isDebugEnabled())
				logger.debug(String.format("Executing stored procedure [%s] with parameter location [%s]", GET, location));
					
			List<Holiday> allHolidays = getSimpleJdbcTemplate().query(GET,
			ParameterizedBeanPropertyRowMapper.newInstance(Holiday.class), location);
			
			return allHolidays;
		}
		
		catch(Exception e)
		{
			logger.error("{} -> {}", e.getMessage(), e.getStackTrace());
			return new LinkedList<Holiday>();			
		}
	}
}
