package com.leon.ws.rfq.option.model;
import java.util.Map;
 
public interface OptionPricingModel
{
        public static final String DELTA = "DELTA";    
        public static final String GAMMA = "GAMMA";
        public static final String VEGA = "VEGA";
        public static final String THETA = "THETA";
        public static final String RHO = "RHO";
        public static final  String PRICE = "PRICE";
       
        public static final String VOLATILITY = "VOLATILITY";
        public static final String INTEREST_RATE = "INTEREST_RATE";
        public static final String UNDERLYING_PRICE = "UNDERLYING_PRICE";
        public static final String STRIKE = "STRIKE";
        public static final  String TIME_TO_EXPIRY = "TIME_TO_EXPIRY";
             
        public OptionPriceResult calculate(Map<String, Double> input) throws Exception;
        public OptionPriceResultSet calculateRange(Map<String, Double> input, String rangeKey, double startValue, double endValue, double increment) throws Exception;
        public void setToCall(boolean isCallOption);
        public void setToEuropean(boolean isEuropeanOption);
}