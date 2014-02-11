package com.leon.ws.rfq.group;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import com.leon.ws.rfq.database.GenericDatabaseCommandExecutor;
import com.leon.ws.rfq.user.UserDetailImpl;

public class GroupManagerDaoImpl implements GroupManagerDao
{
	private static final String DELETE_GROUP = "CALL groups_DELETE (?)";
	private static final String SAVE_GROUP = "CALL groups_SAVE (?, ?)";
	private static final String UPDATE_GROUP_VALIDITY = "CALL groups_UPDATE_VALIDITY (?, ?, ?)";
	private static final String SELECT_ALL_GROUPS = "CALL groups_SELECT_ALL";
	private static final String SELECT_GROUP_BY_GROUP_ID = "CALL groups_SELECT_BY_GROUP_ID (?)";
	
	private GenericDatabaseCommandExecutor databaseExecutor;

	private class GroupDetailParameterizedRowMapper implements ParameterizedRowMapper<GroupDetailImpl>
	{
		@Override
		public GroupDetailImpl mapRow(ResultSet rs, int rowNum) throws SQLException
		{
			GroupDetailImpl groupDetailImpl = new GroupDetailImpl(rs.getInt("groupId"), rs.getString("groupName"), rs.getString("isValid").equals("Y"));

			return groupDetailImpl;
		}
	}
	
	GroupManagerDaoImpl() {}

	GroupManagerDaoImpl(GenericDatabaseCommandExecutor databaseExecutor)
	{
		this.databaseExecutor = databaseExecutor;
	}

	public void setDatabaseCommandExecutor(GenericDatabaseCommandExecutor databaseExecutor)
	{
		this.databaseExecutor = databaseExecutor;
	}
	
	@Override
	public List<GroupDetailImpl> getAll()
	{
		return this.databaseExecutor.<GroupDetailImpl>getResultSet(SELECT_ALL_GROUPS, new GroupDetailParameterizedRowMapper());
	}

	@Override
	public GroupDetailImpl getGroupByGroupId(int groupId)
	{
		return this.databaseExecutor.<GroupDetailImpl>getSingleResult(SELECT_GROUP_BY_GROUP_ID, new GroupDetailParameterizedRowMapper(), groupId);
	}

	@Override
	public boolean updateValidity(int groupId, boolean isValid, String updatedByUser)
	{
		return this.databaseExecutor.<UserDetailImpl>executePreparedStatement(UPDATE_GROUP_VALIDITY, groupId, (isValid ? "Y" : "N"), updatedByUser);
	}

	@Override
	public boolean delete(int groupId)
	{
		return this.databaseExecutor.<GroupDetailImpl>executePreparedStatement(DELETE_GROUP, groupId);
	}

	@Override
	public GroupDetailImpl save(String groupName, String savedByUser)
	{
		return this.databaseExecutor.<GroupDetailImpl>getSingleResult(SAVE_GROUP, new GroupDetailParameterizedRowMapper(),
				 groupName, savedByUser);
	}

}
