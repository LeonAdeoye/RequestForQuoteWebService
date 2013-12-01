package com.leon.ws.rfq.database;

import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

public final class GenericDatabaseCommandExecutorImpl extends SimpleJdbcDaoSupport  implements GenericDatabaseCommandExecutor
{
	private static final Logger logger = LoggerFactory.getLogger(GenericDatabaseCommandExecutorImpl.class);

	/**
	 * Executes a prepared statement pass a parameter and returns a boolean result if the successful.
	 * 
	 *  @param preparedStatement		the SQL prepared statement to execute.
	 *  @param params					the variable arguments array.
	 *  @returns						true if successful; false otherwise.
	 */
	@Override
	public <T> boolean executePreparedStatement(String preparedStatement,Object... params)
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

	/**
	 * Returns a result set list resulting from the execution of the prepared statement.
	 * 
	 *  @param preparedStatement		the SQL prepared statement to execute.
	 *  @param params					the variable arguments array.
	 *  @returns						list of type T; an empty list otherwise.
	 */
	@Override
	public <T> List<T> getResultSet(String preparedStatement, ParameterizedRowMapper<T> rowMapper, Object... params)
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

	/**
	 * Returns a single result set resulting from the execution of the prepared statement.
	 * 
	 *  @param preparedStatement		the SQL prepared statement to execute.
	 *  @param params					the variable arguments array.
	 *  @returns						an object of type T; null otherwise.
	 */
	@Override
	public <T> T getSingleResult(String preparedStatement, ParameterizedRowMapper<T> rowMapper, Object... params)
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
