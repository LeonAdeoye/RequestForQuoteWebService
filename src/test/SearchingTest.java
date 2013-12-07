import static org.junit.Assert.assertNotNull;

import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import com.leon.ws.rfq.search.SearchController;
import com.leon.ws.rfq.search.SearchManagerDao;

@ContextConfiguration(locations = { "classpath:/cxf-servlet-test.xml" })
public class SearchingTest extends AbstractJUnit4SpringContextTests
{
	@Rule public JUnitRuleMockery mockContext = new JUnitRuleMockery();
	
	@Autowired
	private SearchController searcher;
	
	private final SearchManagerDao daoMock = this.mockContext.mock(SearchManagerDao.class);
	
	public SearchingTest() {}
		
	@Before
	public void setUp()
	{
		assertNotNull("autowired holiday controller should not be null", this.searcher);
	}
	
	@Test
	public void test_save_DaoSaveMethodCalled()
	{
		// arrange
		this.mockContext.checking(new Expectations() {{  oneOf (SearchingTest.this.daoMock).save("test", "test", "test", "test", true, true); }});
		// act
		this.searcher.save("test", "test", "test", "test", true, true);
	}

	@Test
	public void test_delete_DaoDeleteMethodCalled()
	{
		// arrange
		this.mockContext.checking(new Expectations() {{  oneOf (SearchingTest.this.daoMock).delete("test", "test"); }});
		// act
		this.searcher.delete("test", "test");
	}

	@Test
	public void test_updatePrivacy_DaoUpdatePrivacyMethodCalled()
	{
		// arrange
		this.mockContext.checking(new Expectations() {{  oneOf (SearchingTest.this.daoMock).updatePrivacy("test", "test", true); }});
		// act
		this.searcher.updatePrivacy("test", "test", true);
	}

	@Test
	public void test_getAll_DaoGetAllMethodCalled()
	{
		// arrange
		this.mockContext.checking(new Expectations() {{  oneOf (SearchingTest.this.daoMock).getAll(); }});
		// act
		this.searcher.getAll();
	}
	
	@Test
	public void test_get_DaoGetMethodCalled()
	{
		// arrange
		this.mockContext.checking(new Expectations() {{  oneOf (SearchingTest.this.daoMock).get("test", "test"); }});
		// act
		this.searcher.get("test", "test");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void test_Save_InvalidOnwer_ThrowsIllegalArgumentException()
	{
		this.searcher.save("", "bob", "bob", "bob", true, true);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void test_Save_InvalidKey_ThrowsIllegalArgumentException()
	{
		this.searcher.save("bob", "", "bob", "bob", true, true);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void test_Save_InvalidControlName_ThrowsIllegalArgumentException()
	{
		this.searcher.save("bob", "bob", "", "bob", true, true);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void test_Save_InvalidControlValue_ThrowsIllegalArgumentException()
	{
		this.searcher.save("bob", "bob", "bob", "", true, true);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void test_delete_InvalidOwner_ThrowsIllegalArgumentException()
	{
		this.searcher.delete("", "bob");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void test_delete_InvalidKey_ThrowsIllegalArgumentException()
	{
		this.searcher.delete("bob", "");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void test_updatePrivacy_InvalidOwner_ThrowsIllegalArgumentException()
	{
		this.searcher.updatePrivacy("", "bob", false);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void test_updatePrivacy_InvalidKey_ThrowsIllegalArgumentException()
	{
		this.searcher.updatePrivacy("bob", "", false);
	}
}
