package com.leon.ws.rfq.user;

import java.util.List;

public interface UserManagerDao
{
	boolean delete(String userId);
	UserDetailImpl save(String userId, String firstName, String lastName, int locationId, int groupId, String emailAddress, String savedByUser);
	List<UserDetailImpl> getAll();
	List<UserDetailImpl> getByLocation(int locationId);
	List<UserDetailImpl> getByGroup(int groupId);
	UserDetailImpl getByEmailAddress(String emailAddress);
	UserDetailImpl getByUserId(String userId);
	boolean updateValidity(String userId, boolean isValid);
}
