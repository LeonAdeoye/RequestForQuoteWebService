package com.leon.ws.rfq.group;

import java.util.List;

public interface GroupManagerDao
{
	List<GroupDetailImpl> getAll();
	GroupDetailImpl getGroupByGroupId(int groupId);
	boolean updateValidity(int groupId, boolean isValid, String updatedByUser);
	boolean delete(int groupId);
	GroupDetailImpl save(String groupName, String savedByUser);
}
