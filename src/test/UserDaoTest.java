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

import com.leon.ws.rfq.user.UserDetailImpl;
import com.leon.ws.rfq.user.UserManagerDao;

@ContextConfiguration(locations = { "classpath:/cxf-servlet-test.xml" })
public class UserDaoTest extends AbstractJUnit4SpringContextTests
{
	@Autowired
	private UserManagerDao userDao;
	
	@Autowired
	private DataSourceTransactionManager transactionManager;
	
	private TransactionStatus status;
	
	public UserDaoTest() {}

	@Test
	public void test_save_AddValidTestUser_TotalCountOfUsersIncrementedByOne()
	{
		// Arrange
		List<UserDetailImpl> beforeSave = this.userDao.getAll();
				
		// Act
		this.userDao.save("testUserId", "firstName", "lastName", "emailAddress", "locationName", 1, "testUser");
		List<UserDetailImpl> afterSave = this.userDao.getAll();
		// Assert
		assertEquals("Test user not saved with user name TEST", beforeSave.size() + 1, afterSave.size());
	}
	
	@Test
	public void test_getAll_AddValidTestUser_ReturnsAllUsersIncludingRecentlyAddedTestUser()
	{
		// Arrange
		List<UserDetailImpl> before = this.userDao.getAll();
		this.userDao.save("testUserId", "firstName", "lastName", "emailAddress", "locationName", 1, "testUser");
		
		// Act
		List<UserDetailImpl> after = this.userDao.getAll();
		
		// Assert
		assertEquals("getAll does not return saved users", before.size() + 1, after.size());
	}
	
	@Test
	public void test_delete_AddAndDeleteValidTestUser_DeletesNewlyAddedUser()
	{
		// Arrange
		List<UserDetailImpl> beforeSave = this.userDao.getAll();
		UserDetailImpl savedUser = this.userDao.save("testUserId", "firstName", "lastName",
				"emailAddress", "locationName", 1, "testUser");
		
		// Act
		this.userDao.delete(savedUser.getUserId());
		List<UserDetailImpl> afterDelete = this.userDao.getAll();
		
		// Assert
		assertEquals("delete method does not remove newly saved user", beforeSave.size(), afterDelete.size());
	}
	
	@Test
	public void test_getUserByUserId_AddAndRetrieveValidTestUser_RetrievesNewlyAddedUser()
	{
		// Arrange
		UserDetailImpl savedUser = this.userDao.save("testUserId", "firstName", "lastName",
				"emailAddress", "locationName", 1, "testUser");
		
		// Act
		UserDetailImpl newUser = this.userDao.getUserByUserId(savedUser.getUserId());
		
		// Assert
		assertNotNull("getUserByUserId method does not retrieve newly saved user", newUser);
	}
	
	@Test
	public void test_getUserByEmailAddress_AddAndRetrieveValidTestUser_RetrievesNewlyAddedUser()
	{
		// Arrange
		UserDetailImpl savedUser = this.userDao.save("testUserId", "firstName", "lastName",
				"emailAddress", "locationName", 1, "testUser");
		
		// Act
		UserDetailImpl newUser = this.userDao.getUserByEmailAddress(savedUser.getEmailAddress());
		
		// Assert
		assertNotNull("getUserByEmailAddress method does not retrieve newly saved user", newUser);
	}
	
	@Test
	public void test_getUserByGroup_AddThreeValidTestUserWithSameGroupId_ShouldRetrieveThreeUsers()
	{
		// Arrange
		UserDetailImpl savedUser = this.userDao.save("testUserId1", "firstName", "lastName",
				"emailAddress1", "locationName", 1, "testUser");
		
		this.userDao.save("testUserId2", "firstName", "lastName",
				"emailAddress2", "locationName", 1, "testUser");
		
		this.userDao.save("testUserId3", "firstName", "lastName",
				"emailAddress3", "locationName", 1, "testUser");
		
		// Act
		List<UserDetailImpl> groupOneUsers = this.userDao.getUsersByGroup(savedUser.getGroupId());
		
		// Assert
		assertEquals("getUserByGroup method does not retrieve newly saved users", 3, groupOneUsers.size());
	}
	
	@Test
	public void test_getUserByGroup_AddThreeValidTestUserWithSameGroupIdAndRetrieveWithDifferent_ShouldNotRetrieveThreeUsers()
	{
		// Arrange
		this.userDao.save("testUserId1", "firstName", "lastName",
				"emailAddress1", "locationName", 1, "testUser");
		
		this.userDao.save("testUserId2", "firstName", "lastName",
				"emailAddress2", "locationName", 1, "testUser");
		
		this.userDao.save("testUserId3", "firstName", "lastName",
				"emailAddress3", "locationName", 1, "testUser");
		
		// Act
		List<UserDetailImpl> groupOneUsers = this.userDao.getUsersByGroup(Integer.MAX_VALUE);
		
		// Assert
		assertEquals("getUserByGroup method does not retrieve newly saved users", 0, groupOneUsers.size());
	}
	
	@Test
	public void test_getUserByLocation_AddThreeValidTestUserWithSameLocation_ShouldRetrieveThreeNewlyAddedUser()
	{
		// Arrange
		UserDetailImpl savedUser = this.userDao.save("testUserId1", "firstName", "lastName",
				"emailAddress1", "testLocation", 1, "testUser");
		
		this.userDao.save("testUserId2", "firstName", "lastName",
				"emailAddress2", "testLocation", 1, "testUser");
		
		this.userDao.save("testUserId3", "firstName", "lastName",
				"emailAddress3", "testLocation", 1, "testUser");
		
		// Act
		List<UserDetailImpl> testLocationUsers = this.userDao.getUsersByLocation(savedUser.getLocationName());
		
		// Assert
		assertEquals("getUsersByLocation method does not retrieve newly saved users", 3, testLocationUsers.size());
	}
	
	@Test
	public void test_getUserByLocation_AddThreeValidTestUserWithSameLocationAndRetrieveWithDifferentLocation_ShouldNotRetrieveThreeNewlyAddedUser()
	{
		// Arrange
		this.userDao.save("testUserId1", "firstName", "lastName",
				"emailAddress1", "Tokyo", 1, "testUser");
		
		this.userDao.save("testUserId2", "firstName", "lastName",
				"emailAddress2", "Tokyo", 1, "testUser");
		
		this.userDao.save("testUserId3", "firstName", "lastName",
				"emailAddress3", "Tokyo", 1, "testUser");
		
		// Act
		List<UserDetailImpl> tokyoUsers = this.userDao.getUsersByLocation("HongKong");
		
		// Assert
		assertEquals("getUsersByLocation method incorrectly retrieves newly saved users", 0, tokyoUsers.size());
	}
	
	@Test
	public void test_updateValidity_AddThenUpdateValidityAndRetrieveValidTestUser_UpdatesNewlyAddedUser()
	{
		// Arrange
		UserDetailImpl savedUser = this.userDao.save("testUserId", "firstName", "lastName",
				"emailAddress", "locationName", 1, "testUser");
		
		// Act
		this.userDao.updateValidity(savedUser.getUserId(), false, "testUser");
		UserDetailImpl userAfterUpdate = this.userDao.getUserByUserId(savedUser.getUserId());
		
		// Assert
		assertNotEquals("updateValidity method does not update validity of user",
				savedUser.getIsValid(), userAfterUpdate.getIsValid());
	}
	
	@Before
	public void setUp()
	{
		assertNotNull("autowired user dao should not be null", this.userDao);
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


