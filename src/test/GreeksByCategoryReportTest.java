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

import com.leon.ws.rfq.reporting.GreeksPerCategoryReportDataImpl;
import com.leon.ws.rfq.reporting.ReportingController;

@RunWith(value = Parameterized.class)
public class GreeksByCategoryReportTest
{
	private static ReportingController reportingController;
	private final String categoryType;
	private final GregorianCalendar fromDate;
	private final GregorianCalendar toDate;
	private final double minimumGreeks;

	public GreeksByCategoryReportTest(String categoryType, GregorianCalendar fromDate, GregorianCalendar toDate, double minimumGreeks)
	{
		this.categoryType = categoryType;
		this.fromDate = fromDate;
		this.toDate = toDate;
		this.minimumGreeks = minimumGreeks;
	}

	@Parameters
	public static Iterable<Object []> data()
	{
		return Arrays.asList(new Object[][]
				{
				{"Client", new GregorianCalendar(2013,1,1), new GregorianCalendar(2020,1,1), 0.0},
				{"Status", new GregorianCalendar(2013,1,1), new GregorianCalendar(2020,1,1), 0.0},
				{"TradeDate", new GregorianCalendar(2013,1,1), new GregorianCalendar(2020,1,1), 0.0},
				{"BookCode", new GregorianCalendar(2013,1,1), new GregorianCalendar(2020,1,1), 0.0},
				{"MaturityDate", new GregorianCalendar(2013,1,1), new GregorianCalendar(2020,1,1), 0.0}
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
	public void test_getGreeksByCategory_VariousInput_ValidResultsShouldBeReturned()
	{
		// Act
		List<GreeksPerCategoryReportDataImpl> result = reportingController.getGreeksByCategory(this.categoryType, this.fromDate, this.toDate, this.minimumGreeks);
		// Assert
		Assert.isTrue(result.size() > 0);
	}
}

