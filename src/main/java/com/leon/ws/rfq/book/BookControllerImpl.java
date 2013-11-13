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

	/**
	 * Sets the Book Manager DAO object reference property.
	 * 
	 * @param dao 						the book manager dao for saving to the database.
	 * @throws NullPointerException 	if the dao parameter is null.
	 */
	@Override
	@WebMethod(exclude = true)
	public void setBookManagerDao(BookManagerDao dao)
	{
		if(dao == null)
			throw new NullPointerException("dao");

		this.dao = dao;
	}

	/**
	 * Sets the application event publisher.
	 * 
	 * @param applicationEventPublisher 	the applicationEventPublisher for publishing events.
	 * @throws NullPointerException 		if the applicationEventPublisher parameter is null.
	 */
	@Override
	public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher)
	{
		if(applicationEventPublisher == null)
			throw new NullPointerException("applicationEventPublisher");

		this.applicationEventPublisher = applicationEventPublisher;
	}

	/**
	 * Default constructor.
	 */
	public BookControllerImpl() {}


	/**
	 * Deletes the book from the database.
	 * 
	 * @param bookCode 						the bookCode to be deleted.
	 * @returns	true if the delete was successful; false otherwise.
	 * @throws IllegalArgumentException 	if the bookCode parameter is an empty string.
	 */
	@Override
	@WebMethod
	public boolean delete(String bookCode)
	{
		if(bookCode.isEmpty())
			throw new IllegalArgumentException("bookCode");

		if(logger.isDebugEnabled())
			logger.debug("Received request to delete the book with book code [" + bookCode + "]");

		return this.dao.delete(bookCode);
	}

	/**
	 * Saves the book to the database and publishes an event for the listening client communicator.
	 * 
	 * @param bookCode 						the bookCode of the book to be saved.
	 * @param entity						the entity of the book to be saved.
	 * @param savedBy						the user who is saving the book.
	 * @returns	true if the save was successful; false otherwise.
	 * @throws IllegalArgumentException 	if the bookCode or entity or savedBy parameter is an empty string.
	 */
	@Override
	@WebMethod
	public boolean save(String bookCode, String entity, String savedBy)
	{
		if(bookCode.isEmpty())
			throw new IllegalArgumentException("bookCode");

		if(entity.isEmpty())
			throw new IllegalArgumentException("entity");

		if(savedBy.isEmpty())
			throw new IllegalArgumentException("savedBy");

		if(logger.isDebugEnabled())
			logger.debug("Received request from user " + savedBy + " to save book with book code [" + bookCode + "] and entity [" + entity + "].");

		BookDetailImpl newBook = this.dao.save(bookCode, entity, savedBy);

		if(newBook != null)
			this.applicationEventPublisher.publishEvent(new NewBookEvent(this, newBook));

		return (newBook != null);
	}

	/**
	 * Updates the validity of the book in the database.
	 * 
	 * @param bookCode 						the bookCode of the book to be updated.
	 * @param isValid						the validity of the book to be updated.
	 * @returns	true if the update was successful; false otherwise.
	 * @throws IllegalArgumentException 	if the bookCode parameter is an empty string.
	 */
	@Override
	@WebMethod
	public boolean updateValidity(String bookCode, boolean isValid)
	{
		if(bookCode.isEmpty())
			throw new IllegalArgumentException("bookCode");

		if(logger.isDebugEnabled())
			logger.debug("Received request to update the validity of book with book code [" + bookCode + "] to [" + (isValid ? "valid" : "invalid") + "].");

		return this.dao.updateValidity(bookCode, isValid);
	}

	/**
	 * Gets all books previously saved to the database.
	 * @returns a list of books that were previously saved in the database.
	 */
	@Override
	@WebMethod
	public List<BookDetailImpl> getAll()
	{
		if(logger.isDebugEnabled())
			logger.debug("Received request to get a list of all books.");

		return this.dao.getAll();
	}
}
