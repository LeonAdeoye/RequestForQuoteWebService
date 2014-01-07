					import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.leon.ws.rfq.request.OptionDetailImpl;
import com.leon.ws.rfq.request.OptionDetailListImpl;
import com.leon.ws.rfq.request.RequestDetailImpl;
import com.leon.ws.rfq.request.RequestManagerDao;


@ContextConfiguration(locations = { "classpath:/cxf-servlet-test.xml" })
public class RequestManagerDaoTest  extends AbstractJUnit4SpringContextTests
{
	@Rule public JUnitRuleMockery mockContext = new JUnitRuleMockery();
	
	@Autowired
	private RequestManagerDao requestManagerDao;
	
	@Autowired
	private DataSourceTransactionManager transactionManager;
	
	private TransactionStatus status;
	
	private final OptionDetailListImpl optionLegs = new OptionDetailListImpl();
	private final RequestDetailImpl request = new RequestDetailImpl();
	
	public RequestManagerDaoTest() {}
	
	@Before
	public void setUp()
	{
		assertNotNull("autowired request manager dao should not be null", this.requestManagerDao);
		assertNotNull("autowired transaction manager should not be null", this.transactionManager);
		
		TransactionDefinition paramTransactionDefinition = new DefaultTransactionDefinition();
		this.status=this.transactionManager.getTransaction(paramTransactionDefinition);
		
		this.optionLegs.setOptionDetailList(new ArrayList<OptionDetailImpl>());
		
		this.request.setLegs(this.optionLegs);
		this.request.setQuantity(1);
		this.request.setAskFinalAmount(1.0);
		this.request.setAskFinalPercentage(2.0);
		this.request.setAskImpliedVol(3.0);
		this.request.setAskPremiumAmount(4.0);
		this.request.setAskPremiumPercentage(5.0);
		this.request.setBidFinalAmount(6.0);
		this.request.setBidFinalPercentage(7.0);
		this.request.setBidPremiumAmount(8.0);
		this.request.setBidPremiumPercentage(9.0);
		this.request.setBookCode("AB01");
		this.request.setClientComment("client comment");
		this.request.setClientId(1);
		this.request.setContracts(2);
		this.request.setDelta(10.0);
		this.request.setDeltaNotional(11.0);
		this.request.setDeltaShares(12.0);
		this.request.setExpiryDate("12/23/2014");
		this.request.setDayCountConvention(250.0);
		this.request.setGamma(13.0);
		this.request.setGammaNotional(14.0);
		this.request.setGammaShares(15.0);
		this.request.setHedgePrice(16.0);
		this.request.setHedgeType("test");
		this.request.setImpliedVol(17.0);
		this.request.setIsOTC(true);
		this.request.setLotSize(3);
		this.request.setMultiplier(4);
		this.request.setNotionalCurrency("USD");
		this.request.setNotionalFXRate(19.0);
		this.request.setNotionalMillions(20.0);
		this.request.setPickedUpBy("test");
		this.request.setPremiumAmount(21.0);
		this.request.setPremiumPercentage(22.0);
		this.request.setPremiumSettlementCurrency("USD");
		this.request.setPremiumSettlementDate("12/23/2014");
		this.request.setPremiumSettlementDaysOverride(5);
		this.request.setPremiumSettlementFXRate(23.0);
		this.request.setRequest("test");
		this.request.setRho(24.0);
		this.request.setRhoNotional(25.0);
		this.request.setRhoShares(26.0);
		this.request.setSalesComment("test");
		this.request.setSalesCreditAmount(27.0);
		this.request.setSalesCreditCurrency("USD");
		this.request.setSalesCreditFXRate(28.0);
		this.request.setSalesCreditPercentage(29.0);
		this.request.setStatus("test");
		this.request.setTheta(31.0);
		this.request.setThetaNotional(32.0);
		this.request.setThetaShares(33.0);
		this.request.setTotalPremium(35.0);
		this.request.setTradeDate("12/23/2013");
		this.request.setTraderComment("test");
		this.request.setVega(36.0);
		this.request.setVegaNotional(37.0);
		this.request.setVegaShares(38.0);
	}
	
	@After
	public void tearDown()
	{
		this.transactionManager.rollback(this.status);
	}
	
