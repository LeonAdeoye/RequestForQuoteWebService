import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.leon.ws.rfq.holiday.HolidayController;
import com.leon.ws.rfq.holiday.HolidayImpl;

@ContextConfiguration(locations = { "classpath:/cxf-servlet-test.xml" })
@Transactional
public class HolidayTest extends AbstractJUnit4SpringContextTests
{
	@Autowired
	private HolidayController holidayController;
	
	@Autowired
	private DataSourceTransactionManager transactionManager;
	
	private TransactionStatus status;

	public HolidayTest() {}

	@Test
	public void test_addOneValidHoliday_HolidaysIncrementedByOne()
	{
		this.holidayController.save("TEST_LOCATION", "23 Dec 2013", "testuser");
		List<HolidayImpl> after = this.holidayController.get("TEST_LOCATION");
		assertEquals("Test holiday not saved for TEST_LOCATION", 1, after.size());
	}
	
	@Test
	public void test_getAll_HolidaysIncrementedByOne()
	{
		List<HolidayImpl> before = this.holidayController.getAll();
		this.holidayController.save("TEST_LOCATION", "23 Dec 2013", "testuser");
		List<HolidayImpl> after = this.holidayController.getAll();
		assertEquals("Test holiday not saved for TEST_LOCATION", before.size() + 1, after.size());
	}
	
	@Before
	public void setUp()
	{
		assertNotNull("autowired holiday controller should not be null", this.holidayController);
		assertNotNull("autowired transaction manager should not be null", this.transactionManager);
		
		TransactionDefinition paramTransactionDefinition = new DefaultTransactionDefinition();
		this.status=this.transactionManager.getTransaction(paramTransactionDefinition);
	}
	
	@After
	public void tearDown()
	{
		this.transactionManager.rollback(this.status);
	}
}
