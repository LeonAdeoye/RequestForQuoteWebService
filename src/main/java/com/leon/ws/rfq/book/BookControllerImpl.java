package com.leon.ws.rfq.book;

import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@WebService(serviceName="BookController", endpointInterface="com.leon.ws.rfq.book.BookController")
public class BookControllerImpl implements BookController
{
	private static Logger logger = LoggerFactory.getLogger(BookControllerImpl.class);
	private BookManagerDao dao;
	
	public void setBookManagerDao(BookManagerDao dao)
	{
		this.dao = dao;
	}
	
	public BookControllerImpl() {}
	
	@WebMethod
	public boolean delete(String bookCode)
	{
		if(logger.isDebugEnabled())
			logger.debug("Received request to delete the book with book code [" + bookCode + "]");		
		
		return dao.delete(bookCode);
	}
	
	@WebMethod
	public boolean save(String bookCode, String entity, String savedByUser)
	{
		if(logger.isDebugEnabled())
			logger.debug("Received request from user " + savedByUser + " to save book with book code [" + bookCode + "] and entity [" + entity + "].");		
		
		return dao.save(bookCode, entity, savedByUser);
	}
		
	@WebMethod
	public boolean updateValidity(String bookCode, boolean isValid)
	{
		if(logger.isDebugEnabled())
			logger.debug("Received request to update the validity of book with book code [" + bookCode + "] to [" + (isValid ? "valid" : "invalid") + "].");				
		
		return dao.updateValidity(bookCode, isValid);
	}
	
	@WebMethod
	public List<BookDetailImpl> getAll()
	{
		if(logger.isDebugEnabled())
			logger.debug("Received request to get a list of all books.");				
		
        return dao.getAll();        
	}
}
