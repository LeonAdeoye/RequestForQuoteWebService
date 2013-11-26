import static java.util.Calendar.DECEMBER;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.GregorianCalendar;

import org.junit.Test;

import com.leon.ws.rfq.utilities.UtilityMethods;


public class UtilityTest
{
	@Test
	public void test_convertToDate_validMMddyyyyDateString_ValidDateReturned()
	{
		// assert
		assertEquals(new GregorianCalendar(2012, DECEMBER, 23).getTime() , UtilityMethods.convertToDate("12/23/2012"));
	}

	@Test(expected = IllegalArgumentException.class)
	public void test_convertToDate_emptyDateString_throwsIllegalArgumentException()
	{
		// assert
		UtilityMethods.convertToDate("");
	}

	@Test
	public void test_convertToDate_invalidDateString_returnsNull()
	{
		// assert
		assertNull(null, UtilityMethods.convertToDate("invalid test date"));
	}

	@Test
	public void test_doubleHashCode_validDouble_returnsValidResult()
	{
		//assert
		assertEquals(165753913, UtilityMethods.doubleHashCode(109.657));
	}

	@Test(expected = IllegalArgumentException.class)
	public void test_convertStringFormatOfDate_emptyInputDate_throwsIllegalArgumentException()
	{
		UtilityMethods.convertStringFormatOfDate("", UtilityMethods.DB_DATE_STRING_FORMAT, UtilityMethods.DOTNET_DATE_STRING_FORMAT);
	}

	@Test(expected = IllegalArgumentException.class)
	public void test_convertStringFormatOfDate_invalidInputFormat_throwsIllegalArgumentException()
	{
		UtilityMethods.convertStringFormatOfDate("20121223", "", UtilityMethods.DOTNET_DATE_STRING_FORMAT);
	}

	@Test(expected = IllegalArgumentException.class)
	public void test_convertStringFormatOfDate_invalidOutputFormat_throwsIllegalArgumentException()
	{
		UtilityMethods.convertStringFormatOfDate("20121223", UtilityMethods.DB_DATE_STRING_FORMAT, "");
	}

	@Test
	public void test_convertStringFormatOfDate_invalidInputDate_returnsEmptyString()
	{
		assertEquals("", UtilityMethods.convertStringFormatOfDate("invalid test date", UtilityMethods.DB_DATE_STRING_FORMAT, UtilityMethods.DOTNET_DATE_STRING_FORMAT));
	}

	@Test
	public void test_convertStringFormatOfDate_validDBDate_validDOTNETDate()
	{
		assertEquals("12/23/2012 12:00:00 AM", UtilityMethods.convertStringFormatOfDate("2012-12-23", UtilityMethods.DB_DATE_STRING_FORMAT, UtilityMethods.DOTNET_DATE_STRING_FORMAT));
	}

	@Test
	public void test_convertStringFormatOfDate_validDOTNETDate_validOuput()
	{
		assertEquals("2012-12-23", UtilityMethods.convertStringFormatOfDate("12/23/2012 7:54:00 PM", UtilityMethods.DOTNET_DATE_STRING_FORMAT, UtilityMethods.DB_DATE_STRING_FORMAT));
	}
	
	@SuppressWarnings("serial")
	@Test(expected = IllegalArgumentException.class)
	public void test_join_emptyDelimiter_throwsIllegalArgumentException()
	{
		UtilityMethods.join(new ArrayList<String>() { { add("A"); add("B"); } }, "");
	}
	
	@Test(expected = NullPointerException.class)
	public void test_join_nullList_throwsNullPointerException()
	{
		UtilityMethods.join(null, "");
	}
	
	@SuppressWarnings("serial")
	@Test
	public void test_join_listWithTwoItemsAndCommaDelimiter_validConcatenatedString()
	{
		assertEquals("A,B", UtilityMethods.join(new ArrayList<String>() { { add("A"); add("B"); } }, ","));
	}
	
	@SuppressWarnings("serial")
	@Test
	public void test_join_listWithOneItemAndCommaDelimiter_validConcatenatedString()
	{
		assertEquals("A", UtilityMethods.join(new ArrayList<String>() { { add("A"); } }, ","));
	}
}
