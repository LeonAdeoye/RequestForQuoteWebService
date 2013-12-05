import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.leon.ws.rfq.underlying.UnderlyingDetailImpl;
import com.leon.ws.rfq.underlying.UnderlyingManagerDao;

@ContextConfiguration(locations = { "classpath:/cxf-servlet-test.xml" })
public class UnderlyingDaoTest extends AbstractJUnit4SpringContextTests
{
	@Autowired
	private UnderlyingManagerDao underlyingDao;
	
	@Autowired
	private DataSourceTransactionManager transactionManager;
	
	private TransactionStatus status;
	
	public UnderlyingDaoTest() {}

	@Test
	public void test_save_AddValidTestUnderlying_TotalCountOfUnderlyingsIncrementedByOne()
	{
		// Arrange
		List<UnderlyingDetailImpl> beforeSave = this.underlyingDao.getAll();
				
		// Act
		this.underlyingDao.save("TEST", "TEST", "testuser");
		List<UnderlyingDetailImpl> afterSave = this.underlyingDao.getAll();
		// Assert
		assertEquals("Test underlying not saved with RIC: TEST", beforeSave.size() + 1, afterSave.size());
	}
	
	@Test
	public void test_getAll_AddValidTestUnderlying_ReturnsAllUnderlyingsIncludingRecentlyAddedTestUnderlying()
	{
		// Arrange
		List<UnderlyingDetailImpl> before = this.underlyingDao.getAll();
		this.underlyingDao.save("TEST", "TEST", "testuser");
		
		// Act
		List<UnderlyingDetailImpl> after = this.underlyingDao.getAll();
		
		// Assert
		assertEquals("getAll does not return saved underlyings", before.size() + 1, after.size());
	}
	
	@Test
	public void test_get_AddAndRetrieveValidTestUnderlying_RetrievesNewlyAddedUnderlying()
	{
		// Arrange
		this.underlyingDao.save("TEST", "TEST", "testuser");
		
		// Act
		UnderlyingDetailImpl newUnderlying = this.underlyingDao.get("TEST");
		
		// Assert
		assertNotNull("get method does not retrieve newly saved underlying", newUnderlying);
	}
	
	@Test
	public void test_updateValidity_AddThenUpdateValidityAndRetrieveValidTestUnderlying_UpdatesNewlyAddedUnderlying()
	{
		// Arrange
		this.underlyingDao.save("TEST", "TEST", "testuser");
		UnderlyingDetailImpl UnderlyingAfterSave = this.underlyingDao.get("TEST");
		
		// Act
		this.underlyingDao.updateValidity("TEST", false, "testuser");
		UnderlyingDetailImpl UnderlyingAfterUpdate = this.underlyingDao.get("TEST");
		
		// Assert
		assertNotEquals("updateValidity method does not update validity of underlying",
				UnderlyingAfterSave.getIsValid(), UnderlyingAfterUpdate.getIsValid());
	}
	
	@Before
	public void setUp()
	{
		assertNotNull("autowired underlying dao should not be null", this.underlyingDao);
		assertNotNull("autowired transaction manager should not be null", this.transactionManager);
		
		TransactionDefinition paramTransactionDefinition = new DefaultTransactionDefinition();
		this.status=this.transactionManager.getTransaction(paramTransactionDefinition);
	}
	
	@After
	public void tearDown()
	{
		this.transactionManager.rollback(this.status);
	}
}
