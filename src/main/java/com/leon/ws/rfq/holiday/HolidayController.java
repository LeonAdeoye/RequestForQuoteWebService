package com.leon.ws.rfq.holiday;

import java.util.Calendar;
import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

@WebService(name="HolidayController")
public interface HolidayController 
{
	@WebMethod
	public void delete(@WebParam(name="location") String location, @WebParam(name="holidayDate") Calendar holidayDate);
	
	@WebMethod
	public void save(@WebParam(name="location")String location,
					@WebParam(name="holidayDate") Calendar holidayDate,
					@WebParam(name="addedByUser") String addedByUser);
	
	@WebMethod
	public List<Holiday> getAll();
	
	@WebMethod
	public List<Holiday> get(@WebParam(name="location")String location);
}
