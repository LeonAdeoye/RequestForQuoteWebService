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
	private GenericDatabaseCommandExecutor<SearchCriterionImpl> databaseExecutor;
	
	SearchManagerDaoImpl()
	{
		
	}
	
	SearchManagerDaoImpl(GenericDatabaseCommandExecutor<SearchCriterionImpl> databaseExecutor)
	{
		this.databaseExecutor = databaseExecutor;
	}
	
	public void setDatabaseCommandExecutor(GenericDatabaseCommandExecutor<SearchCriterionImpl> databaseExecutor)
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
	
	public List<SearchCriterionImpl> getAll()
	{		
		ParameterizedRowMapper<SearchCriterionImpl> criteriaRowMapper = new ParameterizedRowMapper<SearchCriterionImpl>() 
		{
			public SearchCriterionImpl mapRow(ResultSet rs, int rowNum) throws SQLException
			{
				return new SearchCriterionImpl(rs.getString("owner"), rs.getString("keyValue"), rs.getString("controlName"), 
						rs.getString("controlValue"), rs.getString("isPrivate").equals("Y"), rs.getString("isFilter").equals("Y"));					
			}				
		};
		
		return databaseExecutor.getResultSet(SELECT_ALL, criteriaRowMapper);	
	}
	
	public List<SearchCriterionImpl> get(String owner, String key)
	{	
		ParameterizedRowMapper<SearchCriterionImpl> criteriaRowMapper = new ParameterizedRowMapper<SearchCriterionImpl>() 
		{
			public SearchCriterionImpl mapRow(ResultSet rs, int rowNum) throws SQLException
			{
				return new SearchCriterionImpl(rs.getString("owner"), rs.getString("keyValue"), rs.getString("controlName"), 
						rs.getString("controlValue"), rs.getString("isPrivate").equals("Y"), rs.getString("isFilter").equals("Y"));					
			}				
		};
		
		return databaseExecutor.getResultSet(GET, criteriaRowMapper, owner, key);		
	}
}
