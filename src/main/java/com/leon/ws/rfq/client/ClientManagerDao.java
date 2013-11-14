package com.leon.ws.rfq.client;

import java.util.List;

public interface ClientManagerDao
{
	ClientDetailImpl save(String name, String tier, String savedBy);
	List<ClientDetailImpl> getAll();
	boolean updateTier(int identifier, String tier, String updatedBy);
	boolean updateValidity(int identifier, boolean isValid, String updatedBy);
}