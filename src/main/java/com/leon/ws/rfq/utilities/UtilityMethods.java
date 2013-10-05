package com.leon.ws.rfq.utilities;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UtilityMethods
{
	private static Logger logger = LoggerFactory.getLogger(UtilityMethods.class);
	
    public static java.sql.Date convertToDate(String dateToConvert) 
    {
        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
        try
        {
	        Date parsed = format.parse(dateToConvert);        
	        return new java.sql.Date(parsed.getTime());
        }
        catch(ParseException pe)
        {
        	if(logger.isErrorEnabled())
        		logger.error("Failed to convert string " + dateToConvert + " to instance of java.sql.Date. Exception thrown:", pe);
        }
        return null;
    }
}
