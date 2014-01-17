import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import com.leon.ws.rfq.option.model.OptionPriceResult;
import com.leon.ws.rfq.option.model.OptionPriceResultSet;
import com.leon.ws.rfq.option.model.OptionPricingController;
import com.leon.ws.rfq.option.model.OptionPricingModel;

@ContextConfiguration(locations = { "classpath:/cxf-servlet-test.xml" })
public class PricingTest extends AbstractJUnit4SpringContextTests
{
	@Autowired
	private OptionPricingController pricingController;
	
	private OptionPriceResult result1;
	private OptionPriceResult result2;
	private final double strike = 100.0;
	private final double underlyingPrice = 90.0;
	private final double volatility = 0.2;
	private final double dayCountConvention = 250.0;
	private final double daysToExpiry = 250.0;
	private final double interestRate = 0.1;
	private final double startOfRange = 0.15;
	private final double endOfRange1 = 0.17;
	private final double endOfRange2 = 0.16;
	private final double increment = 0.01;
	
	
	public PricingTest() {}
	
	@Before
	public void setUp()
	{
		assertNotNull("autowired option price controller should not be null", this.pricingController);
		
		this.result1 = new OptionPriceResult();
		this.result1.setDelta(0.5156598006927081);
		this.result1.setGamma(0.0295285106625831);
		this.result1.setTheta(-0.06816052348492706);
		this.result1.setVega(0.35877140455038464);
		this.result1.setRho(0.4125266814364821);
		this.result1.setPrice(5.156713918695523);
		this.result1.setRangeVariable(0.15);
		
		this.result2 = new OptionPriceResult();
		this.result2.setDelta(0.5185429152657319);
		this.result2.setGamma(0.027674393521714098);
		this.result2.setTheta(-0.06984624247529421);
		this.result2.setVega(0.35866014004141467);
		this.result2.setRho(0.4115343127198103);
		this.result2.setPrice(5.515431101934844);
		this.result2.setRangeVariable(0.16);
	}
	
