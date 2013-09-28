package com.leon.ws.rfq.request;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.GregorianCalendar;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import com.leon.ws.rfq.database.GenericDatabaseCommandExecutor;
import com.leon.ws.rfq.search.SearchCriteriaImpl;

public class RequestManagerDaoImpl implements RequestManagerDao
{
	private class RequestParameterizedRowMapper implements ParameterizedRowMapper<RequestDetailImpl>
	{
		public RequestDetailImpl mapRow(ResultSet rs, int rowNum) throws SQLException {
			RequestDetailImpl request = new RequestDetailImpl();
			
			request.setIdentifier(rs.getInt("identifier"));
			request.setRequest(rs.getString("request"));
			request.setBookCode(rs.getString("bookCode"));
			request.setClientId(rs.getInt("clientId"));
			request.setIsOTC(rs.getBoolean("isOTC"));
			request.setStatus(rs.getString("status")); //6

			GregorianCalendar tradeDate = new GregorianCalendar();
			tradeDate.setTime(rs.getDate("tradeDate"));
			request.setTradeDate(tradeDate);			
			GregorianCalendar expiryDate = new GregorianCalendar();
			expiryDate.setTime(rs.getDate("expiryDate"));
			request.setExpiryDate(expiryDate); //8
			
			request.setLotSize(rs.getInt("lotSize"));
			request.setMultiplier(rs.getInt("multiplier")); 
			request.setContracts(rs.getInt("contracts")); 
			request.setQuantity(rs.getInt("quantity")); //12
			
			request.setNotionalMillions(rs.getBigDecimal("notionalMillions"));
			request.setNotionalFXRate(rs.getBigDecimal("notionalFXRate"));
			request.setNotionalCurrency(rs.getString("notionalCurrency")); //15
			
			request.setDelta(rs.getBigDecimal("delta")); 
			request.setGamma(rs.getBigDecimal("gamma"));
			request.setVega(rs.getBigDecimal("vega")); 
			request.setTheta(rs.getBigDecimal("theta")); 
			request.setRho(rs.getBigDecimal("rho")); //20
			
			request.setDeltaNotional(rs.getBigDecimal("deltaNotional")); 
			request.setGammaNotional(rs.getBigDecimal("gammaNotional"));
			request.setVegaNotional(rs.getBigDecimal("vegaNotional")); 
			request.setThetaNotional(rs.getBigDecimal("thetaNotional")); 
			request.setRhoNotional(rs.getBigDecimal("rhoNotional")); //25
			
			request.setDeltaShares(rs.getBigDecimal("deltaShares")); 
			request.setGammaShares(rs.getBigDecimal("gammaShares"));
			request.setVegaShares(rs.getBigDecimal("vegaShares")); 
			request.setThetaShares(rs.getBigDecimal("thetaShares")); 
			request.setRhoShares(rs.getBigDecimal("rhoShares")); //30
			
			request.setAskFinalAmount(rs.getBigDecimal("askFinalAmount")); 
			request.setAskFinalPercentage(rs.getBigDecimal("askFinalPercentage")); 
			request.setAskImpliedVol(rs.getBigDecimal("askImpliedVol"));
			request.setAskPremiumAmount(rs.getBigDecimal("askPremiumAmount"));
			request.setAskPremiumPercentage(rs.getBigDecimal("askPremiumPercentage")); //35
			
			request.setBidFinalAmount(rs.getBigDecimal("bidFinalAmount")); 
			request.setBidFinalPercentage(rs.getBigDecimal("bidFinalPercentage")); 
			request.setBidImpliedVol(rs.getBigDecimal("bidImpliedVol"));
			request.setBidPremiumAmount(rs.getBigDecimal("bidPremiumAmount")); 
			request.setBidPremiumPercentage(rs.getBigDecimal("bidPremiumPercentage")); //40
			
			request.setPremiumAmount(rs.getBigDecimal("premiumAmount"));
			request.setPremiumPercentage(rs.getBigDecimal("premiumPercentage"));
			request.setImpliedVol(rs.getBigDecimal("impliedVol")); //43
			
			request.setSalesCreditAmount(rs.getBigDecimal("salesCreditAmount"));
			request.setSalesCreditPercentage(rs.getBigDecimal("salesCreditPercentage"));
			request.setSalesCreditCurrency(rs.getString("salesCreditCurrency"));
			request.setSalesCreditFXRate(rs.getBigDecimal("salesCreditFXRate")); //47
			
			request.setPremiumSettlementCurrency(rs.getString("premiumSettlementCurrency"));
			GregorianCalendar premiumSettlementDate = new GregorianCalendar();
			premiumSettlementDate.setTime(rs.getDate("premiumSettlementDate"));
			request.setPremiumSettlementDate(premiumSettlementDate);			
			request.setPremiumSettlementDaysOverride(rs.getInt("premiumSettlementDaysOverride"));
			request.setPremiumSettlementFXRate(rs.getBigDecimal("premiumSettlementFXRate")); //51
			
			request.setSalesComment(rs.getString("salesComment"));
			request.setTraderComment(rs.getString("traderComment"));
			request.setClientComment(rs.getString("clientComment")); //54
			
			request.setHedgePrice(rs.getBigDecimal("hedgePrice"));
			request.setHedgeType(rs.getString("hedgeType"));
			request.setTotalPremium(rs.getBigDecimal("totalPremium"));
			request.setPickedUpBy(rs.getString("pickedUpBy")); //58			
						
			return request;
		}		
	}	
	
