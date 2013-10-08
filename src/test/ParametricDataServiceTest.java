import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import com.leon.ws.rfq.parametric.ParametricDataService;
import junit.framework.TestCase;

public class ParametricDataServiceTest extends TestCase
{
	private static final Logger logger = LoggerFactory.getLogger(ParametricDataServiceTest.class);
	private ParametricDataService parametricDataService;
	
	public ParametricDataServiceTest(String name)
	{
		super(name);
		initializeBean();
	}
	
	private void initializeBean()
	{
		try
		{			
			ApplicationContext context = new FileSystemXmlApplicationContext(".\\src\\main\\webapp\\WEB-INF\\cxf-servlet.xml"); 
			parametricDataService = (ParametricDataService) context.getBean("parametricDataService");
			
			if(logger.isDebugEnabled())
				logger.debug("Successfully wired bean parametric data service from file system application context.");			
		}
		catch(BeansException be)
		{
			logger.error("Failed to load application context for parametric data service!", be);
		}
	}
	
	@Before
	public void setUp()
	{
		parametricDataService.clearAll();
	}
	
	@Test
	public void test_setInterestRate_validRate_InterestRateIsReturnedWithMatchingValue()
	{
		parametricDataService.setInterestRate("TEST_CURRENCY", 1972.0);
		assertEquals("interest rate was not added", 1972.0, parametricDataService.getInterestRate("TEST_CURRENCY"));
	}
	
	@Test
	public void test_getInterestRate_NonExistentInterestRate_ZeroInterestRateIsReturned()
	{
		assertEquals("Zero interest rate was not returned", 0.0, parametricDataService.getInterestRate("TEST_CURRENCY"));
	}
	
	@Test
	public void test_setVolatility_validVol_VolatilityIsReturnedWithMatchingValue()
	{
		parametricDataService.setVolatility("TEST_RIC", 0.1972);
		assertEquals("Volatiltiy was not added", 0.1972, parametricDataService.getVolatility("TEST_RIC"));
	}
	
	@Test
	public void test_getVolatility_NonExistentVolatility_ZeroVolatilityIsReturned()
	{
		assertEquals("Zero volatility was not returned", 0.0, parametricDataService.getVolatility("TEST_RIC"));
	}
	
	@Test
	public void test_setInterestRate_AddMultipleValidRates_InterestRateIsReturnedWithMatchingValue()
	{
		parametricDataService.setInterestRate("TEST_CURRENCY1", 1972.0);
		parametricDataService.setInterestRate("TEST_CURRENCY2", 1026.0);
		assertEquals("interest rate was not added", 1972.0, parametricDataService.getInterestRate("TEST_CURRENCY1"));
	}
	
	@Test
	public void test_setVolatility_AddMultipleValidVols_VolatilityIsReturnedWithMatchingValue()
	{
		parametricDataService.setVolatility("TEST_RIC1", 0.1972);
		parametricDataService.setVolatility("TEST_RIC2", 0.1026);
		assertEquals("Volatiltiy was not added", 0.1972, parametricDataService.getVolatility("TEST_RIC1"));
	}
					
	@After
	public void tearDown()
	{
		
	}	
}
