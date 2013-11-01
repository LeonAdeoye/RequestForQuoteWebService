package com.leon.ws.rfq.underlying;

import java.util.List;

public interface UnderlyingManagerDao
{
	UnderlyingDetailImpl save(String ric, String description, String savedBy);

	boolean updateValidity(String ric, boolean isValid, String updatedBy);

	List<UnderlyingDetailImpl> getAll();

}
