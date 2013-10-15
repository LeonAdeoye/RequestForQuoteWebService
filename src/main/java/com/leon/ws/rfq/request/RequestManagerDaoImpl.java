package com.leon.ws.rfq.request;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import com.leon.ws.rfq.database.GenericDatabaseCommandExecutor;
import com.leon.ws.rfq.search.SearchCriteriaImpl;
import com.leon.ws.rfq.utilities.UtilityMethods;

public final class RequestManagerDaoImpl implements RequestManagerDao
{
	private class RequestParameterizedRowMapper implements ParameterizedRowMapper<RequestDetailImpl>
	{
		@Override
		public RequestDetailImpl mapRow(ResultSet rs, int rowNum) throws SQLException {
			RequestDetailImpl request = new RequestDetailImpl();

			request.setIdentifier(rs.getInt("identifier"));
			request.setRequest(rs.getString("request"));
			request.setBookCode(rs.getString("bookCode"));
			request.setClientId(rs.getInt("clientId"));
			request.setIsOTC(rs.getBoolean("isOTC"));
			request.setStatus(rs.getString("status")); //6

			DateFormat df = new SimpleDateFormat("dd MMM yyyy");
			if(rs.getDate("tradeDate") != null)
				request.setTradeDate(df.format(rs.getDate("tradeDate")));
			if(rs.getDate("expiryDate") != null)
				request.setExpiryDate(df.format(rs.getDate("expiryDate"))); //8

			request.setLotSize(rs.getInt("lotSize"));
			request.setMultiplier(rs.getInt("multiplier"));
			request.setContracts(rs.getInt("contracts"));
			request.setQuantity(rs.getInt("quantity")); //12

			request.setNotionalMillions(rs.getDouble("notionalMillions"));
			request.setNotionalFXRate(rs.getDouble("notionalFXRate"));
			request.setNotionalCurrency(rs.getString("notionalCurrency")); //15

			request.setDelta(rs.getDouble("delta"));
			request.setGamma(rs.getDouble("gamma"));
			request.setVega(rs.getDouble("vega"));
			request.setTheta(rs.getDouble("theta"));
			request.setRho(rs.getDouble("rho")); //20

			request.setDeltaNotional(rs.getDouble("deltaNotional"));
			request.setGammaNotional(rs.getDouble("gammaNotional"));
			request.setVegaNotional(rs.getDouble("vegaNotional"));
			request.setThetaNotional(rs.getDouble("thetaNotional"));
			request.setRhoNotional(rs.getDouble("rhoNotional")); //25

			request.setDeltaShares(rs.getDouble("deltaShares"));
			request.setGammaShares(rs.getDouble("gammaShares"));
			request.setVegaShares(rs.getDouble("vegaShares"));
			request.setThetaShares(rs.getDouble("thetaShares"));
			request.setRhoShares(rs.getDouble("rhoShares")); //30

			request.setAskFinalAmount(rs.getDouble("askFinalAmount"));
			request.setAskFinalPercentage(rs.getDouble("askFinalPercentage"));
			request.setAskImpliedVol(rs.getDouble("askImpliedVol"));
			request.setAskPremiumAmount(rs.getDouble("askPremiumAmount"));
			request.setAskPremiumPercentage(rs.getDouble("askPremiumPercentage")); //35

			request.setBidFinalAmount(rs.getDouble("bidFinalAmount"));
			request.setBidFinalPercentage(rs.getDouble("bidFinalPercentage"));
			request.setBidImpliedVol(rs.getDouble("bidImpliedVol"));
			request.setBidPremiumAmount(rs.getDouble("bidPremiumAmount"));
			request.setBidPremiumPercentage(rs.getDouble("bidPremiumPercentage")); //40

			request.setPremiumAmount(rs.getDouble("premiumAmount"));
			request.setPremiumPercentage(rs.getDouble("premiumPercentage"));
			request.setImpliedVol(rs.getDouble("impliedVol")); //43

			request.setSalesCreditAmount(rs.getDouble("salesCreditAmount"));
			request.setSalesCreditPercentage(rs.getDouble("salesCreditPercentage"));
			request.setSalesCreditCurrency(rs.getString("salesCreditCurrency"));
			request.setSalesCreditFXRate(rs.getDouble("salesCreditFXRate")); //47

			if(rs.getDate("premiumSettlementDate") != null)
				request.setPremiumSettlementDate(df.format(rs.getDate("premiumSettlementDate")));

			request.setPremiumSettlementCurrency(rs.getString("premiumSettlementCurrency"));
			request.setPremiumSettlementDaysOverride(rs.getInt("premiumSettlementDaysOverride"));
			request.setPremiumSettlementFXRate(rs.getDouble("premiumSettlementFXRate")); //51

			request.setSalesComment(rs.getString("salesComment"));
			request.setTraderComment(rs.getString("traderComment"));
			request.setClientComment(rs.getString("clientComment")); //54

			request.setHedgePrice(rs.getDouble("hedgePrice"));
			request.setHedgeType(rs.getString("hedgeType"));
			request.setTotalPremium(rs.getDouble("totalPremium"));
			request.setPickedUpBy(rs.getString("pickedUpBy")); //58

			return request;
		}
	}

