package com.leon.ws.rfq.client;

import java.util.List;

public interface ClientManagerDao 
{
	boolean delete(int identifier);
	boolean save(String name, int tier);
	List<ClientDetail> getAll();
	boolean updateTier(int identifier, int tier);
	boolean updateValidity(int identifier, boolean isValid);
}