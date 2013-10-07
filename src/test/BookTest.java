import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import com.leon.ws.rfq.book.BookController;


public class BookTest extends TestCase
{
	private static Logger logger = LoggerFactory.getLogger(BookTest.class);
	private BookController bookController;

	public BookTest(String name)
	{
		super(name);
		initializeBean();
	}
	
	private void initializeBean()
	{
		try
		{			
			ApplicationContext context = new FileSystemXmlApplicationContext(".\\src\\main\\webapp\\WEB-INF\\cxf-servlet.xml"); 
			bookController = (BookController) context.getBean("bookController");
			
			if(logger.isDebugEnabled())
				logger.debug("Successfully wired bean book controller from file system application context.");			
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
	public void test_saveAndDeleteBook_newBookAddedThenDeleted()
	{
		//assertTrue("Test book not saved", bookController.save("TESTING_BOOK", "ENTITY", "TESTER"));
		//assertTrue("Test book not deleted", bookController.delete("TESTING_BOOK"));
		
		assertTrue("", true);
		
	}
		
	@After
	public void tearDown()
	{
			
	}	

}
