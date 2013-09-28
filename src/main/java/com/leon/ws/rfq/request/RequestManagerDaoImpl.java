package com.leon.ws.rfq.request;

import com.leon.ws.rfq.database.GenericDatabaseCommandExecutor;
import com.leon.ws.rfq.search.SearchCriteriaImpl;

public class RequestManagerDaoImpl implements RequestManagerDao
{
	private static final String SAVE = "CALL request_SAVE (?, ?, ?)";
	private static final String UPDATE = "CALL request_UPDATE (?, ?, ?)";
	private static final String GET = "CALL request_GET";
	private static final String SELECT_ALL = "CALL requests_SELECT_ALL";
	private GenericDatabaseCommandExecutor<RequestDetailImpl> databaseExecutor;
	
	RequestManagerDaoImpl()
	{
		
	}
	
	RequestManagerDaoImpl(GenericDatabaseCommandExecutor<RequestDetailImpl> databaseExecutor)
	{
		this.databaseExecutor = databaseExecutor;
	}
	
	public void setDatabaseCommandExecutor(GenericDatabaseCommandExecutor<RequestDetailImpl> databaseExecutor)
	{
		this.databaseExecutor = databaseExecutor;
	}	

	@Override
	public int save(RequestDetailImpl request, String savedByUser)
	{
		return 0;
	}

	@Override
	public boolean update(RequestDetailImpl request, String updatedByUser)
	{
		return databaseExecutor.executePreparedStatement(UPDATE, 
				request.getIdentifier(), 
				request.getRequest(), 
				request.getBookCode(), 
				request.getClientId(),  
				request.getIsOTC(), 
				request.getStatus(),

				request.getTradeDate(), 
				request.getExpiryDate(),
								
				request.getLotSize(),
				request.getMultiplier(), 
				request.getContracts(), 
				request.getQuantity(), 
				
				request.getNotionalMillions(),
				request.getNotionalFXRate(), 
				request.getNotionalCurrency(), 
				
				request.getDelta(), 
				request.getGamma(),
				request.getVega(), 
				request.getTheta(), 
				request.getRho(), 
				
				request.getDeltaNotional(), 
				request.getGammaNotional(),
				request.getVegaNotional(), 
				request.getThetaNotional(), 
				request.getRhoNotional(), 
				
				request.getDeltaShares(),
				request.getGammaShares(), 
				request.getVegaShares(), 
				request.getThetaShares(), 
				request.getRhoShares(),
				
				request.getAskFinalAmount(), 
				request.getAskFinalPercentage(), 
				request.getAskImpliedVol(), 
				request.getAskPremiumAmount(),
				request.getAskPremiumPercentage(), 
				
				request.getBidFinalAmount(), 
				request.getBidFinalPercentage(), 
				request.getBidImpliedVol(),
				request.getBidPremiumAmount(), 
				request.getBidPremiumPercentage(), 
				
				request.getPremiumAmount(), 
				request.getPremiumPercentage(),
				request.getImpliedVol(),
				
				request.getSalesCreditAmount(),
				request.getSalesCreditPercentage(),
				request.getSalesCreditCurrency(),
				request.getSalesCreditFXRate(),
				
				request.getPremiumSettlementCurrency(),
				request.getPremiumSettlementDate(),
				request.getPremiumSettlementDaysOverride(),
				request.getPremiumSettlementFXRate(),
				
				request.getSalesComment(),
				request.getTraderComment(),
				request.getClientComment(),
				
				request.getHedgePrice(),
				request.getHedgeType(),
				request.getTotalPremium(),
				request.getPickedUpBy(),
								
				updatedByUser);
	}

	@Override
	public RequestDetailImpl getRequest(int identifier)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RequestDetailListImpl getRequestsForToday()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RequestDetailListImpl getRequestsMatchingAdhocCriteria(SearchCriteriaImpl criteria)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RequestDetailListImpl getRequestsMatchingExistingCriteria(String criteriaOwner, String criteriaKey)
	{
		// TODO Auto-generated method stub
		return null;
	}

}
