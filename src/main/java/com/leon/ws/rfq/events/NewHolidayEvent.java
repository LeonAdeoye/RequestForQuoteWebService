package com.leon.ws.rfq.events;

import org.springframework.context.ApplicationEvent;

import com.google.gson.Gson;
import com.leon.ws.rfq.holiday.Holiday;

@SuppressWarnings("serial")
public class NewHolidayEvent extends ApplicationEvent implements JsonSerializableEvent
{
	private final Holiday holiday;
	private static final String NEW_HOLIDAY_UPDATE = "NewHolidayUpdate";

	public NewHolidayEvent(Object source, Holiday holiday)
	{
		super(source);
		this.holiday = holiday;
	}

	@Override
	public String getJson()
	{
		return new Gson().toJson(this.holiday);
	}

	@Override
	public String getMessageType()
	{
		return NEW_HOLIDAY_UPDATE;
	}

	@Override
	public String toString()
	{
		return "New holiday event: " + this.holiday;
	}

	public Holiday getHoliday()
	{
		return this.holiday;
	}
}
