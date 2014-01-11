import junit.framework.TestCase;

import org.junit.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.leon.ws.rfq.option.model.OptionPriceResult;
import com.leon.ws.rfq.option.model.OptionPricingController;

public class PricingTest extends TestCase
{
	private static final Logger logger = LoggerFactory.getLogger(PricingTest.class);
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
			this.pricingController = (OptionPricingController) context.getBean("optionPricer");
			
			if(logger.isDebugEnabled())
				logger.debug("Successfully wired bean option pricing controller from file system application context.");
		}
		catch(BeansException be)
		{
			logger.error("Failed to load application context for option controller!", be);
		}
	}
	
	@Override
	@Before
	public void setUp()
	{
		this.result1 = new OptionPriceResult();
		this.result1.setDelta(0.5156598006927081);
		this.result1.setGamma(0.0295285106625831);
		this.result1.setTheta(-0.06816052348492706);
		this.result1.setVega(0.35877140455038464);
		this.result1.setRho(0.4125266814364821);
		this.result1.setPrice(5.156713918695523);
		
		this.result2 = new OptionPriceResult();
		this.result2.setDelta(0.5185429152657319);
		this.result2.setGamma(0.027674393521714098);
		this.result2.setTheta(-0.06984624247529421);
		this.result2.setVega(0.35866014004141467);
		this.result2.setRho(0.4115343127198103);
		this.result2.setPrice(5.515431101934844);
	}
	
	public void test_calculate_validInput_validDeltaResult()
	{
		OptionPriceResult result = this.pricingController.calculate(this.strike,this.volatility,this.underlyingPrice, this.daysToExpiry, this.interestRate, true, true, this.dayCountConvention);
		assertEquals("Delta does not match expectations!", 0.52917556104899343694114577374421060085296630859375, result.getDelta());
	}

	public void test_calculate_validInput_validGammaResult()
	{
		OptionPriceResult result = this.pricingController.calculate(this.strike,this.volatility,this.underlyingPrice, this.daysToExpiry, this.interestRate, true, true, this.dayCountConvention);
		assertEquals("Gamma does not match expectations!", 0.0221041650951190084273267899561687954701483249664306640625, result.getGamma());
	}
	
	public void test_calculate_validInput_validThetaResult()
	{
		OptionPriceResult result = this.pricingController.calculate(this.strike,this.volatility,this.underlyingPrice, this.daysToExpiry, this.interestRate, true, true, this.dayCountConvention);
		assertEquals("Theta does not match expectations!", -0.07648555867321778223288930576018174178898334503173828125, result.getTheta());
	}
	
	public void test_calculate_validInput_validVegaResult()
	{
		OptionPriceResult result = this.pricingController.calculate(this.strike,this.volatility,this.underlyingPrice, this.daysToExpiry, this.interestRate, true, true, this.dayCountConvention);
		assertEquals("Vega does not match expectations!", 0.3580874745409279302776894837734289467334747314453125, result.getVega());
	}
	
	public void test_calculate_validInput_validRhoResult()
	{
		OptionPriceResult result = this.pricingController.calculate(this.strike,this.volatility,this.underlyingPrice, this.daysToExpiry, this.interestRate, true, true, this.dayCountConvention);
		assertEquals("Rho does not match expectations!", 0.406768112191249919806779189457301981747150421142578125, result.getRho());
	}
	
	// TODO
	/*
	public void test_calculateRange_validInputToCalculateRangeOfTwo_resultSetOfSizeTwo()
	{
		this.pricingController.parameterize(this.strike,this.volatility,this.underlyingPrice,
				this.daysToExpiry, this.interestRate, true, true, this.dayCountConvention);
		
		ExtrapolationPoints results = this.pricingController.calculateRange(OptionPricingModel.VOLATILITY ,
				this.startOfRange, this.endOfRange1, this.increment);
		
		assertEquals("Calculated range resultSet size does not match expectations!",
				3, results.getExtrapolationPoints().size());
	}
	
	public void test_calculateRange_validInputWithRangeOfTwo_resultSetsMatch()
	{
		this.pricingController.parameterize(this.strike,this.volatility,this.underlyingPrice,
				this.daysToExpiry, this.interestRate, true, true, this.dayCountConvention);
		
		ExtrapolationPoints resultSet = this.pricingController.calculateRange(OptionPricingModel.VOLATILITY,
				this.startOfRange, this.endOfRange2, this.increment);
		
		OptionPriceResult[] actualResultSet = new OptionPriceResult[resultSet.getExtrapolationPoints().size()];
		
		for(int i = 0; i < resultSet.getExtrapolationPoints().size(); i++)
		{
			actualResultSet[i] = resultSet.getExtrapolationPoints().get(i).getOptionPriceResult();
		}
				
		OptionPriceResult[] expectedResultSet = new OptionPriceResult[2];
		expectedResultSet[0]= this.result1;
		expectedResultSet[1]= this.result2;
		
		Assert.assertArrayEquals("Calculated range resultset does not match expectations!",
				expectedResultSet, actualResultSet);
	}
	*/
}
