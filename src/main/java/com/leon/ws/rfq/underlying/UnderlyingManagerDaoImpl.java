package com.leon.ws.rfq.underlying;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import com.leon.ws.rfq.database.GenericDatabaseCommandExecutor;

public class UnderlyingManagerDaoImpl implements UnderlyingManagerDao
{
	private static final String SELECT_ALL = "CALL underlyings_SELECT_ALL";
	private static final String UPDATE_VALIDITY = "CALL underlyings_UPDATE_VALIDITY (?, ?, ?)";
	private static final String SAVE = "CALL underlyings_SAVE (?, ?, ?)";

	private GenericDatabaseCommandExecutor databaseExecutor;

	private class UnderlyingDetailParameterizedRowMapper implements ParameterizedRowMapper<UnderlyingDetailImpl>
	{
		@Override
		public UnderlyingDetailImpl mapRow(ResultSet rs, int rowNum) throws SQLException
		{
			UnderlyingDetailImpl newUnderlying = new UnderlyingDetailImpl(rs.getString("ric"),
					rs.getString("description"), rs.getString("isValid").equals("Y"));

			return newUnderlying;
		}
	}

	public UnderlyingManagerDaoImpl() {}

	public void setDatabaseCommandExecutor(GenericDatabaseCommandExecutor databaseExecutor)
	{
		if(databaseExecutor == null)
			throw new NullPointerException("databaseExecutor");

		this.databaseExecutor = databaseExecutor;
	}

	@Override
	public UnderlyingDetailImpl save(String ric, String description, String savedBy)
	{
		return this.databaseExecutor.<UnderlyingDetailImpl>getSingleResult(SAVE, new UnderlyingDetailParameterizedRowMapper(), ric, description, savedBy);
	}

	@Override
	public boolean updateValidity(String ric, boolean isValid, String updatedBy)
	{
		return  this.databaseExecutor.<UnderlyingDetailImpl>executePreparedStatement(UPDATE_VALIDITY, ric, isValid ? "Y" : "N", updatedBy);
	}

	@Override
	public List<UnderlyingDetailImpl> getAll()
	{
		return this.databaseExecutor.<UnderlyingDetailImpl>getResultSet(SELECT_ALL, new UnderlyingDetailParameterizedRowMapper());
	}
}
