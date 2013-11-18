package com.leon.ws.rfq.database;

import java.sql.SQLException;
import java.sql.Savepoint;
import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

public final class GenericDatabaseCommandExecutorImpl extends SimpleJdbcDaoSupport  implements GenericDatabaseCommandExecutor
{
	private static final Logger logger = LoggerFactory.getLogger(GenericDatabaseCommandExecutorImpl.class);

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

	@Override
	public Savepoint startTransaction() throws SQLException
	{
		this.getConnection().setAutoCommit(false);
		return this.getConnection().setSavepoint();
	}

	@Override
	public void commitTransaction() throws SQLException
	{
		this.getConnection().commit();
	}

	@Override
	public void rollbackTransaction(Savepoint savePoint) throws SQLException
	{
		this.getConnection().rollback(savePoint);
	}
}
