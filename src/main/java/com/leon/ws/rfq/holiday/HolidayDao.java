package com.leon.ws.rfq.holiday;

import java.util.Calendar;
import java.util.List;
import com.leon.ws.rfq.holiday.Holiday;

public interface HolidayDao
{
	boolean delete(String location, Calendar holidayDate);
	boolean save(String location, Calendar holidayDate, String updatedByUser);
	List<Holiday> getAll();
	List<Holiday> get(String location);
}
