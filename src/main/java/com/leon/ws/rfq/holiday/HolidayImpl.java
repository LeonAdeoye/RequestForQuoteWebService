package com.leon.ws.rfq.holiday;

import java.util.Date;
import javax.xml.bind.annotation.XmlRootElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@XmlRootElement(name="HolidayImpl")
public class HolidayImpl 
{
	private static Logger logger = LoggerFactory.getLogger(HolidayImpl.class);
	private String location;
	private Date holidayDate;

	public HolidayImpl(String location, Date holidayDate)
	{
		this.location = location;
		this.holidayDate = holidayDate;
		
		logger.debug("HolidayImpl object instantiated = > " +  this);
	}
	
	public HolidayImpl() {} 
	
	public String getLocation()
	{
		return this.location;
	}
	
	public void setLocation(String location)
	{
		this.location = location;
	}	
	
	public Date getHolidayDate()
	{
		return this.holidayDate;
	}
	
	public void setHolidayDate(Date holidayDate)
	{
		this.holidayDate = holidayDate;
	}		
		
	@Override	
	public String toString()
	{
		StringBuffer buf = new StringBuffer();
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
		result = 37 * result + (location == null ? 0 : location.hashCode());
		result = 37 * result + (holidayDate == null ? 0 : holidayDate.hashCode());
		return result;
	}	
}
