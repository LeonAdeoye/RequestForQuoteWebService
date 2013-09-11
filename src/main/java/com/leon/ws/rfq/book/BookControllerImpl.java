package com.leon.ws.rfq.book;

import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService(serviceName="BookController", endpointInterface="com.leon.ws.rfq.book.BookController")
public class BookControllerImpl implements BookController
{
	private BookManagerDao dao;
	
	public void setBookManagerDao(BookManagerDao dao)
	{
		this.dao = dao;
	}
	
	public BookControllerImpl() {}
	
	@WebMethod
	public boolean delete(String bookCode)
	{
		return dao.delete(bookCode);
	}
	
	@WebMethod
	public boolean save(String bookCode, String entity, String updatedByUser)
	{
		return dao.save(bookCode, entity, updatedByUser);
	}
		
	@WebMethod
	public boolean updateValidity(String bookCode, boolean isValid)
	{
		return dao.updateValidity(bookCode, isValid);
	}
	
	@WebMethod
	public List<BookDetail> getAll()
	{
        return dao.getAll();        
	}
}
