import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

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

	public RequestManagerDaoTest() {}
	
	@Before
	public void setUp()
	{
		assertNotNull("autowired request manager dao should not be null", this.requestManagerDao);
		assertNotNull("autowired transaction manager should not be null", this.transactionManager);
		
		TransactionDefinition paramTransactionDefinition = new DefaultTransactionDefinition();
		this.status=this.transactionManager.getTransaction(paramTransactionDefinition);
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
		RequestDetailImpl request = new RequestDetailImpl();
		request.setRequest("test");
		request.setTradeDate(null);
		request.setExpiryDate("12/23/2014");
		request.setPremiumSettlementDate("12/23/2014");
		this.requestManagerDao.save(request, "testUser");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void test_save_InvalidTradeDate_ExceptionTrown()
	{
		RequestDetailImpl request = new RequestDetailImpl();
		request.setRequest("test");
		request.setTradeDate("");
		request.setExpiryDate("12/23/2014");
		request.setPremiumSettlementDate("12/23/2014");
		this.requestManagerDao.save(request, "testUser");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void test_save_NullExpiryDate_ExceptionTrown()
	{
		RequestDetailImpl request = new RequestDetailImpl();
		request.setRequest("test");
		request.setTradeDate("12/23/2013");
		request.setExpiryDate(null);
		request.setPremiumSettlementDate("12/23/2014");
		this.requestManagerDao.save(request, "testUser");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void test_save_InvalidExpiryDate_ExceptionTrown()
	{
		RequestDetailImpl request = new RequestDetailImpl();
		request.setRequest("test");
		request.setTradeDate("12/23/2013");
		request.setExpiryDate("");
		request.setPremiumSettlementDate("12/23/2014");
		this.requestManagerDao.save(request, "testUser");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void test_save_NullPremiumSettlementDate_ExceptionTrown()
	{
		RequestDetailImpl request = new RequestDetailImpl();
		request.setRequest("test");
		request.setTradeDate("12/23/2013");
		request.setExpiryDate("12/23/2014");
		request.setPremiumSettlementDate(null);
		this.requestManagerDao.save(request, "testUser");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void test_save_InvalidPremiumSettlementDate_ExceptionTrown()
	{
		RequestDetailImpl request = new RequestDetailImpl();
		request.setRequest("test");
		request.setTradeDate("12/23/2013");
		request.setExpiryDate("12/23/2014");
		request.setPremiumSettlementDate("");
		this.requestManagerDao.save(request, "testUser");
	}
}
