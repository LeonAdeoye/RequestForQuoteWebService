package com.leon.ws.rfq.option.model;

import java.util.HashMap;
import java.util.Map;
 
class OptionPricingModelContext
{
        private OptionPricingModel model = null;
        private Map<String, Double> input = new HashMap<String, Double>();
        private double dayCountConvention = 250.0;
               
        OptionPricingModelContext(OptionPricingModel model)
        {
                this.model = model;
        }
       
        OptionPricingModelContext(OptionPricingModel model, double volatility, double interestRate,
                        double strike, double underlyingPrice, double timeToExpiry)
        {
                this.model = model;
                this.input.put(OptionPricingModel.VOLATILITY , volatility);
                this.input.put(OptionPricingModel.INTEREST_RATE , interestRate);
                this.input.put(OptionPricingModel.STRIKE , strike);
                this.input.put(OptionPricingModel.UNDERLYING_PRICE , underlyingPrice);
                this.input.put(OptionPricingModel.TIME_TO_EXPIRY , timeToExpiry);
        }
       
        void setModel(OptionPricingModel model)
        {
                this.model = model;
        }
        
        OptionPricingModel getModel()
        {
        	return this.model;
        }
        
        void setToCall(boolean isCallOption)
        {
                if(model != null)
                        model.setToCall(isCallOption);
        }
       
        void setToEuropean(boolean isEuropeanOption)
        {
                if(model != null)
                        model.setToEuropean(isEuropeanOption);
        }      
       
        void setDayCountConvention(double dayCountConvention)
        {
                this.dayCountConvention = dayCountConvention;
        }
       
        void setVolatility(double volatility)
        {
                this.input.put(OptionPricingModel.VOLATILITY , volatility);
        }
       
        void setInterestRate(double interestRate)
        {
                this.input.put(OptionPricingModel.INTEREST_RATE , interestRate);
        }
       
        void setSrike(double strike)
        {
                this.input.put(OptionPricingModel.STRIKE , strike);
        }
       
        void setUnderlyingPrice(double underlyingPrice)
        {
                this.input.put(OptionPricingModel.UNDERLYING_PRICE , underlyingPrice);
        }
        
        void setDaysToExpiry(double daysToExpiry)
        {
                this.input.put(OptionPricingModel.TIME_TO_EXPIRY , daysToExpiry / dayCountConvention);
        }
       
        void setYearsToExpiry(double yearsToExpiry)
        {
                this.input.put(OptionPricingModel.TIME_TO_EXPIRY , yearsToExpiry);
        }      
               
        OptionPriceResult calculate() throws Exception
        {
                return model.calculate(this.input);            
        }
       
        OptionPriceResultSet calculateRange(String rangeKey, double startValue, double endValue, double increment) throws Exception
        {
                return model.calculateRange(this.input, rangeKey, startValue, endValue, increment);
        }
}
