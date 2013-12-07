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

import com.leon.ws.rfq.search.SearchCriterionImpl;
import com.leon.ws.rfq.search.SearchManagerDao;

@ContextConfiguration(locations = { "classpath:/cxf-servlet-test.xml" })
public class SearchManagerDaoTest extends AbstractJUnit4SpringContextTests
{
	@Autowired
	private SearchManagerDao searchManagerDao;
	
	@Autowired
	private DataSourceTransactionManager transactionManager;
	
	private TransactionStatus status;
	
	public SearchManagerDaoTest() {}

	@Test
	public void test_save_SaveValidTestSearch_TotalCountOfSearchsIncrementedByOne()
	{
		// Arrange
		List<SearchCriterionImpl> beforeSave = this.searchManagerDao.getAll();
				
		// Act
		this.searchManagerDao.save("TEST", "TEST", "TEST", "TEST", true, true);
		List<SearchCriterionImpl> afterSave = this.searchManagerDao.getAll();
		
		// Assert
		assertEquals("Test search not saved", beforeSave.size() + 1, afterSave.size());
	}
	
	@Test
	public void test_getAll_SaveValidTestSearch_ReturnsAllSearchsIncludingRecentlyAddedTestSearch()
	{
		// Arrange
		List<SearchCriterionImpl> before = this.searchManagerDao.getAll();
		this.searchManagerDao.save("TEST", "TEST", "TEST", "TEST", true, true);
		
		// Act
		List<SearchCriterionImpl> after = this.searchManagerDao.getAll();
		
		// Assert
		assertEquals("getAll method does not return saved searches", before.size() + 1, after.size());
	}
	
	@Test
	public void test_delete_SaveAndDeleteValidTestSearch_DeletesNewlyAddedSearch()
	{
		// Arrange
		List<SearchCriterionImpl> beforeSave = this.searchManagerDao.getAll();
		this.searchManagerDao.save("TEST", "TEST", "TEST", "TEST", true, true);
		
		// Act
		this.searchManagerDao.delete("TEST", "TEST");
		List<SearchCriterionImpl> afterDelete = this.searchManagerDao.getAll();
		
		// Assert
		assertEquals("delete method does not remove newly saved search", beforeSave.size(), afterDelete.size());
	}
	
	@Test
	public void test_get_SaveAndRetrieveValidTestSearch_RetrievesNewlyAddedSearch()
	{
		// Arrange
		this.searchManagerDao.save("TEST", "TEST", "TEST1", "TEST1", true, true);
		this.searchManagerDao.save("TEST", "TEST", "TEST2", "TEST2", true, true);
		
		// Act
		List<SearchCriterionImpl> searches = this.searchManagerDao.get("TEST", "TEST");
		
		// Assert
		assertNotNull("get method returns null and does not retrieve newly saved Search", searches);
		assertEquals("get method does not retrieve the 2 newly saved Search", 2, searches.size());
	}
	
	@Test
	public void test_get_NonExistantSearchOwnerAndKey_RetrievesNonNullEmptyList()
	{
		// Arrange
		// Act
		List<SearchCriterionImpl> searches = this.searchManagerDao.get("TEST", "TEST");
		
		// Assert
		assertEquals("get method does not return non-null empty list for non existant searches", 0, searches.size());
	}
	
	@Test
	public void test_updatePrivacy_SaveThenUpdatePrivacyAndThenRetrieveValidTestSearch_UpdatesNewlyAddedSearch()
	{
		// Arrange
		this.searchManagerDao.save("TEST", "TEST", "TEST", "TEST", true, true);
		List<SearchCriterionImpl> beforeUpdate = this.searchManagerDao.get("TEST", "TEST");
		
		// Act
		this.searchManagerDao.updatePrivacy("TEST", "TEST", false);
		List<SearchCriterionImpl> afterUpdate = this.searchManagerDao.get("TEST", "TEST");
		
		// Assert
		assertNotEquals("updateValidity method does not update validity of Search",
				beforeUpdate.get(0).getIsPrivate(), afterUpdate.get(0).getIsPrivate());
	}
	
	@Before
	public void setUp()
	{
		assertNotNull("autowired search dao should not be null", this.searchManagerDao);
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
