package com.leon.ws.rfq.search;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import com.leon.ws.rfq.database.GenericDatabaseCommandExecutor;

public final class SearchManagerDaoImpl implements SearchManagerDao
{
	private static final String DELETE = "CALL searches_DELETE (?, ?)";
	private static final String SAVE = "CALL searches_SAVE (?, ?, ?, ?, ?, ?)";
	private static final String SELECT_ALL = "CALL searches_SELECT_ALL";
	private static final String GET = "CALL searches_GET (?, ?)";
	private static final String UPDATE_PRIVACY = "CALL searches_UPDATE_PRIVACY (?, ?, ?)";
	private GenericDatabaseCommandExecutor databaseExecutor;

	private class SearchCriterionParameterizedRowMapper implements ParameterizedRowMapper<SearchCriterionImpl>
	{
		@Override
		public SearchCriterionImpl mapRow(ResultSet rs, int rowNum) throws SQLException
		{
			SearchCriterionImpl criterion = new SearchCriterionImpl(rs.getString("owner"), rs.getString("keyValue"),
					rs.getString("controlName"), rs.getString("controlValue"), rs.getString("isPrivate").equals("Y"),
					rs.getString("isFilter").equals("Y"));

			return criterion;
		}
	}

	SearchManagerDaoImpl()
	{

	}

	SearchManagerDaoImpl(GenericDatabaseCommandExecutor databaseExecutor)
	{
		this.databaseExecutor = databaseExecutor;
	}

	public void setDatabaseCommandExecutor(GenericDatabaseCommandExecutor databaseExecutor)
	{
		this.databaseExecutor = databaseExecutor;
	}

	@Override
	public boolean delete(String owner, String key)
	{
		return this.databaseExecutor.<SearchCriterionImpl>executePreparedStatement(DELETE, owner, key);
	}

	@Override
	public boolean updatePrivacy(String owner, String key, Boolean isPrivate)
	{
		return this.databaseExecutor.<SearchCriterionImpl>executePreparedStatement(UPDATE_PRIVACY, owner, key, isPrivate ? "Y" : "N");
	}

	@Override
	public SearchCriterionImpl save(String owner, String key, String controlName, String controlValue, Boolean isPrivate, Boolean isFilter)
	{
		return this.databaseExecutor.<SearchCriterionImpl>getSingleResult(SAVE, new SearchCriterionParameterizedRowMapper(),
				owner, key, controlName, controlValue, isPrivate ? "Y" : "N",  isFilter ? "Y" : "N");
	}

	@Override
	public List<SearchCriterionImpl> getAll()
	{
		return this.databaseExecutor.<SearchCriterionImpl>getResultSet(SELECT_ALL, new SearchCriterionParameterizedRowMapper());
	}

	@Override
	public List<SearchCriterionImpl> get(String owner, String key)
	{
		return this.databaseExecutor.<SearchCriterionImpl>getResultSet(GET, new SearchCriterionParameterizedRowMapper(), owner, key);
	}
}
