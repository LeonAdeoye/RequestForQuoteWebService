package com.leon.ws.rfq.group;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService(serviceName="GroupController", endpointInterface="com.leon.ws.rfq.group.GroupController")
public class GroupControllerImpl implements GroupController
{
	private GroupManagerDao dao;
	
	@Override
	@WebMethod(exclude = true)
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
	public boolean updateValidity(int groupId, boolean isValid, String updatedByUser)
	{
		if(updatedByUser.isEmpty() || (updatedByUser == null))
			throw new IllegalArgumentException("updatedByUser");
		
		return this.dao.updateValidity(groupId, isValid, updatedByUser);
	}

	@Override
	public List<GroupDetailImpl> getAll()
	{
		return getAll();
	}

}
