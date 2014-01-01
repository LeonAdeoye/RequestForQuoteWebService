import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

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
		this.request.setClientComment("test comment");
		this.request.setClientId(1);
		this.request.setContracts(2);
		this.request.setDelta(10.0);
		this.request.setDeltaNotional(11.0);
		this.request.setDeltaShares(12.0);
		this.request.setExpiryDate("12/23/2014");
		this.request.setGamma(13.0);
		this.request.setGammaNotional(14.0);
		this.request.setGammaShares(15.0);
		this.request.setHedgePrice(16.0);
		this.request.setHedgeType("Test");
		this.request.setImpliedVol(17.0);
		this.request.setInterestRate(18.0);
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
		this.request.setStrike(30.0);
		this.request.setTheta(31.0);
		this.request.setThetaNotional(32.0);
		this.request.setThetaShares(33.0);
		this.request.setTimeToExpiry(34.0);
		this.request.setTotalPremium(35.0);
		this.request.setTradeDate("12/23/2013");
		this.request.setTraderComment("test");
		this.request.setUnderlyingPrice(100);
		this.request.setVega(36.0);
		this.request.setVegaNotional(37.0);
		this.request.setVegaShares(38.0);
		this.request.setVolatility(39.0);
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
	public void test_save_CompleteRequest_SavedAskFinalAmountShouldBeRetrieved()
	{
		// Act
		RequestDetailImpl result = this.requestManagerDao.save(this.request, "testUser");
		// Assert
		assertEquals("Ask final amount should be 1.0", 1.0, result.getAskFinalAmount(), 0.1);
	}
	
	@Test
	public void test_save_CompleteRequest_SavedAskFinalPercentageShouldBeRetrieved()
	{
		// Act
		RequestDetailImpl result = this.requestManagerDao.save(this.request, "testUser");
		// Assert
		assertEquals("Ask final percentage should be 2.0", 2.0, result.getAskFinalPercentage(), 0.1);
	}
	
	@Test
	public void test_save_CompleteRequest_SavedAskImpliedVolShouldBeRetrieved()
	{
		// Act
		RequestDetailImpl result = this.requestManagerDao.save(this.request, "testUser");
		// Assert
		assertEquals("Ask implied vol should be 3.0", 3.0, result.getAskImpliedVol(), 0.1);
	}
	
	@Test
	public void test_save_CompleteRequest_SavedAskPremiumAmountShouldBeRetrieved()
	{
		// Act
		RequestDetailImpl result = this.requestManagerDao.save(this.request, "testUser");
		// Assert
		assertEquals("Ask premium amount should be 4.0", 4.0, result.getAskPremiumAmount(), 0.1);
	}
	
	@Test
	public void test_save_CompleteRequest_SavedAskPremiumPercentageShouldBeRetrieved()
	{
		// Act
		RequestDetailImpl result = this.requestManagerDao.save(this.request, "testUser");
		// Assert
		assertEquals("Ask premium percentage should be 5.0", 5.0, result.getAskPremiumPercentage(), 0.1);
	}
	
	@Test
	public void test_save_CompleteRequest_SavedBidFinalAmountShouldBeRetrieved()
	{
		// Act
		RequestDetailImpl result = this.requestManagerDao.save(this.request, "testUser");
		// Assert
		assertEquals("Bid final amount should be 6.0", 6.0, result.getBidFinalAmount(), 0.1);
	}
	
	@Test
	public void test_save_CompleteRequest_SavedBidFinalPercentageShouldBeRetrieved()
	{
		// Act
		RequestDetailImpl result = this.requestManagerDao.save(this.request, "testUser");
		// Assert
		assertEquals("Bid final percentage should be 7.0", 7.0, result.getBidFinalPercentage(), 0.1);
	}
}
