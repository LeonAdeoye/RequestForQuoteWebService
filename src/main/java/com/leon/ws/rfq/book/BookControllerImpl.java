package com.leon.ws.rfq.book;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;

import com.leon.ws.rfq.events.NewBookEvent;


@WebService(serviceName="BookController", endpointInterface="com.leon.ws.rfq.book.BookController")
public final class BookControllerImpl implements BookController, ApplicationEventPublisherAware
{
	private static Logger logger = LoggerFactory.getLogger(BookControllerImpl.class);
	private ApplicationEventPublisher applicationEventPublisher;
	private BookManagerDao dao;

	public void setBookManagerDao(BookManagerDao dao)
	{
		this.dao = dao;
	}

	@Override
	public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher)
	{
		this.applicationEventPublisher = applicationEventPublisher;
	}

	public BookControllerImpl() {}

	@Override
	@WebMethod
	public boolean delete(String bookCode)
	{
		if(logger.isDebugEnabled())
			logger.debug("Received request to delete the book with book code [" + bookCode + "]");

		return this.dao.delete(bookCode);
	}

	@Override
	@WebMethod
	public boolean save(String bookCode, String entity, String savedByUser)
	{
		if(logger.isDebugEnabled())
			logger.debug("Received request from user " + savedByUser + " to save book with book code [" + bookCode + "] and entity [" + entity + "].");

		boolean isSaved = this.dao.save(bookCode, entity, savedByUser);

		if(isSaved)
			this.applicationEventPublisher.publishEvent(new NewBookEvent(this, new BookDetailImpl(bookCode, entity, true)));

		return isSaved;
	}

	@Override
	@WebMethod
	public boolean updateValidity(String bookCode, boolean isValid)
	{
		if(logger.isDebugEnabled())
			logger.debug("Received request to update the validity of book with book code [" + bookCode + "] to [" + (isValid ? "valid" : "invalid") + "].");

		return this.dao.updateValidity(bookCode, isValid);
	}

	@Override
	@WebMethod
	public List<BookDetailImpl> getAll()
	{
		if(logger.isDebugEnabled())
			logger.debug("Received request to get a list of all books.");

		return this.dao.getAll();
	}
}
