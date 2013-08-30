package com.leon.ws.rfq.client;

import java.util.List;

public interface ClientManagerDao 
{
	public boolean delete(int identifier);
	public boolean save(String name, int tier);
	public List<ClientDetail> getAll();
	public boolean updateTier(int identifier, int tier);
	public boolean updateValidity(int identifier, boolean isValid);
}