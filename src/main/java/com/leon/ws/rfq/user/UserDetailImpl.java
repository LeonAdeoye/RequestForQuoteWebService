package com.leon.ws.rfq.user;

import javax.xml.bind.annotation.XmlRootElement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@XmlRootElement(name="UserDetailImpl")
public class UserDetailImpl
{
	private static final Logger logger = LoggerFactory.getLogger(UserDetailImpl.class);
	private String userId;
	private String emailAddress;
	private String firstName;
	private String lastName;
	private String locationName;
	private int groupId;
	
	public UserDetailImpl() {}
	
	public UserDetailImpl(String userId, String emailAddress, String firstName,
			String lastName, String locationName, int groupId)
	{
		this.userId = userId;
		this.emailAddress = emailAddress;
		this.firstName = firstName;
		this.lastName = lastName;
		this.locationName = locationName;
		this.groupId = groupId;
		
		logger.debug("UserDetailImpl object instantiated => " +  this);
	}
	/**
	 * @return the userId
	 */
	public String getUserId()
	{
		return this.userId;
	}
	/**
	 * @param userId the userId to set
	 */
	public void setUserId(String userId)
	{
		this.userId = userId;
	}
	/**
	 * @return the emailAddress
	 */
	public String getEmailAddress()
	{
		return this.emailAddress;
	}
	/**
	 * @param emailAddress the emailAddress to set
	 */
	public void setEmailAddress(String emailAddress)
	{
		this.emailAddress = emailAddress;
	}
	/**
	 * @return the firstName
	 */
	public String getFirstName()
	{
		return this.firstName;
	}
	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName)
	{
		this.firstName = firstName;
	}
	/**
	 * @return the lastName
	 */
	public String getLastName()
	{
		return this.lastName;
	}
	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName)
	{
		this.lastName = lastName;
	}
	/**
	 * @return the locationName
	 */
	public String getLocationName()
	{
		return this.locationName;
	}
	/**
	 * @param locationName the locationName to set
	 */
	public void setLocationName(String locationName)
	{
		this.locationName = locationName;
	}
	/**
	 * @return the groupId
	 */
	public int getGroupId()
	{
		return this.groupId;
	}
	/**
	 * @param groupId the groupId to set
	 */
	public void setGroupId(int groupId)
	{
		this.groupId = groupId;
	}

	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		builder.append("UserDetailImpl [userId=");
		builder.append(this.userId);
		builder.append(", emailAddress=");
		builder.append(this.emailAddress);
		builder.append(", firstName=");
		builder.append(this.firstName);
		builder.append(", lastName=");
		builder.append(this.lastName);
		builder.append(", locationName=");
		builder.append(this.locationName);
		builder.append(", groupId=");
		builder.append(this.groupId);
		builder.append("]");
		return builder.toString();
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = (prime * result)	+ ((this.emailAddress == null) ? 0 : this.emailAddress.hashCode());
		result = (prime * result)	+ ((this.firstName == null) ? 0 : this.firstName.hashCode());
		result = (prime * result)	+ ((this.lastName == null) ? 0 : this.lastName.hashCode());
		result = (prime * result)	+ ((this.locationName == null) ? 0 : this.locationName.hashCode());
		result = (prime * result) + ((this.userId == null) ? 0 : this.userId.hashCode());
		result = (prime * result) + this.groupId;
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		
		if (obj == null)
			return false;
		
		if (!(obj instanceof UserDetailImpl))
			return false;
		
		UserDetailImpl other = (UserDetailImpl) obj;
		
		if (this.emailAddress == null)
		{
			if (other.emailAddress != null)
				return false;
		} else if (!this.emailAddress.equals(other.emailAddress))
			return false;
		
		if (this.firstName == null)
		{
			if (other.firstName != null)
				return false;
		} else if (!this.firstName.equals(other.firstName))
			return false;
		
		if (this.groupId != other.groupId)
			return false;
		
		if (this.lastName == null)
		{
			if (other.lastName != null)
				return false;
		} else if (!this.lastName.equals(other.lastName))
			return false;
		
		if (this.locationName == null)
		{
			if (other.locationName != null)
				return false;
		} else if (!this.locationName.equals(other.locationName))
			return false;
		
		if (this.userId == null)
		{
			if (other.userId != null)
				return false;
		} else if (!this.userId.equals(other.userId))
			return false;
		
		return true;
	}
	
	
}
