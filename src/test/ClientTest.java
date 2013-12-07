import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import com.leon.ws.rfq.client.ClientController;
import com.leon.ws.rfq.client.ClientManagerDao;

@ContextConfiguration(locations = { "classpath:/cxf-servlet-test.xml" })
public class ClientTest extends AbstractJUnit4SpringContextTests
{
	@Rule public final JUnitRuleMockery mockContext = new JUnitRuleMockery();
	
	@Autowired
	private ClientController clientController;
	
	private final ClientManagerDao daoMock = this.mockContext.mock(ClientManagerDao.class);

	public ClientTest() {}

	@Before
	public void setUp()
	{
		this.clientController.setClientManagerDao(this.daoMock);
	}

	@Test
	public void test_save_DaoSaveMethodCalled()
	{
		// arrange
		this.mockContext.checking(new Expectations() {{  oneOf (ClientTest.this.daoMock).save("test client", "test tier", "test user"); }});
		// act
		this.clientController.save("test client", "test tier", "test user");
	}

	@Test
	public void test_updateTier_DaoUpdateTierMethodCalled()
	{
		// arrange
		this.mockContext.checking(new Expectations() {{  oneOf (ClientTest.this.daoMock).updateTier(Integer.MAX_VALUE, "test tier", "test user"); }});
		// act
		this.clientController.updateTier(Integer.MAX_VALUE, "test tier", "test user");
	}

	@Test
	public void test_updateValidity_DaoUpdateValidityMethodCalled()
	{
		// arrange
		this.mockContext.checking(new Expectations() {{  oneOf(ClientTest.this.daoMock).updateValidity(Integer.MAX_VALUE, true, "test user"); }});
		// act
		this.clientController.updateValidity(Integer.MAX_VALUE, true, "test user");
	}

	@Test
	public void test_getAll_DaoGetAllMethodCalled()
	{
		// arrange
		this.mockContext.checking(new Expectations() {{  oneOf (ClientTest.this.daoMock).getAll(); }});
		// act
		this.clientController.getAll();
	}

	@Test(expected = IllegalArgumentException.class)
	public void test_save_emptyName_illegalArgumentExceptionThrown()
	{
		// act
		this.clientController.save("", "test tier", "test user");
	}

	@Test(expected = IllegalArgumentException.class)
	public void test_save_emptyTier_illegalArgumentExceptionThrown()
	{
		// act
		this.clientController.save("test name", "", "test user");
	}

	@Test(expected = IllegalArgumentException.class)
	public void test_save_emptyUser_illegalArgumentExceptionThrown()
	{
		// act
		this.clientController.save("test name", "test entity", "");
	}

	@Test(expected = IllegalArgumentException.class)
	public void test_updateTier_emptyTier_illegalArgumentExceptionThrown()
	{
		// act
		this.clientController.updateTier(Integer.MAX_VALUE, "", "test user");
	}

	@Test(expected = IllegalArgumentException.class)
	public void test_updateTier_emptyUser_illegalArgumentExceptionThrown()
	{
		// act
		this.clientController.updateTier(Integer.MAX_VALUE, "test tier", "");
	}

	@Test(expected = IllegalArgumentException.class)
	public void test_updateTier_invalidIdentifier_illegalArgumentExceptionThrown()
	{
		// act
		this.clientController.updateTier(Integer.MIN_VALUE, "test tier", "test user");
	}

	@Test(expected = IllegalArgumentException.class)
	public void test_updateValidity_emptyUser_illegalArgumentExceptionThrown()
	{
		// act
		this.clientController.updateValidity(Integer.MAX_VALUE, true, "");
	}

	@Test(expected = IllegalArgumentException.class)
	public void test_updateValidity_invalidIdentifier_illegalArgumentExceptionThrown()
	{
		// act
		this.clientController.updateValidity(Integer.MIN_VALUE, true, "test user");
	}

	@After
	public void tearDown()
	{
	}
}
