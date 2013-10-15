package com.leon.ws.rfq.holiday;

import java.util.List;

interface HolidayDao
{
	boolean delete(String location, String holidayDate);
	boolean save(String location, String holidayDate, String updatedByUser);
	List<HolidayImpl> getAll();
	List<HolidayImpl> get(String location);
}
