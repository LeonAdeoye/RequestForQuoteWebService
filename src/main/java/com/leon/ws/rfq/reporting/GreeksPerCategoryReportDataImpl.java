package com.leon.ws.rfq.reporting;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.leon.ws.rfq.utilities.UtilityMethods;

public class GreeksPerCategoryReportDataImpl
{
	private static final Logger logger = LoggerFactory.getLogger(GreeksPerCategoryReportDataImpl.class);
	private String categoryValue;
	private double delta;
	private double gamma;
	private double vega;
	private double theta;
	private double rho;

	public GreeksPerCategoryReportDataImpl(String categoryValue, double delta, double gamma, double vega, double theta, double rho)
	{
		this.categoryValue = categoryValue;
		this.delta = delta;
		this.gamma = gamma;
		this.vega = vega;
		this.theta = theta;
		this.rho = rho;
		
		logger.debug("GreeksPerCatgeoryReportDataImpl object instantiated = > " +  this);
	}

	public GreeksPerCategoryReportDataImpl() {}

	public String getCategoryValue()
	{
		return this.categoryValue;
	}

	public void setCategoryValue(String categoryValue)
	{
		this.categoryValue = categoryValue;
	}
	
	public double getDelta()
	{
		return this.delta;
	}

	public void setDelta(double delta)
	{
		this.delta = delta;
	}
	
	public double getGamma()
	{
		return this.gamma;
	}

	public void setGamma(double gamma)
	{
		this.gamma = gamma;
	}
	
	public double getTheta()
	{
		return this.theta;
	}

	public void setTheta(double theta)
	{
		this.theta = theta;
	}
	
	public double getVega()
	{
		return this.vega;
	}

	public void setVega(double vega)
	{
		this.vega = vega;
	}
	
	public double getRho()
	{
		return this.rho;
	}

	public void setRho(double rho)
	{
		this.rho = rho;
	}

	@Override
	public String toString()
	{
		StringBuilder buf = new StringBuilder();
		buf.append("Category value: ");
		buf.append(this.categoryValue);
		buf.append(", Delta: ");
		buf.append(this.delta);
		buf.append(", Gamma: ");
		buf.append(this.gamma);
		buf.append(", Theta: ");
		buf.append(this.theta);
		buf.append(", Vega: ");
		buf.append(this.vega);
		buf.append(", Rho: ");
		buf.append(this.rho);
		return buf.toString();
	}

	@Override
	public boolean equals(Object o)
	{
		if(this == o)
			return true;

		if(!(o instanceof GreeksPerCategoryReportDataImpl))
			return false;

		GreeksPerCategoryReportDataImpl param = (GreeksPerCategoryReportDataImpl) o;

		return 	this.categoryValue.equals(param.categoryValue) &&
				(this.delta == param.delta) &&
				(this.gamma == param.gamma) &&
				(this.vega == param.vega) &&
				(this.theta == param.theta) &&
				(this.rho == param.rho);
	}

	@Override
	public int hashCode()
	{
		int result = 17;
		result = (37 * result) + (this.categoryValue == null ? 0 : this.categoryValue.hashCode());
		result = (37 * result) + UtilityMethods.doubleHashCode(this.delta);
		result = (37 * result) + UtilityMethods.doubleHashCode(this.gamma);
		result = (37 * result) + UtilityMethods.doubleHashCode(this.theta);
		result = (37 * result) + UtilityMethods.doubleHashCode(this.vega);
		result = (37 * result) + UtilityMethods.doubleHashCode(this.rho);
		return result;
	}
}