	private static final String SAVE = 
			"CALL request_SAVE (?, ?, ?, ?, ?, ?, ?, ?, ?, ?"
								+ "?, ?, ?, ?, ?, ?, ?, ?, ?, ?"
								+ "?, ?, ?, ?, ?, ?, ?, ?, ?, ?"
								+ "?, ?, ?, ?, ?, ?, ?, ?, ?, ?"
								+ "?, ?, ?, ?, ?, ?, ?, ?, ?, ?"
								+ "?, ?, ?, ?, ?, ?, ?, ?, ?)";
	
	private static final String UPDATE = 
			"CALL request_UPDATE (?, ?, ?, ?, ?, ?, ?, ?, ?, ?"
								+ "?, ?, ?, ?, ?, ?, ?, ?, ?, ?"
								+ "?, ?, ?, ?, ?, ?, ?, ?, ?, ?"
								+ "?, ?, ?, ?, ?, ?, ?, ?, ?, ?"
								+ "?, ?, ?, ?, ?, ?, ?, ?, ?, ?"
								+ "?, ?, ?, ?, ?, ?, ?, ?, ?)"; //59
	
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
		return databaseExecutor.getSingleResult(SAVE, new RequestParameterizedRowMapper(), 
				request.getIdentifier(), 
				request.getRequest(), 
				request.getBookCode(), 
				request.getClientId(),  
				request.getIsOTC(), 
				request.getStatus(), //6

				request.getTradeDate(), 
				request.getExpiryDate(), //8
								
				request.getLotSize(),
				request.getMultiplier(), 
				request.getContracts(), 
				request.getQuantity(), //12
				
				request.getNotionalMillions(),
				request.getNotionalFXRate(), 
				request.getNotionalCurrency(), //15
				
				request.getDelta(), 
				request.getGamma(),
				request.getVega(), 
				request.getTheta(), 
				request.getRho(), //20
				
				request.getDeltaNotional(), 
				request.getGammaNotional(),
				request.getVegaNotional(), 
				request.getThetaNotional(), 
				request.getRhoNotional(), //25
				
				request.getDeltaShares(),
				request.getGammaShares(), 
				request.getVegaShares(), 
				request.getThetaShares(), 
				request.getRhoShares(), //30
				
				request.getAskFinalAmount(), 
				request.getAskFinalPercentage(), 
				request.getAskImpliedVol(), 
				request.getAskPremiumAmount(),
				request.getAskPremiumPercentage(), //35
				
				request.getBidFinalAmount(), 
				request.getBidFinalPercentage(), 
				request.getBidImpliedVol(),
				request.getBidPremiumAmount(), 
				request.getBidPremiumPercentage(), //40
				
				request.getPremiumAmount(), 
				request.getPremiumPercentage(),
				request.getImpliedVol(), //43
				
				request.getSalesCreditAmount(),
				request.getSalesCreditPercentage(),
				request.getSalesCreditCurrency(),
				request.getSalesCreditFXRate(), //47
				
				request.getPremiumSettlementCurrency(),
				request.getPremiumSettlementDate(),
				request.getPremiumSettlementDaysOverride(),
				request.getPremiumSettlementFXRate(), //51
				
				request.getSalesComment(),
				request.getTraderComment(),
				request.getClientComment(), //54
				
				request.getHedgePrice(),
				request.getHedgeType(),
				request.getTotalPremium(),
				request.getPickedUpBy(), //58
				
				savedByUser ).getIdentifier(); //59
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
				request.getStatus(), //6

				request.getTradeDate(), 
				request.getExpiryDate(), //8
								
				request.getLotSize(),
				request.getMultiplier(), 
				request.getContracts(), 
				request.getQuantity(), //12
				
				request.getNotionalMillions(),
				request.getNotionalFXRate(), 
				request.getNotionalCurrency(), //15
				
				request.getDelta(), 
				request.getGamma(),
				request.getVega(), 
				request.getTheta(), 
				request.getRho(), //20
				
				request.getDeltaNotional(), 
				request.getGammaNotional(),
				request.getVegaNotional(), 
				request.getThetaNotional(), 
				request.getRhoNotional(), //25
				
				request.getDeltaShares(),
				request.getGammaShares(), 
				request.getVegaShares(), 
				request.getThetaShares(), 
				request.getRhoShares(), //30
				
				request.getAskFinalAmount(), 
				request.getAskFinalPercentage(), 
				request.getAskImpliedVol(), 
				request.getAskPremiumAmount(),
				request.getAskPremiumPercentage(), //35
				
				request.getBidFinalAmount(), 
				request.getBidFinalPercentage(), 
				request.getBidImpliedVol(),
				request.getBidPremiumAmount(), 
				request.getBidPremiumPercentage(), //40
				
				request.getPremiumAmount(), 
				request.getPremiumPercentage(),
				request.getImpliedVol(), //43
				
				request.getSalesCreditAmount(),
				request.getSalesCreditPercentage(),
				request.getSalesCreditCurrency(),
				request.getSalesCreditFXRate(), //47
				
				request.getPremiumSettlementCurrency(),
				request.getPremiumSettlementDate(),
				request.getPremiumSettlementDaysOverride(),
				request.getPremiumSettlementFXRate(), //51
				
				request.getSalesComment(),
				request.getTraderComment(),
				request.getClientComment(), //54
				
				request.getHedgePrice(),
				request.getHedgeType(),
				request.getTotalPremium(),
				request.getPickedUpBy(), //58
								
				updatedByUser); // 59
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
