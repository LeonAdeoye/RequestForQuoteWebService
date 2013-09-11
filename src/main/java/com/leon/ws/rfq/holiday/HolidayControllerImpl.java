package com.leon.ws.rfq.holiday;

import java.util.Calendar;
import java.util.List;
import com.leon.ws.rfq.holiday.HolidayDao;

public class HolidayControllerImpl implements HolidayController
{	
	private HolidayDao dao;
	
	public void setHolidayDao(HolidayDao dao)
	{
		this.dao = dao;
	}	

	@Override
	public boolean delete(String location, Calendar holidayDate)
	{
		return dao.delete(location, holidayDate);		
	}

	@Override
	public boolean save(String location, Calendar holidayDate, String updatedByUser)
	{
		return dao.save(location, holidayDate, updatedByUser);		
	}

	@Override
	public List<Holiday> getAll()
	{
		return dao.getAll();
	}
	
	@Override
	public List<Holiday> get(String location)
	{
		return dao.get(location);
	}	
}
