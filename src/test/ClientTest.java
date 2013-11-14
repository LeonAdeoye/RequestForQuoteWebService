import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.leon.ws.rfq.client.ClientController;
import com.leon.ws.rfq.client.ClientManagerDao;

public class ClientTest
{
	@Rule public JUnitRuleMockery mockContext = new JUnitRuleMockery();
	private static final Logger logger = LoggerFactory.getLogger(ClientTest.class);
	private ClientController clientController;
	private ClientManagerDao daoMock;

	public ClientTest()
	{
		initializeBean();
	}

	@BeforeClass
	public static void oneTimeSetUp()
	{
	}

	private void initializeBean()
	{
		try
		{
			ApplicationContext context = new FileSystemXmlApplicationContext(".\\src\\main\\webapp\\WEB-INF\\cxf-servlet.xml");
			this.clientController = (ClientController) context.getBean("clientController");
			this.daoMock = this.mockContext.mock(ClientManagerDao.class);
			this.clientController.setClientManagerDao(this.daoMock);
		}
		catch(BeansException be)
		{
			logger.error("Failed to load application context for client controller!", be);
		}
	}

	@Before
	public void setUp()
	{
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
