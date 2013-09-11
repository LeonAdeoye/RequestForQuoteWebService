package com.leon.ws.rfq.search;

import java.util.List;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import com.leon.ws.rfq.database.GenericDatabaseCommandExecutor;
import java.sql.ResultSet;
import java.sql.SQLException;

public final class SearchManagerDaoImpl implements SearchManagerDao
{
	private static final String DELETE = "CALL searches_DELETE (?, ?)";
	private static final String SAVE = "CALL searches_SAVE (?, ?, ?, ?, ?, ?)";
	private static final String SELECT_ALL = "CALL searches_SELECT_ALL";
	private static final String GET = "CALL searches_GET (?, ?)";
	private static final String UPDATE_PRIVACY = "CALL searches_UPDATE_PRIVACY (?, ?, ?)";	
	private GenericDatabaseCommandExecutor<SearchCriterion> databaseExecutor;
	
	SearchManagerDaoImpl()
	{
		
	}
	
	SearchManagerDaoImpl(GenericDatabaseCommandExecutor<SearchCriterion> databaseExecutor)
	{
		this.databaseExecutor = databaseExecutor;
	}
	
	public void setDatabaseCommandExecutor(GenericDatabaseCommandExecutor<SearchCriterion> databaseExecutor)
	{
		this.databaseExecutor = databaseExecutor;
	}
	
	public boolean delete(String owner, String key)
	{	
		return databaseExecutor.executePreparedStatement(DELETE, owner, key);
	}
	
	public boolean updatePrivacy(String owner, String key, Boolean isPrivate)
	{		
		return databaseExecutor.executePreparedStatement(UPDATE_PRIVACY, owner, key, isPrivate ? "Y" : "N");
	}	
	
	public boolean save(String owner, String key, String controlName, String controlValue, Boolean isPrivate, Boolean isFilter)
	{		
		return databaseExecutor.executePreparedStatement(SAVE, owner, key, controlName, controlValue, isPrivate ? "Y" : "N",  isFilter ? "Y" : "N");
	}
	
	public List<SearchCriterion> getAll()
	{		
		ParameterizedRowMapper<SearchCriterion> criteriaRowMapper = new ParameterizedRowMapper<SearchCriterion>() 
		{
			public SearchCriterion mapRow(ResultSet rs, int rowNum) throws SQLException
			{
				return new SearchCriterion(rs.getString("owner"), rs.getString("keyValue"), rs.getString("controlName"), 
						rs.getString("controlValue"), rs.getString("isPrivate").equals("Y"), rs.getString("isFilter").equals("Y"));					
			}				
		};
		
		return databaseExecutor.getResultSet(SELECT_ALL, criteriaRowMapper);	
	}
	
	public List<SearchCriterion> get(String owner, String key)
	{	
		ParameterizedRowMapper<SearchCriterion> criteriaRowMapper = new ParameterizedRowMapper<SearchCriterion>() 
		{
			public SearchCriterion mapRow(ResultSet rs, int rowNum) throws SQLException
			{
				return new SearchCriterion(rs.getString("owner"), rs.getString("keyValue"), rs.getString("controlName"), 
						rs.getString("controlValue"), rs.getString("isPrivate").equals("Y"), rs.getString("isFilter").equals("Y"));					
			}				
		};
		
		return databaseExecutor.getResultSet(GET, criteriaRowMapper, owner, key);		
	}
}
