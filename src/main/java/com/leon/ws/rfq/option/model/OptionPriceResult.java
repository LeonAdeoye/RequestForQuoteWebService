package com.leon.ws.rfq.option.model;

import javax.xml.bind.annotation.XmlRootElement;

import com.leon.ws.rfq.utilities.UtilityMethods;

@XmlRootElement(name="OptionPriceResult")
public final class OptionPriceResult
{
	private double delta;
	private double gamma;
	private double rho;
	private double theta;
	private double vega;
	private double price;
	private double rangeVariable;

	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder("\n Delta=");
		sb.append(this.delta);
		sb.append("\n Gamma=");
		sb.append(this.gamma);
		sb.append("\n Vega=");
		sb.append(this.vega);
		sb.append("\n Theta=");
		sb.append(this.theta);
		sb.append("\n Rho=");
		sb.append(this.rho);
		sb.append("\n Price=");
		sb.append(this.price);
		sb.append("\n Range variable=");
		sb.append(this.rangeVariable);
		return sb.toString();
	}

	@Override
	public boolean equals(Object o)
	{
		if(this == o)
			return true;

		if(!(o instanceof OptionPriceResult))
			return false;

		OptionPriceResult res = (OptionPriceResult) o;

		return (this.delta == res.delta) && (this.gamma == res.gamma) && (this.vega == res.vega)
				&& (this.theta == res.theta) && (this.rho == res.rho) && (this.price == res.price)
				&& (this.rangeVariable == res.rangeVariable);
	}

	@Override
	public int hashCode()
	{
		int result = 17;
		result = (37 * result) + UtilityMethods.doubleHashCode(this.delta);
		result = (37 * result) + UtilityMethods.doubleHashCode(this.gamma);
		result = (37 * result) + UtilityMethods.doubleHashCode(this.vega);
		result = (37 * result) + UtilityMethods.doubleHashCode(this.theta);
		result = (37 * result) + UtilityMethods.doubleHashCode(this.rho);
		result = (37 * result) + UtilityMethods.doubleHashCode(this.price);
		result = (37 * result) + UtilityMethods.doubleHashCode(this.rangeVariable);
		return result;
	}
	
	public void add(OptionPriceResult priceResult)
	{
		this.setDelta(this.getDelta() + priceResult.getDelta());
		this.setGamma(this.getGamma() + priceResult.getGamma());
		this.setVega(this.getVega() + priceResult.getVega());
		this.setTheta(this.getTheta() + priceResult.getTheta());
		this.setRho(this.getRho() + priceResult.getRho());
		this.setPrice(this.getPrice() + priceResult.getPrice());
	}

	public void setDelta(double delta)
	{
		this.delta = delta;
	}

	public double getDelta()
	{
		return this.delta;
	}
	
	public void setRangeVariable(double rangeVariable)
	{
		this.rangeVariable = rangeVariable;
	}

	public double getRangeVariable()
	{
		return this.rangeVariable;
	}

	public void setGamma(double gamma)
	{
		this.gamma = gamma;
	}

	public double getGamma()
	{
		return this.gamma;
	}

	public void setTheta(double theta)
	{
		this.theta = theta;
	}

	public double getTheta()
	{
		return this.theta;
	}

	public void setVega(double vega)
	{
		this.vega = vega;
	}

	public double getVega()
	{
		return this.vega;
	}

	public void setRho(double rho)
	{
		this.rho = rho;
	}

	public double getRho()
	{
		return this.rho;
	}

	public void setPrice(double price)
	{
		this.price = price;
	}

	public double getPrice()
	{
		return this.price;
	}
}
