import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.leon.ws.rfq.marketdata.MarketDataService;
import junit.framework.TestCase;


public class MarketDataServiceTest extends TestCase
{
	private static Logger logger = LoggerFactory.getLogger(MarketDataServiceTest.class);
	private MarketDataService marketDataService;
	
	public MarketDataServiceTest(String name)
	{
		super(name);
		initializeBean();
	}
	
	private void initializeBean()
	{
		try
		{			
			ApplicationContext context = new FileSystemXmlApplicationContext(".\\src\\main\\webapp\\WEB-INF\\cxf-servlet.xml"); 
			marketDataService = (MarketDataService) context.getBean("marketDataService");
			
			if(logger.isDebugEnabled())
				logger.debug("Successfully wired bean market data service from file system application context.");			
		}
		catch(BeansException be)
		{
			logger.error("Failed to load application context for market data service!", be);
		}
	}
	
	@Before
	public void setUp()
	{
		marketDataService.clearAll();
	}
	
	@Test
	public void test_setMidPrice_validMidPrice_OnlyOneUnderlyingRICEntryCreated()
	{
		marketDataService.setMidPrice("TEST_RIC", 1972.0);
		assertEquals("Mid price was not added", 1, marketDataService.size());
	}
	
	@Test
	public void test_setAskPrice_validAskPrice_OnlyOneUnderlyingRICEntryCreated()
	{
		marketDataService.setAskPrice("TEST_RIC", 1972.0);
		assertEquals("Ask price was not added", 1, marketDataService.size());
	}
	
	@Test
	public void test_setBidPrice_validClosePrice_OnlyOneUnderlyingRICEntryCreated()
	{
		marketDataService.setBidPrice("TEST_RIC", 1972.0);
		assertEquals("Ask price was not added", 1, marketDataService.size());
	}
	
	@Test
	public void test_setClosePrice_validClosePrice_OnlyOneUnderlyingRICEntryCreated()
	{
		marketDataService.setClosePrice("TEST_RIC", 1972.0);
		assertEquals("Close price was not added", 1, marketDataService.size());
	}
	
	@Test
	public void test_setMidPrice__validMidPriceExists_MidPriceReturned()
	{
		marketDataService.setMidPrice("TEST_RIC", 1972.0);
		assertEquals("Mid price was not added", 1972.0, marketDataService.getMidPrice("TEST_RIC"));
	}
	
	@Test
	public void test_setAskPrice_validAskPriceExists_AskPriceReturned()
	{
		marketDataService.setAskPrice("TEST_RIC", 1972.0);
		assertEquals("Ask price was not added", 1972.0, marketDataService.getAskPrice("TEST_RIC"));
	}
	
	@Test
	public void test_setBidPrice_validBidPriceExists_BidPriceReturned()
	{
		marketDataService.setBidPrice("TEST_RIC", 1972.0);
		assertEquals("Bid price was not added", 1972.0, marketDataService.getBidPrice("TEST_RIC"));
	}
	
	@Test
	public void test_getClosePrice_validClosePriceExists_ClosePriceReturned()
	{
		marketDataService.setClosePrice("TEST_RIC", 1972.0);
		assertEquals("Close price was not added", 1972.0, marketDataService.getClosePrice("TEST_RIC"));
	}
	
	@Test
	public void test_getMidPrice_underlyingRICDoesNotExist_zeroReturned()
	{
		assertEquals("Zero not returned", 0.0, marketDataService.getMidPrice("TEST_RIC"));
	}
	
	@Test
	public void test_getAskPrice_underlyingRICDoesNotExist_zeroReturned()
	{
		assertEquals("Zero not returned", 0.0, marketDataService.getAskPrice("TEST_RIC"));
	}
	
	@Test
	public void test_getBidPrice_underlyingRICDoesNotExist_zeroReturned()
	{
		assertEquals("Zero not returned", 0.0, marketDataService.getBidPrice("TEST_RIC"));
	}
	
	@Test
	public void test_getClosePrice_underlyingRICDoesNotExist_zeroReturned()
	{
		assertEquals("Zero not returned", 0.0, marketDataService.getClosePrice("TEST_RIC"));
	}	
		
	@After
	public void tearDown()
	{
		
	}	
}
