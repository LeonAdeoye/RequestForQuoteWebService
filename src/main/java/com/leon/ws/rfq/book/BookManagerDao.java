package com.leon.ws.rfq.book;

import java.util.List;

public interface BookManagerDao 
{
	public boolean delete(String BookCode);
	public boolean save(String bookCode, String entity, String updatedByUser);
	public List<BookDetail> getAll();
	public boolean updateValidity(String BookCode, boolean isValid);
}