package com.leon.ws.rfq.request;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.leon.ws.rfq.database.GenericDatabaseCommandExecutor;
import com.leon.ws.rfq.search.SearchCriteriaImpl;
import com.leon.ws.rfq.search.SearchCriterionImpl;
import com.leon.ws.rfq.utilities.UtilityMethods;

public final class RequestManagerDaoImpl implements RequestManagerDao
{
	private static final Logger logger = LoggerFactory.getLogger(RequestManagerDaoImpl.class);

	private class OptionLegParameterizedRowMapper implements ParameterizedRowMapper<OptionDetailImpl>
	{
		@Override
		public OptionDetailImpl mapRow(ResultSet rs, int rowNum) throws SQLException
		{
			OptionDetailImpl optionLeg = new OptionDetailImpl();

			optionLeg.setStrike(rs.getDouble("strike"));
			optionLeg.setStrikePercentage(rs.getDouble("strikePercentage"));
			optionLeg.setPremium(rs.getDouble("premium"));
			optionLeg.setPremiumPercentage(rs.getDouble("premiumPercentage"));
			optionLeg.setInterestRate(rs.getDouble("interestRate"));

			optionLeg.setDelta(rs.getDouble("delta"));
			optionLeg.setGamma(rs.getDouble("gamma"));
			optionLeg.setTheta(rs.getDouble("theta"));
			optionLeg.setVega(rs.getDouble("vega"));
			optionLeg.setRho(rs.getDouble("rho"));

			optionLeg.setLegId(rs.getInt("legId"));
			optionLeg.setIsCall(rs.getString("isCall").equals("C"));
			optionLeg.setIsEuropean(rs.getString("isEuropean").equals("E"));
			optionLeg.setSide(rs.getString("side").equals("B") ? "BUY" : "SELL");
			optionLeg.setQuantity(rs.getInt("quantity"));


			optionLeg.setVolatility(rs.getDouble("volatility"));
			optionLeg.setDaysToExpiry(rs.getDouble("daysToExpiry"));
			optionLeg.setYearsToExpiry(rs.getDouble("yearsToExpiry"));
			optionLeg.setUnderlyingPrice(rs.getDouble("underlyingPrice"));
			optionLeg.setUnderlyingRIC(rs.getString("underlyingRIC"));

			optionLeg.setDayCountConvention(rs.getDouble("dayCountConvention"));

			DateFormat df = new SimpleDateFormat("dd MMM yyyy");
			if(rs.getDate("maturityDate") != null)
				optionLeg.setMaturityDate(df.format(rs.getDate("maturityDate")));

			return optionLeg;
		}
	}

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
			
			request.setNotionalMillions(rs.getDouble("dayCountConvention"));

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
					+ "?, ?, ?, ?, ?, ?, ?, ?, ?)";

	private static final String UPDATE =
			"CALL request_UPDATE (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, "
					+ "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, "
					+ "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, "
					+ "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, "
					+ "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, "
					+ "?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"; //59

	private static final String SAVE_LEG =
			"CALL optionLeg_SAVE (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, "
					+ "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, "
					+ "?, ?, ?, ? )";

	private static final String UPDATE_LEG =
			"CALL optionLeg_UPDATE (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, "
					+ "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, "
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
	private static final String SELECT_OPTION_LEG = "CALL optionLeg_SELECT (?)";
	private static final String SEARCH_WITH_EXISTING_CRITERIA = "CALL requests_SEARCH (?, ?)";

	private GenericDatabaseCommandExecutor databaseExecutor;
	private PlatformTransactionManager platformTransactionManager;

	/**
	 * Default Constructor.
	 *
	 */
	RequestManagerDaoImpl() {}

	/**
	 * Constructor.
	 *
	 * @param  databaseExecutor			the database executor object that will send the prepared statements to the database.
	 * @throws NullPointerException		if databaseExecutor parameter is null.
	 */
	RequestManagerDaoImpl(GenericDatabaseCommandExecutor databaseExecutor) throws SQLException
	{
		if(databaseExecutor  == null)
			throw new NullPointerException("databaseExecutor");

		this.databaseExecutor = databaseExecutor;
	}

