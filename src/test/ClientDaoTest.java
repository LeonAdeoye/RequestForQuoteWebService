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

import com.leon.ws.rfq.client.ClientDetailImpl;
import com.leon.ws.rfq.client.ClientManagerDao;

@ContextConfiguration(locations = { "classpath:/cxf-servlet-test.xml" })
public class ClientDaoTest extends AbstractJUnit4SpringContextTests
{
	@Autowired
	private ClientManagerDao clientDao;
	
	@Autowired
	private DataSourceTransactionManager transactionManager;
	
	private TransactionStatus status;
	
	public ClientDaoTest() {}

	@Test
	public void test_save_AddValidTestClient_TotalCountOfClientsIncrementedByOne()
	{
		// Arrange
		List<ClientDetailImpl> beforeSave = this.clientDao.getAll();
				
		// Act
		this.clientDao.save("TEST", "TEST", "testuser");
		List<ClientDetailImpl> afterSave = this.clientDao.getAll();
		// Assert
		assertEquals("Test client not saved", beforeSave.size() + 1, afterSave.size());
	}
	
	@Test
	public void test_getAll_AddValidTestClient_ReturnsAllClientsIncludingRecentlyAddedTestClient()
	{
		// Arrange
		List<ClientDetailImpl> before = this.clientDao.getAll();
		this.clientDao.save("TEST", "TEST", "testuser");
		
		// Act
		List<ClientDetailImpl> after = this.clientDao.getAll();
		
		// Assert
		assertEquals("getAll does not return saved clients", before.size() + 1, after.size());
	}
	
	@Test
	public void test_get_AddAndRetrieveValidTestClient_RetrievesNewlyAddedClient()
	{
		// Arrange
		ClientDetailImpl clientAfterSave = this.clientDao.save("TEST", "TEST", "testuser");
		
		// Act
		ClientDetailImpl newClient = this.clientDao.get(clientAfterSave.getIdentifier());
		
		// Assert
		assertNotNull("get method does not retrieve newly saved client", newClient);
		assertEquals("get method does not return saved clients", clientAfterSave.getIdentifier(), newClient.getIdentifier());
	}
	
	@Test
	public void test_updateValidity_AddThenUpdateValidityAndRetrieveValidTestClient_UpdatesNewlyAddedClient()
	{
		// Arrange
		ClientDetailImpl clientAfterSave = this.clientDao.save("TEST", "TEST", "testuser");
		
		// Act
		this.clientDao.updateValidity(clientAfterSave.getIdentifier(), !clientAfterSave.getIsValid(), "testuser");
		ClientDetailImpl clientAfterUpdate = this.clientDao.get(clientAfterSave.getIdentifier());
		
		// Assert
		assertNotEquals("updateValidity method does not update validity of client",
				clientAfterUpdate.getIsValid(), clientAfterSave.getIsValid());
	}
	
	@Test
	public void test_updateTier_AddThenUpdateTierAndRetrieveValidTestClient_UpdatesNewlyAddedClient()
	{
		// Arrange
		ClientDetailImpl clientAfterSave = this.clientDao.save("TEST", "TEST", "testuser");
		
		// Act
		this.clientDao.updateTier(clientAfterSave.getIdentifier(), "TEST2", "testuser");
		ClientDetailImpl clientAfterUpdate = this.clientDao.get(clientAfterSave.getIdentifier());
		
		// Assert
		assertNotEquals("updateValidity method does not update tier of client",
				clientAfterUpdate.getTier(), clientAfterSave.getTier());
	}
	
	@Before
	public void setUp()
	{
		assertNotNull("autowired client dao should not be null", this.clientDao);
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
