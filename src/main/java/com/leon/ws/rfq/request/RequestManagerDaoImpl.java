package com.leon.ws.rfq.request;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import com.leon.ws.rfq.database.GenericDatabaseCommandExecutor;
import com.leon.ws.rfq.search.SearchCriteriaImpl;
import com.leon.ws.rfq.search.SearchCriterionImpl;
import com.leon.ws.rfq.utilities.UtilityMethods;

public final class RequestManagerDaoImpl implements RequestManagerDao
{
	private static final Logger logger = LoggerFactory.getLogger(RequestManagerDaoImpl.class);

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

	private static final String SAVE_LEG =
			"CALL optionLeg_SAVE (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, "
					+ "?, ?, ?, ?, ?, ?, ?, ?, ?, "
					+ "?, ?, ?, ? )";

	private static final String UPDATE_LEG =
			"CALL optionLeg_UPDATE (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, "
					+ "?, ?, ?, ?, ?, ?, ?, ?, ?, "
					+ "?, ?, ?, ? )";

	private static final String CLIENT_CRITERION = "Client";
	private static final String STATUS_CRITERION = "Status";
	private static final String BOOK_CRITERION = "Book";
	private static final String UNDERLYING_CRITERION = "Underlyier";
	private static final String TRADE_DATE_CRITERION = "TradeDate";
	private static final String EXPIRY_DATE_CRITERION = "ExpiryDate";
	private static final String INITIATOR_CRITERION = "Initiator";
	private static final String PICKER_CRITERION = "Picker";

	private static final String GET = "CALL request_GET (?)";
	private static final String SELECT_TODAY = "CALL requests_SELECT_TODAY";
	private static final String SEARCH_WITH_EXISTING_CRITERIA = "CALL requests_SEARCH (?, ?)";

	private GenericDatabaseCommandExecutor databaseExecutor;

	RequestManagerDaoImpl() {}

	RequestManagerDaoImpl(GenericDatabaseCommandExecutor databaseExecutor) throws SQLException
	{
		this.databaseExecutor = databaseExecutor;
		this.databaseExecutor.setAutoCommit(true);
	}

	public void setDatabaseCommandExecutor(GenericDatabaseCommandExecutor databaseExecutor) throws SQLException
	{
		this.databaseExecutor = databaseExecutor;
		this.databaseExecutor.setAutoCommit(true);
	}

