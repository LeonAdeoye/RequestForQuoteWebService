import java.util.GregorianCalendar;
import java.util.List;

import org.junit.Test;
import org.springframework.test.AbstractDependencyInjectionSpringContextTests;
import org.springframework.util.Assert;

import com.leon.ws.rfq.reporting.ReportingController;
import com.leon.ws.rfq.reporting.RequestCountReportDataImpl;

//@RunWith(Parameterized.class)
public class ReportTest extends AbstractDependencyInjectionSpringContextTests
{
	@Override
	protected String[] getConfigLocations()
	{
		return new String[] {"file:src\\main\\webapp\\WEB-INF\\cxf-servlet.xml"};
	}

	//@Parameters({"Client,2013-01-01T00:00:00,0","Status,2013-01-01T00:00:00,0","TradeDate,2013-01-01T00:00:00,0"})
	@Test
	public void test_getRequestsByCategory_ClientCategory_ResultReturned()
	{
		ReportingController reportingController = (ReportingController) this.applicationContext.getBean("reportingController");
		List<RequestCountReportDataImpl> result = reportingController.getRequestsByCategory("Client", new GregorianCalendar(2013,10,1), 0);
		Assert.isTrue(result.size() > 1);
	}

	@Test
	public void test_getRequestsByCategory_StatusCategory_ResultReturned()
	{
		ReportingController reportingController = (ReportingController) this.applicationContext.getBean("reportingController");
		List<RequestCountReportDataImpl> result = reportingController.getRequestsByCategory("Status", new GregorianCalendar(2013,10,1), 0);
		Assert.isTrue(result.size() > 1);
	}

	@Test
	public void test_getRequestsByCategory_TradeDateCategory_ResultReturned()
	{
		ReportingController reportingController = (ReportingController) this.applicationContext.getBean("reportingController");
		List<RequestCountReportDataImpl> result = reportingController.getRequestsByCategory("TradeDate", new GregorianCalendar(2013,10,1), 0);
		Assert.isTrue(result.size() > 1);
	}

	@Test
	public void test_getRequestsByCategory_BookCodeCategory_ResultReturned()
	{
		ReportingController reportingController = (ReportingController) this.applicationContext.getBean("reportingController");
		List<RequestCountReportDataImpl> result = reportingController.getRequestsByCategory("BookCode", new GregorianCalendar(2013,10,1), 0);
		Assert.isTrue(result.size() > 1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void test_getRequestsByCategory_EmptyCategoryType_IllegalArgumentExceptionThrown()
	{
		ReportingController reportingController = (ReportingController) this.applicationContext.getBean("reportingController");
		reportingController.getRequestsByCategory("", new GregorianCalendar(2013,10,1), 0);
	}
}
