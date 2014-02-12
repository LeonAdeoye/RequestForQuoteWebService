import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import com.leon.ws.rfq.group.GroupController;
import com.leon.ws.rfq.group.GroupManagerDao;

@ContextConfiguration(locations = { "classpath:/cxf-servlet-test.xml" })
public class GroupTest extends AbstractJUnit4SpringContextTests
{
	@Rule public JUnitRuleMockery mockContext = new JUnitRuleMockery();
	
	@Autowired
	private GroupController groupController;
	
	private final GroupManagerDao daoMock = this.mockContext.mock(GroupManagerDao.class);

	public GroupTest()
	{
	}

	@Before
	public void setUp()
	{
		this.groupController.setGroupManagerDao(this.daoMock);
	}

	@Test
	public void test_save_DaoSaveMethodCalled()
	{
		// arrange
		this.mockContext.checking(new Expectations() {{  oneOf (GroupTest.this.daoMock).save("test", "testUser"); }});
		// act
		this.groupController.save("test", "testUser");
	}

	@Test
	public void test_delete_DaoDeleteMethodCalled()
	{
		// arrange
		this.mockContext.checking(new Expectations() {{  oneOf (GroupTest.this.daoMock).delete(1); }});
		// act
		this.groupController.delete(1);
	}

	@Test
	public void test_updateValidity_DaoUpdateValidityMethodCalled()
	{
		// arrange
		this.mockContext.checking(new Expectations() {{  oneOf (GroupTest.this.daoMock).updateValidity(1, true, "testUser"); }});
		// act
		this.groupController.updateValidity(1, true, "testUser");
	}

	@Test
	public void test_getAll_DaoGetAllMethodCalled()
	{
		// arrange
		this.mockContext.checking(new Expectations() {{  oneOf (GroupTest.this.daoMock).getAll(); }});
		// act
		this.groupController.getAll();
	}

	@Test(expected = IllegalArgumentException.class)
	public void test_save_emptyGroupName_illegalArgumentExceptionThrown()
	{
		// act
		this.groupController.save("", "testUser");
	}

	@Test(expected = IllegalArgumentException.class)
	public void test_save_emptyUser_illegalArgumentExceptionThrown()
	{
		// act
		this.groupController.save("test", "");
	}

	@Test(expected = IllegalArgumentException.class)
	public void test_updateValidity_emptyUpdatedBy_illegalArgumentExceptionThrown()
	{
		// act
		this.groupController.updateValidity(1, true, "");
	}

	@After
	public void tearDown()
	{
	}
}

