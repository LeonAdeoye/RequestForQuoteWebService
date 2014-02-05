package com.leon.ws.rfq.user;

import java.util.List;

public class UserManagerDaoImpl implements UserManagerDao
{

	@Override
	public boolean delete(String userId)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public UserDetailImpl save(String userId, String firstName,
			String lastName, int locationId, int groupId, String emailAddress,
			String savedByUser)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<UserDetailImpl> getAll()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<UserDetailImpl> getByLocation(int locationId)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<UserDetailImpl> getByGroup(int groupId)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserDetailImpl getByEmailAddress(String emailAddress)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserDetailImpl getByUserId(String userId)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean updateValidity(String userId, boolean isValid)
	{
		// TODO Auto-generated method stub
		return false;
	}

}
