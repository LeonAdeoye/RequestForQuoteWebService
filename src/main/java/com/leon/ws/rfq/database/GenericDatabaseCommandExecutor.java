package com.leon.ws.rfq.database;

import java.util.List;

import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

public interface GenericDatabaseCommandExecutor<T>
{
	boolean executePreparedStatement(String preparedStatement, Object...  params);
	List<T> getResultSet(String preparedStatement, ParameterizedRowMapper<T> rowMapper);
	List<T> getResultSet(String preparedStatement, ParameterizedRowMapper<T> rowMapper, Object...  params);
	T getSingleResult(String preparedStatement, ParameterizedRowMapper<T> rowMapper);
	T getSingleResult(String preparedStatement, ParameterizedRowMapper<T> rowMapper, Object... params);
}
