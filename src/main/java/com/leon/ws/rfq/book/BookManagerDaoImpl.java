package com.leon.ws.rfq.book;

import java.util.List;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import com.leon.ws.rfq.database.GenericDatabaseCommandExecutor;
import java.sql.ResultSet;
import java.sql.SQLException;

public final class BookManagerDaoImpl implements BookManagerDao
{
	private static final String DELETE = "CALL books_DELETE (?)";
	private static final String SAVE = "CALL books_SAVE (?, ?, ?)";
	private static final String UPDATE_VALIDITY = "CALL books_UPDATE_VALIDITY (?, ?)";
	private static final String SELECT_ALL = "CALL books_SELECT_ALL";
	private GenericDatabaseCommandExecutor<BookDetail> databaseExecutor;
	
	BookManagerDaoImpl()
	{
		
	}
	
	BookManagerDaoImpl(GenericDatabaseCommandExecutor<BookDetail> databaseExecutor)
	{
		this.databaseExecutor = databaseExecutor;
	}
	
	public void setDatabaseCommandExecutor(GenericDatabaseCommandExecutor<BookDetail> databaseExecutor)
	{
		this.databaseExecutor = databaseExecutor;
	}	
	
	public boolean delete(String bookCode)
	{		
		return databaseExecutor.executePreparedStatement(DELETE, bookCode);
	}
	
	public boolean save(String bookCode, String entity, String updatedByUser)
	{	
		return databaseExecutor.executePreparedStatement(SAVE, bookCode, entity, updatedByUser);
	}
	
	public boolean updateValidity(String bookCode, boolean isValid)
	{
		return  databaseExecutor.executePreparedStatement(UPDATE_VALIDITY, bookCode, isValid ? "Y" : "N");
	}	
		
	public List<BookDetail> getAll()
	{		
		ParameterizedRowMapper<BookDetail> booksRowMapper = new ParameterizedRowMapper<BookDetail>()
		{
			public BookDetail mapRow(ResultSet rs, int rowNum) throws SQLException
			{
				return new BookDetail(rs.getString("bookCode"), rs.getString("entity"), rs.getString("isValid").equals("Y"));					
			}				
		};
		
		return databaseExecutor.getResultSet(SELECT_ALL, booksRowMapper);
	}
}
