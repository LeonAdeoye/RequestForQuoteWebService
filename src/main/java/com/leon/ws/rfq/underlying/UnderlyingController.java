package com.leon.ws.rfq.underlying;

import java.util.List;

import javax.jws.WebParam;
import javax.jws.WebService;

@WebService(name="UnderlyingController")
public interface UnderlyingController
{
	boolean save(@WebParam(name="RIC")String RIC,
			@WebParam(name="description")String description,
			@WebParam(name="savedBy")String savedBy);

	boolean updateValidity(@WebParam(name="RIC")String RIC,
			@WebParam(name="isValid")boolean isValid,
			@WebParam(name="updatedBy")String updatedBy);

	List<UnderlyingDetailImpl> getAll();
}
