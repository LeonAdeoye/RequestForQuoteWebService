package com.leon.ws.rfq.book;

import java.util.List;

interface BookManagerDao 
{
	boolean delete(String BookCode);
	boolean save(String bookCode, String entity, String updatedByUser);
	List<BookDetail> getAll();
	boolean updateValidity(String BookCode, boolean isValid);
}