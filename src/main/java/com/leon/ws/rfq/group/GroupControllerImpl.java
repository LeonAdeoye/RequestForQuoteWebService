package com.leon.ws.rfq.group;

import java.util.List;

public class GroupControllerImpl implements GroupController
{
	private GroupManagerDao dao;
	
	@Override
	public void setGroupManagerDao(GroupManagerDao dao)
	{
		this.dao = dao;
	}

	@Override
	public boolean delete(int groupId)
	{
		return this.dao.delete(groupId);
	}

	@Override
	public boolean save(int groupId, String groupName, String savedByUser)
	{
		if(groupName.isEmpty() || (groupName == null))
			throw new IllegalArgumentException("groupName");
		
		if(savedByUser.isEmpty() || (savedByUser == null))
			throw new IllegalArgumentException("savedByUser");
		
		return this.dao.save(groupId, groupName, savedByUser) != null;
	}

	@Override
	public boolean updateValidity(int groupId, boolean isValid)
	{
		return this.dao.updateValidity(groupId, isValid);
	}

	@Override
	public List<GroupDetailImpl> getAll()
	{
		return getAll();
	}

}
