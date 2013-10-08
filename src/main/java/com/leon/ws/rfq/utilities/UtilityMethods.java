package com.leon.ws.rfq.utilities;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class UtilityMethods
{
	private static final Logger logger = LoggerFactory.getLogger(UtilityMethods.class);
	private final static DateFormat MMddyyyyFormat = new SimpleDateFormat("MM/dd/yyyy");
	
	/**
	 * Converts a string date with a format of MM/dd/yyyy to an instance of java.sql.Date.
	 *
	 * @param  	dateToConvert 				the string to convert into a java.sql.Date instance.
	 * @throws  IllegalArgumentException	if dateToConvert string is empty.
	 * @return 								the java.sql.Date if it successfully converts the string otherwise null.
	 */		
    public static java.sql.Date convertToDate(String dateToConvert) 
    {
    	if(dateToConvert.isEmpty())
    		throw new IllegalArgumentException("dateToConvert");
    	
        try
        {
	        java.util.Date parsed = MMddyyyyFormat.parse(dateToConvert);        
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

