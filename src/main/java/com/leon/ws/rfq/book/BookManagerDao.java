package com.leon.ws.rfq.book;

import java.util.List;

public interface BookManagerDao
{
	boolean delete(String BookCode);
	BookDetailImpl save(String bookCode, String entity, String savedBy);
	List<BookDetailImpl> getAll();
	boolean updateValidity(String BookCode, boolean isValid);
}