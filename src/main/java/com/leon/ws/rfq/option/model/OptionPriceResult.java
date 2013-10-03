package com.leon.ws.rfq.option.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="OptionPriceResult")
public class OptionPriceResult
{
	private double delta;
	private double gamma;
	private double rho;
	private double theta;
	private double vega;
	private double price;
	
	public String toString()
	{
		StringBuffer sb = new StringBuffer();
		sb.append("\nDelta=");
		sb.append(delta);
		sb.append("\nGamma=");
		sb.append(gamma);
		sb.append("\nVega=");
		sb.append(vega);
		sb.append("\nTheta=");
		sb.append(theta);
		sb.append("\nRho=");
		sb.append(rho);
		sb.append("\nPrice=");
		sb.append(price);		
		return sb.toString();
	}
	
	public boolean equals(Object o)
	{
		if(this == o)
			return true;
		
		if(!(o instanceof OptionPriceResult))
			return false;
		
		OptionPriceResult res = (OptionPriceResult) o;
		
		return this.delta == res.delta && this.gamma == res.gamma && this.vega == res.vega 
				&& this.theta == res.theta && this.rho == res.rho && this.price == res.price;  
	}
	
	public int hashCode()
	{
		int result = 17;
		long value = Double.doubleToLongBits(delta);
		result = 37 * result + (int) (value ^ value >>> 32);
		value = Double.doubleToLongBits(gamma);
		result = 37 * result + (int) (value ^ value >>> 32);
		value = Double.doubleToLongBits(theta);
		result = 37 * result + (int) (value ^ value >>> 32); 
		value = Double.doubleToLongBits(vega);
		result = 37 * result + (int) (value ^ value >>> 32); 
		value = Double.doubleToLongBits(rho);
		result = 37 * result + (int) (value ^ value >>> 32);
		value = Double.doubleToLongBits(price);
		result = 37 * result + (int) (value ^ value >>> 32); 		
		return result;
	}	
	
	public void setDelta(double delta)
	{
		this.delta = delta;
	}
	
	public double getDelta()
	{
		return this.delta;
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
