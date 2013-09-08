import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import java.util.List;
import junit.framework.TestCase;
import org.junit.*; 
import com.leon.ws.rfq.search.SearchController;
import com.leon.ws.rfq.search.SearchCriterion;

public class SearchingTest extends TestCase
{
	private static Logger logger = LoggerFactory.getLogger(SearchingTest.class);
	private SearchController searcher;
	
	public SearchingTest(String name)
	{		
		super(name);
		//initializeBean();
	}
	
	public void initializeBean()
	{
		try
		{
			if(logger.isDebugEnabled())
				logger.debug("Application context class path = " +  System.getProperty("java.class.path"));
			
			ApplicationContext context = new ClassPathXmlApplicationContext("cxf-servlet.xml"); 
			searcher = (SearchController) context.getBean("searchController");
			
			if(logger.isDebugEnabled())
				logger.debug("Successfully wired bean search controller from class path application context.");			
		}
		catch(BeansException be)
		{
			logger.error("Failed to load class path application context ", be);
		}
	}	
	
	@Before
	public void setUp()
	{
		initializeBean();
	}
	
	public void test_saveAndDelete_addTwoValidCriterionSameOwnerSameKey_twoCriteriaShouldBeAddedWithSameOwnerAndKey()
	{	
		searcher.save("bob", "bob", "bob", "bob", true, true);			
		searcher.save("bob", "bob", "ethan", "ethan", true, true);
		
		List<SearchCriterion> startList = searcher.get("bob", "bob");
		assertEquals("List size of newly added criteria does not match expectations!", 2, startList.size());
				
		searcher.delete("bob", "bob");
		List<SearchCriterion> endList = searcher.get("bob", "bob");
		assertEquals("List size of after deletion of newly added criteria does not match expectations!", 0, endList.size());				
	}
	
	public void test_saveAndDelete_AddValidCriterion_RetrievedCriterionJustAddedShouldMatchExpected()
	{
		searcher.save("bob", "bob", "bob", "bob", true, true);
		List<SearchCriterion> list = searcher.get("bob", "bob");		
		SearchCriterion expectedCriterion = new SearchCriterion("bob", "bob", "bob", "bob", true, true);						
		assertTrue("Newly added criteria does not match expectations!", list.contains(expectedCriterion));
		
		searcher.delete("bob", "bob");
		List<SearchCriterion> endList = searcher.get("bob", "bob");
		assertEquals("List size of after deletion of newly added criteria does not match expectations!", 0, endList.size());				
		
	}	
}
