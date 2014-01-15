package com.leon.ws.rfq.option.model;
import static java.lang.Math.exp;
import static java.lang.Math.log;
import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

import java.util.HashMap;
import java.util.Map;
 
public final class BlackScholesModelImpl implements OptionPricingModel
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
       
        @Override
		public void setToCall(boolean isCallOption)
        {
            this.isCallOption = isCallOption;
        }
       
        @Override
		public void setToEuropean(boolean isEuropeanOption)
        {
            this.isEuropeanOption = isEuropeanOption;
        }
       
        @Override
		public OptionPriceResult calculate(Map<String, Double> input)
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
                this.d1 = (log(underlyingPrice/strike)+((interestRate + ((volatility * volatility)/2))* timeToExpiryInYears))/(volatility * sqrt(timeToExpiryInYears));
                this.d2 = this.d1 - (volatility * sqrt(timeToExpiryInYears));
                this.e = exp(-interestRate * timeToExpiryInYears);
                this.t = sqrt(timeToExpiryInYears);
                                             
        		optionResult.setPrice(this.calculateOptionPrice(underlyingPrice, strike, timeToExpiryInYears, interestRate));
        		optionResult.setDelta(this.calculateOptionDelta());
        		optionResult.setGamma(this.calculateOptionGamma(underlyingPrice, volatility));
        		optionResult.setVega(this.calculateOptionVega(underlyingPrice));
        		optionResult.setRho(this.calculateOptionRho(strike, timeToExpiryInYears, interestRate));
        		optionResult.setTheta(this.calculateOptionTheta(underlyingPrice, strike, interestRate, volatility));
        		
                return optionResult;
            }
            catch(Exception e)
            {
                throw new RuntimeException(this.toString() + " calculation error: " + e.getMessage());
            }
        }
        
        @Override
		public void calculateRange(OptionPriceResultSet optionPriceResultSet, Map<String, Double> input, String rangeKey, double startValue, double endValue, double increment)
        {
            try
            {
                for(double value = startValue; value <= endValue; value += increment)
                {
                    input.put(rangeKey, value);
                    OptionPriceResult optionPriceResult = calculate(input);
                    optionPriceResult.setRangeVariable(value);
                    optionPriceResultSet.merge(optionPriceResult);
                }
            }
            catch(Exception e)
            {
            	throw new RuntimeException(this.toString() + " calculation range error: " + e.getMessage());
            }
        }
                       
        public double calculateOptionPrice(double underlyingPrice, double strike, double timeToExpiryInYears, double interestRate)
        {
            try
            {
                if (this.isCallOption)
                        return (underlyingPrice * CND(this.d1)) - (strike * this.e * CND(this.d2));
                else
                        return (strike * this.e * CND(-this.d2)) - (underlyingPrice * CND(-this.d1));
            }
            catch(Exception e)
            {
                throw new RuntimeException("calculateOptionPrice ERROR: " + e.getMessage());
            }
        }
       
        public double calculateOptionDelta()
        {
            try
            {
                if (this.isCallOption)
                        return CND(this.d1);
                else
                        return -1 * CND(-this.d1);
            }
            catch(Exception e)
            {
                throw new RuntimeException("calculateOptionDelta ERROR: " + e.getMessage());
            }
        }
       
        public double calculateOptionGamma(double underlyingPrice, double volatility)
        {
            try
            {
                return ND(this.d1)/(underlyingPrice * volatility * this.t);
            }
            catch(Exception e)
            {
                throw new RuntimeException("calculateOptionGamma ERROR: " + e.getMessage());
            }
        }
       
        public double calculateOptionVega(double underlyingPrice)
        {
            try
            {
                return (ND(this.d1) *(underlyingPrice * this.t)) / 100.0;
            }
            catch(Exception e)
            {
                throw new RuntimeException("calculateOptionVega ERROR: " + e.getMessage());
            }
        }
       
        public double calculateOptionTheta(double underlyingPrice, double strike, double interestRate, double volatility)
        {
            try
            {
                if (this.isCallOption)
                        return -1 * ((((underlyingPrice * volatility * ND(this.d1))/(2 * this.t))  + (interestRate * strike * this.e * CND(this.d2))) / 100.0);
                else
                        return -1 * ((((underlyingPrice * volatility * ND(this.d1))/(2 * this.t))  + (interestRate * strike * this.e * CND(-this.d2))) / 100.0);
            }
            catch(Exception e)
            {
                throw new RuntimeException("calculateOptionTheta ERROR: " + e.getMessage());
            }
        }
       
        public double calculateOptionRho(double strike, double timeToExpiryInYears, double interestRate)
        {
            try
            {
                if (this.isCallOption)
                        return (timeToExpiryInYears * strike * this.e * CND(this.d2)) / 100.0;
                else
                        return (-timeToExpiryInYears * strike * this.e * CND(-this.d2)) /100.0;
            }
            catch(Exception e)
            {
                throw new RuntimeException("calculateOptionRho ERROR: " + e.getMessage());
            }
        }
 
        // The cumulative normal distribution function
        public double CND(double X)
        {
            double L, K, w ;
            double a1 = 0.31938153, a2 = -0.356563782, a3 = 1.781477937, a4 = -1.821255978, a5 = 1.330274429;
   
            L = Math.abs(X);
            K = 1.0 / (1.0 + (0.2316419 * L));
            w = 1.0 - ((1.0 / sqrt(2.0 * Math.PI)) * exp((-L *L) / 2) * ((a1 * K) + (a2 * K *K) + (a3
            * pow(K,3)) + (a4 * pow(K,4)) + (a5 * pow(K,5))));
   
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
            return (1.0 / Math.sqrt(2.0 * Math.PI)) * Math.exp((-L *L) / 2);
        }
        
        public static void main(String... args)
        {
        	// For testing...
        	Map<String, Double> input = new HashMap<>();
        	
			input.put(VOLATILITY, 0.2);
			input.put(UNDERLYING_PRICE, 90.0);
			input.put(STRIKE, 100.0);
			input.put(TIME_TO_EXPIRY, 1.0);
			input.put(INTEREST_RATE, 0.1);
			
			OptionPriceResultSet resultSet = new OptionPriceResultSet();
			BlackScholesModelImpl model = new BlackScholesModelImpl();
			
			model.calculateRange(resultSet, input, UNDERLYING_PRICE,	70, 90, 5);
			input.put(STRIKE, 100.0);
			input.put(TIME_TO_EXPIRY, 2.0);
			model.calculateRange(resultSet, input, UNDERLYING_PRICE,	70, 90, 5);
        }
}