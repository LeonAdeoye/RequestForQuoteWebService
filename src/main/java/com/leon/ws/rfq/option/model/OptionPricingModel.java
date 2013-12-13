package com.leon.ws.rfq.option.model;
import java.util.Map;
 
public interface OptionPricingModel
{
        static final String DELTA = "DELTA";
        static final String GAMMA = "GAMMA";
        static final String VEGA = "VEGA";
        static final String THETA = "THETA";
        static final String RHO = "RHO";
        static final  String PRICE = "PRICE";
       
        static final String VOLATILITY = "VOLATILITY";
        static final String INTEREST_RATE = "INTEREST_RATE";
        static final String UNDERLYING_PRICE = "UNDERLYING_PRICE";
        static final String STRIKE = "STRIKE";
        static final  String TIME_TO_EXPIRY = "TIME_TO_EXPIRY";
             
        OptionPriceResult calculate(Map<String, Double> input) throws Exception;
        
        ExtrapolationSet calculateRange(Map<String, Double> input, String rangeKey, double startValue,
        		double endValue, double increment) throws Exception;
        
        void setToCall(boolean isCallOption);
        
        void setToEuropean(boolean isEuropeanOption);
}