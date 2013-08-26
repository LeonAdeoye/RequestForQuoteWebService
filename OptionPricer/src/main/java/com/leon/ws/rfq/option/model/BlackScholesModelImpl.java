package com.leon.ws.rfq.option.model;
import java.util.Map;
import java.util.ArrayList;
 
import static java.lang.Math.pow;
import static java.lang.Math.sqrt;
import static java.lang.Math.exp;
import static java.lang.Math.log;
import java.math.BigDecimal;

 
public class BlackScholesModelImpl implements OptionPricingModel
{
        // Variables for intermediate calculations
        private double d1 = 0.0;
        private double d2 = 0.0;
        private double e = 0.0;
        private double t = 0.0;
        private boolean isCallOption = true;
        private boolean isEuropeanOption = true;
        
        public BlackScholesModelImpl()
        {
        }
       
        public void setToCall(boolean isCallOption)
        {
                this.isCallOption = isCallOption;
        }
       
        public void setToEuropean(boolean isEuropeanOption)
        {
                this.isEuropeanOption = isEuropeanOption;
        }
       
        public OptionPriceResult calculate(Map<String, Double> input) throws Exception
        {
        		OptionPriceResult optionResult = new OptionPriceResult();
                try
                {         		
	                double volatility = input.get(VOLATILITY);
	                double interestRate = input.get(INTEREST_RATE);
	                double strike = input.get(STRIKE);
	                double underlyingPrice = input.get(UNDERLYING_PRICE);
	                double timeToExpiryInYears = input.get(TIME_TO_EXPIRY);
	               
	                // Calculate these two intermediate calculations and reuse
	                d1 = (log(underlyingPrice/strike)+(interestRate + volatility * volatility/2)* timeToExpiryInYears)/(volatility * sqrt(timeToExpiryInYears));
	                d2 = d1 - volatility * sqrt(timeToExpiryInYears);
	                e = exp(-interestRate * timeToExpiryInYears);
	                t = sqrt(timeToExpiryInYears);
                                                 
            		optionResult.setPrice(new BigDecimal(this.calculateOptionPrice(underlyingPrice, strike, timeToExpiryInYears, interestRate)));                       
            		optionResult.setDelta(new BigDecimal(this.calculateOptionDelta()));                       
            		optionResult.setGamma(new BigDecimal(this.calculateOptionGamma(underlyingPrice, volatility)));                       
            		optionResult.setVega(new BigDecimal(this.calculateOptionVega(underlyingPrice)));                       
            		optionResult.setRho(new BigDecimal(this.calculateOptionRho(strike, timeToExpiryInYears, interestRate)));                                              
            		optionResult.setTheta(new BigDecimal(this.calculateOptionTheta(underlyingPrice, strike, interestRate, volatility)));
            		
                    return optionResult;
                }
                catch(Exception e)
                {
                        throw new Exception(this.toString() + " calculation error: " + e.getMessage());
                }
        }
       
        public OptionPriceResultSet calculateRange(Map<String, Double> input, String rangeKey, double startValue, double endValue, double increment) throws Exception
        {
        		ArrayList<OptionPriceResult> optionPriceResults = new ArrayList<OptionPriceResult>();
        		OptionPriceResultSet result = new OptionPriceResultSet();
                try
                {
                        for(double value = startValue; value <= endValue; value += increment)
                        {
                                input.put(rangeKey, value);
                                optionPriceResults.add(calculate(input));                                
                        }
                        result.setOptionPriceResult(optionPriceResults);
                }
                catch(Exception e)
                {
                        throw new Exception(this.toString() + " calculation error: " + e.getMessage());
                }              
                return result;
        }     
                       
        public double calculateOptionPrice(double underlyingPrice, double strike, double timeToExpiryInYears, double interestRate) throws Exception
        {
                try
                {
                        if (isCallOption)
                                return underlyingPrice * CND(d1) - strike * e * CND(d2);
                        else
                                return strike * e * CND(-d2) - underlyingPrice * CND(-d1);
                }
                catch(Exception e)
                {
                        throw new Exception("calculateOptionPrice ERROR: " + e.getMessage());
                }
        }
       
        public double calculateOptionDelta() throws Exception
        {
                try
                {
                        if (isCallOption)
                                return CND(d1);
                        else
                                return -1 * CND(-d1);
                }
                catch(Exception e)
                {
                        throw new Exception("calculateOptionDelta ERROR: " + e.getMessage());                  
                }
        }
       
        public double calculateOptionGamma(double underlyingPrice, double volatility) throws Exception
        {
                try
                {
                        return ND(d1)/(underlyingPrice * volatility * t);
                }
                catch(Exception e)
                {
                        throw new Exception("calculateOptionGamma ERROR: " + e.getMessage());
                }
               
        }
       
        public double calculateOptionVega(double underlyingPrice) throws Exception
        {
                try
                {
                        return (ND(d1) *(underlyingPrice * t)) / 100.0;
                }
                catch(Exception e)
                {
                        throw new Exception("calculateOptionVega ERROR: " + e.getMessage());
                }              
        }
       
        public double calculateOptionTheta(double underlyingPrice, double strike, double interestRate, double volatility) throws Exception
        {
                try
                {
                        if (isCallOption)
                                return -1 * ((((underlyingPrice * volatility * ND(d1))/(2 * t))  + (interestRate * strike * e * CND(d2))) / 100.0);
                        else
                                return -1 * ((((underlyingPrice * volatility * ND(d1))/(2 * t))  + (interestRate * strike * e * CND(-d2))) / 100.0);
                }
                catch(Exception e)
                {
                        throw new Exception("calculateOptionTheta ERROR: " + e.getMessage());
                }
        }
       
        public double calculateOptionRho(double strike, double timeToExpiryInYears, double interestRate) throws Exception
        {
                try
                {
                        if (isCallOption)
                                return (timeToExpiryInYears * strike * e * CND(d2)) / 100.0;
                        else
                                return (-timeToExpiryInYears * strike * e * CND(-d2)) /100.0;
                }
                catch(Exception e)
                {
                        throw new Exception("calculateOptionRho ERROR: " + e.getMessage());
                }
        }      
 
        // The cumulative normal distribution function
        public double CND(double X)
        {
                double L, K, w ;
                double a1 = 0.31938153, a2 = -0.356563782, a3 = 1.781477937, a4 = -1.821255978, a5 = 1.330274429;
       
                L = Math.abs(X);
                K = 1.0 / (1.0 + 0.2316419 * L);
                w = 1.0 - 1.0 / sqrt(2.0 * Math.PI) * exp(-L *L / 2) * (a1 * K + a2 * K *K + a3
                * pow(K,3) + a4 * pow(K,4) + a5 * pow(K,5));
       
                if (X < 0.0)
                {
                        w= 1.0 - w;
                }
                return w;
        }
       
        // The normal distribution function
        public double ND(double X)
        {
                double L = Math.abs(X);
                return 1.0 / Math.sqrt(2.0 * Math.PI) * Math.exp(-L *L / 2);
        }              
}