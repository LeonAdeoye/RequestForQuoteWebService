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

import com.leon.ws.rfq.underlying.UnderlyingController;
import com.leon.ws.rfq.underlying.UnderlyingManagerDao;

public class UnderlyingTest
{
	@Rule public JUnitRuleMockery mockContext = new JUnitRuleMockery();
	private static final Logger logger = LoggerFactory.getLogger(BookTest.class);
	private UnderlyingController underlyingController;
	private UnderlyingManagerDao daoMock;

	public UnderlyingTest()
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
			this.underlyingController = (UnderlyingController) context.getBean("underlyingController");
			this.daoMock = this.mockContext.mock(UnderlyingManagerDao.class);
			this.underlyingController.setUnderlyingManagerDao(this.daoMock);
		}
		catch(BeansException be)
		{
			if(logger.isErrorEnabled())
				logger.error("Failed to load application context for book controller!", be);
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
		this.mockContext.checking(new Expectations() {{  oneOf (UnderlyingTest.this.daoMock).save("test ric", "test description", "test user"); }});
		// act
		this.underlyingController.save("test ric", "test description", "test user");
	}

	@Test
	public void test_updateValidity_DaoUpdateValidityMethodCalled()
	{
		// arrange
		this.mockContext.checking(new Expectations() {{  oneOf (UnderlyingTest.this.daoMock).updateValidity("test ric", true, "test user"); }});
		// act
		this.underlyingController.updateValidity("test ric", true, "test user");
	}

	@Test
	public void test_getAll_DaoGetAllMethodCalled()
	{
		// arrange
		this.mockContext.checking(new Expectations() {{  oneOf (UnderlyingTest.this.daoMock).getAll(); }});
		// act
		this.underlyingController.getAll();
	}

	@Test(expected = IllegalArgumentException.class)
	public void test_save_emptyRic_illegalArgumentExceptionThrown()
	{
		// act
		this.underlyingController.save("", "test description", "test user");
	}

	@Test(expected = IllegalArgumentException.class)
	public void test_save_emptyDescription_illegalArgumentExceptionThrown()
	{
		// act
		this.underlyingController.save("test ric", "", "test user");
	}

	@Test(expected = IllegalArgumentException.class)
	public void test_save_emptyUser_illegalArgumentExceptionThrown()
	{
		// act
		this.underlyingController.save("test ric", "test description", "");
	}

	@Test(expected = IllegalArgumentException.class)
	public void test_updateValidity_emptyRic_illegalArgumentExceptionThrown()
	{
		// act
		this.underlyingController.updateValidity("", true, "test user");
	}

	@Test(expected = IllegalArgumentException.class)
	public void test_updateValidity_emptyUser_illegalArgumentExceptionThrown()
	{
		// act
		this.underlyingController.updateValidity("test ric", true, "");
	}

	@After
	public void tearDown()
	{
	}

}

