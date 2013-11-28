import java.util.List;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.leon.ws.rfq.holiday.HolidayController;
import com.leon.ws.rfq.holiday.HolidayImpl;

public class HolidayTest extends TestCase
{
	private static final Logger logger = LoggerFactory.getLogger(HolidayTest.class);
	private HolidayController holidayController;

	public HolidayTest(String name)
	{
		super(name);
		initializeBean();
	}

	private void initializeBean()
	{
		try
		{
			ApplicationContext context = new FileSystemXmlApplicationContext(".\\src\\main\\webapp\\WEB-INF\\cxf-servlet.xml");
			this.holidayController = (HolidayController) context.getBean("holidayController");

			if(logger.isDebugEnabled())
				logger.debug("Successfully wired bean holiday controller from file system application context.");
		}
		catch(BeansException be)
		{
			logger.error("Failed to load application context for holiday controller!", be);
		}
	}

	@Override
	@Before
	public void setUp()
	{
	}

	@Test
	public void test_addOneValidHolidayToLiverpool_LiverpoolHolidaysIncrementedByOne()
	{
		this.holidayController.delete("TEST_LOCATION", "23 Dec 2013");
		
		this.holidayController.save("TEST_LOCATION", "23 Dec 2013", "testuser");
		List<HolidayImpl> after = this.holidayController.get("TEST_LOCATION");
		assertEquals("Test holiday not saved for TEST_LOCATION", 1, after.size());

		this.holidayController.delete("TEST_LOCATION", "23 Dec 2013");
		after = this.holidayController.get("TEST_LOCATION");
		assertEquals("Test holiday not deleted for TEST_LOCATION", 0, after.size());
	}

	@Override
	@After
	public void tearDown()
	{

	}
}
