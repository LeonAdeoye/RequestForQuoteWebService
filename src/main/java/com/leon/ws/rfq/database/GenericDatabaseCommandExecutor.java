package com.leon.ws.rfq.database;

import java.sql.SQLException;
import java.sql.Savepoint;
import java.util.List;

import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

public interface GenericDatabaseCommandExecutor
{
	<T> boolean executePreparedStatement(String preparedStatement, Object...  params);
	<T> List<T> getResultSet(String preparedStatement, ParameterizedRowMapper<T> rowMapper, Object...  params);
	<T> T getSingleResult(String preparedStatement, ParameterizedRowMapper<T> rowMapper, Object... params);
	void commitTransaction() throws SQLException;
	Savepoint startTransaction() throws SQLException;
	void rollbackTransaction(Savepoint savePoint) throws SQLException;
}
