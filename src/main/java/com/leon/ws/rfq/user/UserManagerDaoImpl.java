package com.leon.ws.rfq.user;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import com.leon.ws.rfq.database.GenericDatabaseCommandExecutor;

public class UserManagerDaoImpl implements UserManagerDao
{
	private static final String DELETE_USER = "CALL users_DELETE (?)";
	private static final String SAVE_USER = "CALL users_SAVE (?, ?, ?, ?, ?, ?, ?)";
	private static final String UPDATE_USER_VALIDITY = "CALL users_UPDATE_VALIDITY (?, ?, ?)";
	private static final String SELECT_ALL_USERS = "CALL users_SELECT_ALL";
	private static final String SELECT_USERS_BY_LOCATION = "CALL users_SELECT_BY_LOCATION (?)";
	private static final String SELECT_USERS_BY_GROUP = "CALL users_SELECT_BY_GROUP (?)";
	private static final String SELECT_USER_BY_EMAIL_ADDRESS = "CALL users_SELECT_BY_EMAIL_ADDRESS (?)";
	private static final String SELECT_USER_BY_USER_ID = "CALL users_SELECT_BY_USER_ID (?)";
	
	private GenericDatabaseCommandExecutor databaseExecutor;

	private class UserDetailParameterizedRowMapper implements ParameterizedRowMapper<UserDetailImpl>
	{
		@Override
		public UserDetailImpl mapRow(ResultSet rs, int rowNum) throws SQLException
		{
			UserDetailImpl userDetailImpl = new UserDetailImpl(rs.getString("userId"), rs.getString("emailAddress"),
					rs.getString("firstName"), rs.getString("lastName"), rs.getString("locationName"), rs.getInt("groupId")
					, rs.getString("isValid").equals("Y"));

			return userDetailImpl;
		}
	}

	UserManagerDaoImpl() {}

	UserManagerDaoImpl(GenericDatabaseCommandExecutor databaseExecutor)
	{
		this.databaseExecutor = databaseExecutor;
	}

	public void setDatabaseCommandExecutor(GenericDatabaseCommandExecutor databaseExecutor)
	{
		this.databaseExecutor = databaseExecutor;
	}

	@Override
	public boolean delete(String userId)
	{
		return this.databaseExecutor.<UserDetailImpl>executePreparedStatement(DELETE_USER, userId);
	}

	@Override
	public UserDetailImpl save(String userId, String firstName, String lastName, String emailAddress,
			 String locationName, int groupId, String savedByUser)
	{
		return this.databaseExecutor.<UserDetailImpl>getSingleResult(SAVE_USER, new UserDetailParameterizedRowMapper(),
				userId, firstName, lastName, emailAddress, locationName, groupId, savedByUser);
	}

	@Override
	public List<UserDetailImpl> getAll()
	{
		return this.databaseExecutor.<UserDetailImpl>getResultSet(SELECT_ALL_USERS, new UserDetailParameterizedRowMapper());
	}

	@Override
	public List<UserDetailImpl> getUsersByLocation(String locationName)
	{
		return this.databaseExecutor.<UserDetailImpl>getResultSet(SELECT_USERS_BY_LOCATION, new UserDetailParameterizedRowMapper(), locationName);
	}

	@Override
	public List<UserDetailImpl> getUsersByGroup(int groupId)
	{
		return this.databaseExecutor.<UserDetailImpl>getResultSet(SELECT_USERS_BY_GROUP, new UserDetailParameterizedRowMapper(), groupId);
	}

	@Override
	public UserDetailImpl getUserByEmailAddress(String emailAddress)
	{
		return this.databaseExecutor.<UserDetailImpl>getSingleResult(SELECT_USER_BY_EMAIL_ADDRESS, new UserDetailParameterizedRowMapper(), emailAddress);
	}

	@Override
	public UserDetailImpl getUserByUserId(String userId)
	{
		return this.databaseExecutor.<UserDetailImpl>getSingleResult(SELECT_USER_BY_USER_ID, new UserDetailParameterizedRowMapper(), userId);
	}

	@Override
	public boolean updateValidity(String userId, boolean isValid, String updatedByUser)
	{
		return this.databaseExecutor.<UserDetailImpl>executePreparedStatement(UPDATE_USER_VALIDITY, userId, (isValid ? 'Y' : 'N'), updatedByUser);
	}
}
