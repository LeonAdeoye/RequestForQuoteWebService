package com.leon.ws.rfq.search;

import java.util.List;
import java.util.LinkedList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

public final class SearchManagerDaoJdbcImpl extends SimpleJdbcDaoSupport implements SearchManagerDao
{
	private static final String DELETE = "CALL searches_DELETE (?, ?)";
	private static final String SAVE = "CALL searches_SAVE (?, ?, ?, ?, ?, ?)";
	private static final String SELECT_ALL = "CALL searches_SELECT_ALL";
	private static final String GET = "CALL searches_GET (?, ?)";
	private static final String UPDATE_PRIVACY = "CALL searches_UPDATE_PRIVACY (?, ?, ?)";	
	private static Logger logger = LoggerFactory.getLogger("DaoLogger");
	
	public boolean delete(String owner, String key)
	{
		try
		{
			if(logger.isDebugEnabled())
				logger.debug("Executing SQL: [{}]", DELETE);
			
			getSimpleJdbcTemplate().update(DELETE, owner, key);			
			return true;
		}
		catch (Exception e)
		{
			logger.error("{} -> {}", e.getMessage(), e.getStackTrace());
		}		
		return false;
	}
	
	public boolean updatePrivacy(String owner, String key, Boolean isPrivate)
	{
		try
		{
			if(logger.isDebugEnabled())	
				logger.debug("Executing SQL: [{}]", UPDATE_PRIVACY);
			
			getSimpleJdbcTemplate().update(UPDATE_PRIVACY, owner, key, isPrivate ? "Y" : "N");			
			return true;
		}
		catch (Exception e)
		{
			logger.error("{} -> {}", e.getMessage(), e.getStackTrace());
		}		
		return false;
	}	
	
	public boolean save(String owner, String key, String controlName, String controlValue, Boolean isPrivate, Boolean isFilter)
	{
		try
		{
			logger.debug("Executing SQL: [{}]", SAVE);			
			getSimpleJdbcTemplate().update(SAVE, owner, key, controlName, controlValue, isPrivate ? "Y" : "N",  isFilter ? "Y" : "N");			
			return true;
		}
		catch (Exception e)
		{
			logger.error("{} -> {}", e.getMessage(), e.getStackTrace());
		}		
		return false;
	}
	
	public List<SearchCriterion> getAll()
	{
		try
		{
			if(logger.isDebugEnabled())	
				logger.debug("Executing SQL: [{}]", SELECT_ALL);
			
			List<SearchCriterion> criteria = getSimpleJdbcTemplate().query(SELECT_ALL, new ParameterizedRowMapper<SearchCriterion>() {
				public SearchCriterion mapRow(ResultSet rs, int rowNum) throws SQLException
				{
					return new SearchCriterion(rs.getString("owner"), rs.getString("keyValue"), rs.getString("controlName"), rs.getString("controlValue"), rs.getString("isPrivate").equals("Y"), rs.getString("isFilter").equals("Y"));					
				}				
			});			
			return criteria;
		}
		
		catch(Exception e)
		{
			logger.error("{} -> {}", e.getMessage(), e.getStackTrace());
			return new LinkedList<SearchCriterion>();			
		}
	}
	
	public List<SearchCriterion> get(String owner, String key)
	{
		try
		{
			if(logger.isDebugEnabled())	
				logger.debug("Executing SQL: [{}]", GET);
			
			List<SearchCriterion> criteria = getSimpleJdbcTemplate().query(GET, new ParameterizedRowMapper<SearchCriterion>() {
				public SearchCriterion mapRow(ResultSet rs, int rowNum) throws SQLException
				{
					return new SearchCriterion(rs.getString("owner"), rs.getString("keyValue"), rs.getString("controlName"), rs.getString("controlValue"), rs.getString("isPrivate").equals("Y"), rs.getString("isFilter").equals("Y"));					
				}				
			}, owner, key);			
			return criteria;
		}
		
		catch(Exception e)
		{
			logger.error("{} -> {}", e.getMessage(), e.getStackTrace());
			return new LinkedList<SearchCriterion>();			
		}		
	}
}
