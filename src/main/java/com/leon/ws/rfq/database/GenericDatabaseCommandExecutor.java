package com.leon.ws.rfq.database;

import java.util.List;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

public interface GenericDatabaseCommandExecutor<T>
{
	public boolean executePreparedStatement(String preparedStatement, Object...  params);
	public List<T> getResultSet(String preparedStatement, ParameterizedRowMapper<T> rowMapper);
}
