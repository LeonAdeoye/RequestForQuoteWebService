import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import com.leon.ws.rfq.user.UserController;
import com.leon.ws.rfq.user.UserManagerDao;

@ContextConfiguration(locations = { "classpath:/cxf-servlet-test.xml" })
public class UserTest extends AbstractJUnit4SpringContextTests
{
	@Rule public JUnitRuleMockery mockContext = new JUnitRuleMockery();
	
	@Autowired
	private UserController userController;
	
	private final UserManagerDao daoMock = this.mockContext.mock(UserManagerDao.class);

	public UserTest()
	{
	}

	@Before
	public void setUp()
	{
		this.userController.setUserManagerDao(this.daoMock);
	}

	@Test
	public void test_save_DaoSaveMethodCalled()
	{
		// arrange
		this.mockContext.checking(new Expectations() {{  oneOf (UserTest.this.daoMock).save("testUserId", "firstName", "lastName",
				"emailAddress", "locationName", 1, "testUser"); }});
		// act
		this.userController.save("testUserId", "firstName", "lastName", "emailAddress", "locationName", 1, "testUser");
	}

	@Test
	public void test_delete_DaoDeleteMethodCalled()
	{
		// arrange
		this.mockContext.checking(new Expectations() {{  oneOf (UserTest.this.daoMock).delete("testUser"); }});
		// act
		this.userController.delete("testUser");
	}

	@Test
	public void test_updateValidity_DaoUpdateValidityMethodCalled()
	{
		// arrange
		this.mockContext.checking(new Expectations() {{  oneOf (UserTest.this.daoMock).updateValidity("testUserId", true, "testUser"); }});
		// act
		this.userController.updateValidity("testUserId", true, "testUser");
	}

	@Test
	public void test_getAll_DaoGetAllMethodCalled()
	{
		// arrange
		this.mockContext.checking(new Expectations() {{  oneOf (UserTest.this.daoMock).getAll(); }});
		// act
		this.userController.getAll();
	}

	@Test(expected = IllegalArgumentException.class)
	public void test_save_emptyUserId_illegalArgumentExceptionThrown()
	{
		// act
		this.userController.save("", "firstName", "lastName", "emailAddress", "locationName", 1, "testUser");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void test_save_emptyFirstName_illegalArgumentExceptionThrown()
	{
		// act
		this.userController.save("testUserId", "", "lastName", "emailAddress", "locationName", 1, "testUser");
	}

	@Test(expected = IllegalArgumentException.class)
	public void test_save_emptyLastName_illegalArgumentExceptionThrown()
	{
		// act
		this.userController.save("testUserId", "firstName", "", "emailAddress", "locationName", 1, "testUser");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void test_save_emptyEmailAddress_illegalArgumentExceptionThrown()
	{
		// act
		this.userController.save("testUserId", "firstName", "lastName", "", "locationName", 1, "testUser");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void test_save_emptyLocationName_illegalArgumentExceptionThrown()
	{
		// act
		this.userController.save("testUserId", "firstName", "lastName", "emailAddress", "", 1, "testUser");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void test_save_emptySavedByUser_illegalArgumentExceptionThrown()
	{
		// act
		this.userController.save("testUserId", "firstName", "lastName", "emailAddress", "locationName", 1, "");
	}

	@Test(expected = IllegalArgumentException.class)
	public void test_updateValidity_emptyUserId_illegalArgumentExceptionThrown()
	{
		// act
		this.userController.updateValidity("", true, "testUser");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void test_updateValidity_emptyUpdatedByUser_illegalArgumentExceptionThrown()
	{
		// act
		this.userController.updateValidity("testUserId", true, "");
	}

	@After
	public void tearDown()
	{
	}
}


