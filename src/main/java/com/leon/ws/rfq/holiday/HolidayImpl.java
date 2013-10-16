package com.leon.ws.rfq.holiday;

import javax.xml.bind.annotation.XmlRootElement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@XmlRootElement(name="HolidayImpl")
public final class HolidayImpl implements Holiday
{
	private static final Logger logger = LoggerFactory.getLogger(HolidayImpl.class);
	private String location;
	private String holidayDate;

	public HolidayImpl(String location, String holidayDate)
	{
		this.location = location;
		this.holidayDate = holidayDate;

		logger.debug("HolidayImpl object instantiated = > " +  this);
	}

	public HolidayImpl() {}

	@Override
	public String getLocation()
	{
		return this.location;
	}

	@Override
	public void setLocation(String location)
	{
		this.location = location;
	}

	@Override
	public String getHolidayDate()
	{
		return this.holidayDate;
	}

	@Override
	public void setHolidayDate(String holidayDate)
	{
		this.holidayDate = holidayDate;
	}

	@Override
	public String toString()
	{
		StringBuilder buf = new StringBuilder();
		buf.append("Location: ");
		buf.append(this.location);
		buf.append(", Date: ");
		buf.append(this.holidayDate);
		return buf.toString();
	}

	@Override
	public boolean equals(Object o)
	{
		if(this == o)
			return true;

		if(!(o instanceof HolidayImpl))
			return false;

		HolidayImpl param = (HolidayImpl) o;

		return 	this.location.equals(param.location) &&
				this.holidayDate.equals(param.holidayDate);
	}

	@Override
	public int hashCode()
	{
		int result = 17;
		result = (37 * result) + (this.location == null ? 0 : this.location.hashCode());
		result = (37 * result) + (this.holidayDate == null ? 0 : this.holidayDate.hashCode());
		return result;
	}
}
