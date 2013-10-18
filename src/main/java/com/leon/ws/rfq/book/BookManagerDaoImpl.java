package com.leon.ws.rfq.book;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import com.leon.ws.rfq.database.GenericDatabaseCommandExecutor;

public final class BookManagerDaoImpl implements BookManagerDao
{
	private static final String DELETE = "CALL books_DELETE (?)";
	private static final String SAVE = "CALL books_SAVE (?, ?, ?)";
	private static final String UPDATE_VALIDITY = "CALL books_UPDATE_VALIDITY (?, ?)";
	private static final String SELECT_ALL = "CALL books_SELECT_ALL";
	private GenericDatabaseCommandExecutor databaseExecutor;

	private class BookDetailParameterizedRowMapper implements ParameterizedRowMapper<BookDetailImpl>
	{
		@Override
		public BookDetailImpl mapRow(ResultSet rs, int rowNum) throws SQLException
		{
			BookDetailImpl newBook = new BookDetailImpl(rs.getString("bookCode"), rs.getString("bookCode"), rs.getString("isValid").equals("Y"));

			return newBook;
		}
	}

	BookManagerDaoImpl() {}

	BookManagerDaoImpl(GenericDatabaseCommandExecutor databaseExecutor)
	{
		this.databaseExecutor = databaseExecutor;
	}

	public void setDatabaseCommandExecutor(GenericDatabaseCommandExecutor databaseExecutor)
	{
		this.databaseExecutor = databaseExecutor;
	}

	@Override
	public boolean delete(String bookCode)
	{
		return this.databaseExecutor.<BookDetailImpl>executePreparedStatement(DELETE, bookCode);
	}

	@Override
	public BookDetailImpl save(String bookCode, String entity, String savedBy)
	{
		return this.databaseExecutor.<BookDetailImpl>getSingleResult(SAVE, new BookDetailParameterizedRowMapper(), bookCode, entity, savedBy);
	}

	@Override
	public boolean updateValidity(String bookCode, boolean isValid)
	{
		return  this.databaseExecutor.<BookDetailImpl>executePreparedStatement(UPDATE_VALIDITY, bookCode, isValid ? "Y" : "N");
	}

	@Override
	public List<BookDetailImpl> getAll()
	{
		return this.databaseExecutor.<BookDetailImpl>getResultSet(SELECT_ALL, new BookDetailParameterizedRowMapper());
	}
}
