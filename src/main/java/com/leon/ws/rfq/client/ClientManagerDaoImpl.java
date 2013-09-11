package com.leon.ws.rfq.client;

import java.util.List;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import com.leon.ws.rfq.database.GenericDatabaseCommandExecutor;
import java.sql.ResultSet;
import java.sql.SQLException;

public final class ClientManagerDaoImpl implements ClientManagerDao
{
	private static final String DELETE = "CALL clients_DELETE (?)";
	private static final String SAVE = "CALL clients_SAVE (?, ?)";
	private static final String UPDATE_TIER = "CALL clients_UPDATE_TIER (?, ?)";
	private static final String UPDATE_VALIDITY = "CALL clients_UPDATE_VALIDITY (?, ?)";
	private static final String SELECT_ALL = "CALL clients_SELECT_ALL";	
	private GenericDatabaseCommandExecutor<ClientDetail> databaseExecutor;
	
	ClientManagerDaoImpl()
	{
		
	}
	
	ClientManagerDaoImpl(GenericDatabaseCommandExecutor<ClientDetail> databaseExecutor)
	{
		this.databaseExecutor = databaseExecutor;
	}
	
	public void setDatabaseCommandExecutor(GenericDatabaseCommandExecutor<ClientDetail> databaseExecutor)
	{
		this.databaseExecutor = databaseExecutor;
	}
	
	public boolean delete(int identifier)
	{		
		return databaseExecutor.executePreparedStatement(DELETE, identifier);
	}
	
	public boolean save(String name, int tier)
	{	
		return databaseExecutor.executePreparedStatement(SAVE, name, tier);
	}
	
	public boolean updateTier(int identifier, int tier)
	{	
		return databaseExecutor.executePreparedStatement(UPDATE_TIER, identifier, tier);
	}
	
	public boolean updateValidity(int identifier, boolean isValid)
	{	
		return databaseExecutor.executePreparedStatement(UPDATE_VALIDITY, identifier, isValid ? "Y" : "N");
	}	
		
	public List<ClientDetail> getAll()
	{	
		ParameterizedRowMapper<ClientDetail> clientsRowMapper = new ParameterizedRowMapper<ClientDetail>() 
		{
			public ClientDetail mapRow(ResultSet rs, int rowNum) throws SQLException
			{
				return new ClientDetail(rs.getString("name"), rs.getInt("identifier"), rs.getInt("tier"), rs.getString("isValid").equals("Y"));					
			}				
		};
		
		return databaseExecutor.getResultSet(SELECT_ALL, clientsRowMapper);		
	}
}
