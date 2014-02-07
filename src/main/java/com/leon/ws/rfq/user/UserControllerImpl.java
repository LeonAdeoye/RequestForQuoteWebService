package com.leon.ws.rfq.user;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;

import com.leon.ws.rfq.events.NewUserEvent;

@WebService(serviceName="UserController", endpointInterface="com.leon.ws.rfq.user.UserController")
public class UserControllerImpl implements UserController, ApplicationEventPublisherAware
{
	private UserManagerDao dao;
	private static Logger logger = LoggerFactory.getLogger(UserControllerImpl.class);
	private ApplicationEventPublisher applicationEventPublisher;
	
	/**
	 * Default constructor.
	 */
	public UserControllerImpl() {}
	
	/**
	 * Sets the User Manager DAO object reference property.
	 * 
	 * @param dao 						the user manager dao for saving to the database.
	 * @throws NullPointerException 	if the dao parameter is null.
	 */
	@Override
	@WebMethod(exclude = true)
	public void setUserManagerDao(UserManagerDao dao)
	{
		if(dao == null)
			throw new NullPointerException("dao");
		
		this.dao = dao;
	}

	/**
	 * Deletes the user from the database.
	 * 
	 * @param userId 						the identifier of the user to be deleted.
	 * @returns	true if the delete was successful; false otherwise.
	 * @throws IllegalArgumentException 	if the userId parameter is an empty string.
	 */
	@Override
	public boolean delete(String userId)
	{
		if(userId.isEmpty() || (userId == null))
			throw new IllegalArgumentException("userId");
		
		if(logger.isDebugEnabled())
			logger.debug("Received request to delete the user with user id [" + userId + "]");
		
		return this.dao.delete(userId);
	}

	/**
	 * Saves the user to the database and publishes an event for the listening client communicator.
	 * 
	 * @param userId 						the identifier of the user to be saved.
	 * @param firstName						the first name of the user to be saved.
	 * @param lastName						the last name of the user to be saved.
	 * @param emailAddress					the emailAddress of the user to be saved.
	 * @param savedByUser					the user who is saving the user.
	 * @returns	true if the save was successful; false otherwise.
	 * @throws IllegalArgumentException 	if the string parameters are empty or null.
	 */
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
			
		if(logger.isDebugEnabled())
			logger.debug("Received request from user " + savedByUser + " to save user with userId [" + userId + "] and first name [" + firstName + "].");

		UserDetailImpl newUser = this.dao.save(userId, firstName, lastName, emailAddress, locationName, groupId, savedByUser);

		if(newUser != null)
			this.applicationEventPublisher.publishEvent(new NewUserEvent(this, newUser));

		return (newUser != null);
	}

	/**
	 * Updates the validity of the user in the database.
	 * 
	 * @param userId 						the identifier of the user to be updated.
	 * @param isValid						the validity of the user to be updated.
	 * @returns	true if the update was successful; false otherwise.
	 * @throws IllegalArgumentException 	if the userId or updatedByuser parameter are empty or null.
	 */
	@Override
	public boolean updateValidity(String userId, boolean isValid, String updatedByUser)
	{
		if(userId.isEmpty() || (userId == null))
			throw new IllegalArgumentException("userId");
		
		if(updatedByUser.isEmpty() || (updatedByUser == null))
			throw new IllegalArgumentException("updatedByUser");
		
		if(logger.isDebugEnabled())
			logger.debug("Received request to update the validity of user with userId [" + userId + "] to [" + (isValid ? "valid" : "invalid") + "].");
				
		return this.dao.updateValidity(userId, isValid, updatedByUser);
	}

	/**
	 * Gets all users previously saved to the database.
	 * @returns a list of users that were previously saved in the database.
	 */
	@Override
	public List<UserDetailImpl> getAll()
	{
		return this.dao.getAll();
	}

	/**
	 * Sets the application event publisher.
	 * 
	 * @param applicationEventPublisher 	the applicationEventPublisher for publishing events.
	 * @throws NullPointerException 		if the applicationEventPublisher parameter is null.
	 */
	@Override
	public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher)
	{
		if(applicationEventPublisher == null)
			throw new NullPointerException("applicationEventPublisher");

		this.applicationEventPublisher = applicationEventPublisher;
	}
}