	/**
	 * Sets the database executor property.
	 *
	 * @param  databaseExecutor			the database executor object that will send the prepared statements to the database.
	 * @throws NullPointerException		if databaseExecutor parameter is null.
	 */
	public void setDatabaseCommandExecutor(GenericDatabaseCommandExecutor databaseExecutor) throws SQLException
	{
		if(databaseExecutor  == null)
			throw new NullPointerException("databaseExecutor");

		this.databaseExecutor = databaseExecutor;
	}

	/**
	 * Sets the transaction manager property.
	 *
	 * @param  platformTransactionManager	the platformTransactionManager object that will manage the database transactions.
	 * @throws NullPointerException			if platformTransactionManager parameter is null.
	 */
	public void setPlatformTransactionManager(PlatformTransactionManager platformTransactionManager)
	{
		if(platformTransactionManager  == null)
			throw new NullPointerException("platformTransactionManager");

		this.platformTransactionManager = platformTransactionManager;
	}

	/**
	 * Saves the request details to the database. Also saves the option leg details.
	 * Uses transaction management to ensure that both the request detail and the option leg are saved.
	 * If the option leg is not saved then the request detail save is rolled back.
	 *
	 * @param  request						the request details that will be saved to the database.
	 * @param  savedByUser					the user saving the request details.
	 * @throws NullPointerException			if platformTransactionManager parameter is null.
	 * @throws IllegalArgumentException		if savedByUser parameter is empty.
	 * @throws IllegalArgumentException		if tradeDate or expiryDate or premiumSettlementDate parameters is empty.
	 */
	@Override
	public RequestDetailImpl save(RequestDetailImpl request, String savedByUser)
	{
		if(request  == null)
			throw new NullPointerException("request");

		if(savedByUser.isEmpty())
			throw new IllegalArgumentException("savedByUser");

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

		TransactionDefinition paramTransactionDefinition = new DefaultTransactionDefinition();
		TransactionStatus status=this.platformTransactionManager.getTransaction(paramTransactionDefinition);

		RequestDetailImpl result = this.databaseExecutor.<RequestDetailImpl>getSingleResult(SAVE, new RequestParameterizedRowMapper(),
				request.getRequest(),
				request.getBookCode(),
				request.getClientId(),
				request.getIsOTC(),
				request.getStatus(), //6

				tradeDate,
				expiryDate,
				request.getDayCountConvention(),

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

			return null;
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

				this.platformTransactionManager.rollback(status);

				throw new IllegalArgumentException("maturityDate");
			}

			if(!this.databaseExecutor.<OptionDetailImpl>executePreparedStatement(SAVE_LEG,	result.getIdentifier(),

					leg.getLegId(),
					leg.getDelta(),
					leg.getGamma(),
					leg.getTheta(),
					leg.getVega(),

					leg.getRho(),
					leg.getVolatility(),
					leg.getDaysToExpiry(),
					leg.getYearsToExpiry(),
					maturityDate,

					leg.getUnderlyingPrice(),
					leg.getUnderlyingRIC(),
					(leg.getIsCall() ? "C" : "P"),
					(leg.getIsEuropean() ? "E" : "A"),
					leg.getInterestRate(),

					leg.getDayCountConvention(),
					leg.getPremium(),
					leg.getPremiumPercentage(),
					leg.getStrike(),
					leg.getStrikePercentage(),

					(leg.getSide().equals("BUY") ? "B" : "S"),
					leg.getQuantity(),
					savedByUser))
			{
				if(logger.isErrorEnabled())
					logger.error("Failed to save request leg: " + leg.getLegId() + " of request: " + request.getRequest());

				this.platformTransactionManager.rollback(status);

				return null;
			}
		}

		this.platformTransactionManager.commit(status);

