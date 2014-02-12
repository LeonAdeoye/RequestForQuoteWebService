package com.leon.ws.rfq.group;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;

import com.leon.ws.rfq.events.NewGroupEvent;

@WebService(serviceName="GroupController", endpointInterface="com.leon.ws.rfq.group.GroupController")
public class GroupControllerImpl implements GroupController, ApplicationEventPublisherAware
{
	private GroupManagerDao dao;
	private static Logger logger = LoggerFactory.getLogger(GroupControllerImpl.class);
	private ApplicationEventPublisher applicationEventPublisher;
	
	/**
	 * Default constructor.
	 */
	GroupControllerImpl() {}

	/**
	 * Sets the Group Manager DAO object reference property.
	 * 
	 * @param dao 						the group manager dao for saving to the database.
	 * @throws NullPointerException 	if the dao parameter is null.
	 */
	@Override
	@WebMethod(exclude = true)
	public void setGroupManagerDao(GroupManagerDao dao)
	{
		if(dao == null)
			throw new NullPointerException("dao");
		
		this.dao = dao;
	}

	/**
	 * Deletes the group from the database.
	 * 
	 * @param groupId 						the identifier of the group to be deleted.
	 * @returns	true if the delete was successful; false otherwise.
	 */
	@Override
	public boolean delete(int groupId)
	{
		if(logger.isDebugEnabled())
			logger.debug("Received request to delete the group with group identifier [" + groupId + "]");
		
		return this.dao.delete(groupId);
	}

	/**
	 * Saves the group to the database and publishes an event for the listening client communicator.
	 * 
	 * @param groupName						the name of the group to be saved.
	 * @param savedByUser					the user who is saving the group.
	 * @returns	true if the save was successful; false otherwise.
	 * @throws IllegalArgumentException 	if the groupId or savedByUser parameter is an empty string.
	 */
	@Override
	public boolean save(String groupName, String savedByUser)
	{
		if(groupName.isEmpty() || (groupName == null))
			throw new IllegalArgumentException("groupName");
		
		if(savedByUser.isEmpty() || (savedByUser == null))
			throw new IllegalArgumentException("savedByUser");
		
		if(logger.isDebugEnabled())
			logger.debug("Received request from user " + savedByUser + " to save group with name [" + groupName + "].");

		GroupDetailImpl newGroup = this.dao.save(groupName, savedByUser);

		if(newGroup != null)
			this.applicationEventPublisher.publishEvent(new NewGroupEvent(this, newGroup));

		return (newGroup != null);
	}

	/**
	 * Updates the validity of the group in the database.
	 * 
	 * @param groupId 						the identifier of the group to be updated.
	 * @param isValid						the validity of the group to be updated.
	 * @returns	true if the update was successful; false otherwise.
	 * @throws IllegalArgumentException 	if the updatedByUser parameter is an empty string.
	 */
	@Override
	public boolean updateValidity(int groupId, boolean isValid, String updatedByUser)
	{
		if(updatedByUser.isEmpty() || (updatedByUser == null))
			throw new IllegalArgumentException("updatedByUser");
		
		if(logger.isDebugEnabled())
			logger.debug("Received request from user " + updatedByUser + " to update the validity of group with the identifier [" + groupId + "] to [" + (isValid ? "valid" : "invalid") + "].");
		
		
		return this.dao.updateValidity(groupId, isValid, updatedByUser);
	}

	/**
	 * Gets all groups previously saved to the database.
	 * @returns a list of groups that were previously saved in the database.
	 */
	@Override
	public List<GroupDetailImpl> getAll()
	{
		if(logger.isDebugEnabled())
			logger.debug("Received request to get a list of all groups.");
		
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
