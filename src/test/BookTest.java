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

import com.leon.ws.rfq.book.BookController;
import com.leon.ws.rfq.book.BookManagerDao;

public class BookTest
{
	@Rule public JUnitRuleMockery mockContext = new JUnitRuleMockery();
	private static final Logger logger = LoggerFactory.getLogger(BookTest.class);
	private BookController bookController;
	private BookManagerDao daoMock;

	public BookTest()
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
			this.bookController = (BookController) context.getBean("bookController");
			this.daoMock = this.mockContext.mock(BookManagerDao.class);
			this.bookController.setBookManagerDao(this.daoMock);
		}
		catch(BeansException be)
		{
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
		this.mockContext.checking(new Expectations() {{  oneOf (BookTest.this.daoMock).save("test bookCode", "test entity", "test user"); }});
		// act
		this.bookController.save("test bookCode", "test entity", "test user");
	}

	@Test
	public void test_delete_DaoDeleteMethodCalled()
	{
		// arrange
		this.mockContext.checking(new Expectations() {{  oneOf (BookTest.this.daoMock).delete("test bookCode"); }});
		// act
		this.bookController.delete("test bookCode");
	}

	@Test
	public void test_updateValidity_DaoUpdateValidityMethodCalled()
	{
		// arrange
		this.mockContext.checking(new Expectations() {{  oneOf (BookTest.this.daoMock).updateValidity("test bookCode", true); }});
		// act
		this.bookController.updateValidity("test bookCode", true);
	}

	@Test
	public void test_getAll_DaoGetAllMethodCalled()
	{
		// arrange
		this.mockContext.checking(new Expectations() {{  oneOf (BookTest.this.daoMock).getAll(); }});
		// act
		this.bookController.getAll();
	}

	@Test(expected = IllegalArgumentException.class)
	public void test_save_emptyBookCode_illegalArgumentExceptionThrown()
	{
		// act
		this.bookController.save("", "test entity", "test user");
	}

	@Test(expected = IllegalArgumentException.class)
	public void test_save_emptyEntity_illegalArgumentExceptionThrown()
	{
		// act
		this.bookController.save("test bookCode", "", "test user");
	}

	@Test(expected = IllegalArgumentException.class)
	public void test_save_emptyUser_illegalArgumentExceptionThrown()
	{
		// act
		this.bookController.save("test bookCode", "test entity", "");
	}

	@Test(expected = IllegalArgumentException.class)
	public void test_delete_emptyBookCode_illegalArgumentExceptionThrown()
	{
		// act
		this.bookController.delete("");
	}

	@Test(expected = IllegalArgumentException.class)
	public void test_updateValidity_emptyBookCode_illegalArgumentExceptionThrown()
	{
		// act
		this.bookController.updateValidity("", true);
	}

	@After
	public void tearDown()
	{
	}

}