	@Test(expected = NullPointerException.class)
	public void test_save_InValidRequestReference_ExceptionThrown()
	{
		this.requestManagerDao.save(null, "testUser");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void test_save_InvalidUser_ExceptionThrown()
	{
		this.requestManagerDao.save(new RequestDetailImpl(), "");
	}
	
	@Test
	public void test_save_IncompleteRequest_NullRequestReturned()
	{
		RequestDetailImpl request = new RequestDetailImpl();
		request.setRequest("test");
		request.setTradeDate("12/23/2013");
		request.setExpiryDate("12/23/2014");
		request.setPremiumSettlementDate("12/23/2014");
		RequestDetailImpl result = this.requestManagerDao.save(request, "testUser");
		
		assertNull("request should be null", result);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void test_save_NullTradeDate_ExceptionTrown()
	{
		// Arrange
		this.request.setTradeDate(null);
		// Act
		this.requestManagerDao.save(this.request, "testUser");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void test_save_InvalidTradeDate_ExceptionTrown()
	{
		// Arrange
		this.request.setTradeDate("");
		// Act
		this.requestManagerDao.save(this.request, "testUser");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void test_save_NullExpiryDate_ExceptionTrown()
	{
		// Arrange
		this.request.setExpiryDate(null);
		// Act
		this.requestManagerDao.save(this.request, "testUser");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void test_save_InvalidExpiryDate_ExceptionTrown()
	{
		// Arrange
		this.request.setExpiryDate("");
		// Act
		this.requestManagerDao.save(this.request, "testUser");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void test_save_NullPremiumSettlementDate_ExceptionTrown()
	{
		// Arrange
		this.request.setPremiumSettlementDate(null);
		// Act
		this.requestManagerDao.save(this.request, "testUser");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void test_save_InvalidPremiumSettlementDate_ExceptionTrown()
	{
		// Arrange
		this.request.setPremiumSettlementDate("");
		// Act
		this.requestManagerDao.save(this.request, "testUser");
	}
	
	@Test
	public void test_save_CompleteRequest_ValidRequestReturned()
	{
		// Act
		RequestDetailImpl result = this.requestManagerDao.save(this.request, "testUser");
		// Assert
		assertNotNull("request should NOT be null", result);
	}
	
	@Test
	public void test_save_CompleteRequest_SavedFieldsShouldBeRetrieved()
	{
		// Act
		RequestDetailImpl result = this.requestManagerDao.save(this.request, "testUser");
		// Assert
		assertEquals("Ask final amount should be 1.0", 1.0, result.getAskFinalAmount(), 0.1);

		assertEquals("Ask final percentage should be 2.0", 2.0, result.getAskFinalPercentage(), 0.1);

		assertEquals("Ask implied vol should be 3.0", 3.0, result.getAskImpliedVol(), 0.1);

		assertEquals("Ask premium amount should be 4.0", 4.0, result.getAskPremiumAmount(), 0.1);

		assertEquals("Ask premium percentage should be 5.0", 5.0, result.getAskPremiumPercentage(), 0.1);

		assertEquals("Bid final amount should be 6.0", 6.0, result.getBidFinalAmount(), 0.1);

		assertEquals("Bid final percentage should be 7.0", 7.0, result.getBidFinalPercentage(), 0.1);

		assertEquals("Bid premium amount should be 8.0", 8.0, result.getBidPremiumAmount(), 0.1);

		assertEquals("Bid premium percentage should be 9.0", 9.0, result.getBidPremiumPercentage(), 0.1);

		assertEquals("Book code should be AB01", "AB01", result.getBookCode());

		assertEquals("Client comment should be client comment", "client comment", result.getClientComment());

		assertEquals("Client id should be 1", 1, result.getClientId());

		assertEquals("Contracts should be 2", 2, result.getContracts());

		assertEquals("Delta should be 10.0", 10.0, result.getDelta(), 0.1);

		assertEquals("Delta notional should be 11.0", 11.0, result.getDeltaNotional(), 0.1);

		assertEquals("Delta shares should be 12.0", 12.0, result.getDeltaShares(), 0.1);

		assertEquals("Expiry date should be 23 Dec 2014", "23 Dec 2014", result.getExpiryDate());

		assertEquals("Day count convention should be 250.0", 250.0, result.getDayCountConvention(), 0.1);

		assertEquals("Gamma should be 13.0", 13.0, result.getGamma(), 0.1);

		assertEquals("Gamma notional should be 14.0", 14.0, result.getGammaNotional(), 0.1);

		assertEquals("Gamma shares should be 15.0", 15.0, result.getGammaShares(), 0.1);
		
		assertEquals("Hedge price should be 16.0", 16.0, result.getHedgePrice(), 0.1);
		
		assertEquals("Hedge type should be test", "test", result.getHedgeType());
		
		assertEquals("Implied vol should be 17.0", 17.0, result.getImpliedVol(), 0.1);
		
		assertTrue("IsOTC should be true", result.getIsOTC());
		
		assertEquals("Lot size should be 3", 3, result.getLotSize());
		
		assertEquals("Multiplier should be 4", 4, result.getMultiplier());
		
		assertEquals("Notional currency should be USD", "USD", result.getNotionalCurrency());
		
		assertEquals("Notional FX rate should be 19.0", 19.0, result.getNotionalFXRate(), 0.1);
		
		assertEquals("Notional millions should be 20.0", 20.0, result.getNotionalMillions(), 0.1);
		
		assertEquals("Picked up by should be test", "test", result.getPickedUpBy());

		assertEquals("Premium amount should be 21.0", 21.0, result.getPremiumAmount(), 0.1);
		
		assertEquals("Premium percentage should be 22.0", 22.0, result.getPremiumPercentage(), 0.1);
		
		assertEquals("Premium settlement currency should be USD", "USD", result.getPremiumSettlementCurrency());
		
		assertEquals("Premium settlement date should be 23 Dec 2014", "23 Dec 2014", result.getPremiumSettlementDate());
		
		assertEquals("Premium settlement days override should be 5", 5, result.getPremiumSettlementDaysOverride());
		
		assertEquals("Premium settlement FX rate should be 23.0", 23.0, result.getPremiumSettlementFXRate(), 0.1);
		
		assertEquals("Request should be test", "test", result.getRequest());
		
		assertEquals("Rho should be 24.0", 24.0, result.getRho(), 0.1);
		
		assertEquals("Rho notional should be 25.0", 25.0, result.getRhoNotional(), 0.1);
		
		assertEquals("Rho shares should be 26.0", 26.0, result.getRhoShares(), 0.1);
		
		assertEquals("Sales comment should be test", "test", result.getSalesComment());
		
		assertEquals("Sales credit amount should be 27.0", 27.0, result.getSalesCreditAmount(), 0.1);
		
		assertEquals("Sales credit currency should be USD", "USD", result.getSalesCreditCurrency());
		
		assertEquals("Sales credit FX rate should be 28.0", 28.0, result.getSalesCreditFXRate(), 0.1);
		
		assertEquals("Sales credit percentage should be 29.0", 29.0, result.getSalesCreditPercentage(), 0.1);
		
		assertEquals("Status should be test", "test", result.getStatus());
		
		assertEquals("Theta should be 31.0", 31.0, result.getTheta(), 0.1);
		
		assertEquals("Theta notional should be 32.0", 32.0, result.getThetaNotional(), 0.1);
		
		assertEquals("Theta shares should be 33.0", 33.0, result.getThetaShares(), 0.1);
		
		assertEquals("Total premium should be 35.0", 35.0, result.getTotalPremium(), 0.1);
		
		assertEquals("Trade date should be 23 Dec 2013", "23 Dec 2013", result.getTradeDate());
		
		assertEquals("Traders comment should be test", "test", result.getTraderComment());
		
		assertEquals("Vega should be 36.0", 36.0, result.getVega(), 0.1);
		
		assertEquals("Vega notional should be 37.0", 37.0, result.getVegaNotional(), 0.1);
		
		assertEquals("Vega shares should be 38.0", 38.0, result.getVegaShares(), 0.1);
	}
	
	@Test
	public void test_update_CompleteRequest_UpdatedFieldsShouldBeRetrieved()
	{
		// Act
		RequestDetailImpl savedRequest = this.requestManagerDao.save(this.request, "testUser");
		savedRequest.setLegs(this.optionLegs);
		savedRequest.setQuantity(11);
		savedRequest.setAskFinalAmount(11.0);
		savedRequest.setAskFinalPercentage(21.0);
		savedRequest.setAskImpliedVol(31.0);
		savedRequest.setAskPremiumAmount(41.0);
		savedRequest.setAskPremiumPercentage(51.0);
		savedRequest.setBidFinalAmount(61.0);
		savedRequest.setBidFinalPercentage(71.0);
		savedRequest.setBidPremiumAmount(81.0);
		savedRequest.setBidPremiumPercentage(91.0);
		savedRequest.setBookCode("AB11");
		savedRequest.setClientComment("client comment 1");
		savedRequest.setClientId(11);
		savedRequest.setContracts(21);
		savedRequest.setDelta(101.0);
		savedRequest.setDeltaNotional(111.0);
		savedRequest.setDeltaShares(121.0);
		savedRequest.setExpiryDate("12/24/2014");
		savedRequest.setDayCountConvention(255.0);
		savedRequest.setGamma(131.0);
		savedRequest.setGammaNotional(141.0);
		savedRequest.setGammaShares(151.0);
		savedRequest.setHedgePrice(161.0);
		savedRequest.setHedgeType("test 1");
		savedRequest.setImpliedVol(171.0);
		savedRequest.setIsOTC(false);
		savedRequest.setLotSize(31);
		savedRequest.setMultiplier(41);
		savedRequest.setNotionalCurrency("HKD");
		savedRequest.setNotionalFXRate(191.0);
		savedRequest.setNotionalMillions(201.0);
		savedRequest.setPickedUpBy("test 1");
		savedRequest.setPremiumAmount(211.0);
		savedRequest.setPremiumPercentage(221.0);
		savedRequest.setPremiumSettlementCurrency("HKD");
		savedRequest.setPremiumSettlementDate("12/24/2014");
		savedRequest.setPremiumSettlementDaysOverride(51);
		savedRequest.setPremiumSettlementFXRate(231.0);
		savedRequest.setRequest("test 1");
		savedRequest.setRho(241.0);
		savedRequest.setRhoNotional(251.0);
		savedRequest.setRhoShares(261.0);
		savedRequest.setSalesComment("test 1");
		savedRequest.setSalesCreditAmount(271.0);
		savedRequest.setSalesCreditCurrency("HKD");
		savedRequest.setSalesCreditFXRate(281.0);
		savedRequest.setSalesCreditPercentage(291.0);
		savedRequest.setStatus("test 1");
		savedRequest.setTheta(311.0);
		savedRequest.setThetaNotional(321.0);
		savedRequest.setThetaShares(331.0);
		savedRequest.setTotalPremium(351.0);
		savedRequest.setTradeDate("12/24/2013");
		savedRequest.setTraderComment("test 1");
		savedRequest.setVega(361.0);
		savedRequest.setVegaNotional(371.0);
		savedRequest.setVegaShares(381.0);
		
		this.requestManagerDao.update(savedRequest, "test");
		RequestDetailImpl updatedRequest = this.requestManagerDao.getRequest(savedRequest.getIdentifier());
		
		// Assert
		assertEquals("Ask final amount should be 11.0", 11.0, updatedRequest.getAskFinalAmount(), 0.1);

		assertEquals("Ask final percentage should be 21.0", 21.0, updatedRequest.getAskFinalPercentage(), 0.1);

		assertEquals("Ask implied vol should be 31.0", 31.0, updatedRequest.getAskImpliedVol(), 0.1);

		assertEquals("Ask premium amount should be 41.0", 41.0, updatedRequest.getAskPremiumAmount(), 0.1);

		assertEquals("Ask premium percentage should be 51.0", 51.0, updatedRequest.getAskPremiumPercentage(), 0.1);

		assertEquals("Bid final amount should be 61.0", 61.0, updatedRequest.getBidFinalAmount(), 0.1);

		assertEquals("Bid final percentage should be 71.0", 71.0, updatedRequest.getBidFinalPercentage(), 0.1);

		assertEquals("Bid premium amount should be 81.0", 81.0, updatedRequest.getBidPremiumAmount(), 0.1);

		assertEquals("Bid premium percentage should be 91.0", 91.0, updatedRequest.getBidPremiumPercentage(), 0.1);

		assertEquals("Book code should be AB11", "AB11", updatedRequest.getBookCode());

		assertEquals("Client comment should be client comment 1", "client comment 1", updatedRequest.getClientComment());

		assertEquals("Client id should be 11", 11, updatedRequest.getClientId());

		assertEquals("Contracts should be 21", 21, updatedRequest.getContracts());

		assertEquals("Delta should be 101.0", 101.0, updatedRequest.getDelta(), 0.1);

		assertEquals("Delta notional should be 111.0", 111.0, updatedRequest.getDeltaNotional(), 0.1);

		assertEquals("Delta shares should be 121.0", 121.0, updatedRequest.getDeltaShares(), 0.1);

		assertEquals("Expiry date should be 24 Dec 2014", "24 Dec 2014", updatedRequest.getExpiryDate());

		assertEquals("Day count convention should be 255.0", 255.0, updatedRequest.getDayCountConvention(), 0.1);

		assertEquals("Gamma should be 131.0", 131.0, updatedRequest.getGamma(), 0.1);

		assertEquals("Gamma notional should be 141.0", 141.0, updatedRequest.getGammaNotional(), 0.1);

		assertEquals("Gamma shares should be 151.0", 151.0, updatedRequest.getGammaShares(), 0.1);
		
		assertEquals("Hedge price should be 161.0", 161.0, updatedRequest.getHedgePrice(), 0.1);
		
		assertEquals("Hedge type should be test 1", "test 1", updatedRequest.getHedgeType());
		
		assertEquals("Implied vol should be 171.0", 171.0, updatedRequest.getImpliedVol(), 0.1);
		
		assertFalse("IsOTC should be false", updatedRequest.getIsOTC());
		
		assertEquals("Lot size should be 31", 31, updatedRequest.getLotSize());
		
		assertEquals("Multiplier should be 41", 41, updatedRequest.getMultiplier());
		
		assertEquals("Notional currency should be HKD", "HKD", updatedRequest.getNotionalCurrency());
		
		assertEquals("Notional FX rate should be 191.0", 191.0, updatedRequest.getNotionalFXRate(), 0.1);
		
		assertEquals("Notional millions should be 201.0", 201.0, updatedRequest.getNotionalMillions(), 0.1);
		
		assertEquals("Picked up by should be test 1", "test 1", updatedRequest.getPickedUpBy());

		assertEquals("Premium amount should be 211.0", 211.0, updatedRequest.getPremiumAmount(), 0.1);
		
		assertEquals("Premium percentage should be 221.0", 221.0, updatedRequest.getPremiumPercentage(), 0.1);
		
		assertEquals("Premium settlement currency should be HKD", "HKD", updatedRequest.getPremiumSettlementCurrency());
		
		assertEquals("Premium settlement date should be 24 Dec 2014", "24 Dec 2014", updatedRequest.getPremiumSettlementDate());
		
		assertEquals("Premium settlement days override should be 51", 51, updatedRequest.getPremiumSettlementDaysOverride());
		
		assertEquals("Premium settlement FX rate should be 231.0", 231.0, updatedRequest.getPremiumSettlementFXRate(), 0.1);
		
		assertEquals("Request should be test 1", "test 1", updatedRequest.getRequest());
		
		assertEquals("Rho should be 241.0", 241.0, updatedRequest.getRho(), 0.1);
		
		assertEquals("Rho notional should be 251.0", 251.0, updatedRequest.getRhoNotional(), 0.1);
		
		assertEquals("Rho shares should be 261.0", 261.0, updatedRequest.getRhoShares(), 0.1);
		
		assertEquals("Sales comment should be test 1", "test 1", updatedRequest.getSalesComment());
		
		assertEquals("Sales credit amount should be 271.0", 271.0, updatedRequest.getSalesCreditAmount(), 0.1);
		
		assertEquals("Sales credit currency should be HKD", "HKD", updatedRequest.getSalesCreditCurrency());
		
		assertEquals("Sales credit FX rate should be 281.0", 281.0, updatedRequest.getSalesCreditFXRate(), 0.1);
		
		assertEquals("Sales credit percentage should be 291.0", 291.0, updatedRequest.getSalesCreditPercentage(), 0.1);
		
		assertEquals("Status should be test", "test 1", updatedRequest.getStatus());
		
		assertEquals("Theta should be 311.0", 311.0, updatedRequest.getTheta(), 0.1);
		
		assertEquals("Theta notional should be 321.0", 321.0, updatedRequest.getThetaNotional(), 0.1);
		
		assertEquals("Theta shares should be 331.0", 331.0, updatedRequest.getThetaShares(), 0.1);
		
		assertEquals("Total premium should be 351.0", 351.0, updatedRequest.getTotalPremium(), 0.1);
		
		assertEquals("Trade date should be 24 Dec 2013", "24 Dec 2013", updatedRequest.getTradeDate());
		
		assertEquals("Traders comment should be test 1", "test 1", updatedRequest.getTraderComment());
		
		assertEquals("Vega should be 361.0", 361.0, updatedRequest.getVega(), 0.1);
		
		assertEquals("Vega notional should be 371.0", 371.0, updatedRequest.getVegaNotional(), 0.1);
		
		assertEquals("Vega shares should be 381.0", 381.0, updatedRequest.getVegaShares(), 0.1);
	}
}