	@Test
	public void test_calculate_validInput_validDeltaResult()
	{
		OptionPriceResult result = this.pricingController.calculate(this.strike,this.volatility,this.underlyingPrice, this.daysToExpiry, this.interestRate, true, true, this.dayCountConvention);
		assertEquals("Delta does not match expectations!", 0.52917556104899343694114577374421060085296630859375, result.getDelta(), 0.0001);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void test_calculate_ZeroDayCountConvention_IllegalArgumentExceptionThrown()
	{
		this.pricingController.calculate(this.strike,this.volatility, this.underlyingPrice,
				this.daysToExpiry, this.interestRate, true, true, 0);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void test_calculate_InvalidDayCountConvention_IllegalArgumentExceptionThrown()
	{
		this.pricingController.calculate(this.strike,this.volatility, this.underlyingPrice,
				this.daysToExpiry, this.interestRate, true, true, Double.POSITIVE_INFINITY);
	}

	@Test(expected = IllegalArgumentException.class)
	public void test_calculate_ZeroStrike_IllegalArgumentExceptionThrown()
	{
		this.pricingController.calculate(0,this.volatility, this.underlyingPrice,
				this.daysToExpiry, this.interestRate, true, true, this.dayCountConvention);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void test_calculate_NegativeDayCountConvention_IllegalArgumentExceptionThrown()
	{
		this.pricingController.calculate(this.strike,this.volatility, this.underlyingPrice,
				this.daysToExpiry, this.interestRate, true, true, Double.NEGATIVE_INFINITY);
	}

	@Test(expected = IllegalArgumentException.class)
	public void test_calculate_NegativeStrike_IllegalArgumentExceptionThrown()
	{
		this.pricingController.calculate(Double.NEGATIVE_INFINITY,this.volatility, this.underlyingPrice,
				this.daysToExpiry, this.interestRate, true, true, this.dayCountConvention);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void test_calculate_ZeroUnderlyingPrice_IllegalArgumentExceptionThrown()
	{
		this.pricingController.calculate(this.strike,this.volatility, 0,
				this.daysToExpiry, this.interestRate, true, true, this.dayCountConvention);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void test_calculate_NegativeUnderlyingPrice_IllegalArgumentExceptionThrown()
	{
		this.pricingController.calculate(this.strike,this.volatility, Double.NEGATIVE_INFINITY,
				this.daysToExpiry, this.interestRate, true, true, this.strike);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void test_calculate_ZeroVolatility_IllegalArgumentExceptionThrown()
	{
		this.pricingController.calculate(this.strike, 0, this.underlyingPrice,
				this.daysToExpiry, this.interestRate, true, true, this.dayCountConvention);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void test_calculate_NegativeVolatility_IllegalArgumentExceptionThrown()
	{
		this.pricingController.calculate(this.strike, Double.NEGATIVE_INFINITY, this.underlyingPrice,
				this.daysToExpiry, this.interestRate, true, true, this.strike);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void test_calculate_NegativeInterestRate_IllegalArgumentExceptionThrown()
	{
		this.pricingController.calculate(this.strike,this.volatility, this.underlyingPrice,
				this.daysToExpiry, Double.NEGATIVE_INFINITY, true, true, this.strike);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void test_calculate_NegativeDaysToExpiry_IllegalArgumentExceptionThrown()
	{
		this.pricingController.calculate(this.strike,this.volatility, this.underlyingPrice,
				Double.NEGATIVE_INFINITY, this.interestRate, true, true, this.dayCountConvention);
	}
	
	@Test
	public void test_calculate_validInput_validGammaResult()
	{
		OptionPriceResult result = this.pricingController.calculate(this.strike,this.volatility,this.underlyingPrice, this.daysToExpiry, this.interestRate, true, true, this.dayCountConvention);
		assertEquals("Gamma does not match expectations!", 0.0221041650951190084273267899561687954701483249664306640625, result.getGamma(), 0.0001);
	}
	
	@Test
	public void test_calculate_validInput_validThetaResult()
	{
		OptionPriceResult result = this.pricingController.calculate(this.strike,this.volatility,this.underlyingPrice, this.daysToExpiry, this.interestRate, true, true, this.dayCountConvention);
		assertEquals("Theta does not match expectations!", -0.07648555867321778223288930576018174178898334503173828125, result.getTheta(), 0.0001);
	}
	
	@Test
	public void test_calculate_validInput_validVegaResult()
	{
		OptionPriceResult result = this.pricingController.calculate(this.strike,this.volatility,this.underlyingPrice, this.daysToExpiry, this.interestRate, true, true, this.dayCountConvention);
		assertEquals("Vega does not match expectations!", 0.3580874745409279302776894837734289467334747314453125, result.getVega(), 0.0001);
	}
	
	@Test
	public void test_calculate_validInput_validRhoResult()
	{
		OptionPriceResult result = this.pricingController.calculate(this.strike,this.volatility,this.underlyingPrice, this.daysToExpiry, this.interestRate, true, true, this.dayCountConvention);
		assertEquals("Rho does not match expectations!", 0.406768112191249919806779189457301981747150421142578125, result.getRho(), 0.0001);
	}
	
	@Test
	public void test_calculateRange_validInputToCalculateRangeOfTwo_resultSetOfSizeTwo()
	{
		this.pricingController.parameterize(this.strike,this.volatility,this.underlyingPrice,
				this.daysToExpiry, this.interestRate, true, true, this.dayCountConvention);
		
		OptionPriceResultSet resultSet = this.pricingController.calculateRange(OptionPricingModel.VOLATILITY ,
				this.startOfRange, this.endOfRange1, this.increment);
		
		assertEquals("Calculated range resultSet size does not match expectations!",
				3, resultSet.getResultSet().size());
	}
	
	@Test
	public void test_calculateRange_validInputWithRangeOfTwo_resultSetsMatch()
	{
		this.pricingController.parameterize(this.strike,this.volatility,this.underlyingPrice,
				this.daysToExpiry, this.interestRate, true, true, this.dayCountConvention);
		
		OptionPriceResultSet resultSet = this.pricingController.calculateRange(OptionPricingModel.VOLATILITY,
				this.startOfRange, this.endOfRange2, this.increment);
		
		OptionPriceResult[] actualResultSet = new OptionPriceResult[resultSet.getResultSet().size()];
		
		for(int i = 0; i < resultSet.getResultSet().size(); i++)
		{
			actualResultSet[i] = resultSet.getResultSet().get(i);
		}
				
		OptionPriceResult[] expectedResultSet = new OptionPriceResult[2];
		expectedResultSet[0]= this.result1;
		expectedResultSet[1]= this.result2;
		
		assertArrayEquals("Calculated range resultset does not match expectations!",
				expectedResultSet, actualResultSet);
	}
	
}
