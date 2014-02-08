package com.leon.ws.rfq.user;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

@WebService(name="UserController")
public interface UserController
{
	void setUserManagerDao(UserManagerDao dao);

	@WebMethod
	boolean delete(@WebParam(name="userId") String userId);

	@WebMethod
	boolean save(@WebParam(name="userId") String userId,
			@WebParam(name="firstName") String firstName,
			@WebParam(name="lastName") String lastName,
			@WebParam(name="emailAddress") String emailAddress,
			@WebParam(name="locationName") String locationName,
			@WebParam(name="groupId") int groupId,
			@WebParam(name="savedByUser") String savedByUser);

	@WebMethod
	boolean updateValidity(@WebParam(name="userId") String userId,
			@WebParam(name="isValid") boolean isValid,
			@WebParam(name="updatedByUser") String updatedByUser);

	@WebMethod
	List<UserDetailImpl> getAll();
}
