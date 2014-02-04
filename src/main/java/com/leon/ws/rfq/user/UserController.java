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
	boolean save(@WebParam(name="userId")String userId,
			@WebParam(name="firstName") String firstName,
			@WebParam(name="lastName") String lastName,
			@WebParam(name="savedByUser") String savedByUser);

	@WebMethod
	boolean updateValidity(@WebParam(name="userId") String userId,
			@WebParam(name="isValid") boolean isValid);

	@WebMethod
	List<UserDetailImpl> getAll();
}
