// Created by Leon Adeoye
// EndPoint interface for BookController public interface

package com.leon.ws.rfq.book;
import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

@WebService(name="BookController")
public interface BookController 
{
	@WebMethod
	void delete(@WebParam(name="bookCode") String bookCode);
	
	@WebMethod
	void save(@WebParam(name="bookCode")String bookCode,
					@WebParam(name="entity") String entity,
					@WebParam(name="updatedByUser") String updatedByUser);
	
	@WebMethod
	void updateValidity(@WebParam(name="bookCode") String bookCode,
							@WebParam(name="isValid") boolean isValid);	
	
	@WebMethod
	List<BookDetail> getAll();
}

