package com.leon.ws.rfq.client;

import java.util.List;

interface ClientManagerDao
{
	ClientDetailImpl save(String name, int tier, String savedBy);
	List<ClientDetailImpl> getAll();
	boolean updateTier(int identifier, int tier, String updatedBy);
	boolean updateValidity(int identifier, boolean isValid, String updatedBy);
}