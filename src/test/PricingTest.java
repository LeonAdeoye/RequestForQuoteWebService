import junit.framework.TestCase;

//import com.leon.ws.rfq.holiday.HolidayController;
import com.leon.ws.rfq.option.model.*;

import org.junit.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import java.math.BigDecimal;

public class PricingTest extends TestCase
{
	private static Logger logger = LoggerFactory.getLogger(PricingTest.class);
	private OptionPricingController pricingController;
	private OptionPriceResult result1;
	private OptionPriceResult result2;
	
	private BigDecimal strike = new BigDecimal("100.0");
	private BigDecimal underlyingPrice = new BigDecimal("90.0");
	private BigDecimal volatility = new BigDecimal("0.2");
	private BigDecimal dayCountConvention = new BigDecimal("250.0");
	private BigDecimal daysToExpiry = new BigDecimal("250.0");
	private BigDecimal interestRate = new BigDecimal("0.1");
	private BigDecimal startOfRange = new BigDecimal("0.15");
	private BigDecimal endOfRange1 = new BigDecimal("0.17");
	private BigDecimal endOfRange2 = new BigDecimal("0.16");
	private BigDecimal increment = new BigDecimal("0.01");	
	
	public PricingTest(String name)
	{
		super(name);
		initializeBean();
	}
	
	private void initializeBean()
	{
		try
		{			
			ApplicationContext context = new FileSystemXmlApplicationContext(".\\src\\main\\webapp\\WEB-INF\\cxf-servlet.xml"); 
			pricingController = (OptionPricingController) context.getBean("optionPricer");
			
			if(logger.isDebugEnabled())
				logger.debug("Successfully wired bean option pricing controller from file system application context.");			
		}
		catch(BeansException be)
		{
			logger.error("Failed to load application context for option controller!", be);
		}
	}	
	
	@Before
	public void setUp()
	{
		//pricingController = new OptionPricingControllerImpl(new BlackScholesModelImpl());
			
		result1 = new OptionPriceResult();
		result1.setDelta(new BigDecimal("0.5156598006927081"));
		result1.setGamma(new BigDecimal("0.0295285106625831"));
		result1.setTheta(new BigDecimal("-0.06816052348492706"));
		result1.setVega(new BigDecimal("0.35877140455038464"));
		result1.setRho(new BigDecimal("0.4125266814364821"));
		
		result2 = new OptionPriceResult();
		result2.setDelta(new BigDecimal("0.5185429152657319"));
		result2.setGamma(new BigDecimal("0.027674393521714098"));
		result2.setTheta(new BigDecimal("-0.06984624247529421"));
		result2.setVega(new BigDecimal("0.35866014004141467"));
		result2.setRho(new BigDecimal("0.4115343127198103"));		
	}
	
	public void test_calculate_validInput_validDeltaResult()
	{                  
		OptionPriceResult result = pricingController.calculate(strike,volatility,underlyingPrice, daysToExpiry, interestRate, true, true, dayCountConvention);
		assertEquals("Delta does not match expectations!", new BigDecimal("0.52917556104899343694114577374421060085296630859375"), result.getDelta());
	}

	public void test_calculate_validInput_validGammaResult()
	{                  
		OptionPriceResult result = pricingController.calculate(strike,volatility,underlyingPrice, daysToExpiry, interestRate, true, true, dayCountConvention);
		assertEquals("Gamma does not match expectations!", new BigDecimal("0.0221041650951190084273267899561687954701483249664306640625"), result.getGamma());
	}
	
	public void test_calculate_validInput_validThetaResult()
	{                  
		OptionPriceResult result = pricingController.calculate(strike,volatility,underlyingPrice, daysToExpiry, interestRate, true, true, dayCountConvention);
		assertEquals("Theta does not match expectations!", new BigDecimal("-0.07648555867321778223288930576018174178898334503173828125"), result.getTheta());
	}
	
	public void test_calculate_validInput_validVegaResult()
	{                  
		OptionPriceResult result = pricingController.calculate(strike,volatility,underlyingPrice, daysToExpiry, interestRate, true, true, dayCountConvention);
		assertEquals("Vega does not match expectations!", new BigDecimal("0.3580874745409279302776894837734289467334747314453125"), result.getVega());
	}
	
	public void test_calculate_validInput_validRhoResult()
	{                  
		OptionPriceResult result = pricingController.calculate(strike,volatility,underlyingPrice, daysToExpiry, interestRate, true, true, dayCountConvention);
		assertEquals("Rho does not match expectations!", new BigDecimal("0.406768112191249919806779189457301981747150421142578125"), result.getRho());
	}
	
	public void test_calculateRange_validInputToCalculateRangeOfTwo_resultSetOfSizeTwo()
	{
		pricingController.parameterize(strike,volatility,underlyingPrice, daysToExpiry, interestRate, true, true, dayCountConvention);
		OptionPriceResultSet results = pricingController.calculateRange(OptionPricingModel.VOLATILITY , startOfRange, endOfRange1, increment);
		assertEquals("Calculated range resultSet size does not match expectations!", 3, results.getOptionPriceResultSet().size());
	}
	
	public void test_calculateRange_validInputWithRangeOfTwo_resultSetOfTwo()
	{
		pricingController.parameterize(strike,volatility,underlyingPrice, daysToExpiry, interestRate, true, true, dayCountConvention);
		OptionPriceResultSet resultSet = pricingController.calculateRange(OptionPricingModel.VOLATILITY , startOfRange, endOfRange2, increment);
		OptionPriceResult[] actualResultSet = resultSet.getOptionPriceResultSet()
				.toArray(new OptionPriceResult[resultSet.getOptionPriceResultSet().size()]);
		
		OptionPriceResult[] expectedResultSet = new OptionPriceResult[2];
		expectedResultSet[0]= result1;
		expectedResultSet[1]= result2;
		
		Assert.assertArrayEquals("Calculated range resultset does not match expectations!", expectedResultSet, actualResultSet);
	}	
}
