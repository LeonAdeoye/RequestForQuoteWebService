package com.leon.ws.rfq.book;

import java.util.List;
import java.util.LinkedList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

public final class BookManagerDaoJdbcImpl extends SimpleJdbcDaoSupport implements BookManagerDao
{
	private static final String DELETE = "CALL books_DELETE (?)";
	private static final String SAVE = "CALL books_SAVE (?, ?, ?)";
	private static final String UPDATE_VALIDITY = "CALL books_UPDATE_VALIDITY (?, ?)";
	private static final String SELECT_ALL = "CALL books_SELECT_ALL";	
	private static Logger logger = LoggerFactory.getLogger(BookManagerDaoJdbcImpl.class);
	
	public boolean delete(String bookCode)
	{
		try
		{
			if(logger.isDebugEnabled())
				logger.debug("Executing SQL: [{}]", DELETE);
			
			getSimpleJdbcTemplate().update(DELETE, bookCode);			
			return true;
		}
		catch (Exception e)
		{
			logger.error("{} -> {}", e.getMessage(), e.getStackTrace());
		}		
		return false;
	}
	
	public boolean save(String bookCode, String entity, String updatedByUser)
	{
		try
		{
			if(logger.isDebugEnabled())
				logger.debug("Executing SQL: [{}]", SAVE);
			
			getSimpleJdbcTemplate().update(SAVE, bookCode, entity, updatedByUser);			
			return true;
		}
		catch (Exception e)
		{
			logger.error("{} -> {}", e.getMessage(), e.getStackTrace());
		}		
		return false;
	}
	
	public boolean updateValidity(String bookCode, boolean isValid)
	{
		try
		{
			if(logger.isDebugEnabled())	
				logger.debug("Executing SQL: [{}]", UPDATE_VALIDITY);
			
			getSimpleJdbcTemplate().update(UPDATE_VALIDITY, bookCode, isValid ? "Y" : "N");			
			return true;
		}
		catch (Exception e)
		{
			logger.error("{} -> {}", e.getMessage(), e.getStackTrace());
		}		
		return false;
	}	
		
	public List<BookDetail> getAll()
	{
		try
		{
			if(logger.isDebugEnabled())
				logger.debug("Executing SQL: [{}]", SELECT_ALL);
			
			List<BookDetail> books = getSimpleJdbcTemplate().query(SELECT_ALL, new ParameterizedRowMapper<BookDetail>() {
				public BookDetail mapRow(ResultSet rs, int rowNum) throws SQLException
				{
					return new BookDetail(rs.getString("bookCode"), rs.getString("entity"), rs.getString("isValid").equals("Y"));					
				}				
			});			
			return books;
		}
		
		catch(Exception e)
		{
			logger.error("{} -> {}", e.getMessage(), e.getStackTrace());
			return new LinkedList<BookDetail>();			
		}
	}
}
