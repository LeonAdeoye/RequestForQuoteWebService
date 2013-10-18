package com.leon.ws.rfq.client;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import com.leon.ws.rfq.database.GenericDatabaseCommandExecutor;

public final class ClientManagerDaoImpl implements ClientManagerDao
{
	private class ClientParameterizedRowMapper implements ParameterizedRowMapper<ClientDetailImpl>
	{
		@Override
		public ClientDetailImpl mapRow(ResultSet rs, int rowNum) throws SQLException {
			ClientDetailImpl client = new ClientDetailImpl();
			client.setName(rs.getString("name"));
			client.setTier(rs.getString("tier"));
			client.setIdentifier(rs.getInt("identifier"));
			client.setIsValid((rs.getString("isValid").equals("Y")));
			return client;
		}
	}

	private static final String SAVE = "CALL clients_SAVE (?, ?, ?)";
	private static final String UPDATE_TIER = "CALL clients_UPDATE_TIER (?, ?, ?)";
	private static final String UPDATE_VALIDITY = "CALL clients_UPDATE_VALIDITY (?, ?, ?)";
	private static final String SELECT_ALL = "CALL clients_SELECT_ALL";
	private GenericDatabaseCommandExecutor databaseExecutor;

	ClientManagerDaoImpl()
	{

	}

	ClientManagerDaoImpl(GenericDatabaseCommandExecutor databaseExecutor)
	{
		this.databaseExecutor = databaseExecutor;
	}

	public void setDatabaseCommandExecutor(GenericDatabaseCommandExecutor databaseExecutor)
	{
		this.databaseExecutor = databaseExecutor;
	}

	@Override
	public ClientDetailImpl save(String name, String tier, String savedBy)
	{
		return this.databaseExecutor.<ClientDetailImpl>getSingleResult(SAVE, new ClientParameterizedRowMapper(), name, tier, savedBy);
	}

	@Override
	public boolean updateTier(int identifier, String tier, String updatedBy)
	{
		return this.databaseExecutor.<ClientDetailImpl>executePreparedStatement(UPDATE_TIER, identifier, tier, updatedBy);
	}

	@Override
	public boolean updateValidity(int identifier, boolean isValid, String updatedBy)
	{
		return this.databaseExecutor.<ClientDetailImpl>executePreparedStatement(UPDATE_VALIDITY, identifier, isValid ? "Y" : "N", updatedBy);
	}

	@Override
	public List<ClientDetailImpl> getAll()
	{
		return this.databaseExecutor.<ClientDetailImpl>getResultSet(SELECT_ALL, new ClientParameterizedRowMapper());
	}
}