	@Override
	public RequestDetailImpl save(RequestDetailImpl request, String savedByUser)
	{
		try
		{
			java.sql.Date tradeDate = UtilityMethods.convertToDate(request.getTradeDate());
			if(tradeDate == null)
			{
				if(logger.isErrorEnabled())
					logger.error("Cannot save request: " + request.getRequest() + " because of an invalid trade date: " + request.getTradeDate());

				throw new IllegalArgumentException("tradeDate");
			}

			java.sql.Date expiryDate = UtilityMethods.convertToDate(request.getExpiryDate());
			if(expiryDate == null)
			{
				if(logger.isErrorEnabled())
					logger.error("Cannot save request: " + request.getRequest() + " because of an invalid expiry date: " + request.getTradeDate());

				throw new IllegalArgumentException("expiryDate");
			}

			java.sql.Date premiumSettlementDate = UtilityMethods.convertToDate(request.getPremiumSettlementDate());
			if(premiumSettlementDate == null)
			{
				if(logger.isErrorEnabled())
					logger.error("Cannot save request: " + request.getRequest() + " because of an invalid premium settlement date: " + request.getPremiumSettlementDate());

				throw new IllegalArgumentException("PremiumSettlementDate");
			}

			this.databaseExecutor.setAutoCommit(false);

			RequestDetailImpl result = this.databaseExecutor.<RequestDetailImpl>getSingleResult(SAVE, new RequestParameterizedRowMapper(),
					request.getRequest(),
					request.getBookCode(),
					request.getClientId(),
					request.getIsOTC(),
					request.getStatus(), //6

					tradeDate,
					expiryDate,

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
					premiumSettlementDate,
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

			if(result == null)
			{
				if(logger.isErrorEnabled())
					logger.error("Failed to save request: " + request.getRequest());

				this.databaseExecutor.rollbackTransaction();
			}

			java.sql.Date maturityDate;

			for(OptionDetailImpl leg : request.getLegs())
			{
				maturityDate = UtilityMethods.convertToDate(leg.getMaturityDate());

				if(maturityDate == null)
				{
					if(logger.isErrorEnabled())
						logger.error("Cannot save leg: " + leg.getLegId() + " of request : " + request.getRequest()
								+ " because of an invalid maturity date: " + leg.getMaturityDate());

					this.databaseExecutor.rollbackTransaction();

					throw new IllegalArgumentException("maturityDate");
				}

				if(!this.databaseExecutor.<OptionDetailImpl>executePreparedStatement(SAVE_LEG,
						leg.getLegId(),
						leg.getDelta(),
						leg.getGamma(),
						leg.getTheta(),
						leg.getVega(),

						leg.getRho(),
						leg.getVolatility(),
						maturityDate,
						leg.getDaysToExpiry(),
						leg.getYearsToExpiry(),

						leg.getUnderlyingPrice(),
						leg.getUnderlyingRIC(),
						leg.getIsCall(),
						leg.getIsEuropean(),

						leg.getInterestRate(),
						leg.getDayCountConvention(),
						leg.getPremium(),
						leg.getPremiumPercentage(),
						leg.getStrike(),

						leg.getStrikePercentage(),
						leg.getSide(),
						leg.getQuantity(),
						savedByUser))
				{
					if(logger.isErrorEnabled())
						logger.error("Failed to save request leg: " + leg.getLegId() + " of request: " + request.getRequest());

					this.databaseExecutor.rollbackTransaction();

					return null;
				}
			}

			this.databaseExecutor.commitTransaction();

			return result;
		}
		catch(SQLException se)
		{
			if(logger.isErrorEnabled())
				logger.error("Failed to save request: " + request.getRequest() + ". Exception thrown: " + se);
		}

		return null;
	}

	@Override
	public boolean update(RequestDetailImpl request, String updatedByUser)
	{
		java.sql.Date tradeDate = UtilityMethods.convertToDate(request.getTradeDate());
		if(tradeDate == null)
		{
			if(logger.isErrorEnabled())
				logger.error("Cannot update request: " + request.getRequest() + " because of an invalid trade date: " + request.getTradeDate());

			throw new IllegalArgumentException("tradeDate");
		}

		java.sql.Date expiryDate = UtilityMethods.convertToDate(request.getExpiryDate());
		if(expiryDate == null)
		{
			if(logger.isErrorEnabled())
				logger.error("Cannot update request: " + request.getRequest() + " because of an invalid expiry date: " + request.getTradeDate());

			throw new IllegalArgumentException("expiryDate");
		}

		java.sql.Date premiumSettlementDate = UtilityMethods.convertToDate(request.getPremiumSettlementDate());
		if(premiumSettlementDate == null)
		{
			if(logger.isErrorEnabled())
				logger.error("Cannot update request: " + request.getRequest() + " because of an invalid premium settlement date: " + request.getPremiumSettlementDate());

			throw new IllegalArgumentException("PremiumSettlementDate");
		}

		boolean updateResult =  this.databaseExecutor.<RequestDetailImpl>executePreparedStatement(UPDATE,
				request.getIdentifier(),
				request.getRequest(),
				request.getBookCode(),
				request.getClientId(),
				request.getIsOTC(),
				request.getStatus(), //6

				tradeDate,
				expiryDate, //8

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
				premiumSettlementDate,
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

		java.sql.Date maturityDate;

		for(OptionDetailImpl leg : request.getLegs())
		{
			maturityDate = UtilityMethods.convertToDate(leg.getMaturityDate());

			if(maturityDate == null)
			{
				if(logger.isErrorEnabled())
					logger.error("Cannot update leg: " + leg.getLegId() + " of request : " + request.getRequest()
							+ " because of an invalid maturity date: " + leg.getMaturityDate());

				//this.databaseExecutor.rollbackTransaction();

				throw new IllegalArgumentException("maturityDate");
			}

			if(!this.databaseExecutor.<OptionDetailImpl>executePreparedStatement(UPDATE_LEG,
					leg.getLegId(),
					leg.getDelta(),
					leg.getGamma(),
					leg.getTheta(),
					leg.getVega(),

					leg.getRho(),
					leg.getVolatility(),
					maturityDate,
					leg.getDaysToExpiry(),
					leg.getYearsToExpiry(),

					leg.getUnderlyingPrice(),
					leg.getUnderlyingRIC(),
					leg.getIsCall(),
					leg.getIsEuropean(),

					leg.getInterestRate(),
					leg.getDayCountConvention(),
					leg.getPremium(),
					leg.getPremiumPercentage(),
					leg.getStrike(),

					leg.getStrikePercentage(),
					leg.getSide(),
					leg.getQuantity(),
					updatedByUser))

				return false;
		}
		return updateResult;
	}

	@Override
	public RequestDetailImpl getRequest(int identifier)
	{
		return this.databaseExecutor.<RequestDetailImpl>getSingleResult(GET, new RequestParameterizedRowMapper(), identifier);
	}

	@Override
	public RequestDetailListImpl getRequestsForToday()
	{
		RequestDetailListImpl requestsForToday = new RequestDetailListImpl();

		ArrayList<RequestDetailImpl> resultSet = (ArrayList<RequestDetailImpl>) this.databaseExecutor
				.<RequestDetailImpl>getResultSet(SELECT_TODAY, new RequestParameterizedRowMapper());

		requestsForToday.setRequestDetailList(resultSet);

		return requestsForToday;
	}

	private String extractYYYYMMDDFormattedDate(String rawDate)
	{
		if(rawDate.isEmpty())
			throw new IllegalArgumentException("rawDate");

		return UtilityMethods.convertStringFormatOfDate(rawDate,
				UtilityMethods.DOTNET_DATE_STRING_FORMAT, UtilityMethods.DB_DATE_STRING_FORMAT);
	}

	private String constructDateWhereClause(String dateName, String criterionValue)
	{
		if(dateName.isEmpty())
			throw new IllegalArgumentException("dateName");

		if(criterionValue.isEmpty())
			throw new IllegalArgumentException("criterionValue");

		int indexOfHyphen = criterionValue.indexOf('-');

		if(criterionValue.startsWith("-")) // if hyphen at start then criterion value is an end date.
		{
			return dateName + " <= '" + extractYYYYMMDDFormattedDate(criterionValue.substring(1)) + "'";
		}
		else if(criterionValue.endsWith("-"))  // if hyphen at end then criterion value is an start date.
		{
			return dateName + " >= '" + extractYYYYMMDDFormattedDate(criterionValue.substring(0, indexOfHyphen)) + "'";
		}
		else // if the hyphen is in the middle then both the end and start date exist.
		{
			String startDate = extractYYYYMMDDFormattedDate(criterionValue.substring(0, indexOfHyphen));
			String endDate = extractYYYYMMDDFormattedDate(criterionValue.substring(indexOfHyphen + 1));

			return dateName + " >= '" + startDate + "' AND " + dateName + " <= '" + endDate + "'";
		}
	}

	@Override
	public RequestDetailListImpl getRequestsMatchingAdhocCriteria(SearchCriteriaImpl criteria)
	{
		if(criteria == null)
			throw new NullPointerException("criteria");

		RequestDetailListImpl requestsMatchingAdhocCriteria = new RequestDetailListImpl();

		StringBuilder builder = new StringBuilder("SELECT * FROM requestforquotemain WHERE ");

		for(int index = 0, size = criteria.getCriteria().size(); index < size; ++index)
		{
			SearchCriterionImpl criterion = criteria.getCriteria().get(index);
			switch(criterion.getControlName())
			{
			case STATUS_CRITERION:
				builder.append("status = '");
				builder.append(criterion.getControlValue());
				builder.append("'");
				break;
			case BOOK_CRITERION:
				builder.append("bookCode = '");
				builder.append(criterion.getControlValue());
				builder.append("'");
				break;
			case CLIENT_CRITERION:
				builder.append("clientId = '");
				builder.append(criterion.getControlValue());
				builder.append("'");
				break;
			case TRADE_DATE_CRITERION:
				builder.append(constructDateWhereClause("tradeDate", criterion.getControlValue()));
				break;
			case EXPIRY_DATE_CRITERION:
				builder.append(constructDateWhereClause("expiryDate", criterion.getControlValue()));
				break;
			case INITIATOR_CRITERION:
				break;
			case PICKER_CRITERION:
				break;
			case UNDERLYING_CRITERION:
				break;
			}
			if(index < (size - 1))
				builder.append(" AND ");
		}

		ArrayList<RequestDetailImpl> resultSet = new ArrayList<>(this.databaseExecutor
				.<RequestDetailImpl>getResultSet(builder.toString(), new RequestParameterizedRowMapper()));

		requestsMatchingAdhocCriteria.setRequestDetailList(resultSet);

		return requestsMatchingAdhocCriteria;
	}

	@Override
	public RequestDetailListImpl getRequestsMatchingExistingCriteria(String criteriaOwner, String criteriaKey)
	{
		RequestDetailListImpl requestsMatchingExistingCriteria = new RequestDetailListImpl();

		ArrayList<RequestDetailImpl> resultSet = new ArrayList<>(this.databaseExecutor
				.<RequestDetailImpl>getResultSet(SEARCH_WITH_EXISTING_CRITERIA, new RequestParameterizedRowMapper(), criteriaOwner, criteriaKey));

		requestsMatchingExistingCriteria.setRequestDetailList(resultSet);

		return requestsMatchingExistingCriteria;
	}

}
