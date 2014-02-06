package com.leon.ws.rfq.user;

import java.util.List;

public interface UserManagerDao
{
	List<UserDetailImpl> getAll();
	List<UserDetailImpl> getUsersByLocation(String locationName);
	List<UserDetailImpl> getUsersByGroup(int groupId);
	UserDetailImpl getUserByEmailAddress(String emailAddress);
	UserDetailImpl getUserByUserId(String userId);
	boolean updateValidity(String userId, boolean isValid);
	boolean delete(String userId);
	UserDetailImpl save(String userId, String firstName, String lastName, String emailAddress,
			 String locationName, int groupId, String savedByUser);
}