		return result;
	}

	/**
	 * Updates the request details to the database. Also updates the option leg details.
	 * Uses transaction management to ensure that both the request detail and the option leg are updated.
	 * If the option leg is not updated then the request detail update is rolled back.
	 *
	 * @param  request						the request details that will be updated in the database.
	 * @param  updatedByUser				the user updating the request details.
	 * @throws NullPointerException			if platformTransactionManager parameter is null.
	 * @throws IllegalArgumentException		if updatedByUser parameter is empty.
	 * @throws IllegalArgumentException		if tradeDate or expiryDate or premiumSettlementDate parameters is empty.
	 */
	@Override
	public boolean update(RequestDetailImpl request, String updatedByUser)
	{
		if(request  == null)
			throw new NullPointerException("request");

		if(updatedByUser.isEmpty())
			throw new IllegalArgumentException("updatedByUser");

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

		TransactionDefinition paramTransactionDefinition = new DefaultTransactionDefinition();
		TransactionStatus status=this.platformTransactionManager.getTransaction(paramTransactionDefinition);

		boolean updateResult =  this.databaseExecutor.<RequestDetailImpl>executePreparedStatement(UPDATE,
				request.getIdentifier(),
				request.getRequest(),
				request.getBookCode(),
				request.getClientId(),
				request.getIsOTC(),
				request.getStatus(), //6

				tradeDate,
				expiryDate, //8
				request.getDayCountConvention(),

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

		if(updateResult == false)
		{
			if(logger.isErrorEnabled())
				logger.error("Failed to update request: " + request.getRequest());

			return false;
		}

		java.sql.Date maturityDate;

		for(OptionDetailImpl leg : request.getLegs())
		{
			maturityDate = UtilityMethods.convertToDate(leg.getMaturityDate());

			if(maturityDate == null)
			{
				if(logger.isErrorEnabled())
					logger.error("Cannot update leg: " + leg.getLegId() + " of request : " + request.getRequest()
							+ " because of an invalid maturity date: " + leg.getMaturityDate());

				this.platformTransactionManager.rollback(status);

				throw new IllegalArgumentException("maturityDate");
			}

			if(!this.databaseExecutor.<OptionDetailImpl>executePreparedStatement(UPDATE_LEG, request.getIdentifier(),

					leg.getLegId(),
					leg.getDelta(),
					leg.getGamma(),
					leg.getTheta(),
					leg.getVega(),

					leg.getRho(),
					leg.getVolatility(),
					leg.getDaysToExpiry(),
					leg.getYearsToExpiry(),
					maturityDate,

					leg.getUnderlyingPrice(),
					leg.getUnderlyingRIC(),
					(leg.getIsCall() ? "C" : "P"),
					(leg.getIsEuropean() ? "E" : "A"),

					leg.getInterestRate(),
					leg.getDayCountConvention(),
					leg.getPremium(),
					leg.getPremiumPercentage(),
					leg.getStrike(),

					leg.getStrikePercentage(),
					(leg.getSide().equals("BUY") ? "B" : "S"),
					leg.getQuantity(),
					updatedByUser))
			{
				if(logger.isErrorEnabled())
					logger.error("Failed to update request leg: " + leg.getLegId() + " of request: " + request.getRequest());

				this.platformTransactionManager.rollback(status);

				return false;
			}
		}

		this.platformTransactionManager.commit(status);

		return updateResult;
	}

	/**
	 * Retrieves the request details matching the request identifier.
	 * Also retrieves the option legs associated with that RFQ.
	 *
	 * @param  identifier					the identifier of the request whose details need to be retrieved.
	 * @throws IllegalArgumentException		if identifier is less than or equal to zero.
	 * @returns 							the request details
	 */
	@Override
	public RequestDetailImpl getRequest(int identifier)
	{
		if(identifier <= 0)
			throw new IllegalArgumentException("identifier");

		RequestDetailImpl request = this.databaseExecutor.<RequestDetailImpl>getSingleResult
				(GET, new RequestParameterizedRowMapper(), identifier);

		request.setLegs(getRequestLegs(request.getIdentifier()));

		return request;
	}

	/**
	 * Retrieves the details of all the RFQs with a tradeDate equal to today.
	 * Also retrieves the option legs associated with each RFQ.
	 *
	 * @returns 							the request details
	 */
	@Override
	public RequestDetailListImpl getRequestsForToday()
	{
		RequestDetailListImpl requestsForToday = new RequestDetailListImpl();

		ArrayList<RequestDetailImpl> resultSet = new ArrayList<>(this.databaseExecutor
				.<RequestDetailImpl>getResultSet(SELECT_TODAY, new RequestParameterizedRowMapper()));

		for(RequestDetailImpl request : resultSet)
			request.setLegs(getRequestLegs(request.getIdentifier()));

		requestsForToday.setRequestDetailList(resultSet);

		return requestsForToday;
	}

	/**
	 * Retrieves the details of the option legs belonging to the RFQ with the requestId passes as a parameter.
	 * 
	 * @param requestId 				the identifier of the parent RFQ.
	 * @returns OptionDetailListImpl 	the list of option leg details.
	 * @throws IllegalArgumentException if the requestId is less than or equal to zero.
	 */
	private OptionDetailListImpl getRequestLegs(int requestId)
	{
		if(requestId <= 0)
			throw new IllegalArgumentException("requestId");

		OptionDetailListImpl optionLegs = new OptionDetailListImpl();

		ArrayList<OptionDetailImpl> resultSet = new ArrayList<>(this.databaseExecutor
				.<OptionDetailImpl>getResultSet(SELECT_OPTION_LEG, new OptionLegParameterizedRowMapper(), requestId));

		optionLegs.setOptionDetailList(resultSet);

		return optionLegs;
	}

	/**
	 * Converts a string date from .NET format to MYSQL DB format.
	 *
	 * @param  rawDate						the raw date format that needs to be converted.
	 * @throws IllegalArgumentException		if rawDate is empty.
	 * @returns 							the date string in the yyyy-MM-dd format.
	 */
	private String extractYYYYMMDDFormattedDate(String rawDate)
	{
		if(rawDate.isEmpty())
			throw new IllegalArgumentException("rawDate");

		return UtilityMethods.convertStringFormatOfDate(rawDate,
				UtilityMethods.DOTNET_DATE_STRING_FORMAT, UtilityMethods.DB_DATE_STRING_FORMAT);
	}

	/** Constructs part of the WHERE clause for a single date or a data range.
	 *
	 * @param  dateName						the name of the date field, for example tradeDate.
	 * @param  criterionValue				the value of the single date or date range.
	 * @throws IllegalArgumentException		if dateName or criterionValue is empty.
	 * @returns 							the part of the WHERE clause covering the date.
	 */
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

	/** Retrieves the list of RFQs matching the criteria specified by the criteria list.
	 *  The method constructs the SQL query on the fly.
	 *  Also retrieves the option legs associated with each RFQ.
	 *
	 * @param  criteria						the list of criteria to be used to search for the RFQs.
	 * @throws NullPointerException			if criteria is null.
	 * @returns 							the list of requests matching the criteria.
	 */
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

		for(RequestDetailImpl request : resultSet)
			request.setLegs(getRequestLegs(request.getIdentifier()));

		requestsMatchingAdhocCriteria.setRequestDetailList(resultSet);

		return requestsMatchingAdhocCriteria;
	}

	/** Retrieves the list of RFQs matching the criteria specified by the criteria owner and key lookups.
	 *  The database procedure first uses the owner and key to lookup the saved search  criteria and then uses
	 *  these criteria to return the list of matching RFQs.
	 *  Also retrieves the option legs associated with each RFQ.
	 *
	 * @param  criteriaOwner				the criteria owner lookup.
	 * @param  criteriaKey					the criteria key lookup.
	 * @throws IllegalArgumentException		if criteriaOwner or criteriaKey is empty.
	 * @returns 							the list of requests matching the criteria specified by the owner and key lookups.
	 */
	@Override
	public RequestDetailListImpl getRequestsMatchingExistingCriteria(String criteriaOwner, String criteriaKey)
	{
		if(criteriaOwner.isEmpty())
			throw new IllegalArgumentException("criteriaOwner");

		if(criteriaKey.isEmpty())
			throw new IllegalArgumentException("criteriaKey");

		RequestDetailListImpl requestsMatchingExistingCriteria = new RequestDetailListImpl();

		ArrayList<RequestDetailImpl> resultSet = new ArrayList<>(this.databaseExecutor
				.<RequestDetailImpl>getResultSet(SEARCH_WITH_EXISTING_CRITERIA,
						new RequestParameterizedRowMapper(), criteriaOwner, criteriaKey));

		for(RequestDetailImpl request : resultSet)
			request.setLegs(getRequestLegs(request.getIdentifier()));

		requestsMatchingExistingCriteria.setRequestDetailList(resultSet);

		return requestsMatchingExistingCriteria;
	}

}
