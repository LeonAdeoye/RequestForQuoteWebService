package com.leon.ws.rfq.group;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

@WebService(name="GroupController")
public interface GroupController
{
	void setGroupManagerDao(GroupManagerDao dao);

	@WebMethod
	boolean delete(@WebParam(name="groupId") int groupId);

	@WebMethod
	boolean save(@WebParam(name="groupId")int groupId,
			@WebParam(name="groupName") String groupName,
			@WebParam(name="savedByUser") String savedByUser);

	@WebMethod
	boolean updateValidity(@WebParam(name="groupId") int groupId,
			@WebParam(name="isValid") boolean isValid,
			@WebParam(name="updatedByUser") String updatedByUser);

	@WebMethod
	List<GroupDetailImpl> getAll();
}
