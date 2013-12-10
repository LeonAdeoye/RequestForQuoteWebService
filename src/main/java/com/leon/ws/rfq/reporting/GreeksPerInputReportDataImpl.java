package com.leon.ws.rfq.reporting;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.leon.ws.rfq.utilities.UtilityMethods;

public class GreeksPerInputReportDataImpl
{
	private static final Logger logger = LoggerFactory.getLogger(GreeksPerInputReportDataImpl.class);
	private double output;
	private double input;

	public GreeksPerInputReportDataImpl(double input, double output)
	{
		this.input = input;
		this.output = output;
		
		logger.debug("GreeksPerInputReportDataImpl object instantiated = > " +  this);
	}

	public GreeksPerInputReportDataImpl() {}

	public double getOutput()
	{
		return this.output;
	}

	public void setOutput(double output)
	{
		this.output = output;
	}
	
	public double getInput()
	{
		return this.input;
	}

	public void setInput(double input)
	{
		this.input = input;
	}
	
	@Override
	public String toString()
	{
		StringBuilder buf = new StringBuilder("Input: ");
		buf.append(this.input);
		buf.append(", Output: ");
		buf.append(this.output);
		return buf.toString();
	}

	@Override
	public boolean equals(Object o)
	{
		if(this == o)
			return true;

		if(!(o instanceof GreeksPerInputReportDataImpl))
			return false;

		GreeksPerInputReportDataImpl param = (GreeksPerInputReportDataImpl) o;

		return 	(this.input == param.input) &&
				(this.output == param.output);
	}

	@Override
	public int hashCode()
	{
		int result = 17;
		result = (37 * result) + UtilityMethods.doubleHashCode(this.input);
		result = (37 * result) + UtilityMethods.doubleHashCode(this.output);
		return result;
	}
}
