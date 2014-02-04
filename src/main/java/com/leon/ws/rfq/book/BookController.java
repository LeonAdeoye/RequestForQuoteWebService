// EndPoint interface for BookController public interface

package com.leon.ws.rfq.book;
import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

@WebService(name="BookController")
public interface BookController
{
	void setBookManagerDao(BookManagerDao dao);

	@WebMethod
	boolean delete(@WebParam(name="bookCode") String bookCode);

	@WebMethod
	boolean save(@WebParam(name="bookCode")String bookCode,
			@WebParam(name="entity") String entity,
			@WebParam(name="savedByUser") String savedByUser);

	@WebMethod
	boolean updateValidity(@WebParam(name="bookCode") String bookCode,
			@WebParam(name="isValid") boolean isValid);

	@WebMethod
	List<BookDetailImpl> getAll();
}

