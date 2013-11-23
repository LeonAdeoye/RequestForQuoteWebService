import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.junit.Test;

import com.leon.ws.rfq.utilities.UtilityMethods;


public class UtilityTest
{
	@Test
	public void test_convertToDate_validMMddyyyyDateString_ValidDateReturned()
	{
		// act
		assertEquals(new GregorianCalendar(2012, Calendar.DECEMBER, 23).getTime() , UtilityMethods.convertToDate("12/23/2012"));
	}


	@Test(expected = IllegalArgumentException.class)
	public void test_convertToDate_emptyDateString_throwsIllegalArgumentException()
	{
		// act
		UtilityMethods.convertToDate("");
	}

	@Test
	public void test_convertToDate_invalidDateString_returnsNull()
	{
		// act
		assertNull(null, UtilityMethods.convertToDate("invalid date"));
	}
}
