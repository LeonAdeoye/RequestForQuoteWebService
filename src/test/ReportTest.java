import java.util.Arrays;
import java.util.GregorianCalendar;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.util.Assert;

import com.leon.ws.rfq.reporting.ReportingController;
import com.leon.ws.rfq.reporting.RequestCountReportDataImpl;

@RunWith(value = Parameterized.class)
public class ReportTest
{
	private static ReportingController reportingController;
	private final String categoryType;
	private final GregorianCalendar fromDate;
	private final int minimumCount;

	public ReportTest(String categoryType, GregorianCalendar fromDate, int minimumCount)
	{
		this.categoryType = categoryType;
		this.fromDate = fromDate;
		this.minimumCount = minimumCount;
	}

	@Parameters
	public static Iterable<Object []> data()
	{
		return Arrays.asList(new Object[][]
				{
				{"Client", new GregorianCalendar(2013,10,1), 0},
				{"Status", new GregorianCalendar(2013,10,1), 0},
				{"TradeDate", new GregorianCalendar(2013,10,1), 0},
				{"BookCode", new GregorianCalendar(2013,10,1), 0}
				});
	}

	@BeforeClass
	public static void oneTimeSetUp()
	{
		// Arrange
		ApplicationContext context = new FileSystemXmlApplicationContext(".\\src\\main\\webapp\\WEB-INF\\cxf-servlet.xml");
		reportingController = (ReportingController) context.getBean("reportingController");
	}

	@Test
	public void test_getRequestsByCategory_VariousInput_ValidResultsShouldBeReturned()
	{
		// Act
		List<RequestCountReportDataImpl> result = reportingController.getRequestsByCategory(this.categoryType, this.fromDate, this.minimumCount);
		// Assert
		Assert.isTrue(result.size() > 1);
	}
}