	private static final String SAVE =
			"CALL request_SAVE (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, "
					+ "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, "
					+ "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, "
					+ "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, "
					+ "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, "
					+ "?, ?, ?, ?, ?, ?, ?, ?)";

	private static final String UPDATE =
			"CALL request_UPDATE (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, "
					+ "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, "
					+ "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, "
					+ "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, "
					+ "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, "
					+ "?, ?, ?, ?, ?, ?, ?, ?, ?)"; //59

	private static final String GET = "CALL request_GET (?)";

	private static final String SELECT_TODAY = "CALL requests_SELECT_TODAY";

	private static final String SELECT_WITH_ADHOC_CRITERIA = "CALL requests_SELECT_WITH_ADHOC_CRITERIA";

	private static final String SELECT_WITH_EXISTING_CRITERIA = "CALL requests_SELECT_WITH_EXISTING_CRITERIA (?, ?)";

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
	public RequestDetailImpl save(RequestDetailImpl request, String savedByUser)
	{
		RequestDetailImpl result = this.databaseExecutor.getSingleResult(SAVE, new RequestParameterizedRowMapper(),
				request.getRequest(),
				request.getBookCode(),
				request.getClientId(),
				request.getIsOTC(),
				request.getStatus(), //6

				UtilityMethods.convertToDate(request.getTradeDate()),
				UtilityMethods.convertToDate(request.getExpiryDate()),

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
				UtilityMethods.convertToDate(request.getPremiumSettlementDate()),
				request.getPremiumSettlementDaysOverride(),
				request.getPremiumSettlementFXRate(), //51

				request.getSalesComment(),
				request.getTraderComment(),
				request.getClientComment(), //54

				request.getHedgePrice(),
				request.getHedgeType(),
				request.getTotalPremium(),
				request.getPickedUpBy(), //58

				savedByUser ); //59

		return result;
	}

	@Override
	public boolean update(RequestDetailImpl request, String updatedByUser)
	{
		return this.databaseExecutor.executePreparedStatement(UPDATE,
				request.getIdentifier(),
				request.getRequest(),
				request.getBookCode(),
				request.getClientId(),
				request.getIsOTC(),
				request.getStatus(), //6

				UtilityMethods.convertToDate(request.getTradeDate()),
				UtilityMethods.convertToDate(request.getExpiryDate()), //8

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
				UtilityMethods.convertToDate(request.getPremiumSettlementDate()),
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
		return this.databaseExecutor.getSingleResult(GET, new RequestParameterizedRowMapper(), identifier);
	}

	@Override
	public RequestDetailListImpl getRequestsForToday()
	{
		RequestDetailListImpl requestsForToday = new RequestDetailListImpl();

		ArrayList<RequestDetailImpl> resultSet = (ArrayList<RequestDetailImpl>) this.databaseExecutor
				.getResultSet(SELECT_TODAY, new RequestParameterizedRowMapper());

		requestsForToday.setRequestDetailList(resultSet);

		return requestsForToday;
	}

	@Override
	public RequestDetailListImpl getRequestsMatchingAdhocCriteria(SearchCriteriaImpl criteria)
	{
		RequestDetailListImpl requestsMatchingAdhocCriteria = new RequestDetailListImpl();

		ArrayList<RequestDetailImpl> resultSet = (ArrayList<RequestDetailImpl>) this.databaseExecutor
				.getResultSet(SELECT_WITH_ADHOC_CRITERIA, new RequestParameterizedRowMapper());

		requestsMatchingAdhocCriteria.setRequestDetailList(resultSet);

		return requestsMatchingAdhocCriteria;
	}

	@Override
	public RequestDetailListImpl getRequestsMatchingExistingCriteria(String criteriaOwner, String criteriaKey)
	{
		RequestDetailListImpl requestsMatchingExistingCriteria = new RequestDetailListImpl();

		ArrayList<RequestDetailImpl> resultSet = (ArrayList<RequestDetailImpl>) this.databaseExecutor
				.getResultSet(SELECT_WITH_EXISTING_CRITERIA, new RequestParameterizedRowMapper(), criteriaOwner, criteriaKey);

		requestsMatchingExistingCriteria.setRequestDetailList(resultSet);

		return requestsMatchingExistingCriteria;
	}

}
