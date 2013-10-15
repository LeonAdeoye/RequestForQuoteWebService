package com.leon.ws.rfq.holiday;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;

import com.leon.ws.rfq.events.NewHolidayEvent;

public final class HolidayControllerImpl implements HolidayController, ApplicationEventPublisherAware
{
	private static Logger logger = LoggerFactory.getLogger(HolidayControllerImpl.class);
	private HolidayDao dao;
	private ApplicationEventPublisher applicationEventPublisher;

	public void setHolidayDao(HolidayDao dao)
	{
		if(dao == null)
			throw new IllegalArgumentException("dao");

		this.dao = dao;
	}

	@Override
	public boolean delete(String location, String holidayDate)
	{
		if(location.isEmpty())
			throw new IllegalArgumentException("location");

		if(holidayDate == null)
			throw new IllegalArgumentException("holidayDate");

		return this.dao.delete(location, holidayDate);
	}

	@Override
	public boolean save(String location, String holidayDate, String savedBy)
	{
		if(location.isEmpty())
			throw new IllegalArgumentException("location");

		if(holidayDate == null)
			throw new IllegalArgumentException("holidayDate");

		if(savedBy.isEmpty())
			throw new IllegalArgumentException("savedBy");

		if(logger.isDebugEnabled())
			logger.debug("Received request from user [" + savedBy + "] to save holida with date [" + holidayDate + "].");

		boolean isSaved = this.dao.save(location, holidayDate, savedBy);

		if(isSaved)
			this.applicationEventPublisher.publishEvent(new NewHolidayEvent(this, new HolidayImpl(location, holidayDate)));

		return isSaved;
	}

	@Override
	public List<HolidayImpl> getAll()
	{
		return this.dao.getAll();
	}

	@Override
	public List<HolidayImpl> get(String location)
	{
		if(location.isEmpty())
			throw new IllegalArgumentException("location");

		return this.dao.get(location);
	}

	@Override
	public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher)
	{
		if(applicationEventPublisher == null)
			throw new IllegalArgumentException("applicationEventPublisher");

		this.applicationEventPublisher = applicationEventPublisher;
	}
}
