import java.util.GregorianCalendar;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.util.Assert;

import com.leon.ws.rfq.reporting.ReportingController;
import com.leon.ws.rfq.reporting.RequestCountReportDataImpl;

public class ReportErrorTest
{
	private static ReportingController reportingController;
	private final String categoryType;
	private final GregorianCalendar fromDate;
	private final GregorianCalendar ToDate;
	private final int minimumCount;

	public ReportErrorTest()
	{
		this.categoryType = "BookCode";
		this.fromDate = new GregorianCalendar(2013,10,1);
		this.ToDate = new GregorianCalendar(2013,11,1);
		this.minimumCount = 0;
	}

	@BeforeClass
	public static void oneTimeSetUp()
	{
		// Arrange
		ApplicationContext context = new FileSystemXmlApplicationContext(".\\src\\main\\webapp\\WEB-INF\\cxf-servlet.xml");
		reportingController = (ReportingController) context.getBean("reportingController");
	}

	@Test
	public void test_getRequestsByCategory_InvalidCategoryType_NoResultsShouldBeReturned()
	{
		// Act
		List<RequestCountReportDataImpl> result = reportingController.getRequestsByCategory("Invalid Category", this.fromDate, this.minimumCount);
		// Assert
		Assert.isTrue(result.size() == 0);
	}

	@Test
	public void test_getRequestsByCategory_FutureDate_NoResultsShouldBeReturned()
	{
		// Act
		List<RequestCountReportDataImpl> result = reportingController.getRequestsByCategory(this.categoryType, new GregorianCalendar(2020,10,1), this.minimumCount);
		// Assert
		Assert.isTrue(result.size() == 0);
	}

	@Test
	public void test_getRequestsByCategory_MaxValueMinimumCount_NoResultsShouldBeReturned()
	{
		// Act
		List<RequestCountReportDataImpl> result = reportingController.getRequestsByCategory(this.categoryType, this.fromDate, Integer.MAX_VALUE);
		// Assert
		Assert.isTrue(result.size() == 0);
	}

	@Test(expected = NullPointerException.class)
	public void test_getRequestsByCategory_NullCalendar_IllegalArgumentExceptionShouldBeThrown()
	{
		// Act
		reportingController.getRequestsByCategory(this.categoryType, null, this.minimumCount);
	}

	@Test(expected = IllegalArgumentException.class)
	public void test_getRequestsByCategory_EmptyCategoryType_IllegalArgumentExceptionShouldBeThrown()
	{
		// Act
		reportingController.getRequestsByCategory("", this.fromDate, this.minimumCount);
	}

	@Test(expected = IllegalArgumentException.class)
	public void test_getRequestsByCategory_NegativeMinimumCount_IllegalArgumentExceptionShouldBeThrown()
	{
		// Act
		reportingController.getRequestsByCategory(this.categoryType, this.fromDate, Integer.MIN_VALUE);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void test_getGreeksByCategory_NegativeMinimumGreek_IllegalArgumentExceptionShouldBeThrown()
	{
		// Act
		reportingController.getGreeksByCategory(this.categoryType, this.fromDate, this.ToDate, -0.01);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void test_getGreeksByCategory_EmptyCategoryType_IllegalArgumentExceptionShouldBeThrown()
	{
		// Act
		reportingController.getGreeksByCategory("", this.fromDate, this.ToDate, 0.0);
	}
}

