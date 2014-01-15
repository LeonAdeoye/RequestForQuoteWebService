import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.leon.ws.rfq.option.model.OptionPriceResultSet;
import com.leon.ws.rfq.option.model.OptionPricingModel;
import com.leon.ws.rfq.reporting.ReportingController;

@RunWith(value = Parameterized.class)
public class GreeksExtrapolationReportTest
{
	private static ReportingController reportingController;
	
	int requestId;
	String rangeVariable;
	double rangeIncrement;
	double rangeMinimum;
	double rangeMaximum;
	
	public GreeksExtrapolationReportTest(int requestId, String rangeVariable,
			double rangeMinimum, double rangeMaximum,  double rangeIncrement)
	{
		this.requestId = requestId;
		this.rangeVariable = rangeVariable;
		this.rangeIncrement = rangeIncrement;
		this.rangeMinimum = rangeMinimum;
		this.rangeMaximum = rangeMaximum;
	}
	
	@Parameters
	public static Iterable<Object []> data()
	{
		return Arrays.asList(new Object[][]
				{
				{94, OptionPricingModel.VOLATILITY, 0.1, 0.2, 0.05},
				{94, OptionPricingModel.INTEREST_RATE, 0.05, 0.1, 0.005},
				{94, OptionPricingModel.UNDERLYING_PRICE, 90, 100, 1},
				{94, OptionPricingModel.TIME_TO_EXPIRY, 100, 150, 1},
				{94, OptionPricingModel.STRIKE, 100, 120, 1}
				});
	}
	
	@BeforeClass
	public static void oneTimeSetUp()
	{
		// Arrange
		ApplicationContext context = new ClassPathXmlApplicationContext("classpath:/cxf-servlet-test.xml");
		reportingController = (ReportingController) context.getBean("reportingController");
	}
	
	@Test
	public void test_getGreeksExtrapolation_VariousInput_ValidResultsShouldBeReturned()
	{
		assertNotNull(reportingController);
		// Act
		OptionPriceResultSet result = reportingController.getGreeksExtrapolation(this.requestId, this.rangeVariable, this.rangeMinimum, this.rangeMaximum, this.rangeIncrement);
		// Assert
		assertTrue(result.getResultSet().size() > 0);
	}
}
