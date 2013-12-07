import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import com.leon.ws.rfq.underlying.UnderlyingController;
import com.leon.ws.rfq.underlying.UnderlyingManagerDao;

@ContextConfiguration(locations = { "classpath:/cxf-servlet-test.xml" })
public class UnderlyingTest extends AbstractJUnit4SpringContextTests
{
	@Rule
	public JUnitRuleMockery mockContext = new JUnitRuleMockery();
	
	@Autowired
	private UnderlyingController underlyingController;
	
	private final UnderlyingManagerDao daoMock = this.mockContext.mock(UnderlyingManagerDao.class);

	public UnderlyingTest() {}

	@BeforeClass
	public static void oneTimeSetUp()
	{
	}

	@Before
	public void setUp()
	{
		this.underlyingController.setUnderlyingManagerDao(this.daoMock);
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

