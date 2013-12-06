import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import com.leon.ws.rfq.search.SearchController;
import com.leon.ws.rfq.search.SearchCriterionImpl;

@ContextConfiguration(locations = { "classpath:/cxf-servlet-test.xml" })
public class SearchingTest extends AbstractJUnit4SpringContextTests
{
	@Autowired
	private SearchController searcher;
	
	public SearchingTest() {}
		
	@Before
	public void setUp()
	{
		assertNotNull("autowired holiday controller should not be null", this.searcher);
	}
	
	public void test_saveAndDelete_addTwoValidCriterionSameOwnerSameKey_twoCriteriaShouldBeAddedWithSameOwnerAndKey()
	{
		this.searcher.save("bob", "bob", "bob", "bob", true, true);
		this.searcher.save("bob", "bob", "ethan", "ethan", true, true);
		
		List<SearchCriterionImpl> startList = this.searcher.get("bob", "bob");
		assertEquals("List size of newly added criteria does not match expectations!", 2, startList.size());
				
		this.searcher.delete("bob", "bob");
		List<SearchCriterionImpl> endList = this.searcher.get("bob", "bob");
		assertEquals("List size of after deletion of newly added criteria does not match expectations!", 0, endList.size());
	}
	
	public void test_saveAndDelete_AddValidCriterion_RetrievedCriterionJustAddedShouldMatchExpected()
	{
		this.searcher.save("bob", "bob", "bob", "bob", true, true);
		List<SearchCriterionImpl> list = this.searcher.get("bob", "bob");
		SearchCriterionImpl expectedCriterion = new SearchCriterionImpl("bob", "bob", "bob", "bob", true, true);
		assertTrue("Newly added criteria does not match expectations!", list.contains(expectedCriterion));
		
		this.searcher.delete("bob", "bob");
		List<SearchCriterionImpl> endList = this.searcher.get("bob", "bob");
		assertEquals("List size of after deletion of newly added criteria does not match expectations!", 0, endList.size());
		
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
