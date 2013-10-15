package com.leon.ws.rfq.holiday;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

@WebService(name="HolidayController")
public interface HolidayController
{
	@WebMethod
	boolean delete(@WebParam(name="location") String location, @WebParam(name="holidayDate") String holidayDate);

	@WebMethod
	boolean save(@WebParam(name="location")String location,
			@WebParam(name="holidayDate") String holidayDate,
			@WebParam(name="addedByUser") String addedByUser);

	@WebMethod
	List<HolidayImpl> getAll();

	@WebMethod
	List<HolidayImpl> get(@WebParam(name="location")String location);
}
