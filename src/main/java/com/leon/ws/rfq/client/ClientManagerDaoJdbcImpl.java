package com.leon.ws.rfq.client;

import java.util.List;
import java.util.LinkedList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

public final class ClientManagerDaoJdbcImpl extends SimpleJdbcDaoSupport implements ClientManagerDao
{
	private static final String DELETE = "CALL clients_DELETE (?)";
	private static final String SAVE = "CALL clients_SAVE (?, ?)";
	private static final String UPDATE_TIER = "CALL clients_UPDATE_TIER (?, ?)";
	private static final String UPDATE_VALIDITY = "CALL clients_UPDATE_VALIDITY (?, ?)";
	private static final String SELECT_ALL = "CALL clients_SELECT_ALL";	
	private static Logger logger = LoggerFactory.getLogger(ClientManagerDaoJdbcImpl.class);
	
	public boolean delete(int identifier)
	{
		try
		{
			if(logger.isDebugEnabled())	
				logger.debug("Executing SQL: [{}]", DELETE);
			
			getSimpleJdbcTemplate().update(DELETE, identifier);			
			return true;
		}
		catch (Exception e)
		{
			logger.error("{} -> {}", e.getMessage(), e.getStackTrace());
		}		
		return false;
	}
	
	public boolean save(String name, int tier)
	{
		try
		{
			if(logger.isDebugEnabled())	
				logger.debug("Executing SQL: [{}]", SAVE);
			
			getSimpleJdbcTemplate().update(SAVE, name, tier);			
			return true;
		}
		catch (Exception e)
		{
			logger.error("{} -> {}", e.getMessage(), e.getStackTrace());
		}		
		return false;
	}
	
	public boolean updateTier(int identifier, int tier)
	{
		try
		{
			if(logger.isDebugEnabled())	
				logger.debug("Executing SQL: [{}]", UPDATE_TIER);
			
			getSimpleJdbcTemplate().update(UPDATE_TIER, identifier, tier);			
			return true;
		}
		catch (Exception e)
		{
			logger.error("{} -> {}", e.getMessage(), e.getStackTrace());
		}		
		return false;
	}
	
	public boolean updateValidity(int identifier, boolean isValid)
	{
		try
		{
			if(logger.isDebugEnabled())	
				logger.debug("Executing SQL: [{}]", UPDATE_VALIDITY);
			
			getSimpleJdbcTemplate().update(UPDATE_VALIDITY, identifier, isValid ? "Y" : "N");			
			return true;
		}
		catch (Exception e)
		{
			logger.error("{} -> {}", e.getMessage(), e.getStackTrace());
		}		
		return false;
	}	
		
	public List<ClientDetail> getAll()
	{
		try
		{
			if(logger.isDebugEnabled())
				logger.debug("Executing SQL: [{}]", SELECT_ALL);
			
			List<ClientDetail> clients = getSimpleJdbcTemplate().query(SELECT_ALL, new ParameterizedRowMapper<ClientDetail>() {
				public ClientDetail mapRow(ResultSet rs, int rowNum) throws SQLException
				{
					return new ClientDetail(rs.getString("name"), rs.getInt("identifier"), rs.getInt("tier"), rs.getString("isValid").equals("Y"));					
				}				
			});			
			return clients;
		}
		
		catch(Exception e)
		{
			logger.error("{} -> {}", e.getMessage(), e.getStackTrace());
			return new LinkedList<ClientDetail>();			
		}
	}
}
