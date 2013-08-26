
package com.leon.ws.rfq.book;

import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.Oneway;


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
	@Oneway
	public void delete(String bookCode)
	{
		dao.delete(bookCode);
	}
	
	@WebMethod
	@Oneway
	public void save(String bookCode, String entity, String updatedByUser)
	{
		dao.save(bookCode, entity, updatedByUser);
	}
		
	@WebMethod
	@Oneway
	public void updateValidity(String bookCode, boolean isValid)
	{
		dao.updateValidity(bookCode, isValid);
	}
	
	@WebMethod
	public List<BookDetail> getAll()
	{
        return dao.getAll();        
	}
}
