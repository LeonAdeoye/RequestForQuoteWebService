package com.leon.ws.rfq.database;

import java.util.List;

import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

public interface GenericDatabaseCommandExecutor
{
	<T> boolean executePreparedStatement(String preparedStatement, Object...  params);
	<T> List<T> getResultSet(String preparedStatement, ParameterizedRowMapper<T> rowMapper, Object...  params);
	<T> T getSingleResult(String preparedStatement, ParameterizedRowMapper<T> rowMapper, Object... params);
}
