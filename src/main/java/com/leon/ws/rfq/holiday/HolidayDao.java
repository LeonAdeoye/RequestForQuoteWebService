package com.leon.ws.rfq.holiday;

import java.util.Calendar;
import java.util.List;
import com.leon.ws.rfq.holiday.Holiday;

public interface HolidayDao
{
	public boolean delete(String location, Calendar holidayDate);
	public boolean save(String location, Calendar holidayDate, String updatedByUser);
	public List<Holiday> getAll();
	public List<Holiday> get(String location);
}
