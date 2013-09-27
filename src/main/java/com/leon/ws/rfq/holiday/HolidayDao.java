package com.leon.ws.rfq.holiday;

import java.util.Calendar;
import java.util.List;
import com.leon.ws.rfq.holiday.HolidayImpl;

interface HolidayDao
{
	boolean delete(String location, Calendar holidayDate);
	boolean save(String location, Calendar holidayDate, String updatedByUser);
	List<HolidayImpl> getAll();
	List<HolidayImpl> get(String location);
}
