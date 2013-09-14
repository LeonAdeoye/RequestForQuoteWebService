package com.leon.ws.rfq.database;

import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

public class GenericDatabaseCommandExecutorImpl<T> extends SimpleJdbcDaoSupport  implements GenericDatabaseCommandExecutor<T>
{
	private static Logger logger = LoggerFactory.getLogger(GenericDatabaseCommandExecutorImpl.class);
	
	@Override
	public boolean executePreparedStatement(String preparedStatement,Object... params)
	{
		try
		{
			if(logger.isInfoEnabled())
				logger.info("Executing prepared statement: {} with params: {}.", preparedStatement, params);
			
			getSimpleJdbcTemplate().update(preparedStatement, params);
			return true;
			
		}
		catch(Exception exception)
		{
			logger.error("Exception thrown when executing the prepared statement: ", exception);
		}
		return false;
	}

	@Override
	public List<T> getResultSet(String preparedStatement, ParameterizedRowMapper<T> rowMapper)
	{
		try
		{
			if(logger.isInfoEnabled())
				logger.info("Executing prepared statement to retreive a result set: {}.", preparedStatement);
			
			return getSimpleJdbcTemplate().query(preparedStatement, rowMapper);
		}
		catch(Exception exception)
		{
			logger.error("Exception thrown when getting a result set from a the prepared statement: ", exception);
		}
		return new LinkedList<T>();
	}

	@Override
	public List<T> getResultSet(String preparedStatement, ParameterizedRowMapper<T> rowMapper, Object... params)
	{
		try
		{
			if(logger.isInfoEnabled())
				logger.info("Executing prepared statement to retreive a result set:  {} with params: {}.", preparedStatement, params);
			
			return getSimpleJdbcTemplate().query(preparedStatement, rowMapper, params);
		}
		catch(Exception exception)
		{
			logger.error("Exception thrown when getting a result set from a the prepared statement: ", exception);
		}
		return new LinkedList<T>();
	}
	
	@Override
	public T getSingleResult(String preparedStatement, ParameterizedRowMapper<T> rowMapper)
	{
		try
		{
			if(logger.isInfoEnabled())
				logger.info("Executing prepared statement to retreive a result set: {}", preparedStatement);
			
			return getSimpleJdbcTemplate().queryForObject(preparedStatement, rowMapper);
		}
		catch(Exception exception)
		{
			logger.error("Exception thrown when getting a single result from a the prepared statement: ", exception);
		}
		return null;
	}	
	
	@Override
	public T getSingleResult(String preparedStatement, ParameterizedRowMapper<T> rowMapper, Object... params)
	{
		try
		{
			if(logger.isInfoEnabled())
				logger.info("Executing prepared statement to retreive a result set:  {} with params: {}", preparedStatement, params);
			
			return getSimpleJdbcTemplate().queryForObject(preparedStatement, rowMapper, params);
		}
		catch(Exception exception)
		{
			logger.error("Exception thrown when getting a single result from a the prepared statement: ", exception);
		}
		return null;
	}	
}
