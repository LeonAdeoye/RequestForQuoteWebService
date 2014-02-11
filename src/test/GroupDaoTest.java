import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

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

import com.leon.ws.rfq.group.GroupDetailImpl;
import com.leon.ws.rfq.group.GroupManagerDao;

@ContextConfiguration(locations = { "classpath:/cxf-servlet-test.xml" })
public class GroupDaoTest extends AbstractJUnit4SpringContextTests
{
	@Autowired
	private GroupManagerDao groupDao;
	
	@Autowired
	private DataSourceTransactionManager transactionManager;
	
	private TransactionStatus status;
	
	public GroupDaoTest() {}

	@Test
	public void test_save_AddValidTestGroup_TotalCountOfGroupsIncrementedByOne()
	{
		// Arrange
		List<GroupDetailImpl> beforeSave = this.groupDao.getAll();
				
		// Act
		this.groupDao.save("TEST", "testUser");
		List<GroupDetailImpl> afterSave = this.groupDao.getAll();
		// Assert
		assertEquals("Test group not saved with group name TEST", beforeSave.size() + 1, afterSave.size());
	}
	
	@Test
	public void test_getAll_AddValidTestGroup_ReturnsAllGroupsIncludingRecentlyAddedTestGroup()
	{
		// Arrange
		List<GroupDetailImpl> before = this.groupDao.getAll();
		this.groupDao.save("TEST", "testUser");
		
		// Act
		List<GroupDetailImpl> after = this.groupDao.getAll();
		
		// Assert
		assertEquals("getAll does not return saved groups", before.size() + 1, after.size());
	}
	
	@Test
	public void test_delete_AddAndDeleteValidTestGroup_DeletesNewlyAddedGroup()
	{
		// Arrange
		List<GroupDetailImpl> beforeSave = this.groupDao.getAll();
		GroupDetailImpl savedGroup = this.groupDao.save("TEST", "testUser");
		
		// Act
		this.groupDao.delete(savedGroup.getGroupId());
		List<GroupDetailImpl> afterDelete = this.groupDao.getAll();
		
		// Assert
		assertEquals("delete method does not remove newly saved group", beforeSave.size(), afterDelete.size());
	}
	
	@Test
	public void test_getGroupByGroupId_AddAndRetrieveValidTestGroup_RetrievesNewlyAddedGroup()
	{
		// Arrange
		GroupDetailImpl savedGroup = this.groupDao.save("TEST", "testUser");
		
		// Act
		GroupDetailImpl newGroup = this.groupDao.getGroupByGroupId(savedGroup.getGroupId());
		
		// Assert
		assertNotNull("getGroupByGroupId method does not retrieve newly saved group", newGroup);
	}
	
	@Test
	public void test_getGroupByGroupId_RetrieveNonExistentGroup_ShouldReturnNull()
	{
		GroupDetailImpl newGroup = this.groupDao.getGroupByGroupId(Integer.MAX_VALUE);
		
		// Assert
		assertNull("getGroupByGroupId method incorrectly retrieves non existent group", newGroup);
	}
	
	@Test
	public void test_updateValidity_AddThenUpdateValidityAndRetrieveValidTestGroup_UpdatesNewlyAddedGroup()
	{
		// Arrange
		GroupDetailImpl savedGroup = this.groupDao.save("TEST", "testUser");
		
		// Act
		this.groupDao.updateValidity(savedGroup.getGroupId(), false, "testUser");
		GroupDetailImpl groupAfterUpdate = this.groupDao.getGroupByGroupId(savedGroup.getGroupId());
		
		// Assert
		assertNotEquals("updateValidity method does not update validity of group",
				savedGroup.getIsValid(), groupAfterUpdate.getIsValid());
	}
	
	@Before
	public void setUp()
	{
		assertNotNull("autowired group dao should not be null", this.groupDao);
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

