package com.leon.ws.rfq.user;

import java.util.List;

public class UserControllerImpl implements UserController
{
	private UserManagerDao dao;
	
	@Override
	public void setUserManagerDao(UserManagerDao dao)
	{
		this.dao = dao;
	}

	@Override
	public boolean delete(String userId)
	{
		if(userId.isEmpty() || (userId == null))
			throw new IllegalArgumentException("userId");
		
		return this.dao.delete(userId);
	}

	@Override
	public boolean save(String userId, String firstName, String lastName, String emailAddress,
			 String locationName, int groupId, String savedByUser)
	{
		if(userId.isEmpty() || (userId == null))
			throw new IllegalArgumentException("userId");
		
		if(firstName.isEmpty() || (firstName == null))
			throw new IllegalArgumentException("firstName");
		
		if(lastName.isEmpty() || (lastName == null))
			throw new IllegalArgumentException("lastName");
		
		if(emailAddress.isEmpty() || (emailAddress == null))
			throw new IllegalArgumentException("emailAddress");
		
		if(locationName.isEmpty() || (locationName == null))
			throw new IllegalArgumentException("locationName");
		
		if(savedByUser.isEmpty() || (savedByUser == null))
			throw new IllegalArgumentException("savedByUser");
		
		return this.dao.save(userId, firstName, lastName, emailAddress, locationName, groupId, savedByUser) != null;
	}

	@Override
	public boolean updateValidity(String userId, boolean isValid)
	{
		if(userId.isEmpty() || (userId == null))
			throw new IllegalArgumentException("userId");
		
		return this.dao.updateValidity(userId, isValid);
	}

	@Override
	public List<UserDetailImpl> getAll()
	{
		return this.dao.getAll();
	}

}
