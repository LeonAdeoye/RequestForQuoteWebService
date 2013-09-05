import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import com.leon.ws.rfq.holiday.Holiday;
import com.leon.ws.rfq.holiday.HolidayController;
import java.util.GregorianCalendar;
import java.util.List;
import junit.framework.TestCase;

public class HolidayTest extends TestCase
{
	private HolidayController holidayController;

	public HolidayTest(String name)
	{
		super(name);
	}
	
	@Before
	public void setUp()
	{
		// TODO switch to a relative path
		ApplicationContext context = new FileSystemXmlApplicationContext("C:\\development\\git\\RequestForQuoteWebService\\src\\main\\webapp\\WEB-INF\\cxf-servlet.xml");
		holidayController = (HolidayController) context.getBean("holidayController");
	}
	
	@Test
	public void test_addOneValidHolidayToLiverpool_LiverpoolHolidaysIncrementedByOne()
	{	
		holidayController.save("TEST_LOCATION", new GregorianCalendar(2013, 10, 26), "testuser");
		List<Holiday> after = holidayController.get("TEST_LOCATION");
		assertEquals("Test holiday not saved for TEST_LOCATION", 1, after.size());
		
		holidayController.delete("TEST_LOCATION", new GregorianCalendar(2013, 10, 26));
		after = holidayController.get("TEST_LOCATION");
		assertEquals("Test holiday not saved for TEST_LOCATION", 0, after.size());		
	}
	
	@After
	public void tearDown()
	{
			
	}	
}
