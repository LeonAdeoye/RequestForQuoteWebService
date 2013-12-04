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
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.leon.ws.rfq.holiday.HolidayController;
import com.leon.ws.rfq.holiday.HolidayImpl;

@ContextConfiguration(locations = { "classpath:/cxf-servlet-test.xml" })
public class HolidayTest extends AbstractJUnit4SpringContextTests
{
	@Autowired
	private HolidayController holidayController;
	
	@Autowired
	private DataSourceTransactionManager transactionManager;
	
	private TransactionStatus status;
	
	public HolidayTest() {}

	@Test
	public void test_save_AddValidTestHoliday_TotalCountOfHolidaysIncrementedByOne()
	{
		// Arrange
		
		// Act
		this.holidayController.save("TEST_LOCATION", "23 Dec 2013", "testuser");
		List<HolidayImpl> after = this.holidayController.get("TEST_LOCATION");
		// Assert
		assertEquals("Test holiday not saved for TEST_LOCATION", 1, after.size());
	}
	
	@Test
	public void test_getAll_AddValidTestHoliday_ReturnsAllHolidaysIncludingRecentlyAddedTestHoliday()
	{
		// Arrange
		List<HolidayImpl> before = this.holidayController.getAll();
		this.holidayController.save("TEST_LOCATION", "23 Dec 2013", "testuser");
		
		// Act
		List<HolidayImpl> after = this.holidayController.getAll();
		
		// Assert
		assertEquals("getAll does not return all saved holidays", before.size() + 1, after.size());
	}
	
	@Test
	public void test_delete_AddAndDeleteValidTestHoliday_DeletesNewlyAddedHoliday()
	{
		// Arrange
		this.holidayController.save("TEST_LOCATION", "23 Dec 2013", "testuser");
		List<HolidayImpl> afterSave = this.holidayController.get("TEST_LOCATION");
		
		// Act
		this.holidayController.delete("TEST_LOCATION", "23 Dec 2013");
		List<HolidayImpl> afterDelete = this.holidayController.get("TEST_LOCATION");
		
		// Assert
		assertEquals("save method does not add test holiday", 1, afterSave.size());
		assertEquals("delete method does not remove newly saved holiday", 0, afterDelete.size());
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
