import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import com.leon.ws.rfq.book.BookController;
import com.leon.ws.rfq.book.BookManagerDao;

@ContextConfiguration(locations = { "classpath:/cxf-servlet-test.xml" })
public class BookTest extends AbstractJUnit4SpringContextTests
{
	@Rule public JUnitRuleMockery mockContext = new JUnitRuleMockery();
	
	@Autowired
	private BookController bookController;
	
	private final BookManagerDao daoMock = this.mockContext.mock(BookManagerDao.class);

	public BookTest()
	{
	}

	@Before
	public void setUp()
	{
		this.bookController.setBookManagerDao(this.daoMock);
	}

	@Test
	public void test_save_DaoSaveMethodCalled()
	{
		// arrange
		this.mockContext.checking(new Expectations() {{  oneOf (BookTest.this.daoMock).save("test", "test", "test"); }});
		// act
		this.bookController.save("test", "test", "test");
	}

	@Test
	public void test_delete_DaoDeleteMethodCalled()
	{
		// arrange
		this.mockContext.checking(new Expectations() {{  oneOf (BookTest.this.daoMock).delete("test"); }});
		// act
		this.bookController.delete("test");
	}

	@Test
	public void test_updateValidity_DaoUpdateValidityMethodCalled()
	{
		// arrange
		this.mockContext.checking(new Expectations() {{  oneOf (BookTest.this.daoMock).updateValidity("test", true); }});
		// act
		this.bookController.updateValidity("test", true);
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
		this.bookController.save("", "test", "test");
	}

	@Test(expected = IllegalArgumentException.class)
	public void test_save_emptyEntity_illegalArgumentExceptionThrown()
	{
		// act
		this.bookController.save("test", "", "test");
	}

	@Test(expected = IllegalArgumentException.class)
	public void test_save_emptyUser_illegalArgumentExceptionThrown()
	{
		// act
		this.bookController.save("test", "test", "");
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
