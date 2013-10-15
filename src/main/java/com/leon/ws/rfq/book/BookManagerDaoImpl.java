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
	private GenericDatabaseCommandExecutor<BookDetailImpl> databaseExecutor;

	private class BookDetailParameterizedRowMapper implements ParameterizedRowMapper<BookDetailImpl>
	{
		@Override
		public BookDetailImpl mapRow(ResultSet rs, int rowNum) throws SQLException {
			BookDetailImpl newBook = new BookDetailImpl();
			newBook.setBookCode(rs.getString("bookCode"));
			newBook.setEntity(rs.getString("entity"));
			newBook.setIsValid(rs.getString("isValid").equals("Y"));
			return newBook;
		}
	}

	BookManagerDaoImpl()
	{

	}

	BookManagerDaoImpl(GenericDatabaseCommandExecutor<BookDetailImpl> databaseExecutor)
	{
		this.databaseExecutor = databaseExecutor;
	}

	public void setDatabaseCommandExecutor(GenericDatabaseCommandExecutor<BookDetailImpl> databaseExecutor)
	{
		this.databaseExecutor = databaseExecutor;
	}

	@Override
	public boolean delete(String bookCode)
	{
		return this.databaseExecutor.executePreparedStatement(DELETE, bookCode);
	}

	@Override
	public BookDetailImpl save(String bookCode, String entity, String updatedByUser)
	{
		return this.databaseExecutor.getSingleResult(SAVE, new BookDetailParameterizedRowMapper(), bookCode, entity, updatedByUser);
	}

	@Override
	public boolean updateValidity(String bookCode, boolean isValid)
	{
		return  this.databaseExecutor.executePreparedStatement(UPDATE_VALIDITY, bookCode, isValid ? "Y" : "N");
	}

	@Override
	public List<BookDetailImpl> getAll()
	{
		return this.databaseExecutor.getResultSet(SELECT_ALL, new BookDetailParameterizedRowMapper());
	}
}
